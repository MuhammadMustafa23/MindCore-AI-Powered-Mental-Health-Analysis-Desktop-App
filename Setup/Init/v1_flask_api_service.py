"""
Mental Health Analysis API Service
Production-ready Flask API for serving ML predictions

Model Details:
- Algorithm: Logistic Regression (Multi-class)
- Classes: 7 mental health conditions
- Accuracy: 85%
- Vocabulary: 5,000 TF-IDF features with bigrams
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

# Severity levels based on class
SEVERITY_MAP = {
    'Normal': 'Low',
    'Stress': 'Moderate',
    'Anxiety': 'Moderate',
    'Depression': 'High',
    'Bipolar': 'High',
    'Personality disorder': 'High',
    'Suicidal': 'Critical'
}

# Color codes for UI (JavaFX can use these)
COLOR_MAP = {
    'Normal': '#4CAF50',        # Green
    'Stress': '#FF9800',        # Orange
    'Anxiety': '#FF9800',       # Orange
    'Depression': '#F44336',    # Red
    'Bipolar': '#F44336',       # Red
    'Personality disorder': '#F44336',  # Red
    'Suicidal': '#9C27B0'       # Purple (Critical)
}


def load_model():
    """Load the trained model and vectorizer"""
    global model, vectorizer
    
    try:
        logger.info("Loading model and vectorizer...")
        
        # Adjust paths as needed
        model_path = 'mental_health_model.pkl'
        vectorizer_path = 'tfidf_vectorizer.pkl'
        
        model = joblib.load(model_path)
        vectorizer = joblib.load(vectorizer_path)
        
        logger.info(f"✅ Model loaded successfully: {type(model).__name__}")
        logger.info(f"✅ Vectorizer loaded successfully: {type(vectorizer).__name__}")
        logger.info(f"📊 Number of classes: {len(model.classes_)}")
        logger.info(f"📚 Vocabulary size: {len(vectorizer.vocabulary_)}")
        
        return True
        
    except FileNotFoundError as e:
        logger.error(f"❌ Model files not found: {e}")
        return False
    except Exception as e:
        logger.error(f"❌ Error loading model: {e}")
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
            '/health': 'Health check',
            '/predict': 'POST - Get mental health prediction',
            '/batch_predict': 'POST - Batch predictions',
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
    if model is None or vectorizer is None:
        return jsonify({'error': 'Model not loaded'}), 503
    
    return jsonify({
        'model_type': type(model).__name__,
        'classes': CLASSES,
        'num_classes': len(CLASSES),
        'vocabulary_size': len(vectorizer.vocabulary_),
        'ngram_range': vectorizer.ngram_range,
        'max_features': vectorizer.max_features,
        'accuracy': '85%',
        'severity_levels': SEVERITY_MAP
    })


@app.route('/predict', methods=['POST'])
def predict():
    """
    Main prediction endpoint
    
    Request JSON:
    {
        "text": "I feel anxious and stressed",
        "include_probabilities": true,  // optional
        "top_n": 3  // optional, default 3
    }
    
    Response JSON:
    {
        "prediction": "Stress",
        "confidence": 0.78,
        "severity": "Moderate",
        "color": "#FF9800",
        "probabilities": {...},
        "top_predictions": [...],
        "timestamp": "2026-01-29T..."
    }
    """
    try:
        # Validate model is loaded
        if model is None or vectorizer is None:
            return jsonify({'error': 'Model not loaded'}), 503
        
        # Get request data
        data = request.get_json()
        
        if not data:
            return jsonify({'error': 'No JSON data provided'}), 400
        
        text = data.get('text', '').strip()
        
        if not text:
            return jsonify({'error': 'No text provided or text is empty'}), 400
        
        # Configuration
        include_probabilities = data.get('include_probabilities', True)
        top_n = min(data.get('top_n', 3), len(CLASSES))
        
        logger.info(f"Prediction request: '{text[:50]}...'")
        
        # Vectorize the input text
        text_vectorized = vectorizer.transform([text])
        
        # Get prediction
        prediction = model.predict(text_vectorized)[0]
        probabilities = model.predict_proba(text_vectorized)[0]
        
        # Get confidence (probability of predicted class)
        class_index = list(model.classes_).index(prediction)
        confidence = float(probabilities[class_index])
        
        # Get top N predictions
        top_indices = np.argsort(probabilities)[-top_n:][::-1]
        top_predictions = [
            {
                'class': model.classes_[idx],
                'probability': float(probabilities[idx]),
                'severity': SEVERITY_MAP.get(model.classes_[idx], 'Unknown'),
                'color': COLOR_MAP.get(model.classes_[idx], '#757575')
            }
            for idx in top_indices
        ]
        
        # Build response
        response = {
            'success': True,
            'prediction': prediction,
            'confidence': round(confidence, 4),
            'severity': SEVERITY_MAP.get(prediction, 'Unknown'),
            'color': COLOR_MAP.get(prediction, '#757575'),
            'top_predictions': top_predictions,
            'timestamp': datetime.now().isoformat()
        }
        
        # Add full probabilities if requested
        if include_probabilities:
            response['probabilities'] = {
                class_name: round(float(prob), 4)
                for class_name, prob in zip(model.classes_, probabilities)
            }
        
        logger.info(f"✅ Prediction: {prediction} (confidence: {confidence:.2%})")
        
        return jsonify(response), 200
        
    except Exception as e:
        logger.error(f"❌ Prediction error: {e}")
        return jsonify({
            'success': False,
            'error': str(e),
            'timestamp': datetime.now().isoformat()
        }), 500


@app.route('/batch_predict', methods=['POST'])
def batch_predict():
    """
    Batch prediction endpoint for multiple texts
    
    Request JSON:
    {
        "texts": ["text1", "text2", "text3"],
        "include_probabilities": false
    }
    
    Response JSON:
    {
        "results": [...],
        "count": 3
    }
    """
    try:
        if model is None or vectorizer is None:
            return jsonify({'error': 'Model not loaded'}), 503
        
        data = request.get_json()
        
        if not data:
            return jsonify({'error': 'No JSON data provided'}), 400
        
        texts = data.get('texts', [])
        
        if not texts or not isinstance(texts, list):
            return jsonify({'error': 'Invalid or empty texts array'}), 400
        
        include_probabilities = data.get('include_probabilities', False)
        
        logger.info(f"Batch prediction request: {len(texts)} texts")
        
        # Vectorize all texts
        texts_vectorized = vectorizer.transform(texts)
        
        # Get predictions
        predictions = model.predict(texts_vectorized)
        probabilities = model.predict_proba(texts_vectorized)
        
        # Build results
        results = []
        for i, (pred, probs) in enumerate(zip(predictions, probabilities)):
            class_index = list(model.classes_).index(pred)
            confidence = float(probs[class_index])
            
            result = {
                'index': i,
                'text_preview': texts[i][:50] + ('...' if len(texts[i]) > 50 else ''),
                'prediction': pred,
                'confidence': round(confidence, 4),
                'severity': SEVERITY_MAP.get(pred, 'Unknown'),
                'color': COLOR_MAP.get(pred, '#757575')
            }
            
            if include_probabilities:
                result['probabilities'] = {
                    class_name: round(float(prob), 4)
                    for class_name, prob in zip(model.classes_, probs)
                }
            
            results.append(result)
        
        logger.info(f"✅ Batch prediction complete: {len(results)} results")
        
        return jsonify({
            'success': True,
            'results': results,
            'count': len(results),
            'timestamp': datetime.now().isoformat()
        }), 200
        
    except Exception as e:
        logger.error(f"❌ Batch prediction error: {e}")
        return jsonify({
            'success': False,
            'error': str(e)
        }), 500


@app.errorhandler(404)
def not_found(error):
    """Handle 404 errors"""
    return jsonify({
        'error': 'Endpoint not found',
        'message': 'Please check the API documentation'
    }), 404


@app.errorhandler(500)
def internal_error(error):
    """Handle 500 errors"""
    return jsonify({
        'error': 'Internal server error',
        'message': str(error)
    }), 500


if __name__ == '__main__':
    # Load model on startup
    if load_model():
        logger.info("=" * 60)
        logger.info("🚀 Starting Mental Health Analysis API Server")
        logger.info("=" * 60)
        logger.info(f"📊 Classes: {', '.join(CLASSES)}")
        logger.info(f"🌐 Server starting on http://0.0.0.0:5000")
        logger.info("=" * 60)
        
        # Run the Flask app
        app.run(
            host='0.0.0.0',
            port=5000,
            debug=True,  # Set to False in production
            threaded=True
        )
    else:
        logger.error("Failed to load model. Exiting...")
