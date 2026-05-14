# Mental Health Analysis Application

A desktop application that uses a fine-tuned DistilBERT model to analyse free-text input and classify it into seven mental health categories, giving users personalised insights, mood tracking, and actionable self-care recommendations.

---

## Table of Contents

1. [Introduction](#introduction)
2. [Aim](#aim)
3. [How It Works](#how-it-works)
4. [Tech Stack](#tech-stack)
5. [GRASP Patterns](#grasp-patterns)
6. [Project Structure](#project-structure)
7. [Running the App](#running-the-app)
8. [Team](#team)

---

## Introduction

Mental health is a growing concern, yet access to timely, personalised support remains limited. This application bridges that gap by combining Natural Language Processing with a clean desktop interface. A user types how they are feeling — a journal entry, a few sentences, anything — and the app classifies their mental state, tracks it over time, and offers evidence-based coping strategies.

The system runs entirely offline after setup (no data ever leaves the device), which makes it suitable for sensitive mental health contexts.

---

## Aim

| Goal | Description |
|------|-------------|
| **Early detection** | Identify patterns such as anxiety, depression, or stress before they escalate |
| **Trend awareness** | Show mood history and weekly trends so users can see changes over time |
| **Personalised guidance** | Recommend coping strategies tailored to the user's current mood state |
| **Privacy-first** | All data stays local — SQLite database on the user's machine, model runs on CPU/GPU locally |
| **Accessibility** | Simple, friendly UI that requires no clinical knowledge to use |

---

## How It Works

### End-to-end flow

```
User types text
      │
      ▼
JavaFX Desktop App (AnalysisController)
      │  HTTP POST /predict
      ▼
Flask REST API (localhost:5000)
      │  tokenise → DistilBERT forward pass → softmax
      ▼
Prediction  {label, confidence, severity, colour}
      │
      ▼
Stored in SQLite  →  Dashboard, History, Recommendations updated
```

### Screens

| Screen | Purpose |
|--------|---------|
| **Login / Register** | Secure account creation with PBKDF2-hashed passwords and security-question recovery |
| **Dashboard** | Live metrics: streak, dominant mood, trend (Improving / Stable / Declining), 7-day chart, daily tip, self-care checklist |
| **New Analysis** | Free-text input → real-time BERT classification with confidence bar |
| **Mood Tracker** | Calendar-style mood score entry, separate from AI analysis |
| **History** | Paginated, filterable log of all past analyses; supports starring, tagging, notes, and CSV export |
| **Recommendations** | Mood-adaptive self-care plan with step-by-step guidance and weekly progress stats |
| **Profile** | Edit personal details, emergency contacts, affirmations, and account security |
| **About** | Project information and team |

### ML Model

- **Architecture**: DistilBERT (`distilbert-base-uncased`) — a lightweight 66M-parameter transformer
- **Task**: Single-label sequence classification
- **Classes**: `Anxiety`, `Bipolar`, `Depression`, `Normal`, `Personality Disorder`, `Stress`, `Suicidal`
- **Inference**: Model and tokeniser load from `python-api/` at startup; runs on CUDA if available, CPU otherwise
- **API endpoint**: `POST /predict` — accepts `{"text": "..."}`, returns label, confidence, severity, colour

---

## Tech Stack

### Java Desktop Application

| Technology | Version | Role |
|-----------|---------|------|
| **Java** | 21 | Language |
| **JavaFX** | 21.0.2 | UI framework (FXML + CSS) |
| **SQLite via Xerial JDBC** | 3.45.1 | Local persistent storage |
| **Gson** | 2.10.1 | JSON parsing for API communication |
| **Maven** | 3.x | Build and dependency management |

### Python ML API

| Technology | Version | Role |
|-----------|---------|------|
| **Python** | 3.10+ | Language |
| **Flask** | 3.x | REST API server |
| **Flask-CORS** | – | Cross-origin requests from Java client |
| **Hugging Face Transformers** | 4.x | DistilBERT model + tokeniser |
| **PyTorch** | 2.x | Model inference engine |
| **NumPy** | – | Class label loading (`.npy`) |
| **python-dotenv** | – | Environment variable management |

### AI Report Generation (Consoling Report)

The **Consoling Report** feature sends the user's mood history to a large language model and receives a personalised, empathetic wellness summary. The integration is entirely server-side inside the Flask API.

#### Flow

```
Java App (MLAPIService)
    │
    │  POST /generate-report
    │  { user_name, mood_history: [{date, prediction, confidence, input_text, severity}, …] }
    ▼
Flask API (app.py — /generate-report)
    │
    │  1. Aggregates mood counts → dominant mood
    │  2. Builds a structured natural-language prompt
    │  3. Calls Hugging Face Router API with model fallback chain
    ▼
Hugging Face Router  (router.huggingface.co/v1/chat/completions)
    │
    │  Tries models in order until one succeeds:
    │    1. Qwen/Qwen2.5-Coder-32B-Instruct
    │    2. deepseek-ai/DeepSeek-R1
    │    3. Qwen/Qwen2.5-7B-Instruct-1M
    │    4. moonshotai/Kimi-K2-Instruct-0905
    ▼
Generated Report Text (200–350 words)
    │
    ▼
Flask → Java App → displayed in MoodTrackerController
        + saved to consoling_reports table in SQLite
```

#### Prompt design

The prompt instructs the model to act as **MindCore Companion** — a warm, caring friend. It injects the user's name, each day's mood label, confidence score, severity, and the user's own words verbatim. The model is told to:

- Greet by name, reference actual entries day by day
- Be encouraging and provide gentle perspective
- Include the Pakistan crisis helpline (`0311-7786264`) if `Suicidal` mood appears
- End with hope; tone like a best friend texting
- Stay within 200–350 words

#### Configuration

| Setting | Value |
|---------|-------|
| Auth | `HF_API_TOKEN` in `python-api/.env` |
| Endpoint | `https://router.huggingface.co/v1/chat/completions` |
| Max tokens | 512 |
| Temperature | 0.7 |
| Top-p | 0.9 |
| Timeout | 60 s per model (90 s after 503 retry) |

> **Note:** If all four models fail or the token is missing, the endpoint returns `{ success: false, error: "…" }` and the Java app surfaces the message to the user.

---

### Tooling

| Tool | Role |
|------|------|
| **Git** | Version control |
| **PlantUML** | UML diagrams (class, sequence) |
| **run.ps1** | Windows PowerShell launcher |

---

## GRASP Patterns

The architecture applies all nine GRASP (General Responsibility Assignment Software Patterns) principles. Below are the seven most visible ones with concrete examples from the codebase.

---

### 1. Creator

> *Assign responsibility for creating object B to class A if A contains, records, or closely uses B.*

| Creator | Creates | Reason |
|---------|---------|--------|
| `DashboardService` | `DashboardData` | Aggregates all prediction data needed to populate it |
| `HistoryRepository` | `HistoryEntry` | Holds the ResultSet data required to construct it |
| `AuthService` | `User` | Has all registration fields and performs validation |
| All Repositories | Domain objects | Map database rows → model instances |

```java
// DashboardService.getDashboardData() — Creator
DashboardData data = new DashboardData();
data.totalAnalyses = allPredictions.size();
data.dominantMood  = computeDominantMood(last7Days);
return data;
```

---

### 2. Information Expert

> *Assign responsibility to the class that has the information needed to fulfill it.*

| Expert | Owns | Responsibility |
|--------|------|---------------|
| `UserRepository` | User table schema | All user CRUD operations |
| `PredictionRepository` | Prediction table | Trend queries, 7/30-day windows |
| `MoodUtils` | Mood → score/colour/emoji mappings | All mood conversions across the app |
| `PasswordUtils` | PBKDF2 algorithm | Hashing, verification, rehash detection |
| `ValidationUtils` | Regex patterns | All input validation rules |

```java
// MoodUtils is the sole expert on mood-to-colour mapping
public static String getMoodColor(String mood) {
    return switch (mood.trim()) {
        case "Normal"     -> "#10b981";
        case "Depression" -> "#ef4444";
        case "Suicidal"   -> "#991b1b";
        // ...
    };
}
```

---

### 3. Controller

> *Assign responsibility for receiving and handling system events to a controller, which delegates to other objects.*

- **`BaseController`** (abstract) provides shared navigation handlers (`handleDashboard()`, `handleLogout()`, etc.) inherited by all 10 screen controllers.
- Each concrete controller is a *thin* coordinator: it reads from the UI, calls a service, then updates the UI.

```java
// LoginController — receives event, delegates to AuthService, updates UI
@FXML
public void onLoginButtonClicked() {
    User user = authService.login(
        usernameField.getText(), passwordField.getText()
    );
    if (user != null) Main.switchScene("MainWindow.fxml");
}
```

Dependency chain:
```
Controller  →  Service  →  Repository Interface  →  Repository  →  SQLite
```

---

### 4. Low Coupling

> *Minimise dependencies between classes.*

- Controllers depend on **service interfaces**, not concrete classes.
- Services depend on **repository interfaces**, not concrete repositories.
- `MoodUtils`, `PasswordUtils`, `ValidationUtils` are stateless utility classes with zero dependencies.
- Dependency injection via constructor allows swapping implementations without touching callers.

```java
// DashboardService accepts an interface — decoupled from SQLite details
public DashboardService(IPredictionRepository repo, SelfCareRepository sc) {
    this.predictionRepository = repo;   // any IPredicitionRepository works
}
```

---

### 5. High Cohesion

> *Keep related responsibilities together; a class should do one thing well.*

| Class | Responsibility | What it does NOT do |
|-------|---------------|---------------------|
| `UserRepository` | User persistence only | No prediction logic |
| `DashboardService` | Dashboard metrics only | No history filtering |
| `HistoryController` | History screen events only | No dashboard updates |
| `MoodUtils` | Mood conversions only | No database access |
| `CardAnimations` | JavaFX animation only | No business logic |

---

### 6. Polymorphism

> *Use interfaces to allow behaviour to vary across implementations.*

- **Service interfaces** (`IDashboardService`, `IHistoryService`, `IRecommendationService`) allow mock implementations in tests.
- **Repository interfaces** (`IUserRepository`, `IPredictionRepository`, `IEmergencyContactRepository`) decouple services from SQLite.
- **`BaseController`** defines a polymorphic navigation contract; each screen controller provides its own `getRoot()`.

```java
public abstract class BaseController {
    protected abstract BorderPane getRoot();   // each subclass provides its pane
    public void handleDashboard() { navigateTo("MainWindow.fxml"); }
}
```

---

### 7. Pure Fabrication

> *Introduce an artificial class that has no real-world counterpart to achieve low coupling and high cohesion.*

| Fabrication | Why it was created |
|-------------|-------------------|
| `AuthService` | No real-world "auth service" object — created to isolate login/register logic |
| `DashboardService` | Artificial aggregator so the controller gets one `DashboardData` object |
| `RecommendationService` | Artificial generator — no real-world equivalent |
| `DashboardData` | Value object that packages all dashboard metrics; never persisted |
| `HistoryEntry` | Composed from `Prediction` + metadata; does not map 1-to-1 to any table |
| `MoodUtils`, `PasswordUtils`, `CardAnimations` | Stateless helpers with no domain counterpart |

---

### 8. Indirection

> *Introduce an intermediate object to reduce direct coupling.*

```
WITHOUT indirection:   Controller  →  Repository  →  Database
WITH indirection:      Controller  →  Service  →  IRepository  →  Repository  →  Database
```

The **Service layer** acts as the indirection point: controllers never import repository classes, and services never import concrete repository implementations — they use interfaces.

---

### 9. Protected Variations

> *Shield elements from the impact of variations in other elements by wrapping the unstable point with a stable interface.*

- **ML API variation**: `MLAPIService` wraps all HTTP calls to Flask. If the API URL, request format, or model changes, only `MLAPIService` needs to change — no controller is affected.
- **Database variation**: Repository interfaces protect services from schema changes.
- **UI variation**: FXML files and CSS are fully decoupled from Java logic — redesigning a screen does not require touching a controller.

---

## Project Structure

```
Starter-Code/
├── mental-health-app/          # JavaFX desktop application
│   ├── src/main/java/com/mentalhealth/
│   │   ├── controller/         # 11 FXML controllers
│   │   ├── service/            # Business logic + interfaces
│   │   ├── repository/         # Data access + interfaces
│   │   ├── model/              # Domain objects (User, Prediction, …)
│   │   ├── database/           # DatabaseManager (singleton)
│   │   ├── util/               # MoodUtils, PasswordUtils, ValidationUtils, CardAnimations
│   │   └── Main.java           # JavaFX entry point, scene management
│   ├── src/main/resources/
│   │   ├── fxml/               # 10 screen layouts
│   │   └── css/styles.css      # Global stylesheet
│   └── pom.xml
├── python-api/                 # Flask + DistilBERT inference API
│   ├── app.py                  # REST endpoints (/predict, /health)
│   ├── model.safetensors       # Fine-tuned DistilBERT weights
│   ├── tokenizer.json          # Tokeniser vocabulary
│   ├── classes.npy             # Label array
│   └── requirements.txt
├── DOCX/                       # Architecture docs and UML diagrams
├── Makefile                    # Unix/Git-Bash build targets
└── run.ps1                     # Windows PowerShell launcher
```

---

## Running the App

### Prerequisites

| Requirement | Version |
|------------|---------|
| Java JDK | 21 |
| Apache Maven | 3.6+ |
| Python | 3.10+ |
| (Optional) CUDA GPU | For faster inference |

### Environment Setup (Required for AI Report)

The **Consoling Report** feature requires a free Hugging Face API token.

1. Copy the example env file:
   ```bash
   cp python-api/.env.example python-api/.env
   ```
   Or on PowerShell:
   ```powershell
   Copy-Item python-api\.env.example python-api\.env
   ```

2. Get a free token at [huggingface.co](https://huggingface.co) → Settings → Access Tokens → New Token (read access is enough)

3. Open `python-api/.env` and replace the placeholder:
   ```
   HF_API_TOKEN=your_actual_token_here
   ```

> If you skip this step the app still works — only the AI report generation will be disabled.

---

The Python virtual environment (`python-api/venv/`) and Maven dependencies must be present. To install them:

```bash
# Unix / Git Bash
make install

# PowerShell
.\run.ps1 install
```

---

### Option A — Single command (recommended)

**PowerShell:**
```powershell
.\run.ps1 run
```

**Git Bash / Unix with `make`:**
```bash
make run
```

Both start the Flask API in the background, wait 10 seconds for the model to load, then launch the Java app.

---

### Option B — Two terminals (manual)

**Terminal 1 — Flask API:**
```bash
# Git Bash / macOS / Linux
cd python-api
source venv/bin/activate        # Windows: venv\Scripts\activate
python app.py
```

```powershell
# PowerShell
cd python-api
.\venv\Scripts\Activate.ps1
python app.py
```

**Terminal 2 — Java app:**
```bash
# Git Bash / macOS / Linux
cd mental-health-app
mvn javafx:run
```

```powershell
# PowerShell
cd mental-health-app
mvn javafx:run
```

---

### All available commands

| `run.ps1` | `make` | Action |
|-----------|--------|--------|
| `.\run.ps1 run` | `make run` | Start everything |
| `.\run.ps1 api` | `make api` | Flask API only |
| `.\run.ps1 app` | `make app` | Java app only |
| `.\run.ps1 build` | `make build` | Compile Java (no run) |
| `.\run.ps1 install` | `make install` | Install all dependencies |
| `.\run.ps1 stop` | `make stop` | Kill Flask process |
| `.\run.ps1 help` | `make help` | Show command list |

> **Installing `make` on Windows** (optional):
> ```powershell
> winget install GnuWin32.Make
> ```

---

## Team

### Muhammad Mustafa — `24i-0525`
**Developer & Team Manager**

Led the team, architected the full-stack application, and drove end-to-end development from UI design to backend integration.

---

### Muhammad Mughees Tariq — `24i-0806`
**Developer**

Core developer responsible for implementing key features, database design, and ensuring robust functionality across the application.

---

### Anas Mumtaz — `24i-0653`
**Analyst**

Conducted requirements analysis, defined system specifications, and validated the ML model's accuracy against mental health datasets.

---

*SDA Course Project*
