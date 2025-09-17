def example2(x):
    if x > 0:
        if x % 2 == 0:
            return "Positive Even"
        else:
            return "Positive Odd"
    else:
        if x == 0:
            return "Zero"
        else:
            return "Negative"