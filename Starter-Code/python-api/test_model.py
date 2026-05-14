
import sys, os
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))

import numpy as np
import torch
from transformers import AutoTokenizer, AutoModelForSequenceClassification

# ---- Load model ----
model_dir = os.path.dirname(os.path.abspath(__file__))
tokenizer = AutoTokenizer.from_pretrained(model_dir)
model = AutoModelForSequenceClassification.from_pretrained(model_dir)
model.eval()
device = torch.device("cpu")
classes = np.load(os.path.join(model_dir, "classes.npy"), allow_pickle=True)

from app import preprocess_text, analyze_text_signals, adjust_probabilities

def predict(text):
    processed = preprocess_text(text)
    inputs = tokenizer(processed, return_tensors="pt", truncation=True, padding=True, max_length=512)
    with torch.no_grad():
        logits = model(**inputs).logits
        probs = torch.softmax(logits, dim=1)[0].numpy()
    adjusted = adjust_probabilities(probs, classes, text, preprocessed_text=processed)
    idx = int(np.argmax(adjusted))
    return str(classes[idx]), float(adjusted[idx])

# ============================================================
# TEST CASES
# ============================================================
test_cases = [
    # ---- STANDARD (one clear category) ----
    ("I feel so happy and grateful today", "Normal"),
    ("Life is wonderful, everything is going great", "Normal"),
    ("I feel calm and at peace with everything", "Normal"),
    ("I am extremely stressed about my exams", "Stress"),
    ("Work deadlines are piling up and I can't cope", "Stress"),
    ("My workload is suffocating, I'm burned out", "Stress"),
    ("I keep having panic attacks and can't breathe", "Anxiety"),
    ("I'm terrified of going outside, social anxiety is ruining my life", "Anxiety"),
    ("I can't stop worrying about everything, my heart keeps racing", "Anxiety"),
    ("I feel so depressed and hopeless, nothing matters anymore", "Depression"),
    ("I can't get out of bed, I've lost interest in everything", "Depression"),
    ("I feel completely numb and empty inside", "Depression"),
    ("I go from extremely happy to extremely sad within hours", "Bipolar"),
    ("One day I feel invincible and the next I can't move from depression", "Bipolar"),
    ("My mood swings are out of control, manic highs and depressive lows", "Bipolar"),
    ("I feel like a completely different person every day, I don't know who I am", "Personality disorder"),
    ("My fear of abandonment ruins all my relationships", "Personality disorder"),
    ("I have unstable relationships and an intense fear of abandonment", "Personality disorder"),
    ("I want to kill myself, I see no reason to live", "Suicidal"),
    ("I've been thinking about ending my life, I feel better off dead", "Suicidal"),
    ("I wish I was never born, no one would miss me", "Suicidal"),

    # ---- NEGATION (should flip or suppress the keyword class) ----
    ("I am not depressed, I'm doing fine", "Normal"),
    ("I don't have anxiety anymore, therapy helped a lot", "Normal"),
    ("I'm no longer stressed about work, things are under control", "Normal"),
    ("I am not suicidal, please don't worry about me", "Normal"),
    ("I used to be depressed but I'm not sad anymore", "Normal"),
    ("I never feel anxious at social events", "Normal"),

    # ---- MIXED / CONTRAST (second clause after 'but' should dominate) ----
    ("I feel happy but also very anxious about tomorrow", "Anxiety"),
    ("Things are okay but I'm extremely stressed about deadlines", "Stress"),
    ("My life looks fine but I feel completely empty inside", "Depression"),
    ("I sometimes enjoy things but mostly I feel hopeless and numb", "Depression"),
    ("I was doing well but now I have panic attacks every day", "Anxiety"),
    ("Overall I'm fine but my mood swings are getting worse", "Bipolar"),

    # ---- METAPHORICAL / IDIOMATIC ----
    ("The deadlines are killing me, I'm pulling my hair out", "Stress"),
    ("I feel like I'm drowning in sorrow and can't come up for air", "Depression"),
    ("It's like a dark cloud follows me everywhere I go", "Depression"),
    ("My emotions are a rollercoaster, up one minute and crashing the next", "Bipolar"),
    ("I have butterflies in my stomach every time I leave the house", "Anxiety"),
    ("I feel like an empty shell just going through the motions", "Depression"),
    ("I hit rock bottom and I don't know how to get back up", "Depression"),
    ("The weight of the world is on my shoulders", "Stress"),

    # ---- EMPHASIS / CAPS ----
    ("I am SO ANXIOUS right now I can't think straight", "Anxiety"),
    ("I'm EXTREMELY stressed and completely overwhelmed!!!", "Stress"),
    ("HELP I am having a panic attack and can't breathe!", "Anxiety"),
    ("I feel NOTHING. Absolutely NOTHING. Just emptiness.", "Depression"),

    # ---- PREVIOUS 30 TEST CASES (regression check) ----
    ("I feel very stressed and overwhelmed with everything", "Stress"),
    ("My anxiety is through the roof, I can't relax", "Anxiety"),
    ("I have been feeling really down and depressed lately", "Depression"),
    ("I want to end my life, there is no point anymore", "Suicidal"),
    ("Life is beautiful and I'm enjoying every moment", "Normal"),
    ("My moods keep swinging from extreme highs to extreme lows", "Bipolar"),
    ("I don't know who I am anymore, I feel like a different person every day", "Personality disorder"),
    ("These deadlines are killing me, so much work to do", "Stress"),
    ("I feel happy but then suddenly extremely sad for no reason", "Bipolar"),
    ("My relationships are unstable and I have intense fear of abandonment", "Personality disorder"),
    ("I've been having severe panic attacks and heart palpitations", "Anxiety"),
    ("I feel worthless and can't find any reason to keep going", "Depression"),
    ("I feel on top of the world one day and cant get out of bed the next", "Bipolar"),
    ("I can't stop my intrusive thoughts and obsessive behavior", "Anxiety"),
    ("The traffic and waiting is stressing me out so much", "Stress"),
    ("I've been self-harming and thinking about ending it all", "Suicidal"),
    ("I feel peaceful and content with my life right now", "Normal"),
    ("I keep pushing people away because I fear they will abandon me", "Personality disorder"),
    ("I have no motivation, everything feels pointless", "Depression"),
    ("I wish I was dead, nobody would care if I disappeared", "Suicidal"),
    ("I'm so terrified of being in crowds, I can't leave my house", "Anxiety"),
    ("I go from spending sprees and euphoria to complete despair", "Bipolar"),
    ("I wish I was never born, I feel like a burden to everyone", "Suicidal"),
    ("I don't know what's real anymore, I feel like I'm dissociating", "Personality disorder"),
    ("I'm burnt out from work, completely drained and exhausted", "Stress"),
    ("I feel grateful and blessed, life is truly wonderful", "Normal"),
    ("I feel completely empty, like nothing matters at all", "Depression"),
    ("My emotions are all over the place, one day up the next down", "Bipolar"),
    ("I wear different masks with different people, I have no real sense of who I am", "Personality disorder"),
    ("I just want to disappear forever, no one would miss me", "Suicidal"),
]

# ---- Run tests ----
passed = 0
failed = 0
failures = []

for text, expected in test_cases:
    pred, conf = predict(text)
    ok = (pred == expected)
    if ok:
        passed += 1
        print(f"  OK  {pred:25s} ({conf:.2%})  |  {text[:70]}")
    else:
        failed += 1
        failures.append((text, expected, pred, conf))
        print(f" FAIL {pred:25s} ({conf:.2%})  expected={expected:20s}  |  {text[:70]}")

print(f"\n{'='*60}")
print(f"Results: {passed}/{passed+failed} passed ({passed/(passed+failed)*100:.0f}%)")
if failures:
    print(f"\nFailed ({len(failures)}):")
    for text, exp, got, conf in failures:
        print(f"  Expected={exp:20s}  Got={got:20s}  ({conf:.2%})  |  {text[:70]}")
