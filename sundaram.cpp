#include <iostream>
#include <vector>
#include <windows.h>
#include <psapi.h>

int sieve(int n) {
    int x = (n - 1) / 2;
    std::vector<bool> marked(x + 1, false);

    for (int i = 1; i < x; i++) {
        for (int j = 1; j < x; j++) {
            int id = i + j + 2 * i * j;
            if (id <= x) {
                marked[id] = true;
            }
        }
    }

    int count = 0;
    if (n >= 2) count++;

    for (int i = 1; i <= x; i++) {
        if (!marked[i]) {
            count++;
        }
    }
    return count;
}

// Get wall-clock time in seconds
double getWallClockTime() {
    static LARGE_INTEGER frequency;
    static BOOL use_qpc = QueryPerformanceFrequency(&frequency);
    LARGE_INTEGER now;
    QueryPerformanceCounter(&now);
    return (double)now.QuadPart / frequency.QuadPart;
}

// Get CPU time in seconds
double getCPUTime() {
    FILETIME createTime, exitTime, kernelTime, userTime;
    if (GetProcessTimes(GetCurrentProcess(), &createTime, &exitTime, &kernelTime, &userTime)) {
        ULARGE_INTEGER kt, ut;
        kt.LowPart = kernelTime.dwLowDateTime;
        kt.HighPart = kernelTime.dwHighDateTime;
        ut.LowPart = userTime.dwLowDateTime;
        ut.HighPart = userTime.dwHighDateTime;
        return (kt.QuadPart + ut.QuadPart) / 10000000.0; // convert to seconds
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
    int n;
    std::cout << "Enter a number: ";
    std::cin >> n;

    SIZE_T memoryBefore = getMemoryUsageKB();
    double cpuBefore = getCPUTime();
    double startTime = getWallClockTime();

    int result = sieve(n);

    double endTime = getWallClockTime();
    double cpuAfter = getCPUTime();
    SIZE_T memoryAfter = getMemoryUsageKB();

    std::cout << "\nNumber of primes <= " << n << ": " << result << "\n";
    std::cout << "Wall-clock time: " << (endTime - startTime) << " seconds\n";
    std::cout << "CPU time: " << (cpuAfter - cpuBefore) << " seconds\n";
    std::cout << "Memory change: " << (memoryAfter - memoryBefore) << " KB\n";

    return 0;
}
