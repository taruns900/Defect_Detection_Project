import ast

code = """
class Car:
    def __init__(self, make, model):
        self.make = make
        self.model = model
        self.odometer = 0

    def drive(self, miles):
        self.odometer += miles
        self.last_trip = miles  # dynamically added attribute
"""

tree = ast.parse(code)

attributes = set()
for node in ast.walk(tree):
    if isinstance(node, ast.Attribute):
        if isinstance(node.value, ast.Name) and node.value.id == "self":
            attributes.add(node.attr)

print(f"Attribute Count: {len(attributes)}")
print(f"Attributes: {sorted(attributes)}")