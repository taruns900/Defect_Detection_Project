import ast

# Example source code with multi-level inheritance
code = """
class LivingBeing:
    pass

class Animal(LivingBeing):
    def eat(self):
        pass

class Mammal(Animal):
    def breathe(self):
        pass

class Dog(Mammal):
    def bark(self):
        pass
"""

# Parse the code into an AST
tree = ast.parse(code)

# Extract class hierarchy
class_parents = {}
for node in ast.walk(tree):
    if isinstance(node, ast.ClassDef):
        # Get parent class names (only simple names, not complex expressions)
        parents = [base.id for base in node.bases if isinstance(base, ast.Name)]
        class_parents[node.name] = parents

print("Class Hierarchy:", class_parents)