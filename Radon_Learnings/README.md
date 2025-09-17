# Radon Analysis

This project contains two Python functions (`add` and `complicated`) for demonstration.  
We analyze the code using [Radon](https://radon.readthedocs.io/).

---

## Commands Used

```bash
radon raw file_name.py
```

file_name.py
    LOC: 17
    LLOC: 9
    SLOC: 9
    Comments: 2
    Multi: 0
    Blank: 2

```bash
radon cc -s file_name.py
```
file_name.py
    F 2:0 add - A (1)
    F 5:0 complicated - B (4)


file_name.py
```bash
radon hal file_name.py
```
    h1: 7
    h2: 10
    N1: 12
    N2: 17
    vocabulary: 17
    length: 29
    calculated_length: 33.12
    volume: 118.51
    difficulty: 5.95
    effort: 705.31

```bash
radon mi -s file_name.py
```
file_name.py - MI: 77.45 - C


