import ast

code = """
class Vehicle:
    def start(self):
        pass
    def stop(self):
        pass

class Car(Vehicle):
    def __init__(self, model):
        self.model = model
    def honk(self):
        print("Beep!")

class Bicycle:
    pass
"""

tree = ast.parse(code)

for node in ast.walk(tree):
    if isinstance(node, ast.ClassDef):
        methods = [n.name for n in node.body if isinstance(n, ast.FunctionDef)]
        print(f"Class: {node.name}")
        print(f"  Method Count: {len(methods)}")
        if methods:
            print(f"  Methods: {', '.join(methods)}")
        else:
            print("  Methods: (none)")