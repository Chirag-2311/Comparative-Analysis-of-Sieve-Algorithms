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

int sieveEratosthenes(int n) {
    std::vector<bool> prime(n + 1, true);
    prime[0] = prime[1] = false;

    for (int p = 2; p * p <= n; ++p) {
        if (prime[p]) {
            for (int i = p * p; i <= n; i += p) {
                prime[i] = false;
            }
        }
    }

    int count = 0;
    for (int i = 2; i <= n; i++)
        if (prime[i]) count++;
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

    int count = sieveEratosthenes(n);

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
