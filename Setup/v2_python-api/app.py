"""
Mental Health Analysis API Service
Flask API for serving ML predictions
"""

from flask import Flask, request, jsonify
from flask_cors import CORS
import joblib
import numpy as np
from datetime import datetime
import logging
import os

# Configure logging
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s - %(name)s - %(levelname)s - %(message)s'
)
logger = logging.getLogger(__name__)

# Initialize Flask app
app = Flask(__name__)
CORS(app)  # Enable CORS for JavaFX client

# Global variables for model and vectorizer
model = None
vectorizer = None

# Mental health classes
CLASSES = ['Anxiety', 'Bipolar', 'Depression', 'Normal', 
           'Personality disorder', 'Stress', 'Suicidal']

# Severity mapping
SEVERITY_MAP = {
    'Normal': 'Low',
    'Stress': 'Moderate',
    'Anxiety': 'Moderate',
    'Depression': 'High',
    'Bipolar': 'High',
    'Personality disorder': 'High',
    'Suicidal': 'Critical'
}

# Color codes for UI
COLOR_MAP = {
    'Normal': '#4CAF50',
    'Stress': '#FF9800',
    'Anxiety': '#FF9800',
    'Depression': '#F44336',
    'Bipolar': '#F44336',
    'Personality disorder': '#F44336',
    'Suicidal': '#9C27B0'
}


def load_model():
    """Load the trained model and vectorizer"""
    global model, vectorizer
    
    try:
        logger.info("Loading model and vectorizer...")
        
        model_path = 'mental_health_model.pkl'
        vectorizer_path = 'tfidf_vectorizer.pkl'
        
        # Check if files exist
        if not os.path.exists(model_path):
            logger.warning(f"Model file not found: {model_path}")
            logger.info("API will run in demo mode with mock predictions")
            return False
            
        if not os.path.exists(vectorizer_path):
            logger.warning(f"Vectorizer file not found: {vectorizer_path}")
            logger.info("API will run in demo mode with mock predictions")
            return False
        
        model = joblib.load(model_path)
        vectorizer = joblib.load(vectorizer_path)
        
        logger.info(f"✅ Model loaded: {type(model).__name__}")
        logger.info(f"✅ Vectorizer loaded: {len(vectorizer.vocabulary_)} features")
        
        return True
        
    except Exception as e:
        logger.error(f"❌ Error loading model: {e}")
        logger.info("API will run in demo mode with mock predictions")
        return False


@app.route('/', methods=['GET'])
def home():
    """API information endpoint"""
    return jsonify({
        'service': 'Mental Health Analysis API',
        'version': '1.0.0',
        'status': 'running',
        'model_loaded': model is not None,
        'endpoints': {
            '/': 'API info',
            '/health': 'Health check',
            '/predict': 'POST - Get mental health prediction',
            '/model_info': 'GET - Model information'
        }
    })


@app.route('/health', methods=['GET'])
def health_check():
    """Health check endpoint"""
    return jsonify({
        'status': 'healthy',
        'model_loaded': model is not None,
        'vectorizer_loaded': vectorizer is not None,
        'timestamp': datetime.now().isoformat()
    })


@app.route('/model_info', methods=['GET'])
def model_info():
    """Get model information"""
    return jsonify({
        'model_type': type(model).__name__ if model else 'Not loaded',
        'classes': CLASSES,
        'num_classes': len(CLASSES),
        'vocabulary_size': len(vectorizer.vocabulary_) if vectorizer else 0,
        'severity_levels': SEVERITY_MAP
    })


@app.route('/predict', methods=['POST'])
def predict():
    """
    Main prediction endpoint
    
    Request JSON: {"text": "I feel anxious and stressed"}
    
    Response JSON: {
        "success": true,
        "prediction": "Anxiety",
        "confidence": 0.82,
        "severity": "Moderate",
        "color": "#FF9800"
    }
    """
    try:
        # Get request data
        data = request.get_json()
        
        if not data:
            return jsonify({'success': False, 'error': 'No JSON data provided'}), 400
        
        text = data.get('text', '').strip()
        
        if not text:
            return jsonify({'success': False, 'error': 'No text provided'}), 400
        
        if len(text) < 3:
            return jsonify({'success': False, 'error': 'Text too short'}), 400
        
        logger.info(f"Prediction request: '{text[:50]}...'")
        
        # If model is loaded, use it
        if model is not None and vectorizer is not None:
            # Vectorize the input text
            text_vectorized = vectorizer.transform([text])
            
            # Get prediction
            prediction = model.predict(text_vectorized)[0]
            probabilities = model.predict_proba(text_vectorized)[0]
            
            # Get confidence
            class_index = list(model.classes_).index(prediction)
            confidence = float(probabilities[class_index])
            
            # Get top 3 predictions
            top_indices = np.argsort(probabilities)[-3:][::-1]
            top_predictions = [
                {
                    'class': model.classes_[idx],
                    'probability': float(probabilities[idx]),
                    'severity': SEVERITY_MAP.get(model.classes_[idx], 'Unknown'),
                    'color': COLOR_MAP.get(model.classes_[idx], '#757575')
                }
                for idx in top_indices
            ]
        else:
            # Demo mode - return mock prediction based on keywords
            prediction, confidence = get_mock_prediction(text)
            top_predictions = [
                {
                    'class': prediction,
                    'probability': confidence,
                    'severity': SEVERITY_MAP.get(prediction, 'Unknown'),
                    'color': COLOR_MAP.get(prediction, '#757575')
                }
            ]
        
        # Build response
        response = {
            'success': True,
            'prediction': prediction,
            'confidence': round(confidence, 4),
            'severity': SEVERITY_MAP.get(prediction, 'Unknown'),
            'color': COLOR_MAP.get(prediction, '#757575'),
            'top_predictions': top_predictions,
            'timestamp': datetime.now().isoformat(),
            'demo_mode': model is None
        }
        
        logger.info(f"✅ Prediction: {prediction} ({confidence:.2%})")
        
        return jsonify(response), 200
        
    except Exception as e:
        logger.error(f"❌ Prediction error: {e}")
        return jsonify({
            'success': False,
            'error': str(e),
            'timestamp': datetime.now().isoformat()
        }), 500


def get_mock_prediction(text):
    """
    Generate mock prediction based on keywords (for demo mode)
    """
    text_lower = text.lower()
    
    if any(word in text_lower for word in ['suicide', 'suicidal', 'kill myself', 'end my life']):
        return 'Suicidal', 0.85
    elif any(word in text_lower for word in ['depressed', 'depression', 'hopeless', 'worthless']):
        return 'Depression', 0.78
    elif any(word in text_lower for word in ['anxious', 'anxiety', 'worried', 'panic', 'nervous']):
        return 'Anxiety', 0.75
    elif any(word in text_lower for word in ['stressed', 'stress', 'overwhelmed', 'pressure']):
        return 'Stress', 0.72
    elif any(word in text_lower for word in ['manic', 'bipolar', 'mood swings']):
        return 'Bipolar', 0.70
    elif any(word in text_lower for word in ['happy', 'good', 'great', 'fine', 'okay']):
        return 'Normal', 0.80
    else:
        return 'Stress', 0.55


@app.errorhandler(404)
def not_found(error):
    return jsonify({'error': 'Endpoint not found'}), 404


@app.errorhandler(500)
def internal_error(error):
    return jsonify({'error': 'Internal server error'}),