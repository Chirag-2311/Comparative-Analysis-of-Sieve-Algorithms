import java.util.Scanner;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Atkin {

    private static int sieve(int N) {
        if (N < 2) return 0;

        int count = 0;
        boolean[] prime = new boolean[N + 1];

        if (N >= 2) prime[2] = true;
        if (N >= 3) prime[3] = true;

        for (int x = 1; x * x <= N; x++) {
            for (int y = 1; y * y <= N; y++) {

                int res = 4 * x * x + y * y;
                if (res <= N && (res % 12 == 1 || res % 12 == 5)) {
                    prime[res] ^= true;
                }

                res = 3 * x * x + y * y;
                if (res <= N && res % 12 == 7) {
                    prime[res] ^= true;
                }

                if (x > y) {
                    res = 3 * x * x - y * y;
                    if (res <= N && res % 12 == 11) {
                        prime[res] ^= true;
                    }
                }
            }
        }

        for (int i = 5; i * i <= N; i++) {
            if (prime[i]) {
                for (int j = i * i; j <= N; j += i * i) {
                    prime[j] = false;
                }
            }
        }

        // Count primes
        for (int i = 2; i <= N; i++) {
            if (prime[i]) count++;
        }

        return count;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int n = sc.nextInt();

        // Prepare runtime and CPU bean
        Runtime runtime = Runtime.getRuntime();
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        // Garbage collect before measurement to clean up old allocations
        runtime.gc();

        // Memory before
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        // CPU time before (nanoseconds)
        long cpuBefore = bean.isCurrentThreadCpuTimeSupported()
                ? bean.getCurrentThreadCpuTime()
                : 0;

        // Wall-clock start
        long startTime = System.nanoTime();

        int result = sieve(n);

        // Wall-clock end
        long endTime = System.nanoTime();

        // CPU time after
        long cpuAfter = bean.isCurrentThreadCpuTimeSupported()
                ? bean.getCurrentThreadCpuTime()
                : 0;

        // Memory after
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;
        double cpuTimeSeconds = (cpuAfter - cpuBefore) / 1_000_000_000.0;
        long memoryUsedBytes = memoryAfter - memoryBefore;

        System.out.println("\nNumber of primes ≤ " + n + ": " + result);
        System.out.printf("Wall-clock time: %.6f seconds%n", durationInSeconds);
        System.out.printf("CPU time: %.6f seconds%n", cpuTimeSeconds);
        System.out.printf("Memory change: %.2f KB%n", memoryUsedBytes / 1024.0);

        sc.close();
    }
}
