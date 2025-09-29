import ast

code = """
def greet(name):
    return "Hello, " + name

class Calculator:
    def add(self, a, b):
        return a + b

    def compute(self, x, y):
        result = self.add(x, y)
        print(result)
        greet("User")
"""

tree = ast.parse(code)

for node in ast.walk(tree):
    if isinstance(node, ast.Call):
        if isinstance(node.func, ast.Attribute):
            # Method call (e.g., self.add, obj.method)
            print(f"Method call: {node.func.attr}")
        elif isinstance(node.func, ast.Name):
            # Function call (e.g., print, greet)
            print(f"Function call: {node.func.id}")