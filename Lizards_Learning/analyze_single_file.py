import lizard

analysis = lizard.analyze_file("example.py")

print("Functions found:")
for func in analysis.function_list:
    print(f"Function: {func.name}")
    print(f"  CCN: {func.cyclomatic_complexity}")
    print(f"  NLOC: {func.nloc}")
    print(f"  Params: {func.parameter_count}")
    print(f"  Length: {func.length}")
    print(f"  Location: line {func.start_line}-{func.end_line}")
    print()