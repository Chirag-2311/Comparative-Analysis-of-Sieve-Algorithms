import time
import psutil
import os
import gc

def wheel_primes(limit):
    if limit < 2:
        return []

    if limit >= 5:
        primes = [2, 3, 5]
    else:
        return [p for p in [2, 3, 5] if p <= limit]

    wheel = [4, 2, 4, 2, 4, 6, 2, 6]  # step pattern for mod 30
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


if __name__ == "__main__":
    process = psutil.Process(os.getpid())
    limit = int(input("Enter the limit: "))

    # Clean up before measuring
    gc.collect()

    # Memory before
    mem_before = process.memory_info().rss

    # CPU before
    cpu_before = time.process_time()

    # Wall-clock before
    wall_before = time.perf_counter()

    # Run algorithm
    primes = wheel_primes(limit)

    # Wall-clock after
    wall_after = time.perf_counter()

    # CPU after
    cpu_after = time.process_time()

    # Memory after
    mem_after = process.memory_info().rss

    # Metrics
    wall_clock_time = wall_after - wall_before
    cpu_time = cpu_after - cpu_before
    memory_change_kb = (mem_after - mem_before) / 1024

    print(f"Number of primes: {len(primes)}")
    print(f"Wall-clock time: {wall_clock_time:.6f} seconds")
    print(f"CPU time: {cpu_time:.6f} seconds")
    print(f"Memory change: {memory_change_kb:.2f} KB")
