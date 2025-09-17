def example3(a, b, c):
    if a > 0:
        if b > 0:
            if c > 0:
                return "All positive"
            else:
                if c == 0:
                    return "C is zero"
                else:
                    return "C negative"
        else:
            return "B non-positive"
    else:
        if a == 0:
            return "A is zero"
        else:
            return "A negative"