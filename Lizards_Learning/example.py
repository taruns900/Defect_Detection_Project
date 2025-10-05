# example.py
def simple_function(x):
    return x + 1

def complex_function(a, b, c):
    if a > 0:
        for i in range(b):
            if i % 2 == 0:
                print("Even")
            else:
                print("Odd")
    elif b < 0:
        while c > 0:
            c -= 1
            if c == 5:
                break
    else:
        try:
            result = a / b
        except ZeroDivisionError:
            result = None
    return result

class Vehicle:
    def start(self):
        print("Engine on")

    def drive(self, speed):
        if speed > 100:
            print("Too fast!")
        else:
            print(f"Driving at {speed} km/h")