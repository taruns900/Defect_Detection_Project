import lizard, csv

result = lizard.analyze_path("my_project/")

with open("lizard_report.csv", "w", newline="") as f:
    writer = csv.writer(f)
    writer.writerow(["File", "Function", "NLOC", "CCN", "Params", "Length"])
    for file_result in result:
        for func in file_result.function_list:
            writer.writerow([
                file_result.filename,
                func.long_name,
                func.nloc,
                func.cyclomatic_complexity,
                func.parameter_count,
                func.length,
            ])

print("Lizard report saved to lizard_report.csv")