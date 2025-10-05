import lizard

result = lizard.analyze_path("Lizards_Learning/")
for file_result in result:
    print(f"File: {file_result.filename}")
    for func in file_result.function_list:
        print(f"  {func.name} -> CCN={func.cyclomatic_complexity}, NLOC={func.nloc}")