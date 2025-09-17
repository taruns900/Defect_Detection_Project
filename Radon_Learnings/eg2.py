def classify_grade(score):
    """Classifies a student's grade based on score with nested if conditions."""
    if score >= 0:
        if score <= 100:
            if score >= 90:
                return "A - Excellent!"
            elif score >= 80:
                return "B - Good job!"
            elif score >= 70:
                return "C - Fair."
            elif score >= 60:
                return "D - Needs improvement."
            else:
                return "F - Failed."
        else:
            return "Error: Score cannot be greater than 100."
    else:
        return "Error: Score cannot be negative."

if __name__ == "__main__":
    try:
        user_score = float(input("Enter student's score (0-100): "))
        result = classify_grade(user_score)
        print(result)
    except ValueError:
        print("Invalid input. Please enter a numeric value.")