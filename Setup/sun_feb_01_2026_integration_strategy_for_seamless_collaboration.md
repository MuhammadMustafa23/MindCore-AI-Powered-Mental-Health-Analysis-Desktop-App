# Copilot Chat Conversation Export: # Integration strategy for seamless collaboration

**User:** @MuhammadMustafa23


## @MuhammadMustafa23

Mental Health Analysis System
Complete Architecture & Project Documentation
Java Desktop Application + Python ML Model (Flask API)
1. Project Overview
This project is a mental health analysis system designed as a desktop-based application developed solely in
Java, integrated with a machine learning model trained in Python. The system analyzes user-written text such
as journals, mood notes, or self-reflections and predicts mental health conditions using a trained Natural
Language Processing (NLP) model. The integration is achieved using a RESTful Flask API, following
real-world microservice architecture principles.
2. Complete System Architecture
The system follows a layered and service-oriented architecture where the Java desktop client communicates
with a Python-based ML service over HTTP. This ensures modularity, scalability, and maintainability.
JAVA DESKTOP APPLICATION (JavaFX / Swing) ------------------------------------------------ • UI Layer
(Dashboard, Journal, Analytics) • Controller Layer • HTTP Client Module • Local Data Storage (Encrypted) | |
REST API (JSON over HTTP) v FLASK API SERVICE (Python) ------------------------------------------------ • Flask
Web Server • REST Controllers • Input Validation • Logging & Error Handling • Security (CORS) | v ML
PIPELINE ------------------------------------------------ • TF-IDF Vectorizer (Bigrams, 5000 features) • Logistic
Regression Classifier • 7 Mental Health Classes • Probability & Severity Mapping • Color Coding for UI
3. Java Desktop Application Layer
The Java desktop application acts as the user-facing component of the system. It is responsible for user
interaction, data collection, visualization, and secure local storage. The application never directly accesses the
machine learning model, ensuring data abstraction and security.
• User Profile Management (Age group, goals, baseline mood)
• Mood Tracking & Journaling Interface
• Analytics Dashboard (Trends, weekly summaries)
• HTTP Client for Flask API communication
• AES Encrypted Local Storage
4. Flask API Service Layer
The Flask API serves as the middleware between the Java client and the machine learning model. It exposes
REST endpoints that accept text input and return structured prediction results in JSON format.
• Endpoint: /predict – Single text prediction
• Endpoint: /batch_predict – Multiple text predictions
• Endpoint: /model_info – Model metadata
• Endpoint: /health – Service health check
• CORS Enabled for JavaFX Client
5. Machine Learning Model Details
The machine learning component is trained in Python using classical NLP techniques optimized for
explainability and academic evaluation. The model is loaded into memory when the Flask service starts.
• Algorithm: Logistic Regression (Multi-class)
• Vectorization: TF-IDF with Bigrams
• Vocabulary Size: 5,000 features
• Classes: Anxiety, Bipolar, Depression, Normal, Personality Disorder, Stress, Suicidal
• Accuracy: ~85%
6. End-to-End Prediction Flow
1. User enters journal text in Java application
2. Java client sends POST request to Flask /predict endpoint
3. Flask validates and vectorizes text
4. Model predicts class probabilities
5. Severity and UI color mapping applied
6. JSON response returned to Java
7. Result displayed with confidence and recommendations
7. Security, Privacy & Ethical Considerations
• No raw mental health data is sent to third-party services
• Model runs locally on secured server or localhost
• User data encrypted at rest
• Predictions are advisory, not diagnostic
• Critical cases prompt help resources instead of automation
8. Academic & Industry Relevance
This architecture demonstrates strong software engineering practices including separation of concerns,
microservices integration, RESTful API design, applied machine learning, and ethical system design. It is
aligned with real-world healthcare analytics systems and is suitable for Final Year Project evaluation.


This is the entire project we are building solely on java for our sda project , timeline is 1 month and we are a group of 3 , i already have the trained model in python along with its pkl file and tfidf vectorizer pkl file with me which we will integrate in java through flask api , now what you have to do is analyse this project identify any loopholes or things of improvement , then separately provide us step by step detailed guidance on how to complete this efficiently and divide the work amongst the 3 members such that no one's work is overlapping with one another so no one is bound by other member , also list all the features from start to end which we will impliment in this and  then also separately explain and guide in detail what frameworks will be used in this project and guide their installation and setup for vs code so we can set it up before development

## Copilot

# Comprehensive Project Analysis & Implementation Guide

## 📋 Table of Contents
1. [Project Analysis & Loopholes](#1-project-analysis--loopholes)
2. [Complete Feature List](#2-complete-feature-list)
3. [Team Work Division](#3-team-work-division)
4. [Framework Setup Guide](#4-framework-setup-guide)
5. [Step-by-Step Implementation](#5-step-by-step-implementation)

---

## 1. Project Analysis & Loopholes

### ✅ Strengths of Current Design
1. **ML Model Ready** - 85% accuracy, 7 classes, well-documented
2. **Clear Architecture** - Separation between Java frontend and Python API
3. **Realistic Scope** - Achievable in 4 weeks with 3 people
4. **Good Documentation** - Detailed technical specs provided

### ⚠️ Identified Loopholes & Issues

#### **Architecture Issues**

| Issue | Problem | Solution |
|-------|---------|----------|
| **No Offline Mode** | App fails completely if Flask API is down | Add local caching + graceful degradation |
| **Single Point of Failure** | Flask server crash = entire app unusable | Add retry logic + connection pooling |
| **No API Authentication** | Anyone can access your ML API | Add API key authentication |
| **Missing Rate Limiting** | API vulnerable to abuse | Implement request throttling |

#### **Database Issues**

| Issue | Problem | Solution |
|-------|---------|----------|
| **No Migration Strategy** | Schema changes break existing data | Use version-controlled migrations |
| **Missing Indexes** | Slow queries on large datasets | Add indexes on `user_id`, `created_at` |
| **No Backup Mechanism** | Data loss risk | Auto-backup to local folder |

#### **Security Gaps**

| Issue | Problem | Solution |
|-------|---------|----------|
| **Plain Text Storage** | Sensitive journal entries exposed | Encrypt `input_text` field |
| **No Session Management** | Users stay logged in forever | Add session timeout |
| **API Key Hardcoded** | Security vulnerability | Use environment variables |

#### **UX Problems**

| Issue | Problem | Solution |
|-------|---------|----------|
| **No Loading States** | User doesn't know if app is working | Add spinners/progress indicators |
| **No Input Validation** | Empty or very short text submitted | Minimum character requirements |
| **Missing Error Messages** | Generic errors confuse users | Specific, actionable error messages |

#### **Missing Features (Critical)**

| Feature | Why It's Needed |
|---------|-----------------|
| **Prediction History View** | Users can't review past analyses |
| **Export Data** | No way to backup or share data |
| **Settings Screen** | No user preferences management |
| **About/Help Screen** | Missing app information |

---

### 🔧 Recommended Improvements

#### 1. Add API Health Monitoring
```java
// Check API before making requests
public boolean checkAPIHealth() {
    try {
        HttpResponse<String> response = httpClient.send(
            HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/health"))
                .timeout(Duration.ofSeconds(3))
                .GET()
                .build(),
            HttpResponse.BodyHandlers.ofString()
        );
        return response.statusCode() == 200;
    } catch (Exception e) {
        return false;
    }
}
```

#### 2. Add Request Retry Logic
```java
public PredictionResponse predictWithRetry(String text, int maxRetries) {
    for (int i = 0; i < maxRetries; i++) {
        try {
            return predict(text);
        } catch (Exception e) {
            if (i == maxRetries - 1) throw e;
            Thread.sleep(1000 * (i + 1)); // Exponential backoff
        }
    }
    return null;
}
```

#### 3. Add API Key Authentication (Flask)
```python
from functools import wraps

API_KEY = os.environ.get('ML_API_KEY', 'your-secret-key')

def require_api_key(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        key = request.headers.get('X-API-Key')
        if key != API_KEY:
            return jsonify({'error': 'Invalid API key'}), 401
        return f(*args, **kwargs)
    return decorated

@app.route('/predict', methods=['POST'])
@require_api_key
def predict():
    # ... existing code
```

---

## 2. Complete Feature List

### 🎯 Core Features (Must Have - Week 1-2)

| # | Feature | Description | Priority |
|---|---------|-------------|----------|
| 1 | **User Registration/Login** | Create account, login, logout | CRITICAL |
| 2 | **User Profile Management** | Edit name, age, goals, baseline stress | CRITICAL |
| 3 | **Text Analysis Screen** | Input text, submit for prediction | CRITICAL |
| 4 | **Prediction Results Display** | Show prediction, confidence, severity, color | CRITICAL |
| 5 | **Flask API Integration** | HTTP client calling Python ML service | CRITICAL |
| 6 | **SQLite Database** | Store users, predictions, mood entries | CRITICAL |
| 7 | **Basic Navigation** | Menu/sidebar to switch between screens | CRITICAL |

### 📊 Analytics Features (Should Have - Week 2-3)

| # | Feature | Description | Priority |
|---|---------|-------------|----------|
| 8 | **Mood Tracking** | Daily mood score entry (1-10) with notes | HIGH |
| 9 | **Prediction History** | View all past predictions with filters | HIGH |
| 10 | **Mood Trend Chart** | Line chart showing mood over time | HIGH |
| 11 | **Weekly Summary** | Aggregate stats for the week | MEDIUM |
| 12 | **Condition Distribution** | Pie/bar chart of prediction types | MEDIUM |

### 💡 Recommendation Features (Should Have - Week 3)

| # | Feature | Description | Priority |
|---|---------|-------------|----------|
| 13 | **Personalized Recommendations** | Tips based on prediction result | HIGH |
| 14 | **Crisis Alert System** | Emergency resources for "Suicidal" predictions | CRITICAL |
| 15 | **Resource Library** | Static mental health resources | MEDIUM |

### ⚙️ Utility Features (Nice to Have - Week 3-4)

| # | Feature | Description | Priority |
|---|---------|-------------|----------|
| 16 | **Reminder System** | Scheduled notifications for journaling | LOW |
| 17 | **Data Export** | Export history to CSV/PDF | LOW |
| 18 | **Settings Screen** | App preferences, theme, API config | MEDIUM |
| 19 | **OCR Integration** | Extract text from images | LOW |
| 20 | **Data Encryption** | AES encryption for sensitive data | MEDIUM |

### 🧪 Quality Features (Week 4)

| # | Feature | Description | Priority |
|---|---------|-------------|----------|
| 21 | **Input Validation** | Character limits, empty check | HIGH |
| 22 | **Error Handling** | Graceful error messages | HIGH |
| 23 | **Loading Indicators** | Spinners during API calls | MEDIUM |
| 24 | **Help/About Screen** | App info, crisis resources | LOW |

---

## 3. Team Work Division

### 👥 Team Structure (Zero Overlap Design)

The key principle: **Each person owns a complete vertical slice** - from database to UI for their features. This eliminates dependencies and waiting.

---

### 👤 **PERSON A: Frontend & UI Specialist**

**Ownership:** All JavaFX screens, FXML layouts, CSS styling, navigation

#### Week 1 Tasks
| Day | Task | Deliverable |
|-----|------|-------------|
| Mon | Project setup, JavaFX configuration | Running empty JavaFX app |
| Tue | Main window layout, navigation sidebar | `MainWindow.fxml`, navigation working |
| Wed | Login screen UI | `LoginScreen.fxml` with form fields |
| Thu | Registration screen UI | `RegisterScreen.fxml` |
| Fri | Profile management screen | `ProfileScreen.fxml` |

#### Week 2 Tasks
| Day | Task | Deliverable |
|-----|------|-------------|
| Mon | Text analysis input screen | `AnalysisScreen.fxml` with TextArea |
| Tue | Prediction results display | `ResultsScreen.fxml` with styled labels |
| Wed | Top predictions cards, confidence bar | Styled result components |
| Thu | Recommendations display section | Recommendations list view |
| Fri | Integration testing with Person C | End-to-end UI flow working |

#### Week 3 Tasks
| Day | Task | Deliverable |
|-----|------|-------------|
| Mon | Mood tracking entry form | `MoodEntryScreen.fxml` |
| Tue | Mood history list view | `MoodHistoryScreen.fxml` |
| Wed | Line chart for mood trends | Working JavaFX LineChart |
| Thu | Prediction history screen | `HistoryScreen.fxml` with filters |
| Fri | Analytics dashboard layout | `DashboardScreen.fxml` |

#### Week 4 Tasks
| Day | Task | Deliverable |
|-----|------|-------------|
| Mon | Settings screen, About screen | Settings & Help screens |
| Tue | CSS polish, animations | Professional look & feel |
| Wed | Loading spinners, error dialogs | UX improvements |
| Thu | Screenshots for documentation | All screen captures |
| Fri | Final UI testing | Bug-free UI |

#### Files Person A Creates:
```
src/main/resources/
├── fxml/
│   ├── MainWindow.fxml
│   ├── LoginScreen.fxml
│   ├── RegisterScreen.fxml
│   ├── ProfileScreen.fxml
│   ├── AnalysisScreen.fxml
│   ├── ResultsScreen.fxml
│   ├── MoodEntryScreen.fxml
│   ├── MoodHistoryScreen.fxml
│   ├── DashboardScreen.fxml
│   ├── HistoryScreen.fxml
│   ├── SettingsScreen.fxml
│   └── AboutScreen.fxml
├── css/
│   ├── main.css
│   ├── components.css
│   └── charts.css
└── images/
    └── (icons, logos)
```

---

### 👤 **PERSON B: Backend & API Specialist**

**Ownership:** Flask API, Python ML integration, Java HTTP client, recommendations engine

#### Week 1 Tasks
| Day | Task | Deliverable |
|-----|------|-------------|
| Mon | Flask project setup, load model | Flask app running, model loaded |
| Tue | `/predict` endpoint | Working prediction endpoint |
| Wed | `/health`, `/model_info` endpoints | Health check working |
| Thu | Error handling, logging | Robust error responses |
| Fri | API testing with Postman | Documented API tests |

#### Week 2 Tasks
| Day | Task | Deliverable |
|-----|------|-------------|
| Mon | Java HTTP client service | `MLAPIService.java` |
| Tue | Request/Response models | `PredictionRequest.java`, `PredictionResponse.java` |
| Wed | Retry logic, timeout handling | Robust API client |
| Thu | `/batch_predict` endpoint | Batch predictions working |
| Fri | Integration with Person A's UI | API connected to UI |

#### Week 3 Tasks
| Day | Task | Deliverable |
|-----|------|-------------|
| Mon | Recommendation engine | `RecommendationEngine.java` |
| Tue | Crisis detection & alerts | Suicidal prediction handling |
| Wed | API key authentication | Secured endpoints |
| Thu | Rate limiting | Request throttling |
| Fri | Performance optimization | Response time <500ms |

#### Week 4 Tasks
| Day | Task | Deliverable |
|-----|------|-------------|
| Mon | OCR integration (optional) | Tesseract setup |
| Tue | API documentation | Swagger/OpenAPI docs |
| Wed | Deployment guide | README with setup steps |
| Thu | Load testing | Performance report |
| Fri | Final API testing | Production-ready API |

#### Files Person B Creates:
```
python-api/
├── app.py
├── requirements.txt
├── mental_health_model.pkl
├── tfidf_vectorizer.pkl
├── config.py
├── README.md
└── tests/
    └── test_api.py

java-app/src/main/java/com/mentalhealth/
├── service/
│   ├── MLAPIService.java
│   └── RecommendationEngine.java
├── model/
│   ├── PredictionRequest.java
│   ├── PredictionResponse.java
│   └── Recommendation.java
└── config/
    └── APIConfig.java
```

---

### 👤 **PERSON C: Database & Integration Specialist**

**Ownership:** SQLite database, all repositories, data models, controllers, encryption

#### Week 1 Tasks
| Day | Task | Deliverable |
|-----|------|-------------|
| Mon | Database schema design | Schema diagram, SQL scripts |
| Tue | SQLite setup, connection manager | `DatabaseManager.java` |
| Wed | User table, UserRepository | CRUD for users |
| Thu | User model, authentication logic | Login/register working |
| Fri | Session management | User sessions |

#### Week 2 Tasks
| Day | Task | Deliverable |
|-----|------|-------------|
| Mon | Prediction table, PredictionRepository | CRUD for predictions |
| Tue | Controllers for login/register | `AuthController.java` |
| Wed | Controller for predictions | `PredictionController.java` |
| Thu | Connect controllers to UI | Wire up Person A's screens |
| Fri | End-to-end testing | Full flow working |

#### Week 3 Tasks
| Day | Task | Deliverable |
|-----|------|-------------|
| Mon | MoodEntry table, repository | CRUD for mood entries |
| Tue | Analytics queries | Weekly stats, averages |
| Wed | MoodController, DashboardController | Controllers for analytics |
| Thu | Data aggregation for charts | Chart data providers |
| Fri | Profile controller | Profile CRUD working |

#### Week 4 Tasks
| Day | Task | Deliverable |
|-----|------|-------------|
| Mon | AES encryption utility | `EncryptionUtil.java` |
| Tue | Data export (CSV) | Export functionality |
| Wed | Database backup utility | Auto-backup |
| Thu | Unit tests for repositories | JUnit tests |
| Fri | Final integration testing | Bug-free data layer |

#### Files Person C Creates:
```
java-app/src/main/java/com/mentalhealth/
├── database/
│   ├── DatabaseManager.java
│   ├── schema.sql
│   └── migrations/
├── model/
│   ├── User.java
│   ├── Prediction.java
│   ├── MoodEntry.java
│   └── Reminder.java
├── repository/
│   ├── UserRepository.java
│   ├── PredictionRepository.java
│   ├── MoodRepository.java
│   └── BaseRepository.java
├── controller/
│   ├── AuthController.java
│   ├── PredictionController.java
│   ├── MoodController.java
│   ├── ProfileController.java
│   └── DashboardController.java
├── util/
│   ├── EncryptionUtil.java
│   ├── ValidationUtil.java
│   └── ExportUtil.java
└── session/
    └── SessionManager.java
```

---

### 📊 Work Distribution Summary

| Component | Person A | Person B | Person C |
|-----------|----------|----------|----------|
| FXML Screens | ✅ | | |
| CSS Styling | ✅ | | |
| JavaFX Charts | ✅ | | |
| Flask API | | ✅ | |
| ML Integration | | ✅ | |
| HTTP Client | | ✅ | |
| Recommendations | | ✅ | |
| Database Schema | | | ✅ |
| Repositories | | | ✅ |
| Controllers | | | ✅ |
| Data Models | | | ✅ |
| Encryption | | | ✅ |

### 🔗 Integration Points (Minimal Dependencies)

```
Week 1: Everyone works independently
        - Person A: UI layouts (no data needed)
        - Person B: API (standalone testing with Postman)
        - Person C: Database (standalone testing with test data)

Week 2 Friday: First Integration
        - Person C provides controllers to Person A
        - Person B provides API client to Person C
        - Quick integration meeting

Week 3 Friday: Second Integration
        - Analytics features connected
        - Charts populated with real data

Week 4: Polish and testing together
```

---

## 4. Framework Setup Guide

### 🛠️ Required Software

| Software | Version | Purpose |
|----------|---------|---------|
| Java JDK | 21 (LTS) | Java development |
| Python | 3.10+ | ML API |
| VS Code | Latest | IDE |
| Git | Latest | Version control |
| Maven | 3.9+ | Java dependency management |

---

### 📦 Step 1: Install Java JDK 21

#### Windows:
1. Download from: https://adoptium.net/temurin/releases/?version=21
2. Choose **Windows x64 MSI installer**
3. Run installer, check "Set JAVA_HOME variable"
4. Verify installation:
```bash
java --version
# Should show: openjdk 21.x.x
```

#### macOS:
```bash
brew install openjdk@21
echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
java --version
```

#### Linux (Ubuntu/Debian):
```bash
sudo apt update
sudo apt install openjdk-21-jdk
java --version
```

---

### 📦 Step 2: Install Python 3.10+

#### Windows:
1. Download from: https://www.python.org/downloads/
2. **Important:** Check "Add Python to PATH" during installation
3. Verify:
```bash
python --version
pip --version
```

#### macOS:
```bash
brew install python@3.11
python3 --version
```

#### Linux:
```bash
sudo apt install python3 python3-pip python3-venv
python3 --version
```

---

### 📦 Step 3: Install Maven

#### Windows:
1. Download from: https://maven.apache.org/download.cgi
2. Extract to `C:\Program Files\Maven`
3. Add to PATH:
   - Open System Properties → Environment Variables
   - Add `C:\Program Files\Maven\apache-maven-3.9.6\bin` to PATH
4. Verify:
```bash
mvn --version
```

#### macOS:
```bash
brew install maven
mvn --version
```

#### Linux:
```bash
sudo apt install maven
mvn --version
```

---

### 📦 Step 4: VS Code Setup

#### Install VS Code Extensions

Open VS Code and install these extensions (Ctrl+Shift+X):

**For Java Development:**
1. **Extension Pack for Java** (Microsoft) - Essential
2. **Maven for Java** (Microsoft) - Build tool
3. **JavaFX Support** (JFormDesigner) - Optional but helpful

**For Python Development:**
1. **Python** (Microsoft) - Essential
2. **Pylance** (Microsoft) - Better IntelliSense

**General:**
1. **GitLens** - Git integration
2. **Thunder Client** - API testing (alternative to Postman)
3. **SQLite Viewer** - View database files

#### VS Code Settings for Java

Create/edit `.vscode/settings.json` in your project:
```json
{
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.compile.nullAnalysis.mode": "automatic",
    "java.jdt.ls.vmargs": "-XX:+UseParallelGC -XX:GCTimeRatio=4 -XX:AdaptiveSizePolicyWeight=90 -Dsun.zip.disableMemoryMapping=true -Xmx2G -Xms100m",
    "java.configuration.runtimes": [
        {
            "name": "JavaSE-21",
            "path": "/path/to/jdk-21",
            "default": true
        }
    ]
}
```

---

### 📦 Step 5: Create Project Structure

#### Create Main Project Directory
```bash
mkdir mental-health-analysis
cd mental-health-analysis
```

#### Create Java Project with Maven
```bash
mvn archetype:generate \
  -DgroupId=com.mentalhealth \
  -DartifactId=mental-health-app \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DarchetypeVersion=1.4 \
  -DinteractiveMode=false
```

#### Create Python API Directory
```bash
mkdir python-api
cd python-api
python -m venv venv
```

---

### 📦 Step 6: Configure Maven for JavaFX

Replace the contents of `mental-health-app/pom.xml`:

```xml name=pom.xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.mentalhealth</groupId>
    <artifactId>mental-health-app</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <name>Mental Health Analysis App</name>
    <description>AI-Based Mental Health Analysis Desktop Application</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <javafx.version>21.0.1</javafx.version>
        <gson.version>2.10.1</gson.version>
        <sqlite.version>3.44.1.0</sqlite.version>
        <junit.version>5.10.1</junit.version>
    </properties>

    <dependencies>
        <!-- JavaFX Controls -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <!-- JavaFX FXML -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <!-- Gson for JSON parsing -->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>${gson.version}</version>
        </dependency>

        <!-- SQLite JDBC Driver -->
        <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>${sqlite.version}</version>
        </dependency>

        <!-- JUnit 5 for Testing -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.11.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>

            <!-- JavaFX Maven Plugin -->
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>com.mentalhealth.Main</mainClass>
                </configuration>
            </plugin>

            <!-- Surefire for tests -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.2</version>
            </plugin>
        </plugins>
    </build>
</project>
```

---

### 📦 Step 7: Setup Python Environment

```bash
cd python-api

# Activate virtual environment
# Windows:
venv\Scripts\activate

# macOS/Linux:
source venv/bin/activate

# Install dependencies
pip install flask flask-cors scikit-learn joblib numpy

# Create requirements.txt
pip freeze > requirements.txt
```

#### Create `requirements.txt`:
```txt name=requirements.txt
Flask==3.0.0
Flask-CORS==4.0.0
scikit-learn==1.4.0
joblib==1.3.2
numpy==1.26.3
Werkzeug==3.0.1
```

---

### 📦 Step 8: Create Project File Structure

```bash
mental-health-analysis/
├── mental-health-app/                  # Java Application
│   ├── pom.xml
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/
│   │   │   │       └── mentalhealth/
│   │   │   │           ├── Main.java
│   │   │   │           ├── controller/
│   │   │   │           ├── model/
│   │   │   │           ├── repository/
│   │   │   │           ├── service/
│   │   │   │           ├── database/
│   │   │   │           ├── util/
│   │   │   │           └── session/
│   │   │   └── resources/
│   │   │       ├── fxml/
│   │   │       ├── css/
│   │   │       └── images/
│   │   └── test/
│   │       └── java/
│   │           └── com/
│   │               └── mentalhealth/
│   └── data/                           # SQLite database location
│       └── mental_health.db
│
├── python-api/                         # Flask API
│   ├── app.py
│   ├── config.py
│   ├── requirements.txt
│   ├── mental_health_model.pkl
│   ├── tfidf_vectorizer.pkl
│   └── venv/
│
├── documentation/
│   ├── User_Manual.md
│   ├── Technical_Documentation.md
│   └── API_Documentation.md
│
├── .gitignore
└── README.md
```

Create directories:
```bash
# From mental-health-analysis root
cd mental-health-app/src/main/java/com/mentalhealth
mkdir controller model repository service database util session

cd ../../resources
mkdir fxml css images

cd ../../../..
mkdir -p data
mkdir -p test/java/com/mentalhealth
```

---

### 📦 Step 9: Create Main Application Entry Point

```java name=src/main/java/com/mentalhealth/Main.java
package com.mentalhealth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Main entry point for Mental Health Analysis Application
 */
public class Main extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        
        try {
            // Load the main window
            Parent root = FXMLLoader.load(
                getClass().getResource("/fxml/MainWindow.fxml")
            );
            
            Scene scene = new Scene(root, 1200, 800);
            
            // Load CSS
            scene.getStylesheets().add(
                getClass().getResource("/css/main.css").toExternalForm()
            );
            
            stage.setTitle("Mental Health Analysis - AI Powered");
            stage.setScene(scene);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.show();
            
            System.out.println("✅ Application started successfully!");
            
        } catch (IOException e) {
            System.err.println("❌ Failed to load application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get the primary stage for scene switching
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Switch to a different scene
     */
    public static void switchScene(String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(
            Main.class.getResource("/fxml/" + fxmlFile)
        );
        primaryStage.getScene().setRoot(root);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

---

### 📦 Step 10: Create module-info.java

```java name=src/main/java/module-info.java
module com.mentalhealth {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    requires java.net.http;

    opens com.mentalhealth to javafx.fxml;
    opens com.mentalhealth.controller to javafx.fxml;
    opens com.mentalhealth.model to com.google.gson;

    exports com.mentalhealth;
    exports com.mentalhealth.controller;
    exports com.mentalhealth.model;
    exports com.mentalhealth.service;
    exports com.mentalhealth.repository;
}
```

---

### 📦 Step 11: Create .gitignore

```gitignore name=.gitignore
# Java
target/
*.class
*.jar
*.war
*.ear
*.log

# Maven
.mvn/
mvnw
mvnw.cmd

# IDE
.idea/
*.iml
.vscode/
*.swp
*.swo

# Python
python-api/venv/
__pycache__/
*.pyc
*.pyo
.Python
*.egg-info/
dist/
build/

# OS
.DS_Store
Thumbs.db

# Database
*.db
*.sqlite
*.sqlite3

# Environment
.env
*.env.local

# Model files (large, should be shared separately)
# Uncomment if you want to exclude model files
# *.pkl
```

---

### 📦 Step 12: Verify Setup

#### Test Java Setup:
```bash
cd mental-health-app
mvn clean compile
# Should complete without errors

mvn javafx:run
# Will fail (no FXML yet) but proves JavaFX is configured
```

#### Test Python Setup:
```bash
cd python-api
source venv/bin/activate  # or venv\Scripts\activate on Windows
python -c "import flask; import sklearn; print('✅ Python setup OK')"
```

---

### 🎯 Quick Reference Commands

| Task | Command |
|------|---------|
| **Build Java** | `mvn clean compile` |
| **Run Java App** | `mvn javafx:run` |
| **Run Tests** | `mvn test` |
| **Package JAR** | `mvn package` |
| **Start Flask** | `python app.py` |
| **Install Python deps** | `pip install -r requirements.txt` |
| **Activate venv (Win)** | `venv\Scripts\activate` |
| **Activate venv (Mac/Linux)** | `source venv/bin/activate` |

---

## 5. Step-by-Step Implementation

### 📅 Week 1: Foundation

#### Day 1 (All Members): Setup & Architecture

**Everyone does together:**
1. Clone/create Git repository
2. Set up project structure (from Step 8)
3. Verify all installations work
4. Create initial empty files
5. First commit

```bash
git init
git add .
git commit -m "Initial project setup with Maven and Flask structure"
```

#### Day 2-5: Parallel Work Begins

**Person A (UI):** Create basic FXML layouts
```xml name=src/main/resources/fxml/MainWindow.fxml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.layout.*?>
<?import javafx.scene.control.*?>

<BorderPane xmlns:fx="http://javafx.com/fxml" 
            fx:controller="com.mentalhealth.controller.MainController"
            prefWidth="1200" prefHeight="800">
    
    <!-- Left Sidebar Navigation -->
    <left>
        <VBox styleClass="sidebar" spacing="10" prefWidth="200">
            <Label text="Mental Health" styleClass="logo"/>
            <Button text="🏠 Dashboard" onAction="#showDashboard" maxWidth="Infinity"/>
            <Button text="📝 New Analysis" onAction="#showAnalysis" maxWidth="Infinity"/>
            <Button text="📊 History" onAction="#showHistory" maxWidth="Infinity"/>
            <Button text="😊 Mood Tracker" onAction="#showMoodTracker" maxWidth="Infinity"/>
            <Button text="⚙️ Settings" onAction="#showSettings" maxWidth="Infinity"/>
            <Region VBox.vgrow="ALWAYS"/>
            <Button text="🚪 Logout" onAction="#handleLogout" maxWidth="Infinity"/>
        </VBox>
    </left>
    
    <!-- Main Content Area -->
    <center>
        <StackPane fx:id="contentArea">
            <Label text="Welcome to Mental Health Analysis"/>
        </StackPane>
    </center>
    
</BorderPane>
```

**Person B (API):** Create Flask API with your existing model files

Copy your `mental_health_model.pkl` and `tfidf_vectorizer.pkl` to `python-api/`, then use the Flask code from your `flask_api_service.py` document.

**Person C (Database):** Create database schema and manager

```java name=src/main/java/com/mentalhealth/database/DatabaseManager.java
package com.mentalhealth.database;

import java.sql.*;
import java.nio.file.*;

/**
 * Singleton database manager for SQLite operations
 */
public class DatabaseManager {
    
    private static DatabaseManager instance;
    private static final String DB_PATH = "data/mental_health.db";
    private Connection connection;
    
    private DatabaseManager() {
        initializeDatabase();
    }
    
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    private void initializeDatabase() {
        try {
            // Ensure data directory exists
            Files.createDirectories(Paths.get("data"));
            
            // Connect to SQLite
            String url = "jdbc:sqlite:" + DB_PATH;
            connection = DriverManager.getConnection(url);
            
            System.out.println("✅ Database connected: " + DB_PATH);
            
            // Create tables
            createTables();
            
        } catch (Exception e) {
            System.err.println("❌ Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void createTables() throws SQLException {
        String[] createStatements = {
            // Users table
            """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password_hash TEXT NOT NULL,
                name TEXT NOT NULL,
                age INTEGER,
                goal TEXT,
                baseline_stress INTEGER DEFAULT 5,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """,
            
            // Predictions table
            """
            CREATE TABLE IF NOT EXISTS predictions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                input_text TEXT NOT NULL,
                prediction TEXT NOT NULL,
                confidence REAL NOT NULL,
                severity TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(id)
            )
            """,
            
            // Mood entries table
            """
            CREATE TABLE IF NOT EXISTS mood_entries (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                mood_score INTEGER NOT NULL CHECK(mood_score >= 1 AND mood_score <= 10),
                notes TEXT,
                entry_date DATE NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(id)
            )
            """,
            
            // Create indexes
            "CREATE INDEX IF NOT EXISTS idx_predictions_user ON predictions(user_id)",
            "CREATE INDEX IF NOT EXISTS idx_predictions_date ON predictions(created_at)",
            "CREATE INDEX IF NOT EXISTS idx_mood_user ON mood_entries(user_id)",
            "CREATE INDEX IF NOT EXISTS idx_mood_date ON mood_entries(entry_date)"
        };
        
        try (Statement stmt = connection.createStatement()) {
            for (String sql : createStatements) {
                stmt.execute(sql);
            }
            System.out.println("✅ Database tables created/verified");
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing database: " + e.getMessage());
        }
    }
}
```

---

### 📅 Week 2-4: Continue Development

Follow the week-by-week task breakdown in Section 3 for each team member.

---

## 🚀 Quick Start Guide

### To Run the Complete Application:

**Terminal 1 - Start Flask API:**
```bash
cd python-api
source venv/bin/activate  # or venv\Scripts\activate
python app.py
# API running on http://localhost:5000
```

**Terminal 2 - Run Java Application:**
```bash
cd mental-health-app
mvn javafx:run
```

---

## 📝 Final Checklist Before Submission

- [ ] Flask API starts without errors
- [ ] Java app connects to API successfully
- [ ] User registration/login works
- [ ] Predictions return correct results
- [ ] Mood tracking saves to database
- [ ] Charts display data correctly
- [ ] Crisis alerts show for "Suicidal" predictions
- [ ] Error handling works gracefully
- [ ] All documentation complete
- [ ] Demo video recorded
- [ ] Code is commented
- [ ] Git history is clean

---

This guide provides everything you need to successfully complete your project. Each team member has clear, independent tasks, and the setup instructions ensure everyone starts from the same working environment.

## @MuhammadMustafa23

is this all manageable in a month?

## Copilot

# Honest Assessment: Is This Manageable in 1 Month?

## Short Answer: **Yes, but with conditions** ✅

Let me break this down realistically:

---

## 📊 Time Analysis

### Available Resources
| Factor | Calculation |
|--------|-------------|
| Team Size | 3 people |
| Duration | 4 weeks = 20 working days |
| Hours/day (realistic for students) | 4-5 hours |
| **Total Person-Hours** | 3 × 20 × 4.5 = **270 hours** |

### Time Already Saved (Your Biggest Advantage)
| Task | Typical Time | Your Status |
|------|--------------|-------------|
| Dataset collection | 3-4 days | ✅ Done |
| Data preprocessing | 2-3 days | ✅ Done |
| Model training & tuning | 5-7 days | ✅ Done |
| Model validation | 2 days | ✅ Done |
| **Total Saved** | **12-16 days** | 🎉 |

> **You've essentially completed 40% of a typical ML project before even starting.**

---

## 🎯 Realistic Feature Prioritization

Here's what I recommend based on 1-month timeline:

### ✅ **MUST DO (Week 1-2)** — These are non-negotiable
| Feature | Time | Difficulty |
|---------|------|------------|
| Flask API with `/predict` endpoint | 2 days | Easy |
| Basic JavaFX UI (3-4 screens) | 4 days | Medium |
| SQLite database + User table | 2 days | Easy |
| Login/Register functionality | 2 days | Easy |
| Text input → API → Display result | 3 days | Medium |
| **Subtotal** | **~13 days** | |

### ✅ **SHOULD DO (Week 3)** — High value, reasonable effort
| Feature | Time | Difficulty |
|---------|------|------------|
| Prediction history storage & view | 2 days | Easy |
| Basic mood tracking (entry + list) | 2 days | Easy |
| Simple recommendations display | 1 day | Easy |
| One chart (mood trend line) | 2 days | Medium |
| **Subtotal** | **~7 days** | |

### ⚠️ **CAN SKIP** — Nice but not essential
| Feature | Why Skip |
|---------|----------|
| OCR integration | Complex, low academic value |
| Data encryption (AES) | Time-consuming, can mention as "future work" |
| Reminder system | Requires background tasks, complex |
| PDF export | Low priority |
| Multiple charts | One chart is enough for demo |

---

## 📅 Simplified 4-Week Plan

### Week 1: Foundation
| Day | Person A (UI) | Person B (API) | Person C (Database) |
|-----|---------------|----------------|---------------------|
| 1 | Setup JavaFX project | Setup Flask + load model | Setup SQLite + schema |
| 2 | Login screen FXML | `/predict` endpoint | User table + repository |
| 3 | Register screen FXML | `/health` endpoint | Authentication logic |
| 4 | Main window + navigation | Test with Postman | Session management |
| 5 | Analysis input screen | Java HTTP client | Connect login to DB |

**Week 1 Goal:** Login works, API returns predictions ✅

---

### Week 2: Core Integration
| Day | Person A (UI) | Person B (API) | Person C (Database) |
|-----|---------------|----------------|---------------------|
| 1 | Results display screen | Finish HTTP client | Prediction table |
| 2 | Style results (colors, confidence) | Error handling | Save predictions |
| 3 | Profile edit screen | Recommendations logic | Profile CRUD |
| 4 | Integration with Person C | Integration testing | Wire up controllers |
| 5 | **INTEGRATION DAY** | **INTEGRATION DAY** | **INTEGRATION DAY** |

**Week 2 Goal:** Full flow works (login → analyze → see results → save) ✅

---

### Week 3: Features
| Day | Person A (UI) | Person B (API) | Person C (Database) |
|-----|---------------|----------------|---------------------|
| 1 | Mood entry form | Crisis alert logic | MoodEntry table |
| 2 | Mood history list | Batch predict (optional) | Mood repository |
| 3 | Simple line chart | API documentation | Analytics queries |
| 4 | Prediction history screen | Performance tuning | History retrieval |
| 5 | Dashboard layout | Testing | Integration |

**Week 3 Goal:** Mood tracking + history + 1 chart working ✅

---

### Week 4: Polish & Submit
| Day | Person A (UI) | Person B (API) | Person C (Database) |
|-----|---------------|----------------|---------------------|
| 1 | CSS styling, polish | Final API testing | Unit tests |
| 2 | Loading spinners, errors | Deployment guide | Bug fixes |
| 3 | Screenshots | API docs | Technical docs |
| 4 | Demo video | README | User manual |
| 5 | **FINAL TESTING & SUBMISSION** |

**Week 4 Goal:** Polished, documented, ready to submit ✅

---

## ⚠️ Risk Factors & Mitigations

| Risk | Likelihood | Impact | Mitigation |
|------|------------|--------|------------|
| JavaFX learning curve | Medium | High | Use Scene Builder for FXML, follow tutorials |
| API integration issues | Medium | High | Test API with Postman FIRST before Java |
| Team member unavailable | Low | High | Each person's work is independent |
| Feature creep | High | Medium | Stick to MUST DO list, no new features after Week 2 |
| Last-minute bugs | High | Medium | Start testing in Week 2, not Week 4 |

---

## 💡 Key Success Factors

### 1. **Start Flask API on Day 1**
Your model is ready. Get the API running immediately:
```bash
cd python-api
python app.py
# Test: curl http://localhost:5000/health
```

### 2. **Test API Before Java Integration**
Use Postman or Thunder Client (VS Code extension) to verify API works:
```json
POST http://localhost:5000/predict
{
    "text": "I feel very anxious and stressed about exams"
}
```

### 3. **Keep UI Simple**
Don't over-design. A clean, functional UI beats a fancy broken one:
- Use default JavaFX styling initially
- Add CSS polish in Week 4 only
- Focus on functionality first

### 4. **Daily 15-Minute Standups**
Quick sync every day:
- What did I do yesterday?
- What will I do today?
- Any blockers?

### 5. **Commit Code Daily**
```bash
git add .
git commit -m "Day X: [brief description]"
git push
```

---

## 🎓 What Professors Actually Look For

| Criteria | Weight | How to Score Well |
|----------|--------|-------------------|
| **Working Demo** | 30% | End-to-end flow must work flawlessly |
| **Technical Implementation** | 25% | Clean code, proper patterns (MVC, Repository) |
| **ML Integration** | 20% | Show prediction accuracy, explain model |
| **Documentation** | 15% | Clear README, API docs, architecture diagram |
| **Presentation** | 10% | Confident demo, handle questions well |

> **Key insight:** A simple project that works perfectly scores higher than a complex project that crashes during demo.

---

## ✅ Final Verdict

| Question | Answer |
|----------|--------|
| Is it manageable? | **Yes** |
| Will it be easy? | **No** — expect 4-5 hours/day of focused work |
| Can you get 85%+ grade? | **Yes** — if you follow the plan and don't add features |
| Biggest risk? | Spending too much time on fancy features instead of core functionality |

### My Recommendation:

**Build the simplest version that works, then polish.**

| Week | Focus |
|------|-------|
| Week 1-2 | Make it **work** |
| Week 3 | Make it **complete** |
| Week 4 | Make it **presentable** |

---

## 🚀 Minimum Viable Product (Guaranteed Passing)

If you're ever behind schedule, here's the absolute minimum you need:

1. ✅ Flask API serves predictions
2. ✅ JavaFX app with login screen
3. ✅ Text input → calls API → shows result
4. ✅ Results saved to SQLite
5. ✅ View past predictions
6. ✅ Basic documentation

**This alone = 70-75% grade.** Add mood tracking + one chart = 80-85%.

---

You've got this! The hardest part (ML model) is already done. Now it's just software engineering, which is very doable in 4 weeks with 3 focused people. 💪

## @MuhammadMustafa23

and how will the work of every individual come together week by week without any integration issue

## Copilot

# Integration Strategy: How 3 People's Work Comes Together Seamlessly

## 🎯 The Core Problem

Without a clear integration plan:
- Person A builds UI that expects data format X
- Person C builds database returning format Y
- Person B builds API returning format Z
- **Result: Nothing works together** 😭

## ✅ The Solution: Contract-First Development

**Define the "contracts" (interfaces) on Day 1, then everyone builds to those contracts.**

---

## 📋 Step 1: Define Contracts on Day 1 (All 3 Together)

Spend 2-3 hours on Day 1 agreeing on these exact specifications:

### Contract 1: Data Models (Everyone uses these exact classes)

```java name=src/main/java/com/mentalhealth/model/User.java
package com.mentalhealth.model;

/**
 * User model - AGREED BY ALL TEAM MEMBERS
 * DO NOT CHANGE field names without team discussion
 */
public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String name;
    private Integer age;
    private String goal;
    private int baselineStress;
    private String createdAt;
    
    // Default constructor
    public User() {}
    
    // Constructor for new user registration
    public User(String username, String passwordHash, String name) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.baselineStress = 5;
    }
    
    // Full constructor
    public User(int id, String username, String passwordHash, String name, 
                Integer age, String goal, int baselineStress, String createdAt) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.name = name;
        this.age = age;
        this.goal = goal;
        this.baselineStress = baselineStress;
        this.createdAt = createdAt;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }
    
    public String getGoal() { return goal; }
    public void setGoal(String goal) { this.goal = goal; }
    
    public int getBaselineStress() { return baselineStress; }
    public void setBaselineStress(int baselineStress) { this.baselineStress = baselineStress; }
    
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
```

```java name=src/main/java/com/mentalhealth/model/Prediction.java
package com.mentalhealth.model;

import java.util.List;
import java.util.Map;

/**
 * Prediction model - AGREED BY ALL TEAM MEMBERS
 */
public class Prediction {
    private int id;
    private int userId;
    private String inputText;
    private String prediction;
    private double confidence;
    private String severity;
    private String color;
    private List<TopPrediction> topPredictions;
    private String createdAt;
    
    // Default constructor
    public Prediction() {}
    
    // Constructor for saving to database
    public Prediction(int userId, String inputText, String prediction, 
                      double confidence, String severity) {
        this.userId = userId;
        this.inputText = inputText;
        this.prediction = prediction;
        this.confidence = confidence;
        this.severity = severity;
    }
    
    // Inner class for top predictions
    public static class TopPrediction {
        private String className;
        private double probability;
        private String severity;
        private String color;
        
        public String getClassName() { return className; }
        public void setClassName(String className) { this.className = className; }
        
        public double getProbability() { return probability; }
        public void setProbability(double probability) { this.probability = probability; }
        
        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }
        
        public String getColor() { return color; }
        public void setColor(String color) { this.color = color; }
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getInputText() { return inputText; }
    public void setInputText(String inputText) { this.inputText = inputText; }
    
    public String getPrediction() { return prediction; }
    public void setPrediction(String prediction) { this.prediction = prediction; }
    
    public double getConfidence() { return confidence; }
    public void setConfidence(double confidence) { this.confidence = confidence; }
    
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    
    public List<TopPrediction> getTopPredictions() { return topPredictions; }
    public void setTopPredictions(List<TopPrediction> topPredictions) { 
        this.topPredictions = topPredictions; 
    }
    
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
```

```java name=src/main/java/com/mentalhealth/model/MoodEntry.java
package com.mentalhealth.model;

/**
 * MoodEntry model - AGREED BY ALL TEAM MEMBERS
 */
public class MoodEntry {
    private int id;
    private int userId;
    private int moodScore;  // 1-10
    private String notes;
    private String entryDate;  // YYYY-MM-DD
    private String createdAt;
    
    public MoodEntry() {}
    
    public MoodEntry(int userId, int moodScore, String notes, String entryDate) {
        this.userId = userId;
        this.moodScore = moodScore;
        this.notes = notes;
        this.entryDate = entryDate;
    }
    
    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public int getMoodScore() { return moodScore; }
    public void setMoodScore(int moodScore) { this.moodScore = moodScore; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public String getEntryDate() { return entryDate; }
    public void setEntryDate(String entryDate) { this.entryDate = entryDate; }
    
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
```

---

### Contract 2: Repository Interfaces (Person C implements, Person A uses)

```java name=src/main/java/com/mentalhealth/repository/IUserRepository.java
package com.mentalhealth.repository;

import com.mentalhealth.model.User;
import java.util.Optional;

/**
 * User Repository Interface
 * Person C: Implement this exactly
 * Person A: Call these methods from controllers
 */
public interface IUserRepository {
    
    // Create new user, returns user with generated ID
    User save(User user);
    
    // Find user by ID
    Optional<User> findById(int id);
    
    // Find user by username (for login)
    Optional<User> findByUsername(String username);
    
    // Update existing user
    boolean update(User user);
    
    // Check if username exists
    boolean existsByUsername(String username);
}
```

```java name=src/main/java/com/mentalhealth/repository/IPredictionRepository.java
package com.mentalhealth.repository;

import com.mentalhealth.model.Prediction;
import java.util.List;

/**
 * Prediction Repository Interface
 */
public interface IPredictionRepository {
    
    // Save prediction, returns with generated ID
    Prediction save(Prediction prediction);
    
    // Get all predictions for a user (newest first)
    List<Prediction> findByUserId(int userId);
    
    // Get recent predictions (limit)
    List<Prediction> findByUserIdLimit(int userId, int limit);
    
    // Get prediction count by type for user
    // Returns: {"Anxiety": 5, "Stress": 3, ...}
    java.util.Map<String, Integer> getCountByPredictionType(int userId);
}
```

```java name=src/main/java/com/mentalhealth/repository/IMoodRepository.java
package com.mentalhealth.repository;

import com.mentalhealth.model.MoodEntry;
import java.util.List;

/**
 * Mood Repository Interface
 */
public interface IMoodRepository {
    
    // Save mood entry
    MoodEntry save(MoodEntry entry);
    
    // Get mood entries for date range
    List<MoodEntry> findByUserIdAndDateRange(int userId, String startDate, String endDate);
    
    // Get last N mood entries
    List<MoodEntry> findByUserIdLimit(int userId, int limit);
    
    // Get average mood for date range
    double getAverageMood(int userId, String startDate, String endDate);
}
```

---

### Contract 3: API Response Format (Person B defines, Person A expects)

```java name=src/main/java/com/mentalhealth/model/ApiResponse.java
package com.mentalhealth.model;

import java.util.List;
import java.util.Map;

/**
 * API Response from Flask - EXACT format Person B will return
 * Person A: Parse responses into this format
 */
public class ApiResponse {
    private boolean success;
    private String prediction;
    private double confidence;
    private String severity;
    private String color;
    private List<Prediction.TopPrediction> top_predictions;
    private Map<String, Double> probabilities;
    private String timestamp;
    private String error;  // Only present if success=false
    
    // Getters
    public boolean isSuccess() { return success; }
    public String getPrediction() { return prediction; }
    public double getConfidence() { return confidence; }
    public String getSeverity() { return severity; }
    public String getColor() { return color; }
    public List<Prediction.TopPrediction> getTopPredictions() { return top_predictions; }
    public Map<String, Double> getProbabilities() { return probabilities; }
    public String getTimestamp() { return timestamp; }
    public String getError() { return error; }
}
```

---

### Contract 4: Service Interfaces (How components communicate)

```java name=src/main/java/com/mentalhealth/service/IMLService.java
package com.mentalhealth.service;

import com.mentalhealth.model.ApiResponse;

/**
 * ML API Service Interface
 * Person B: Implement this
 * Person A/C: Call this for predictions
 */
public interface IMLService {
    
    // Check if API is available
    boolean isHealthy();
    
    // Get prediction for text
    ApiResponse predict(String text) throws Exception;
    
    // Get API base URL
    String getBaseUrl();
}
```

```java name=src/main/java/com/mentalhealth/service/IAuthService.java
package com.mentalhealth.service;

import com.mentalhealth.model.User;
import java.util.Optional;

/**
 * Authentication Service Interface
 * Person C: Implement this
 * Person A: Call this for login/register
 */
public interface IAuthService {
    
    // Register new user, returns user if successful
    Optional<User> register(String username, String password, String name);
    
    // Login, returns user if credentials valid
    Optional<User> login(String username, String password);
    
    // Get currently logged in user
    Optional<User> getCurrentUser();
    
    // Logout current user
    void logout();
    
    // Check if user is logged in
    boolean isLoggedIn();
}
```

---

## 📂 Step 2: File Ownership (Who Creates What)

### Clear File Ownership Prevents Conflicts

```
src/main/java/com/mentalhealth/
│
├── model/                          # SHARED (Created Day 1 together)
│   ├── User.java                   ✅ Created together
│   ├── Prediction.java             ✅ Created together
│   ├── MoodEntry.java              ✅ Created together
│   └── ApiResponse.java            ✅ Created together
│
├── repository/                     # PERSON C ONLY
│   ├── IUserRepository.java        ✅ Interface (shared)
│   ├── IPredictionRepository.java  ✅ Interface (shared)
│   ├── IMoodRepository.java        ✅ Interface (shared)
│   ├── UserRepository.java         👤 Person C
│   ├── PredictionRepository.java   👤 Person C
│   └── MoodRepository.java         👤 Person C
│
├── service/                        # PERSON B + C
│   ├── IMLService.java             ✅ Interface (shared)
│   ├── IAuthService.java           ✅ Interface (shared)
│   ├── MLAPIService.java           👤 Person B
│   ├── AuthService.java            👤 Person C
│   └── RecommendationEngine.java   👤 Person B
│
├── controller/                     # PERSON A (uses interfaces)
│   ├── LoginController.java        👤 Person A
│   ├── RegisterController.java     👤 Person A
│   ├── MainController.java         👤 Person A
│   ├── AnalysisController.java     👤 Person A
│   ├── HistoryController.java      👤 Person A
│   ├── MoodController.java         👤 Person A
│   └── DashboardController.java    👤 Person A
│
├── database/                       # PERSON C ONLY
│   └── DatabaseManager.java        👤 Person C
│
└── session/                        # PERSON C ONLY
    └── SessionManager.java         👤 Person C

src/main/resources/
├── fxml/                           # PERSON A ONLY
│   ├── LoginScreen.fxml            👤 Person A
│   ├── RegisterScreen.fxml         👤 Person A
│   ├── MainWindow.fxml             👤 Person A
│   └── ...                         👤 Person A
│
└── css/                            # PERSON A ONLY
    └── main.css                    👤 Person A

python-api/                         # PERSON B ONLY
├── app.py                          👤 Person B
├── config.py                       👤 Person B
└── requirements.txt                👤 Person B
```

---

## 🔄 Step 3: Weekly Integration Process

### Week 1: Build in Isolation with Mock Data

```
┌─────────────────────────────────────────────────────────────────┐
│                        WEEK 1: ISOLATION                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Person A (UI)              Person B (API)         Person C (DB)│
│  ┌─────────────┐           ┌─────────────┐       ┌─────────────┐│
│  │ Build FXML  │           │ Build Flask │       │ Build SQLite││
│  │ screens     │           │ API         │       │ + Repos     ││
│  │             │           │             │       │             ││
│  │ Uses MOCK   │           │ Test with   │       │ Uses MOCK   ││
│  │ services    │           │ Postman     │       │ data        ││
│  └─────────────┘           └─────────────┘       └─────────────┘│
│        │                         │                     │        │
│        └─────────────────────────┴─────────────────────┘        │
│                              │                                  │
│                    No dependencies yet!                         │
│                    Everyone works freely                        │
└─────────────────────────────────────────────────────────────────┘
```

#### Person A: Use Mock Service for Testing UI

```java name=src/main/java/com/mentalhealth/service/MockAuthService.java
package com.mentalhealth.service;

import com.mentalhealth.model.User;
import java.util.Optional;

/**
 * Mock Auth Service for UI testing
 * Person A uses this until Person C's real implementation is ready
 */
public class MockAuthService implements IAuthService {
    
    private User mockUser = null;
    
    @Override
    public Optional<User> register(String username, String password, String name) {
        // Simulate successful registration
        User user = new User(username, "hashed", name);
        user.setId(1);
        return Optional.of(user);
    }
    
    @Override
    public Optional<User> login(String username, String password) {
        // Accept any login for testing
        if (username != null && password != null) {
            mockUser = new User(username, "hashed", "Test User");
            mockUser.setId(1);
            return Optional.of(mockUser);
        }
        return Optional.empty();
    }
    
    @Override
    public Optional<User> getCurrentUser() {
        return Optional.ofNullable(mockUser);
    }
    
    @Override
    public void logout() {
        mockUser = null;
    }
    
    @Override
    public boolean isLoggedIn() {
        return mockUser != null;
    }
}
```

```java name=src/main/java/com/mentalhealth/service/MockMLService.java
package com.mentalhealth.service;

import com.mentalhealth.model.ApiResponse;

/**
 * Mock ML Service for UI testing
 * Person A uses this until Person B's real implementation is ready
 */
public class MockMLService implements IMLService {
    
    @Override
    public boolean isHealthy() {
        return true;
    }
    
    @Override
    public ApiResponse predict(String text) {
        // Return fake prediction for UI testing
        ApiResponse response = new ApiResponse();
        // Use reflection or create setters for testing
        // This just demonstrates the concept
        return createMockResponse(text);
    }
    
    private ApiResponse createMockResponse(String text) {
        // Create a mock response based on keywords in text
        // This lets Person A test UI without real API
        String prediction = "Stress";
        double confidence = 0.75;
        
        if (text.toLowerCase().contains("happy")) {
            prediction = "Normal";
            confidence = 0.85;
        } else if (text.toLowerCase().contains("anxious")) {
            prediction = "Anxiety";
            confidence = 0.80;
        }
        
        // Return mock response (implement properly with setters)
        return new ApiResponse(); // Simplified
    }
    
    @Override
    public String getBaseUrl() {
        return "http://localhost:5000";
    }
}
```

#### Person A: Controller Uses Interface (Easy to Swap Later)

```java name=src/main/java/com/mentalhealth/controller/LoginController.java
package com.mentalhealth.controller;

import com.mentalhealth.model.User;
import com.mentalhealth.service.IAuthService;
import com.mentalhealth.service.MockAuthService;
import com.mentalhealth.Main;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Optional;

public class LoginController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;
    
    // Use interface - can be Mock or Real implementation
    private IAuthService authService;
    
    @FXML
    public void initialize() {
        // Week 1: Use mock
        // authService = new MockAuthService();
        
        // Week 2+: Use real (Person C's implementation)
        authService = ServiceFactory.getAuthService();
    }
    
    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        // Validation
        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter username and password");
            return;
        }
        
        // Attempt login
        Optional<User> user = authService.login(username, password);
        
        if (user.isPresent()) {
            // Login successful - navigate to main window
            try {
                Main.switchScene("MainWindow.fxml");
            } catch (Exception e) {
                showError("Failed to load main window");
            }
        } else {
            showError("Invalid username or password");
        }
    }
    
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }
}
```

---

### Service Factory: The Integration Switch

```java name=src/main/java/com/mentalhealth/service/ServiceFactory.java
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
```

---

### Week 2 Friday: First Integration Day

```
┌─────────────────────────────────────────────────────────────────┐
│                 WEEK 2 FRIDAY: INTEGRATION DAY                  │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  Step 1: Everyone pulls latest code                             │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  $ git pull origin main                                  │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                 │
│  Step 2: Person B verifies API works                           │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  $ cd python-api                                         │   │
│  │  $ python app.py                                         │   │
│  │  # Test: curl http://localhost:5000/health               │   │
│  │  # Expected: {"status": "healthy", "model_loaded": true} │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                 │
│  Step 3: Person C verifies database works                      │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  // Quick test                                           │   │
│  │  User user = new User("test", "hash", "Test");          │   │
│  │  userRepository.save(user);                              │   │
│  │  Optional<User> found = userRepository.findByUsername(); │   │
│  │  assert found.isPresent();                               │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                 │
│  Step 4: Update ServiceFactory to use real implementations     │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  private static final boolean USE_REAL_AUTH = true;      │   │
│  │  private static final boolean USE_REAL_ML = true;        │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                 │
│  Step 5: Run full app and test end-to-end                      │
│  ┌─────────────────────────────────────────────────────────┐   │
│  │  $ mvn javafx:run                                        │   │
│  │  # Test: Register → Login → Analyze text → See result    │   │
│  └─────────────────────────────────────────────────────────┘   │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 🔧 Step 4: Integration Testing Checklist

### End-of-Week-2 Integration Tests

```java name=src/test/java/com/mentalhealth/IntegrationTest.java
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
```

### Integration Test Commands

```bash
# Run all tests
mvn test

# Run only integration tests
mvn test -Dtest=IntegrationTest

# Run with verbose output
mvn test -Dtest=IntegrationTest -X
```

---

## 📅 Step 5: Daily Sync Protocol

### 15-Minute Daily Standup (Video Call or Chat)

**Format:**
```
Person A:
- Yesterday: Created LoginScreen.fxml, styled with CSS
- Today: Will create RegisterScreen.fxml
- Blockers: None

Person B:
- Yesterday: Implemented /predict endpoint, tested with Postman
- Today: Will create Java HTTP client
- Blockers: None

Person C:
- Yesterday: Created UserRepository with all methods
- Today: Will implement AuthService
- Blockers: None
```

### Git Workflow to Prevent Conflicts

```bash
# Start of day
git pull origin main

# Before any work
git checkout -b feature/your-feature-name

# End of day
git add .
git commit -m "Day X: Brief description of work"
git push origin feature/your-feature-name

# Create Pull Request on GitHub
# Another team member reviews and merges
```

### Branch Naming Convention

| Person | Branch Pattern | Example |
|--------|----------------|---------|
| Person A | `feature/ui-*` | `feature/ui-login-screen` |
| Person B | `feature/api-*` | `feature/api-predict-endpoint` |
| Person C | `feature/db-*` | `feature/db-user-repository` |

---

## 🛡️ Step 6: Conflict Prevention Rules

### Rule 1: Never Edit Same File
If you need to modify a file owned by someone else:
1. Create a GitHub Issue describing the change
2. Discuss in standup
3. Owner makes the change

### Rule 2: Use Constants for Shared Values

```java name=src/main/java/com/mentalhealth/Constants.java
package com.mentalhealth;

/**
 * Shared constants - edit only in standup together
 */
public final class Constants {
    
    // API Configuration
    public static final String API_BASE_URL = "http://localhost:5000";
    public static final int API_TIMEOUT_SECONDS = 30;
    
    // Database
    public static final String DB_PATH = "data/mental_health.db";
    
    // Severity Colors (used by UI)
    public static final String COLOR_NORMAL = "#4CAF50";
    public static final String COLOR_MODERATE = "#FF9800";
    public static final String COLOR_HIGH = "#F44336";
    public static final String COLOR_CRITICAL = "#9C27B0";
    
    // Validation
    public static final int MIN_TEXT_LENGTH = 10;
    public static final int MAX_TEXT_LENGTH = 5000;
    public static final int MIN_PASSWORD_LENGTH = 6;
    
    private Constants() {} // Prevent instantiation
}
```

### Rule 3: Document Interface Changes

If Person C needs to change a repository interface:
1. Announce in group chat
2. Person A acknowledges
3. Change is made
4. Person A updates controller

---

## 📊 Visual: How Integration Works

```
┌─────────────────────────────────────────────────────────────────────────┐
│                    COMPLETE INTEGRATION FLOW                            │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  [User Types Text]                                                      │
│         │                                                               │
│         ▼                                                               │
│  ┌─────────────────┐                                                    │
│  │ AnalysisScreen  │  ◄── Person A builds                              │
│  │ (FXML + CSS)    │                                                    │
│  └────────┬────────┘                                                    │
│           │                                                             │
│           ▼                                                             │
│  ┌─────────────────────┐                                                │
│  │ AnalysisController  │  ◄── Person A builds (uses interfaces)        │
│  │ - validates input   │                                                │
│  │ - calls services    │                                                │
│  └────────┬────────────┘                                                │
│           │                                                             │
│           ├──────────────────────────────────┐                          │
│           │                                  │                          │
│           ▼                                  ▼                          │
│  ┌─────────────────┐                ┌─────────────────┐                 │
│  │ IMLService      │                │ IAuthService    │                 │
│  │ (interface)     │                │ (interface)     │                 │
│  └────────┬────────┘                └────────┬────────┘                 │
│           │                                  │                          │
│           ▼                                  ▼                          │
│  ┌─────────────────┐                ┌─────────────────┐                 │
│  │ MLAPIService    │  ◄── B builds  │ AuthService     │  ◄── C builds  │
│  │ - HTTP client   │                │ - session mgmt  │                 │
│  │ - JSON parsing  │                │ - password hash │                 │
│  └────────┬────────┘                └────────┬────────┘                 │
│           │                                  │                          │
│           ▼                                  ▼                          │
│  ┌─────────────────┐                ┌─────────────────┐                 │
│  │ Flask API       │  ◄── B builds  │ UserRepository  │  ◄── C builds  │
│  │ /predict        │                │ - save()        │                 │
│  │ /health         │                │ - findById()    │                 │
│  └────────┬────────┘                └────────┬────────┘                 │
│           │                                  │                          │
│           ▼                                  ▼                          │
│  ┌─────────────────┐                ┌─────────────────┐                 │
│  │ ML Model        │  ◄── B loads   │ SQLite Database │  ◄── C creates │
│  │ (PKL files)     │                │ mental_health.db│                 │
│  └─────────────────┘                └─────────────────┘                 │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## ✅ Integration Checklist by Week

### Week 1 Friday
- [ ] All 3 members can build and run their component independently
- [ ] Model classes (User, Prediction, MoodEntry) committed and shared
- [ ] Interfaces committed and shared
- [ ] Person B: API returns prediction via Postman
- [ ] Person C: Can save/retrieve user from database

### Week 2 Friday (Main Integration)
- [ ] ServiceFactory switched to real implementations
- [ ] Login → Main screen flow works
- [ ] Text analysis → API call → Result display works
- [ ] Prediction saved to database
- [ ] All integration tests pass

### Week 3 Friday
- [ ] Mood tracking saves and displays
- [ ] Prediction history shows past entries
- [ ] At least one chart displays data
- [ ] Recommendations show based on prediction

### Week 4 Friday (Final)
- [ ] All features work end-to-end
- [ ] No crashes or unhandled errors
- [ ] Demo runs smoothly
- [ ] Documentation complete

---

## 🎯 Summary: Why This Works

| Problem | Solution |
|---------|----------|
| Merge conflicts | Clear file ownership |
| Waiting for others | Mock services for testing |
| Integration failures | Defined contracts (interfaces) |
| Miscommunication | Daily standups |
| Last-minute bugs | Weekly integration tests |
| Different data formats | Shared model classes |

**The key insight:** By agreeing on interfaces and data models on Day 1, everyone can work independently for the entire week, then integrate smoothly on Fridays.

## @MuhammadMustafa23

considering all this you provided, the entire project will be in java right apart from the python model integration using flask api

## Copilot

# Yes, Exactly Right! Here's the Clear Breakdown

## 🎯 Technology Split

```
┌─────────────────────────────────────────────────────────────────────┐
│                        YOUR PROJECT STRUCTURE                       │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │                                                             │   │
│  │                    JAVA (95% of project)                    │   │
│  │                                                             │   │
│  │  • Desktop Application (JavaFX)                             │   │
│  │  • User Interface (FXML + CSS)                              │   │
│  │  • Controllers                                              │   │
│  │  • Database (SQLite via JDBC)                               │   │
│  │  • Business Logic                                           │   │
│  │  • HTTP Client (calls Python API)                           │   │
│  │  • Session Management                                       │   │
│  │  • Data Models                                              │   │
│  │  • Repositories                                             │   │
│  │  • Charts & Analytics                                       │   │
│  │                                                             │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                              │                                     │
│                              │ HTTP Request (JSON)                 │
│                              ▼                                     │
│  ┌─────────────────────────────────────────────────────────────┐   │
│  │                                                             │   │
│  │                   PYTHON (5% of project)                    │   │
│  │                                                             │   │
│  │  • Flask API Server (~200 lines of code)                    │   │
│  │  • Load trained ML model (pkl files)                        │   │
│  │  • Expose /predict endpoint                                 │   │
│  │  • Return JSON response                                     │   │
│  │                                                             │   │
│  └─────────────────────────────────────────────────────────────┘   │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

---

## 📊 Exact Code Distribution

| Component | Language | Lines of Code (Approx) | Percentage |
|-----------|----------|------------------------|------------|
| JavaFX UI (FXML files) | XML/FXML | ~800 lines | 15% |
| CSS Styling | CSS | ~200 lines | 4% |
| Java Controllers | Java | ~1,200 lines | 22% |
| Java Models | Java | ~400 lines | 7% |
| Java Repositories | Java | ~600 lines | 11% |
| Java Services | Java | ~500 lines | 9% |
| Java Utilities | Java | ~300 lines | 6% |
| Database Manager | Java | ~150 lines | 3% |
| **Total Java/FXML/CSS** | **Java** | **~4,150 lines** | **~77%** |
| Flask API | Python | ~250 lines | 5% |
| ML Model Files | Binary (pkl) | N/A | N/A |
| Tests | Java | ~500 lines | 9% |
| Documentation | Markdown | ~500 lines | 9% |
| **TOTAL** | | **~5,400 lines** | **100%** |

---

## 🔍 What Python Actually Does (Minimal)

The Python part is **extremely simple** - it's just a wrapper around your already-trained model:

```python name=python-api/app.py
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
```

**That's it!** The Python code is complete. Person B just needs to refine this slightly.

---

## ☕ What Java Does (Everything Else)

### 1. User Interface (JavaFX + FXML)

```
All screens your users see and interact with:

├── Login Screen
├── Registration Screen  
├── Main Dashboard
├── Text Analysis Screen (input journal text)
├── Results Display Screen (show prediction)
├── Mood Tracking Screen
├── History Screen (past predictions)
├── Analytics/Charts Screen
├── Settings Screen
└── Profile Screen
```

### 2. Business Logic (Pure Java)

```java
// Authentication
- User registration with password hashing
- Login validation
- Session management

// Predictions
- Call Flask API
- Parse JSON response
- Save to database
- Display results

// Mood Tracking
- Save daily mood scores
- Calculate averages
- Generate trends

// Analytics
- Aggregate prediction history
- Calculate statistics
- Prepare chart data

// Recommendations
- Rule-based suggestions based on prediction
- Crisis detection for "Suicidal" results
```

### 3. Database (Java + SQLite)

```java
// All database operations in Java
- Create tables
- Insert/Update/Delete records
- Query data
- Manage connections
```

### 4. HTTP Communication (Java)

```java
// Java calls Python API
- Build HTTP requests
- Send JSON payload
- Receive JSON response
- Parse into Java objects
```

---

## 🎓 Why This Architecture is Good for Your SDA Project

### Academic Justification

| Principle | How Your Project Demonstrates It |
|-----------|----------------------------------|
| **Separation of Concerns** | UI, Business Logic, Data Access, ML are all separate |
| **Microservices** | Java app + Python API = 2 services communicating |
| **MVC Pattern** | Controllers, Models, Views clearly separated |
| **Repository Pattern** | Data access abstracted behind interfaces |
| **Singleton Pattern** | DatabaseManager, SessionManager |
| **Factory Pattern** | ServiceFactory for dependency injection |
| **Interface Segregation** | Small, focused interfaces (IAuthService, IMLService) |

### What You Can Say in Presentation

> "Our system uses a **microservices architecture** where the Java desktop application communicates with a Python ML service via REST API. The Java application follows **MVC pattern** with clear separation between controllers, models, and views. We implemented **Repository pattern** for data access and **Singleton pattern** for resource management. The ML model is served through a **Flask API**, demonstrating real-world integration between different technology stacks."

---

## 📁 Final Project Structure

```
mental-health-analysis/
│
├── mental-health-app/                 # ☕ JAVA APPLICATION (95%)
│   ├── pom.xml                        # Maven configuration
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   └── com/mentalhealth/
│   │   │   │       ├── Main.java                    # App entry point
│   │   │   │       │
│   │   │   │       ├── controller/                  # Handle user actions
│   │   │   │       │   ├── LoginController.java
│   │   │   │       │   ├── RegisterController.java
│   │   │   │       │   ├── MainController.java
│   │   │   │       │   ├── AnalysisController.java
│   │   │   │       │   ├── ResultsController.java
│   │   │   │       │   ├── HistoryController.java
│   │   │   │       │   ├── MoodController.java
│   │   │   │       │   ├── DashboardController.java
│   │   │   │       │   └── ProfileController.java
│   │   │   │       │
│   │   │   │       ├── model/                       # Data structures
│   │   │   │       │   ├── User.java
│   │   │   │       │   ├── Prediction.java
│   │   │   │       │   ├── MoodEntry.java
│   │   │   │       │   └── ApiResponse.java
│   │   │   │       │
│   │   │   │       ├── repository/                  # Database access
│   │   │   │       │   ├── IUserRepository.java
│   │   │   │       │   ├── UserRepository.java
│   │   │   │       │   ├── IPredictionRepository.java
│   │   │   │       │   ├── PredictionRepository.java
│   │   │   │       │   ├── IMoodRepository.java
│   │   │   │       │   └── MoodRepository.java
│   │   │   │       │
│   │   │   │       ├── service/                     # Business logic
│   │   │   │       │   ├── IAuthService.java
│   │   │   │       │   ├── AuthService.java
│   │   │   │       │   ├── IMLService.java
│   │   │   │       │   ├── MLAPIService.java        # Calls Python API
│   │   │   │       │   ├── RecommendationEngine.java
│   │   │   │       │   └── ServiceFactory.java
│   │   │   │       │
│   │   │   │       ├── database/                    # Database management
│   │   │   │       │   └── DatabaseManager.java
│   │   │   │       │
│   │   │   │       ├── session/                     # User session
│   │   │   │       │   └── SessionManager.java
│   │   │   │       │
│   │   │   │       └── util/                        # Utilities
│   │   │   │           ├── ValidationUtil.java
│   │   │   │           └── PasswordUtil.java
│   │   │   │
│   │   │   └── resources/
│   │   │       ├── fxml/                            # UI layouts
│   │   │       │   ├── LoginScreen.fxml
│   │   │       │   ├── RegisterScreen.fxml
│   │   │       │   ├── MainWindow.fxml
│   │   │       │   ├── AnalysisScreen.fxml
│   │   │       │   ├── ResultsScreen.fxml
│   │   │       │   ├── HistoryScreen.fxml
│   │   │       │   ├── MoodScreen.fxml
│   │   │       │   ├── DashboardScreen.fxml
│   │   │       │   └── ProfileScreen.fxml
│   │   │       │
│   │   │       └── css/                             # Styling
│   │   │           └── styles.css
│   │   │
│   │   └── test/                                    # Unit tests
│   │       └── java/com/mentalhealth/
│   │           └── ...
│   │
│   └── data/
│       └── mental_health.db                         # SQLite database
│
│
├── python-api/                        # 🐍 PYTHON API (5%)
│   ├── app.py                         # Flask server (~200 lines)
│   ├── requirements.txt               # Dependencies
│   ├── mental_health_model.pkl        # Your trained model
│   └── tfidf_vectorizer.pkl           # Your vectorizer
│
│
├── documentation/                     # 📄 DOCS
│   ├── README.md
│   ├── User_Manual.md
│   └── Technical_Documentation.md
│
└── .gitignore
```

---

## 🔄 How Java and Python Communicate

```
┌─────────────────────────────────────────────────────────────────────┐
│                     COMMUNICATION FLOW                              │
├─────────────────────────────────────────────────────────────────────┤
│                                                                     │
│  JAVA APPLICATION                          PYTHON FLASK API         │
│  ────────────────                          ─────────────────        │
│                                                                     │
│  User types: "I feel anxious about exams"                          │
│         │                                                           │
│         ▼                                                           │
│  ┌─────────────────────────┐                                       │
│  │ AnalysisController.java │                                       │
│  │ handleAnalyzeButton()   │                                       │
│  └───────────┬─────────────┘                                       │
│              │                                                      │
│              ▼                                                      │
│  ┌─────────────────────────┐                                       │
│  │ MLAPIService.java       │                                       │
│  │ predict(text)           │                                       │
│  └───────────┬─────────────┘                                       │
│              │                                                      │
│              │  HTTP POST Request                                   │
│              │  ┌─────────────────────────────┐                    │
│              │  │ {                           │                    │
│              │  │   "text": "I feel anxious   │                    │
│              │  │           about exams"      │                    │
│              │  │ }                           │                    │
│              │  └─────────────────────────────┘                    │
│              │                                                      │
│              └──────────────────────────────────►┌────────────────┐│
│                                                  │ Flask /predict ││
│                                                  │                ││
│                                                  │ 1. Vectorize   ││
│                                                  │ 2. Predict     ││
│                                                  │ 3. Return JSON ││
│                                                  └───────┬────────┘│
│              ◄───────────────────────────────────────────┘         │
│              │                                                      │
│              │  HTTP Response                                       │
│              │  ┌─────────────────────────────┐                    │
│              │  │ {                           │                    │
│              │  │   "success": true,          │                    │
│              │  │   "prediction": "Anxiety",  │                    │
│              │  │   "confidence": 0.82,       │                    │
│              │  │   "severity": "Moderate",   │                    │
│              │  │   "color": "#FF9800"        │                    │
│              │  │ }                           │                    │
│              │  └─────────────────────────────┘                    │
│              │                                                      │
│              ▼                                                      │
│  ┌─────────────────────────┐                                       │
│  │ Parse JSON to           │                                       │
│  │ ApiResponse object      │                                       │
│  └───────────┬─────────────┘                                       │
│              │                                                      │
│              ▼                                                      │
│  ┌─────────────────────────┐                                       │
│  │ Display result in UI    │                                       │
│  │ Save to SQLite database │                                       │
│  └─────────────────────────┘                                       │
│                                                                     │
└─────────────────────────────────────────────────────────────────────┘
```

---

## 📝 The Actual Java HTTP Client Code

This is how Java calls the Python API:

```java name=src/main/java/com/mentalhealth/service/MLAPIService.java
package com.mentalhealth.service;

import com.google.gson.Gson;
import com.mentalhealth.model.ApiResponse;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Service to communicate with Python Flask API
 * This is the ONLY place where Java talks to Python
 */
public class MLAPIService implements IMLService {
    
    private static final String BASE_URL = "http://localhost:5000";
    private static final int TIMEOUT_SECONDS = 30;
    
    private final HttpClient httpClient;
    private final Gson gson;
    
    public MLAPIService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
    }
    
    @Override
    public boolean isHealthy() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/health"))
                    .GET()
                    .timeout(Duration.ofSeconds(5))
                    .build();
            
            HttpResponse<String> response = httpClient.send(
                    request, 
                    HttpResponse.BodyHandlers.ofString()
            );
            
            return response.statusCode() == 200;
            
        } catch (Exception e) {
            System.err.println("API health check failed: " + e.getMessage());
            return false;
        }
    }
    
    @Override
    public ApiResponse predict(String text) throws Exception {
        // Validate input
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }
        
        // Create JSON body
        String jsonBody = gson.toJson(new PredictRequest(text.trim()));
        
        // Build HTTP POST request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/predict"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
        
        // Send request to Python API
        HttpResponse<String> response = httpClient.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );
        
        // Check status
        if (response.statusCode() != 200) {
            throw new Exception("API error: " + response.statusCode());
        }
        
        // Parse JSON response into Java object
        ApiResponse apiResponse = gson.fromJson(
                response.body(), 
                ApiResponse.class
        );
        
        if (!apiResponse.isSuccess()) {
            throw new Exception("Prediction failed: " + apiResponse.getError());
        }
        
        return apiResponse;
    }
    
    @Override
    public String getBaseUrl() {
        return BASE_URL;
    }
    
    // Inner class for request body
    private static class PredictRequest {
        private final String text;
        
        PredictRequest(String text) {
            this.text = text;
        }
    }
}
```

---

## ✅ Summary

| Question | Answer |
|----------|--------|
| **Is this project mainly Java?** | **Yes, 95% Java** |
| **What does Python do?** | Only serves the ML model via REST API (~200 lines) |
| **Is this appropriate for SDA course?** | **Absolutely** - demonstrates Java skills, design patterns, architecture |
| **Do we need Python expertise?** | Minimal - Person B just needs to understand the Flask code provided |
| **Can we say "Java project" in presentation?** | Yes - "Java desktop application with Python ML microservice" |

### For Your Professor

> "This is a **Java-based desktop application** built with JavaFX, following MVC architecture and Repository pattern. The machine learning component is served through a lightweight Python Flask API, demonstrating **microservices integration** principles. All business logic, user interface, database operations, and application architecture are implemented in **Java**."

---

## 🚀 Quick Start Commands

```bash
# Terminal 1: Start Python API (runs in background)
cd python-api
source venv/bin/activate  # Windows: venv\Scripts\activate
python app.py
# Output: Running on http://localhost:5000

# Terminal 2: Run Java Application
cd mental-health-app
mvn javafx:run
# Opens JavaFX window
```

The Python API just sits there waiting for requests. All the action happens in Java! 💪

## @MuhammadMustafa23

now help me set up the complete environment in my vscode for this project all the frameworks that will be required from start to end

## Copilot

# Complete VS Code Environment Setup Guide

## 📋 Prerequisites Checklist

Before we start, let's verify your system is ready:

| Requirement | Minimum Version | Check Command |
|-------------|-----------------|---------------|
| Windows 10/11, macOS, or Linux | - | - |
| Internet Connection | - | - |
| 8GB RAM (recommended) | - | - |
| 5GB free disk space | - | - |

---

## 🚀 Step 1: Install Java JDK 21

### Windows

1. **Download JDK 21:**
   - Go to: https://adoptium.net/temurin/releases/?version=21
   - Select: **Windows x64** → **JDK** → **.msi**
   - Download the installer

2. **Run Installer:**
   ```
   ✅ Check "Set JAVA_HOME variable"
   ✅ Check "Add to PATH"
   Click Next → Install → Finish
   ```

3. **Verify Installation:**
   Open **Command Prompt** (Win + R → type `cmd` → Enter):
   ```bash
   java --version
   ```
   Expected output:
   ```
   openjdk 21.0.x 2024-xx-xx
   OpenJDK Runtime Environment Temurin-21.0.x+x (build 21.0.x+x)
   OpenJDK 64-Bit Server VM Temurin-21.0.x+x (build 21.0.x+x, mixed mode)
   ```

4. **Verify JAVA_HOME:**
   ```bash
   echo %JAVA_HOME%
   ```
   Should show something like: `C:\Program Files\Eclipse Adoptium\jdk-21.0.x.x-hotspot`

### macOS

```bash
# Install Homebrew if not installed
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install JDK 21
brew install openjdk@21

# Add to PATH
echo 'export PATH="/opt/homebrew/opt/openjdk@21/bin:$PATH"' >> ~/.zshrc
echo 'export JAVA_HOME="/opt/homebrew/opt/openjdk@21"' >> ~/.zshrc
source ~/.zshrc

# Verify
java --version
```

### Linux (Ubuntu/Debian)

```bash
sudo apt update
sudo apt install openjdk-21-jdk -y

# Set JAVA_HOME
echo 'export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64' >> ~/.bashrc
echo 'export PATH=$JAVA_HOME/bin:$PATH' >> ~/.bashrc
source ~/.bashrc

# Verify
java --version
```

---

## 🚀 Step 2: Install Apache Maven

### Windows

1. **Download Maven:**
   - Go to: https://maven.apache.org/download.cgi
   - Download: **apache-maven-3.9.6-bin.zip** (Binary zip archive)

2. **Extract:**
   - Extract to: `C:\Program Files\Maven\apache-maven-3.9.6`

3. **Set Environment Variables:**
   - Press **Win + S** → Search "Environment Variables"
   - Click "Edit the system environment variables"
   - Click "Environment Variables" button
   - Under "System Variables":
     
     **Add New Variable:**
     ```
     Variable name: MAVEN_HOME
     Variable value: C:\Program Files\Maven\apache-maven-3.9.6
     ```
     
     **Edit PATH Variable:**
     - Click on `Path` → Edit
     - Click "New"
     - Add: `%MAVEN_HOME%\bin`
     - Click OK → OK → OK

4. **Verify (Open NEW Command Prompt):**
   ```bash
   mvn --version
   ```
   Expected:
   ```
   Apache Maven 3.9.6
   Maven home: C:\Program Files\Maven\apache-maven-3.9.6
   Java version: 21.0.x
   ```

### macOS

```bash
brew install maven
mvn --version
```

### Linux

```bash
sudo apt install maven -y
mvn --version
```

---

## 🚀 Step 3: Install Python 3.11

### Windows

1. **Download Python:**
   - Go to: https://www.python.org/downloads/
   - Download: **Python 3.11.x** (Windows installer 64-bit)

2. **Run Installer:**
   ```
   ⚠️ IMPORTANT: Check "Add Python to PATH" at the bottom!
   ✅ Check "Add Python to PATH"
   Click "Install Now"
   ```

3. **Verify:**
   Open **NEW Command Prompt**:
   ```bash
   python --version
   ```
   Expected: `Python 3.11.x`
   
   ```bash
   pip --version
   ```
   Expected: `pip 23.x.x from ...`

### macOS

```bash
brew install python@3.11
echo 'export PATH="/opt/homebrew/opt/python@3.11/bin:$PATH"' >> ~/.zshrc
source ~/.zshrc
python3 --version
pip3 --version
```

### Linux

```bash
sudo apt install python3 python3-pip python3-venv -y
python3 --version
pip3 --version
```

---

## 🚀 Step 4: Install Git

### Windows

1. **Download:**
   - Go to: https://git-scm.com/download/win
   - Download the 64-bit installer

2. **Install:**
   - Run installer
   - Keep all default options
   - Click Next → Next → ... → Install

3. **Verify:**
   ```bash
   git --version
   ```
   Expected: `git version 2.x.x`

### macOS

```bash
brew install git
git --version
```

### Linux

```bash
sudo apt install git -y
git --version
```

---

## 🚀 Step 5: Install VS Code

1. **Download:**
   - Go to: https://code.visualstudio.com/
   - Download for your OS

2. **Install:**
   - Run installer
   - ✅ Check "Add to PATH"
   - ✅ Check "Add 'Open with Code' to context menu"

3. **Verify:**
   ```bash
   code --version
   ```

---

## 🚀 Step 6: Install VS Code Extensions

Open VS Code, then press `Ctrl+Shift+X` (Windows/Linux) or `Cmd+Shift+X` (macOS) to open Extensions.

### Essential Extensions (Install All)

Search and install each of these:

#### Java Development
| Extension | Publisher | Extension ID |
|-----------|-----------|--------------|
| **Extension Pack for Java** | Microsoft | `vscjava.vscode-java-pack` |
| **Maven for Java** | Microsoft | `vscjava.vscode-maven` |
| **Java Test Runner** | Microsoft | `vscjava.vscode-java-test` |

#### Python Development
| Extension | Publisher | Extension ID |
|-----------|-----------|--------------|
| **Python** | Microsoft | `ms-python.python` |
| **Pylance** | Microsoft | `ms-python.vscode-pylance` |

#### General Development
| Extension | Publisher | Extension ID |
|-----------|-----------|--------------|
| **GitLens** | GitKraken | `eamodio.gitlens` |
| **Thunder Client** | Ranga Vadhineni | `rangav.vscode-thunder-client` |
| **SQLite Viewer** | Florian Klampfer | `qwtel.sqlite-viewer` |
| **XML** | Red Hat | `redhat.vscode-xml` |

### Quick Install via Command Line

Open terminal and run:

```bash
# Java Extensions
code --install-extension vscjava.vscode-java-pack
code --install-extension vscjava.vscode-maven
code --install-extension vscjava.vscode-java-test

# Python Extensions
code --install-extension ms-python.python
code --install-extension ms-python.vscode-pylance

# General Extensions
code --install-extension eamodio.gitlens
code --install-extension rangav.vscode-thunder-client
code --install-extension qwtel.sqlite-viewer
code --install-extension redhat.vscode-xml
```

### Verify Extensions Installed

1. Press `Ctrl+Shift+X`
2. Click on "Installed" tab
3. You should see all extensions listed

---

## 🚀 Step 7: Create Project Structure

### Open Terminal in VS Code

Press `` Ctrl+` `` to open integrated terminal.

### Create Project Directory

```bash
# Navigate to where you want your project (e.g., Desktop or Documents)
cd ~/Desktop   # or cd C:\Users\YourName\Desktop on Windows

# Create main project folder
mkdir mental-health-analysis
cd mental-health-analysis

# Initialize Git
git init
```

### Create Java Project with Maven

```bash
# Create Java project structure
mvn archetype:generate -DgroupId=com.mentalhealth -DartifactId=mental-health-app -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false

# Navigate into Java project
cd mental-health-app

# Create additional directories
mkdir -p src/main/java/com/mentalhealth/controller
mkdir -p src/main/java/com/mentalhealth/model
mkdir -p src/main/java/com/mentalhealth/repository
mkdir -p src/main/java/com/mentalhealth/service
mkdir -p src/main/java/com/mentalhealth/database
mkdir -p src/main/java/com/mentalhealth/session
mkdir -p src/main/java/com/mentalhealth/util
mkdir -p src/main/resources/fxml
mkdir -p src/main/resources/css
mkdir -p src/main/resources/images
mkdir -p src/test/java/com/mentalhealth
mkdir -p data

# Go back to root
cd ..
```

### Create Python API Directory

```bash
# Create Python API folder
mkdir python-api
cd python-api

# Create Python virtual environment
python -m venv venv

# Activate virtual environment
# Windows:
venv\Scripts\activate

# macOS/Linux:
# source venv/bin/activate

# Install Python dependencies
pip install flask flask-cors scikit-learn joblib numpy

# Save dependencies
pip freeze > requirements.txt

# Deactivate for now
deactivate

# Go back to root
cd ..
```

### Create Documentation Folder

```bash
mkdir documentation
```

### Open Project in VS Code

```bash
# Open entire project in VS Code
code .
```

---

## 🚀 Step 8: Configure pom.xml for JavaFX

Replace the contents of `mental-health-app/pom.xml` with this complete configuration:

```xml name=mental-health-app/pom.xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.mentalhealth</groupId>
    <artifactId>mental-health-app</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <name>Mental Health Analysis App</name>
    <description>AI-Based Mental Health Analysis Desktop Application</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <javafx.version>21.0.2</javafx.version>
    </properties>

    <dependencies>
        <!-- JavaFX Controls (Buttons, Labels, TextFields, etc.) -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <!-- JavaFX FXML (For XML-based UI layouts) -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>${javafx.version}</version>
        </dependency>

        <!-- Gson (JSON parsing for API communication) -->
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.10.1</version>
        </dependency>

        <!-- SQLite JDBC Driver (Database) -->
        <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.45.1.0</version>
        </dependency>

        <!-- JUnit 5 (Testing) -->
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>

        <!-- Mockito (Mocking for tests) -->
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Compiler Plugin -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.12.1</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>

            <!-- JavaFX Maven Plugin (to run JavaFX apps) -->
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>com.mentalhealth.Main</mainClass>
                </configuration>
            </plugin>

            <!-- Surefire Plugin (for running tests) -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
            </plugin>

            <!-- JAR Plugin (for creating executable JAR) -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.3.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>com.mentalhealth.Main</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

---

## 🚀 Step 9: Create module-info.java

Create file: `mental-health-app/src/main/java/module-info.java`

```java name=mental-health-app/src/main/java/module-info.java
module com.mentalhealth {
    // JavaFX modules
    requires javafx.controls;
    requires javafx.fxml;
    
    // JSON parsing
    requires com.google.gson;
    
    // Database
    requires java.sql;
    
    // HTTP Client (built into Java 11+)
    requires java.net.http;
    
    // Open packages to JavaFX for reflection (FXML loading)
    opens com.mentalhealth to javafx.fxml;
    opens com.mentalhealth.controller to javafx.fxml;
    opens com.mentalhealth.model to com.google.gson, javafx.fxml;
    
    // Export packages for use
    exports com.mentalhealth;
    exports com.mentalhealth.controller;
    exports com.mentalhealth.model;
    exports com.mentalhealth.service;
    exports com.mentalhealth.repository;
    exports com.mentalhealth.database;
    exports com.mentalhealth.session;
    exports com.mentalhealth.util;
}
```

---

## 🚀 Step 10: Create VS Code Workspace Settings

Create folder `.vscode` in project root and add these files:

### settings.json

```json name=.vscode/settings.json
{
    // Java Settings
    "java.configuration.updateBuildConfiguration": "automatic",
    "java.compile.nullAnalysis.mode": "automatic",
    "java.jdt.ls.vmargs": "-XX:+UseParallelGC -XX:GCTimeRatio=4 -XX:AdaptiveSizePolicyWeight=90 -Dsun.zip.disableMemoryMapping=true -Xmx2G -Xms100m",
    "java.configuration.runtimes": [
        {
            "name": "JavaSE-21",
            "default": true
        }
    ],
    
    // Maven Settings
    "maven.executable.path": "",
    "java.configuration.maven.userSettings": "",
    
    // Python Settings
    "python.defaultInterpreterPath": "${workspaceFolder}/python-api/venv/Scripts/python.exe",
    "python.terminal.activateEnvironment": true,
    
    // Editor Settings
    "editor.formatOnSave": true,
    "editor.tabSize": 4,
    "editor.insertSpaces": true,
    
    // File Associations
    "files.associations": {
        "*.fxml": "xml"
    },
    
    // Exclude from search
    "files.exclude": {
        "**/target": true,
        "**/.git": true,
        "**/venv": true,
        "**/__pycache__": true,
        "**/*.pyc": true
    },
    
    // Terminal Settings
    "terminal.integrated.defaultProfile.windows": "Command Prompt"
}
```

**Note for macOS/Linux:** Change the Python path:
```json
"python.defaultInterpreterPath": "${workspaceFolder}/python-api/venv/bin/python"
```

### launch.json (Debug Configurations)

```json name=.vscode/launch.json
{
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Run JavaFX App",
            "request": "launch",
            "mainClass": "com.mentalhealth.Main",
            "projectName": "mental-health-app",
            "vmArgs": "--module-path ${env:PATH_TO_FX} --add-modules javafx.controls,javafx.fxml"
        },
        {
            "type": "java",
            "name": "Debug JavaFX App",
            "request": "launch",
            "mainClass": "com.mentalhealth.Main",
            "projectName": "mental-health-app",
            "console": "integratedTerminal"
        },
        {
            "name": "Python: Flask API",
            "type": "debugpy",
            "request": "launch",
            "program": "${workspaceFolder}/python-api/app.py",
            "cwd": "${workspaceFolder}/python-api",
            "env": {
                "FLASK_ENV": "development",
                "FLASK_DEBUG": "1"
            },
            "jinja": true
        }
    ],
    "compounds": [
        {
            "name": "Full Stack (Flask + JavaFX)",
            "configurations": ["Python: Flask API", "Run JavaFX App"]
        }
    ]
}
```

### tasks.json (Build Tasks)

```json name=.vscode/tasks.json
{
    "version": "2.0.0",
    "tasks": [
        {
            "label": "Maven: Clean",
            "type": "shell",
            "command": "mvn clean",
            "options": {
                "cwd": "${workspaceFolder}/mental-health-app"
            },
            "group": "build"
        },
        {
            "label": "Maven: Compile",
            "type": "shell",
            "command": "mvn compile",
            "options": {
                "cwd": "${workspaceFolder}/mental-health-app"
            },
            "group": "build"
        },
        {
            "label": "Maven: Run JavaFX",
            "type": "shell",
            "command": "mvn javafx:run",
            "options": {
                "cwd": "${workspaceFolder}/mental-health-app"
            },
            "group": {
                "kind": "build",
                "isDefault": true
            },
            "problemMatcher": []
        },
        {
            "label": "Maven: Test",
            "type": "shell",
            "command": "mvn test",
            "options": {
                "cwd": "${workspaceFolder}/mental-health-app"
            },
            "group": "test"
        },
        {
            "label": "Maven: Package",
            "type": "shell",
            "command": "mvn package",
            "options": {
                "cwd": "${workspaceFolder}/mental-health-app"
            },
            "group": "build"
        },
        {
            "label": "Python: Start Flask API",
            "type": "shell",
            "command": "${workspaceFolder}/python-api/venv/Scripts/python ${workspaceFolder}/python-api/app.py",
            "windows": {
                "command": "${workspaceFolder}\\python-api\\venv\\Scripts\\python ${workspaceFolder}\\python-api\\app.py"
            },
            "linux": {
                "command": "${workspaceFolder}/python-api/venv/bin/python ${workspaceFolder}/python-api/app.py"
            },
            "osx": {
                "command": "${workspaceFolder}/python-api/venv/bin/python ${workspaceFolder}/python-api/app.py"
            },
            "group": "build",
            "isBackground": true,
            "problemMatcher": []
        },
        {
            "label": "Python: Install Dependencies",
            "type": "shell",
            "command": "${workspaceFolder}/python-api/venv/Scripts/pip install -r ${workspaceFolder}/python-api/requirements.txt",
            "windows": {
                "command": "${workspaceFolder}\\python-api\\venv\\Scripts\\pip install -r ${workspaceFolder}\\python-api\\requirements.txt"
            },
            "linux": {
                "command": "${workspaceFolder}/python-api/venv/bin/pip install -r ${workspaceFolder}/python-api/requirements.txt"
            },
            "osx": {
                "command": "${workspaceFolder}/python-api/venv/bin/pip install -r ${workspaceFolder}/python-api/requirements.txt"
            },
            "group": "build"
        }
    ]
}
```

---

## 🚀 Step 11: Create Initial Java Files

### Main.java (Application Entry Point)

Delete the auto-generated `App.java` and create:

```java name=mental-health-app/src/main/java/com/mentalhealth/Main.java
package com.mentalhealth;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

import com.mentalhealth.database.DatabaseManager;

import java.io.IOException;

/**
 * Main entry point for Mental Health Analysis Application
 */
public class Main extends Application {

    private static Stage primaryStage;
    private static final int DEFAULT_WIDTH = 1200;
    private static final int DEFAULT_HEIGHT = 800;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        
        try {
            // Initialize database
            System.out.println("🔄 Initializing database...");
            DatabaseManager.getInstance();
            System.out.println("✅ Database initialized successfully");
            
            // Load the login screen
            System.out.println("🔄 Loading UI...");
            Parent root = FXMLLoader.load(
                getClass().getResource("/fxml/LoginScreen.fxml")
            );
            
            Scene scene = new Scene(root, DEFAULT_WIDTH, DEFAULT_HEIGHT);
            
            // Load CSS if exists
            var cssResource = getClass().getResource("/css/styles.css");
            if (cssResource != null) {
                scene.getStylesheets().add(cssResource.toExternalForm());
            }
            
            stage.setTitle("Mental Health Analysis - AI Powered");
            stage.setScene(scene);
            stage.setMinWidth(800);
            stage.setMinHeight(600);
            stage.show();
            
            System.out.println("✅ Application started successfully!");
            System.out.println("🌐 Make sure Flask API is running on http://localhost:5000");
            
        } catch (IOException e) {
            System.err.println("❌ Failed to load application: " + e.getMessage());
            e.printStackTrace();
            showErrorAlert("Failed to Start", 
                "Could not load the application.\n\n" + e.getMessage());
        }
    }

    @Override
    public void stop() {
        // Clean up resources
        System.out.println("🔄 Shutting down application...");
        DatabaseManager.getInstance().closeConnection();
        System.out.println("✅ Application closed");
    }

    /**
     * Get the primary stage for scene switching
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Switch to a different scene
     * @param fxmlFile Name of FXML file (e.g., "MainWindow.fxml")
     */
    public static void switchScene(String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(
            Main.class.getResource("/fxml/" + fxmlFile)
        );
        primaryStage.getScene().setRoot(root);
    }

    /**
     * Show error alert dialog
     */
    public static void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Show info alert dialog
     */
    public static void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
```

### DatabaseManager.java

```java name=mental-health-app/src/main/java/com/mentalhealth/database/DatabaseManager.java
package com.mentalhealth.database;

import java.sql.*;
import java.nio.file.*;

/**
 * Singleton database manager for SQLite operations
 */
public class DatabaseManager {
    
    private static DatabaseManager instance;
    private static final String DB_FOLDER = "data";
    private static final String DB_NAME = "mental_health.db";
    private static final String DB_PATH = DB_FOLDER + "/" + DB_NAME;
    
    private Connection connection;
    
    /**
     * Private constructor for singleton pattern
     */
    private DatabaseManager() {
        initializeDatabase();
    }
    
    /**
     * Get singleton instance
     */
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    /**
     * Initialize database connection and create tables
     */
    private void initializeDatabase() {
        try {
            // Ensure data directory exists
            Path dataPath = Paths.get(DB_FOLDER);
            if (!Files.exists(dataPath)) {
                Files.createDirectories(dataPath);
                System.out.println("📁 Created data directory: " + dataPath.toAbsolutePath());
            }
            
            // Connect to SQLite database (creates file if not exists)
            String url = "jdbc:sqlite:" + DB_PATH;
            connection = DriverManager.getConnection(url);
            
            System.out.println("✅ Database connected: " + DB_PATH);
            
            // Create tables
            createTables();
            
        } catch (Exception e) {
            System.err.println("❌ Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Create all required database tables
     */
    private void createTables() throws SQLException {
        String[] createStatements = {
            // Users table
            """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                password_hash TEXT NOT NULL,
                name TEXT NOT NULL,
                age INTEGER,
                goal TEXT,
                baseline_stress INTEGER DEFAULT 5,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """,
            
            // Predictions table
            """
            CREATE TABLE IF NOT EXISTS predictions (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                input_text TEXT NOT NULL,
                prediction TEXT NOT NULL,
                confidence REAL NOT NULL,
                severity TEXT,
                color TEXT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
            )
            """,
            
            // Mood entries table
            """
            CREATE TABLE IF NOT EXISTS mood_entries (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                user_id INTEGER NOT NULL,
                mood_score INTEGER NOT NULL CHECK(mood_score >= 1 AND mood_score <= 10),
                notes TEXT,
                entry_date DATE NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
            )
            """,
            
            // Create indexes for better query performance
            "CREATE INDEX IF NOT EXISTS idx_predictions_user_id ON predictions(user_id)",
            "CREATE INDEX IF NOT EXISTS idx_predictions_created_at ON predictions(created_at)",
            "CREATE INDEX IF NOT EXISTS idx_mood_entries_user_id ON mood_entries(user_id)",
            "CREATE INDEX IF NOT EXISTS idx_mood_entries_date ON mood_entries(entry_date)"
        };
        
        try (Statement stmt = connection.createStatement()) {
            for (String sql : createStatements) {
                stmt.execute(sql);
            }
            System.out.println("✅ Database tables created/verified");
        }
    }
    
    /**
     * Get the database connection
     */
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                String url = "jdbc:sqlite:" + DB_PATH;
                connection = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error getting connection: " + e.getMessage());
        }
        return connection;
    }
    
    /**
     * Close the database connection
     */
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error closing database: " + e.getMessage());
        }
    }
    
    /**
     * Execute a query that returns results
     */
    public ResultSet executeQuery(String sql) throws SQLException {
        Statement stmt = getConnection().createStatement();
        return stmt.executeQuery(sql);
    }
    
    /**
     * Execute an update query (INSERT, UPDATE, DELETE)
     */
    public int executeUpdate(String sql) throws SQLException {
        try (Statement stmt = getConnection().createStatement()) {
            return stmt.executeUpdate(sql);
        }
    }
}
```

### LoginController.java

```java name=mental-health-app/src/main/java/com/mentalhealth/controller/LoginController.java
package com.mentalhealth.controller;

import com.mentalhealth.Main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * Controller for Login Screen
 */
public class LoginController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;
    @FXML private Button loginButton;
    @FXML private Hyperlink registerLink;
    @FXML private ProgressIndicator loadingIndicator;
    
    @FXML
    public void initialize() {
        // Hide error label initially
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }
        if (loadingIndicator != null) {
            loadingIndicator.setVisible(false);
        }
        
        System.out.println("✅ LoginController initialized");
    }
    
    @FXML
    public void handleLogin() {
        // Clear previous error
        hideError();
        
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        
        // Validation
        if (username.isEmpty()) {
            showError("Please enter your username");
            usernameField.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            showError("Please enter your password");
            passwordField.requestFocus();
            return;
        }
        
        // TODO: Implement actual authentication with AuthService
        // For now, accept any non-empty credentials for testing
        System.out.println("🔐 Login attempt: " + username);
        
        try {
            // Navigate to main window
            Main.switchScene("MainWindow.fxml");
            System.out.println("✅ Login successful, navigating to main window");
            
        } catch (Exception e) {
            showError("Failed to load main window: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleRegisterLink() {
        try {
            Main.switchScene("RegisterScreen.fxml");
        } catch (Exception e) {
            showError("Failed to load registration screen");
            e.printStackTrace();
        }
    }
    
    private void showError(String message) {
        if (errorLabel != null) {
            errorLabel.setText(message);
            errorLabel.setVisible(true);
        }
    }
    
    private void hideError() {
        if (errorLabel != null) {
            errorLabel.setVisible(false);
        }
    }
}
```

---

## 🚀 Step 12: Create Initial FXML Files

### LoginScreen.fxml

```xml name=mental-health-app/src/main/resources/fxml/LoginScreen.fxml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<?import javafx.scene.text.*?>
<?import javafx.geometry.Insets?>

<BorderPane xmlns:fx="http://javafx.com/fxml"
            fx:controller="com.mentalhealth.controller.LoginController"
            prefWidth="1200" prefHeight="800"
            style="-fx-background-color: #f5f5f5;">
    
    <center>
        <VBox alignment="CENTER" spacing="20" maxWidth="400">
            <padding>
                <Insets top="50" right="50" bottom="50" left="50"/>
            </padding>
            
            <!-- Logo/Title -->
            <Label text="🧠 Mental Health Analysis" 
                   style="-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2196F3;"/>
            
            <Label text="AI-Powered Mental Wellness Companion"
                   style="-fx-font-size: 14px; -fx-text-fill: #757575;"/>
            
            <!-- Spacer -->
            <Region prefHeight="30"/>
            
            <!-- Login Form Card -->
            <VBox spacing="15" 
                  style="-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);">
                <padding>
                    <Insets top="30" right="30" bottom="30" left="30"/>
                </padding>
                
                <Label text="Welcome Back" 
                       style="-fx-font-size: 20px; -fx-font-weight: bold;"/>
                
                <Label text="Sign in to continue"
                       style="-fx-text-fill: #757575;"/>
                
                <!-- Error Label -->
                <Label fx:id="errorLabel" 
                       text="Error message" 
                       visible="false"
                       style="-fx-text-fill: #F44336; -fx-font-size: 12px;"/>
                
                <!-- Username Field -->
                <VBox spacing="5">
                    <Label text="Username" style="-fx-font-weight: bold;"/>
                    <TextField fx:id="usernameField" 
                               promptText="Enter your username"
                               prefHeight="40"
                               style="-fx-background-radius: 5;"/>
                </VBox>
                
                <!-- Password Field -->
                <VBox spacing="5">
                    <Label text="Password" style="-fx-font-weight: bold;"/>
                    <PasswordField fx:id="passwordField" 
                                   promptText="Enter your password"
                                   prefHeight="40"
                                   style="-fx-background-radius: 5;"
                                   onAction="#handleLogin"/>
                </VBox>
                
                <!-- Loading Indicator -->
                <ProgressIndicator fx:id="loadingIndicator" 
                                   visible="false" 
                                   prefWidth="30" prefHeight="30"/>
                
                <!-- Login Button -->
                <Button fx:id="loginButton" 
                        text="Sign In" 
                        onAction="#handleLogin"
                        prefWidth="Infinity"
                        prefHeight="45"
                        style="-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;"/>
                
                <!-- Register Link -->
                <HBox alignment="CENTER" spacing="5">
                    <Label text="Don't have an account?"/>
                    <Hyperlink fx:id="registerLink" 
                               text="Sign Up" 
                               onAction="#handleRegisterLink"
                               style="-fx-text-fill: #2196F3;"/>
                </HBox>
            </VBox>
            
            <!-- Footer -->
            <Region VBox.vgrow="ALWAYS"/>
            <Label text="© 2026 Mental Health Analysis. All rights reserved."
                   style="-fx-text-fill: #9E9E9E; -fx-font-size: 11px;"/>
        </VBox>
    </center>
</BorderPane>
```

### MainWindow.fxml (Placeholder)

```xml name=mental-health-app/src/main/resources/fxml/MainWindow.fxml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<?import javafx.geometry.Insets?>

<BorderPane xmlns:fx="http://javafx.com/fxml"
            fx:controller="com.mentalhealth.controller.MainController"
            prefWidth="1200" prefHeight="800"
            style="-fx-background-color: #f5f5f5;">
    
    <!-- Left Sidebar -->
    <left>
        <VBox prefWidth="250" spacing="5"
              style="-fx-background-color: #2196F3;">
            <padding>
                <Insets top="20" right="15" bottom="20" left="15"/>
            </padding>
            
            <!-- Logo -->
            <Label text="🧠 Mental Health" 
                   style="-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;"/>
            <Label text="Analysis System"
                   style="-fx-font-size: 14px; -fx-text-fill: rgba(255,255,255,0.8);"/>
            
            <Region prefHeight="30"/>
            
            <!-- Navigation Buttons -->
            <Button text="📊  Dashboard" 
                    prefWidth="Infinity" prefHeight="45"
                    style="-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: CENTER_LEFT; -fx-background-radius: 5; -fx-cursor: hand;"/>
            
            <Button text="📝  New Analysis" 
                    prefWidth="Infinity" prefHeight="45"
                    style="-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: CENTER_LEFT; -fx-background-radius: 5; -fx-cursor: hand;"/>
            
            <Button text="📜  History" 
                    prefWidth="Infinity" prefHeight="45"
                    style="-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: CENTER_LEFT; -fx-background-radius: 5; -fx-cursor: hand;"/>
            
            <Button text="😊  Mood Tracker" 
                    prefWidth="Infinity" prefHeight="45"
                    style="-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: CENTER_LEFT; -fx-background-radius: 5; -fx-cursor: hand;"/>
            
            <Button text="👤  Profile" 
                    prefWidth="Infinity" prefHeight="45"
                    style="-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: CENTER_LEFT; -fx-background-radius: 5; -fx-cursor: hand;"/>
            
            <!-- Spacer -->
            <Region VBox.vgrow="ALWAYS"/>
            
            <!-- Logout Button -->
            <Button text="🚪  Logout" 
                    prefWidth="Infinity" prefHeight="45"
                    style="-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; -fx-font-size: 14px; -fx-alignment: CENTER_LEFT; -fx-background-radius: 5; -fx-cursor: hand;"/>
        </VBox>
    </left>
    
    <!-- Main Content Area -->
    <center>
        <VBox alignment="CENTER" spacing="20">
            <padding>
                <Insets top="40" right="40" bottom="40" left="40"/>
            </padding>
            
            <Label text="🎉 Welcome to Mental Health Analysis!" 
                   style="-fx-font-size: 28px; -fx-font-weight: bold;"/>
            
            <Label text="Your app is set up correctly and ready for development."
                   style="-fx-font-size: 16px; -fx-text-fill: #757575;"/>
            
            <Region prefHeight="20"/>
            
            <VBox spacing="10" alignment="CENTER"
                  style="-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 30;">
                
                <Label text="✅ JavaFX is working" style="-fx-text-fill: #4CAF50; -fx-font-size: 14px;"/>
                <Label text="✅ FXML is loading correctly" style="-fx-text-fill: #4CAF50; -fx-font-size: 14px;"/>
                <Label text="✅ Controllers are connected" style="-fx-text-fill: #4CAF50; -fx-font-size: 14px;"/>
                <Label text="✅ Database is initialized" style="-fx-text-fill: #4CAF50; -fx-font-size: 14px;"/>
                
                <Region prefHeight="10"/>
                
                <Label text="⚠️ Next Step: Start Flask API and test /predict endpoint"
                       style="-fx-text-fill: #FF9800; -fx-font-size: 14px;"/>
            </VBox>
        </VBox>
    </center>
</BorderPane>
```

### MainController.java (Placeholder)

```java name=mental-health-app/src/main/java/com/mentalhealth/controller/MainController.java
package com.mentalhealth.controller;

import javafx.fxml.FXML;

/**
 * Controller for Main Window
 */
public class MainController {
    
    @FXML
    public void initialize() {
        System.out.println("✅ MainController initialized");
    }
}
```

### RegisterScreen.fxml (Placeholder)

```xml name=mental-health-app/src/main/resources/fxml/RegisterScreen.fxml
<?xml version="1.0" encoding="UTF-8"?>

<?import javafx.scene.control.*?>
<?import javafx.scene.layout.*?>
<?import javafx.geometry.Insets?>

<BorderPane xmlns:fx="http://javafx.com/fxml"
            fx:controller="com.mentalhealth.controller.RegisterController"
            prefWidth="1200" prefHeight="800"
            style="-fx-background-color: #f5f5f5;">
    
    <center>
        <VBox alignment="CENTER" spacing="20" maxWidth="400">
            <padding>
                <Insets top="50" right="50" bottom="50" left="50"/>
            </padding>
            
            <Label text="🧠 Create Account" 
                   style="-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #2196F3;"/>
            
            <VBox spacing="15" 
                  style="-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);">
                <padding>
                    <Insets top="30" right="30" bottom="30" left="30"/>
                </padding>
                
                <Label text="Registration coming soon..."
                       style="-fx-font-size: 16px; -fx-text-fill: #757575;"/>
                
                <Hyperlink text="← Back to Login" 
                           onAction="#handleBackToLogin"
                           style="-fx-text-fill: #2196F3;"/>
            </VBox>
        </VBox>
    </center>
</BorderPane>
```

### RegisterController.java (Placeholder)

```java name=mental-health-app/src/main/java/com/mentalhealth/controller/RegisterController.java
package com.mentalhealth.controller;

import com.mentalhealth.Main;
import javafx.fxml.FXML;

/**
 * Controller for Register Screen
 */
public class RegisterController {
    
    @FXML
    public void initialize() {
        System.out.println("✅ RegisterController initialized");
    }
    
    @FXML
    public void handleBackToLogin() {
        try {
            Main.switchScene("LoginScreen.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

---

## 🚀 Step 13: Create Basic CSS Stylesheet

```css name=mental-health-app/src/main/resources/css/styles.css
/* 
 * Mental Health Analysis - Main Stylesheet
 * Created for JavaFX Application
 */

/* Root Variables */
.root {
    -fx-font-family: "Segoe UI", "Arial", sans-serif;
    -fx-font-size: 14px;
    -primary-color: #2196F3;
    -primary-dark: #1976D2;
    -accent-color: #FF9800;
    -success-color: #4CAF50;
    -warning-color: #FF9800;
    -danger-color: #F44336;
    -critical-color: #9C27B0;
    -text-primary: #212121;
    -text-secondary: #757575;
    -background: #f5f5f5;
    -surface: #ffffff;
}

/* Buttons */
.button {
    -fx-cursor: hand;
}

.button:hover {
    -fx-opacity: 0.9;
}

.button:pressed {
    -fx-opacity: 0.8;
}

/* Primary Button */
.primary-button {
    -fx-background-color: #2196F3;
    -fx-text-fill: white;
    -fx-font-weight: bold;
    -fx-background-radius: 5;
    -fx-padding: 10 20;
}

.primary-button:hover {
    -fx-background-color: #1976D2;
}

/* Text Fields */
.text-field, .password-field, .text-area {
    -fx-background-radius: 5;
    -fx-border-radius: 5;
    -fx-border-color: #E0E0E0;
    -fx-border-width: 1;
    -fx-padding: 10;
}

.text-field:focused, .password-field:focused, .text-area:focused {
    -fx-border-color: #2196F3;
    -fx-border-width: 2;
}

/* Labels */
.title-label {
    -fx-font-size: 24px;
    -fx-font-weight: bold;
}

.subtitle-label {
    -fx-font-size: 16px;
    -fx-text-fill: #757575;
}

.error-label {
    -fx-text-fill: #F44336;
    -fx-font-size: 12px;
}

/* Cards */
.card {
    -fx-background-color: white;
    -fx-background-radius: 10;
    -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);
    -fx-padding: 20;
}

/* Sidebar */
.sidebar {
    -fx-background-color: #2196F3;
}

.sidebar-button {
    -fx-background-color: transparent;
    -fx-text-fill: white;
    -fx-font-size: 14px;
    -fx-alignment: CENTER_LEFT;
    -fx-padding: 12 15;
    -fx-background-radius: 5;
}

.sidebar-button:hover {
    -fx-background-color: rgba(255,255,255,0.2);
}

.sidebar-button-active {
    -fx-background-color: rgba(255,255,255,0.3);
}

/* Severity Colors */
.severity-low {
    -fx-background-color: #4CAF50;
    -fx-text-fill: white;
}

.severity-moderate {
    -fx-background-color: #FF9800;
    -fx-text-fill: white;
}

.severity-high {
    -fx-background-color: #F44336;
    -fx-text-fill: white;
}

.severity-critical {
    -fx-background-color: #9C27B0;
    -fx-text-fill: white;
}

/* Progress Bar */
.progress-bar {
    -fx-accent: #2196F3;
}

.progress-bar > .track {
    -fx-background-color: #E0E0E0;
    -fx-background-radius: 5;
}

.progress-bar > .bar {
    -fx-background-color: #2196F3;
    -fx-background-radius: 5;
}

/* Hyperlink */
.hyperlink {
    -fx-text-fill: #2196F3;
    -fx-underline: false;
}

.hyperlink:hover {
    -fx-underline: true;
}

/* Scroll Pane */
.scroll-pane {
    -fx-background-color: transparent;
}

.scroll-pane > .viewport {
    -fx-background-color: transparent;
}
```

---

## 🚀 Step 14: Create Python Flask API

```python name=python-api/app.py
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





#Step 15: Install VS Code Extensions
Once VS Code opens, press Ctrl+Shift+X to open Extensions panel.

Search and install these extensions:

Extension Name	Publisher	Install Button
Extension Pack for Java	Microsoft	Click Install
Maven for Java	Microsoft	Click Install
Python	Microsoft	Click Install
Pylance	Microsoft	Click Install
XML	Red Hat	Click Install

##To Compile When Code ready
cd mental-health-app
mvn clean compile
mvn javafx:run
