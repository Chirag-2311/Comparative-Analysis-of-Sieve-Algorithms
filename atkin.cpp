#include <iostream>
#include <vector>
#include <cmath>
#include <chrono>
#include <windows.h>
#include <psapi.h>

size_t getMemoryUsageKB() {
    PROCESS_MEMORY_COUNTERS_EX pmc;
    GetProcessMemoryInfo(GetCurrentProcess(), (PROCESS_MEMORY_COUNTERS*)&pmc, sizeof(pmc));
    return pmc.WorkingSetSize / 1024;
}

int sieveAtkin(int limit) {
    if (limit < 2) return 0;

    std::vector<bool> sieve(limit + 1, false);
    int sqrtLimit = std::sqrt(limit);

    for (int x = 1; x <= sqrtLimit; x++) {
        for (int y = 1; y <= sqrtLimit; y++) {
            int n = (4 * x * x) + (y * y);
            if (n <= limit && (n % 12 == 1 || n % 12 == 5))
                sieve[n] = !sieve[n];

            n = (3 * x * x) + (y * y);
            if (n <= limit && n % 12 == 7)
                sieve[n] = !sieve[n];

            n = (3 * x * x) - (y * y);
            if (x > y && n <= limit && n % 12 == 11)
                sieve[n] = !sieve[n];
        }
    }

    for (int r = 5; r <= sqrtLimit; r++) {
        if (sieve[r]) {
            int r2 = r * r;
            for (int i = r2; i <= limit; i += r2)
                sieve[i] = false;
        }
    }

    int count = 0;
    if (limit >= 2) count++;
    if (limit >= 3) count++;

    for (int i = 5; i <= limit; i++)
        if (sieve[i]) count++;

    return count;
}

int main() {
    int n;
    std::cout << "Enter limit: ";
    std::cin >> n;

    size_t memBefore = getMemoryUsageKB();
    auto startWall = std::chrono::high_resolution_clock::now();
    FILETIME startKernel, startUser, dummy;
    GetProcessTimes(GetCurrentProcess(), &dummy, &dummy, &startKernel, &startUser);

    int count = sieveAtkin(n);

    auto endWall = std::chrono::high_resolution_clock::now();
    FILETIME endKernel, endUser;
    GetProcessTimes(GetCurrentProcess(), &dummy, &dummy, &endKernel, &endUser);

    size_t memAfter = getMemoryUsageKB();

    double wallTime = std::chrono::duration<double>(endWall - startWall).count();

    ULARGE_INTEGER ustart, uend, kstart, kend;
    ustart.LowPart = startUser.dwLowDateTime;
    ustart.HighPart = startUser.dwHighDateTime;
    uend.LowPart = endUser.dwLowDateTime;
    uend.HighPart = endUser.dwHighDateTime;

    kstart.LowPart = startKernel.dwLowDateTime;
    kstart.HighPart = startKernel.dwHighDateTime;
    kend.LowPart = endKernel.dwLowDateTime;
    kend.HighPart = endKernel.dwHighDateTime;

    double cpuTime = (double)((uend.QuadPart - ustart.QuadPart) +
                              (kend.QuadPart - kstart.QuadPart)) / 1e7;

    std::cout << "\nNumber of primes ≤ " << n << ": " << count << "\n";
    std::cout << "Wall-clock time: " << wallTime << " seconds\n";
    std::cout << "CPU time: " << cpuTime << " seconds\n";
    std::cout << "Memory change: " << (memAfter - memBefore) << " KB\n";
}
