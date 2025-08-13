import java.util.Scanner;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Sundaram {
    
    public static int sieve(int n) {
        int x = (n - 1) / 2;
        boolean[] marked = new boolean[x + 1];
        
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

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number: ");
        int n = sc.nextInt();

        // Setup performance monitoring
        Runtime runtime = Runtime.getRuntime();
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        runtime.gc(); // force GC to clean up old allocations

        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        long cpuBefore = bean.isCurrentThreadCpuTimeSupported()
                ? bean.getCurrentThreadCpuTime()
                : 0;
        long startTime = System.nanoTime();

        int result = sieve(n);

        long endTime = System.nanoTime();
        long cpuAfter = bean.isCurrentThreadCpuTimeSupported()
                ? bean.getCurrentThreadCpuTime()
                : 0;
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        double wallClockSeconds = (endTime - startTime) / 1_000_000_000.0;
        double cpuSeconds = (cpuAfter - cpuBefore) / 1_000_000_000.0;
        long memoryUsedBytes = memoryAfter - memoryBefore;

        System.out.println("\nNumber of primes ≤ " + n + ": " + result);
        System.out.printf("Wall-clock time: %.6f seconds%n", wallClockSeconds);
        System.out.printf("CPU time: %.6f seconds%n", cpuSeconds);
        System.out.printf("Memory change: %.2f KB%n", memoryUsedBytes / 1024.0);

        sc.close();
    }
}
