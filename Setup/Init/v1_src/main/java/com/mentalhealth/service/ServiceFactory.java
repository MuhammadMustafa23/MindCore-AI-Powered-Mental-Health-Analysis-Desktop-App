package com.mentalhealth.service;

/**
 * Factory to get service implementations
 * 
 * Week 1: Returns mock services
 * Week 2+: Returns real implementations
 * 
 * INTEGRATION POINT: Change these when real implementations are ready
 */
public class ServiceFactory {
    
    // Set to true when Person C's implementation is ready
    private static final boolean USE_REAL_AUTH = false;
    
    // Set to true when Person B's implementation is ready
    private static final boolean USE_REAL_ML = false;
    
    private static IAuthService authService;
    private static IMLService mlService;
    
    public static IAuthService getAuthService() {
        if (authService == null) {
            if (USE_REAL_AUTH) {
                authService = new AuthService();  // Person C's implementation
            } else {
                authService = new MockAuthService();
            }
        }
        return authService;
    }
    
    public static IMLService getMLService() {
        if (mlService == null) {
            if (USE_REAL_ML) {
                mlService = new MLAPIService();  // Person B's implementation
            } else {
                mlService = new MockMLService();
            }
        }
        return mlService;
    }
    
    // Call this to reset services (useful for testing)
    public static void reset() {
        authService = null;
        mlService = null;
    }
}