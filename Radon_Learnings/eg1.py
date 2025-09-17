def add(a, b):
    return a + b
#addition done here

def complicated(n):
    total = 0
    for i in range(n):
        if i % 2 == 0:
            total += i
        else:
            if i % 3 == 0:
                total -= i
            else:
                total += 2 * i
    return total
#total caluated here