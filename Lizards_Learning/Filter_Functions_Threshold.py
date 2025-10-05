import lizard

result = lizard.analyze_path("my_project/")
for file_result in result:
    for func in file_result.function_list:
        if func.cyclomatic_complexity >= 10:
            print(f"⚠ High CCN: {func.long_name} (CCN={func.cyclomatic_complexity})")