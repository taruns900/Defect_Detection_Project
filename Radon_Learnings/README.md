# Radon Analysis

## Commands Used
On running the command:
### Raw Metrics
```bash
radon raw file_name.py
```
Output example:

file_name.py
    LOC: <Lines of Code>
    LLOC: <Logical Lines of Code>
    SLOC: <Source Lines of Code>
    Comments: <Number of Comment Lines>
    Multi: <Number of Multi-line Strings>
    Blank: <Number of Blank Lines>

Where:
- **LOC**: Lines of Code (total lines in the file)
- **LLOC**: Logical Lines of Code (statements, not counting comments or blanks)
- **SLOC**: Source Lines of Code (lines with actual code, excluding comments and blanks)
- **Comments**: Number of comment lines
- **Multi**: Number of multi-line strings
- **Blank**: Number of blank lines




### Cyclomatic Complexity
```bash
radon cc -s file_name.py
```
Output example:

file_name.py
    F 2:0 add - A (1)
    F 5:0 complicated - B (4)

Where:
- **F**: Function (or M for method, C for class)
- `2:0`: Line and column where the function starts
- `add`: Function name
- `A (1)`: Complexity grade and score (A is simplest, F is most complex)



file_name.py

### Halstead Metrics
```bash
radon hal file_name.py
```

Output example:
    h1: <Number of distinct operators>
    h2: <Number of distinct operands>
    N1: <Total occurrences of operators>
    N2: <Total occurrences of operands>
    vocabulary: <h1 + h2>
    length: <N1 + N2>
    calculated_length: <Estimated program length>
    volume: <Program volume>
    difficulty: <Difficulty metric>
    effort: <Effort metric>

Where:
- **h1**: Number of distinct operators
- **h2**: Number of distinct operands
- **N1**: Total occurrences of operators
- **N2**: Total occurrences of operands
- **vocabulary**: h1 + h2
- **length**: N1 + N2
- **calculated_length**: Estimated program length
- **volume**: Program volume
- **difficulty**: Difficulty metric
- **effort**: Effort metric


### Maintainability Index
```bash
radon mi -s file_name.py
```
Output example:
file_name.py - MI: score - grade 
Where:
- **MI**: Maintainability Index (higher is better)
- **grade**: Letter grade (A = best, C = worst)


