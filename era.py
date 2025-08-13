import time
import os
import psutil
import math

def sieve(n):
    is_composite = [False] * (n + 1)
    for i in range(2, int(math.sqrt(n)) + 1):
        if not is_composite[i]:
            for j in range(i * i, n + 1, i):
                is_composite[j] = True
    count = sum(1 for i in range(2, n + 1) if not is_composite[i])
    return count

if __name__ == "__main__":
    n = int(input("Enter a number: "))

    process = psutil.Process(os.getpid())

    # Clean up memory before measurement
    process.memory_info()  # Force update
    mem_before = process.memory_info().rss  # in bytes
    cpu_times_before = process.cpu_times()
    start_time = time.perf_counter()

    result = sieve(n)

    end_time = time.perf_counter()
    cpu_times_after = process.cpu_times()
    mem_after = process.memory_info().rss  # in bytes

    wall_clock_seconds = end_time - start_time
    cpu_seconds = (cpu_times_after.user - cpu_times_before.user) + \
                  (cpu_times_after.system - cpu_times_before.system)
    memory_change_kb = (mem_after - mem_before) / 1024.0

    print(f"\nNumber of primes ≤ {n}: {result}")
    print(f"Wall-clock time: {wall_clock_seconds:.6f} seconds")
    print(f"CPU time: {cpu_seconds:.6f} seconds")
    print(f"Memory change: {memory_change_kb:.2f} KB")
