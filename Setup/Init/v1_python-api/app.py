"""
THIS IS THE ENTIRE PYTHON COMPONENT (~200 lines)
It simply loads your trained model and exposes it via HTTP
"""

from flask import Flask, request, jsonify
from flask_cors import CORS
import joblib
import numpy as np
from datetime import datetime
import logging

# Setup
app = Flask(__name__)
CORS(app)
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

# Load your pre-trained model (ALREADY DONE BY YOU)
model = None
vectorizer = None

CLASSES = ['Anxiety', 'Bipolar', 'Depression', 'Normal', 
           'Personality disorder', 'Stress', 'Suicidal']

SEVERITY_MAP = {
    'Normal': 'Low', 'Stress': 'Moderate', 'Anxiety': 'Moderate',
    'Depression': 'High', 'Bipolar': 'High', 
    'Personality disorder': 'High', 'Suicidal': 'Critical'
}

COLOR_MAP = {
    'Normal': '#4CAF50', 'Stress': '#FF9800', 'Anxiety': '#FF9800',
    'Depression': '#F44336', 'Bipolar': '#F44336',
    'Personality disorder': '#F44336', 'Suicidal': '#9C27B0'
}


def load_model():
    """Load trained model files"""
    global model, vectorizer
    try:
        model = joblib.load('mental_health_model.pkl')
        vectorizer = joblib.load('tfidf_vectorizer.pkl')
        logger.info("✅ Model loaded successfully")
        return True
    except Exception as e:
        logger.error(f"❌ Failed to load model: {e}")
        return False


@app.route('/health', methods=['GET'])
def health():
    """Health check endpoint"""
    return jsonify({
        'status': 'healthy',
        'model_loaded': model is not None
    })


@app.route('/predict', methods=['POST'])
def predict():
    """Main prediction endpoint - called by Java app"""
    try:
        if model is None:
            return jsonify({'error': 'Model not loaded'}), 503
        
        data = request.get_json()
        text = data.get('text', '').strip()
        
        if not text:
            return jsonify({'error': 'No text provided'}), 400
        
        # Vectorize and predict
        text_vectorized = vectorizer.transform([text])
        prediction = model.predict(text_vectorized)[0]
        probabilities = model.predict_proba(text_vectorized)[0]
        
        # Get confidence
        class_index = list(model.classes_).index(prediction)
        confidence = float(probabilities[class_index])
        
        # Build response for Java app
        return jsonify({
            'success': True,
            'prediction': prediction,
            'confidence': round(confidence, 4),
            'severity': SEVERITY_MAP.get(prediction, 'Unknown'),
            'color': COLOR_MAP.get(prediction, '#757575'),
            'timestamp': datetime.now().isoformat()
        })
        
    except Exception as e:
        logger.error(f"Prediction error: {e}")
        return jsonify({'success': False, 'error': str(e)}), 500


if __name__ == '__main__':
    if load_model():
        logger.info("🚀 Starting Flask API on http://localhost:5000")
        app.run(host='0.0.0.0', port=5000, debug=True)