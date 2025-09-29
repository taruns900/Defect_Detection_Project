import ast

def analyze_code(source_code):
    tree = ast.parse(source_code)
    results = []
    class_parents = {}

    for node in ast.walk(tree):
        if isinstance(node, ast.ClassDef):
            # Count methods (FunctionDef inside class body)
            methods = [n for n in node.body if isinstance(n, ast.FunctionDef)]
            
            # Extract instance attributes (self.attr)
            attributes = set()
            for subnode in ast.walk(node):
                if isinstance(subnode, ast.Attribute):
                    if isinstance(subnode.value, ast.Name) and subnode.value.id == "self":
                        attributes.add(subnode.attr)
            
            # Extract parent classes (only simple names, not complex expressions)
            parents = [base.id for base in node.bases if isinstance(base, ast.Name)]
            class_parents[node.name] = parents
            
            results.append({
                "Class": node.name,
                "Method Count": len(methods),
                "Attribute Count": len(attributes),
                "Parents": parents
            })
    return results, class_parents

# New example code (different from the PDF)
code = """
class Vehicle:
    def __init__(self, brand):
        self.brand = brand
        self.speed = 0

    def accelerate(self):
        self.speed += 10

class Car(Vehicle):
    def __init__(self, brand, doors):
        super().__init__(brand)
        self.doors = doors

    def honk(self):
        print("Beep!")

class ElectricCar(Car):
    def charge(self):
        print("Charging...")
"""

data, hierarchy = analyze_code(code)

print("Metrics:")
for cls in data:
    print(cls)

print("\nHierarchy:")
print(hierarchy)