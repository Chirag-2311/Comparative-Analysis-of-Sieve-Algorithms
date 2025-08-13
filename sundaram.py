import time
import psutil
import os
import gc

def sundaram(limit):
    n = (limit - 2) // 2
    count = 0
    if limit > 2:
        count = 1  # account for prime 2
    arr = [False] * (n + 1)

    for i in range(1, n + 1):
        j = i
        while i + j + 2 * i * j <= n:
            arr[i + j + 2 * i * j] = True
            j += 1

    for i in range(1, n + 1):
        if not arr[i]:
            count += 1

    return count


if __name__ == "__main__":
    process = psutil.Process(os.getpid())

    limit = int(input("Enter the range: "))

    # Clean up before measuring
    gc.collect()

    # Memory before
    mem_before = process.memory_info().rss

    # CPU before
    cpu_before = time.process_time()

    # Wall-clock before
    wall_before = time.perf_counter()

    # Run algorithm
    count = sundaram(limit)

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

    print(f"\nNumber of primes ≤ {limit}: {count}")
    print(f"Wall-clock time: {wall_clock_time:.6f} seconds")
    print(f"CPU time: {cpu_time:.6f} seconds")
    print(f"Memory change: {memory_change_kb:.2f} KB")
