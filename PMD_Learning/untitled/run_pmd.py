import subprocess
import csv
import os

# === CONFIGURATION ===
# Adjust these paths as needed. By default SRC_DIR is resolved relative to this script
PMD_CMD = "pmd"   # assuming pmd is in PATH (via Homebrew) or otherwise adjust
BASE_DIR = os.path.dirname(os.path.abspath(__file__))
SRC_DIR = os.path.join(BASE_DIR, "src")  # your Java source directory (resolved to script folder)
OUTPUT_FILE = os.path.join(BASE_DIR, "pmd_report.csv")
RULESET = "rulesets/java/quickstart.xml"

# === RUN PMD ANALYSIS ===
print("Running PMD static analysis...")
print(f"Using SRC_DIR={SRC_DIR}")
print(f"Using RULESET={RULESET}")

cmd = [
    PMD_CMD, "check",
    "--dir", SRC_DIR,
    "--rulesets", RULESET,
    "--format", "csv",
    "--report-file", OUTPUT_FILE
]

result = subprocess.run(cmd, capture_output=True, text=True)

if result.returncode == 0 or result.returncode == 4:  # 4 = issues found
    print(f"✅ PMD analysis completed. Report saved to: {os.path.abspath(OUTPUT_FILE)}")
else:
    print("❌ PMD failed. Details:")
    print(result.stderr)
    print("\nHints:")
    print(" - Ensure the `pmd` CLI is installed and available in PATH (e.g. `brew install pmd`).")
    print(" - If PMD is installed but ruleset can't be resolved, try an absolute ruleset path or check PMD version compatibility.")
    exit(1)

# === READ & SUMMARIZE RESULTS ===
if not os.path.exists(OUTPUT_FILE):
    print("⚠ No report file found. Maybe no Java files or path incorrect.")
    print(f"Checked for report at: {OUTPUT_FILE}")
    exit(0)

with open(OUTPUT_FILE, newline='') as f:
    reader = csv.DictReader(f)
    issues = list(reader)

if not issues:
    print("🎉 No issues found! Code is clean.")
else:
    print(f"⚠ Total issues found: {len(issues)}\n")
    print("Top 5 issues:")
    for issue in issues[:5]:
        print(f"- {issue['File']} (Line {issue['Line']}): {issue['Description']} [{issue['Rule']}]")
