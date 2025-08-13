import time
import psutil
import os

def sieve_atkin(limit, count):
    nums = [0] * (limit + 1)

    if limit > 2:
        nums[2] = 1
    if limit > 3:
        nums[3] = 1

    for i in range(1, int(limit ** 0.5) + 1):
        for j in range(1, int(limit ** 0.5) + 1):

            n = (4 * i * i) + (j * j)
            if n <= limit:
                if (n % 12 == 5 or n % 12 == 1):
                    nums[n] ^= 1

            n = (3 * i * i) + (j * j)
            if n <= limit:
                if (n % 12 == 7):
                    nums[n] ^= 1

            n = (3 * i * i) - (j * j)
            if i > j and n <= limit:
                if n % 12 == 11:
                    nums[n] ^= 1

    for i in range(5, int(limit ** 0.5) + 1):
        if nums[i] == 1:
            for j in range(i * i, limit + 1, i * i):
                nums[j] = 0

    for i in range(2, limit + 1):
        if nums[i] == 1:
            count += 1
    return count


if __name__ == "__main__":
    process = psutil.Process(os.getpid())

    limit = int(input("Enter limit: "))

    # Garbage collect before measurement
    import gc
    gc.collect()

    # Memory before
    mem_before = process.memory_info().rss

    # CPU time before
    cpu_before = time.process_time()

    # Wall-clock time before
    wall_before = time.perf_counter()

    # Run algorithm
    c = sieve_atkin(limit, 0)

    # Wall-clock time after
    wall_after = time.perf_counter()

    # CPU time after
    cpu_after = time.process_time()

    # Memory after
    mem_after = process.memory_info().rss

    # Metrics
    wall_clock_time = wall_after - wall_before
    cpu_time = cpu_after - cpu_before
    memory_change_kb = (mem_after - mem_before) / 1024

    print(f"\nNumber of primes from 1 to {limit} is {c}")
    print(f"Wall-clock time: {wall_clock_time:.6f} seconds")
    print(f"CPU time: {cpu_time:.6f} seconds")
    print(f"Memory change: {memory_change_kb:.2f} KB")
