import time
def wheel_primes(limit):
    if limit < 2:
        return []

    if limit >= 5:
        primes = [2, 3, 5] 
    else:
        return [p for p in [2,3,5] if p <= limit]

    wheel = [4, 2, 4, 2, 4, 6, 2, 6]
    i = 7
    wheel_index = 0

    while i <= limit:
        is_prime = True
        limit_sqrt = int(i ** 0.5)
        for p in primes:
            if p > limit_sqrt:
                break
            if i % p == 0:
                is_prime = False
                break
        if is_prime:
            primes.append(i)
        i += wheel[wheel_index]
        wheel_index = (wheel_index + 1) % len(wheel)

    return primes

limit = int(input("Enter the limit:"))
start=time.perf_counter()
print(wheel_primes(limit))
end=time.perf_counter()
print(f"Time taken {end-start} seconds")
