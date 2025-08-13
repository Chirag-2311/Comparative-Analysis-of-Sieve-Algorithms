#include <iostream>
#include <vector>
#include <cmath>
#include <windows.h>
#include <psapi.h>

bool isPrime(int n, const std::vector<int>& primes) {
    int sqrtN = (int)std::sqrt(n);
    for (int p : primes) {
        if (p > sqrtN) break;
        if (n % p == 0) return false;
    }
    return true;
}

std::vector<int> generatePrimes(int limit) {
    std::vector<int> primes;
    if (limit >= 2) primes.push_back(2);
    if (limit >= 3) primes.push_back(3);
    if (limit >= 5) primes.push_back(5);

    for (int i = 7; i <= limit; i += 2) { // Only odd numbers
        if (i % 3 == 0 || i % 5 == 0) continue; // Skip multiples of 3 and 5
        if (isPrime(i, primes)) {
            primes.push_back(i);
        }
    }
    return primes;
}

// Get wall-clock time
double getWallClockTime() {
    static LARGE_INTEGER frequency;
    static BOOL use_qpc = QueryPerformanceFrequency(&frequency);
    LARGE_INTEGER now;
    QueryPerformanceCounter(&now);
    return (double)now.QuadPart / frequency.QuadPart;
}

// Get CPU time
double getCPUTime() {
    FILETIME createTime, exitTime, kernelTime, userTime;
    if (GetProcessTimes(GetCurrentProcess(), &createTime, &exitTime, &kernelTime, &userTime)) {
        ULARGE_INTEGER kt, ut;
        kt.LowPart = kernelTime.dwLowDateTime;
        kt.HighPart = kernelTime.dwHighDateTime;
        ut.LowPart = userTime.dwLowDateTime;
        ut.HighPart = userTime.dwHighDateTime;
        return (kt.QuadPart + ut.QuadPart) / 10000000.0;
    }
    return 0.0;
}

// Get memory usage in KB
SIZE_T getMemoryUsageKB() {
    PROCESS_MEMORY_COUNTERS pmc;
    if (GetProcessMemoryInfo(GetCurrentProcess(), &pmc, sizeof(pmc))) {
        return pmc.WorkingSetSize / 1024;
    }
    return 0;
}

int main() {
    int limit;
    std::cout << "Enter limit: ";
    std::cin >> limit;

    SIZE_T memoryBefore = getMemoryUsageKB();
    double cpuBefore = getCPUTime();
    double startTime = getWallClockTime();

    std::vector<int> primes = generatePrimes(limit);

    double endTime = getWallClockTime();
    double cpuAfter = getCPUTime();
    SIZE_T memoryAfter = getMemoryUsageKB();

    std::cout << "\n30-Wheel Primes up to " << limit << ":\n";
    for (int p : primes) std::cout << p << " ";
    std::cout << "\n\nNumber of primes: " << primes.size() << "\n";
    std::cout << "Wall-clock time: " << (endTime - startTime) << " seconds\n";
    std::cout << "CPU time: " << (cpuAfter - cpuBefore) << " seconds\n";
    std::cout << "Memory change: " << (memoryAfter - memoryBefore) << " KB\n";

    return 0;
}
