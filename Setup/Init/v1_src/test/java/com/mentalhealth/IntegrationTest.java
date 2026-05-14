package com.mentalhealth;

import com.mentalhealth.model.*;
import com.mentalhealth.repository.*;
import com.mentalhealth.service.*;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests to verify all components work together
 * Run these every Friday during integration
 */
public class IntegrationTest {
    
    private IUserRepository userRepo;
    private IPredictionRepository predictionRepo;
    private IAuthService authService;
    private IMLService mlService;
    
    @BeforeEach
    void setup() {
        // Use real implementations
        userRepo = new UserRepository();
        predictionRepo = new PredictionRepository();
        authService = new AuthService();
        mlService = new MLAPIService();
    }
    
    @Test
    @DisplayName("Test 1: User registration and login flow")
    void testUserRegistrationAndLogin() {
        // Register
        Optional<User> registered = authService.register(
            "testuser", "password123", "Test User"
        );
        assertTrue(registered.isPresent(), "Registration should succeed");
        
        // Login
        Optional<User> loggedIn = authService.login("testuser", "password123");
        assertTrue(loggedIn.isPresent(), "Login should succeed");
        assertEquals("Test User", loggedIn.get().getName());
    }
    
    @Test
    @DisplayName("Test 2: ML API health check")
    void testMLAPIHealth() {
        boolean healthy = mlService.isHealthy();
        assertTrue(healthy, "ML API should be healthy (is Flask running?)");
    }
    
    @Test
    @DisplayName("Test 3: Full prediction flow")
    void testPredictionFlow() throws Exception {
        // 1. Create user
        User user = new User("preduser", "hash", "Pred User");
        user = userRepo.save(user);
        assertNotNull(user.getId());
        
        // 2. Call ML API
        ApiResponse response = mlService.predict(
            "I feel very stressed and anxious about my exams"
        );
        assertTrue(response.isSuccess());
        assertNotNull(response.getPrediction());
        assertTrue(response.getConfidence() > 0);
        
        // 3. Save prediction to database
        Prediction prediction = new Prediction(
            user.getId(),
            "I feel very stressed...",
            response.getPrediction(),
            response.getConfidence(),
            response.getSeverity()
        );
        prediction = predictionRepo.save(prediction);
        assertNotNull(prediction.getId());
        
        // 4. Retrieve prediction
        List<Prediction> history = predictionRepo.findByUserId(user.getId());
        assertEquals(1, history.size());
        assertEquals(response.getPrediction(), history.get(0).getPrediction());
    }
    
    @Test
    @DisplayName("Test 4: Database persistence")
    void testDatabasePersistence() {
        // Save user
        User user = new User("dbtest", "hash", "DB Test");
        User saved = userRepo.save(user);
        
        // Retrieve and verify
        Optional<User> found = userRepo.findById(saved.getId());
        assertTrue(found.isPresent());
        assertEquals("DB Test", found.get().getName());
    }
}