import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class wheel_30 {

    public static List<Integer> generatePrimes(int limit) {
        List<Integer> primes = new ArrayList<>();
        if (limit >= 2) primes.add(2);
        if (limit >= 3) primes.add(3);
        if (limit >= 5) primes.add(5);

        for (int i = 7; i <= limit; i += 2) { // Only odd numbers
            if (i % 3 == 0 || i % 5 == 0) continue; // Skip multiples of 3 and 5

            if (isPrime(i, primes)) {
                primes.add(i);
            }
        }

        return primes;
    }

    private static boolean isPrime(int n, List<Integer> primes) {
        int sqrtN = (int) Math.sqrt(n);
        for (int p : primes) {
            if (p > sqrtN) break;
            if (n % p == 0) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter limit: ");
        int limit = sc.nextInt();

        // Setup performance monitoring
        Runtime runtime = Runtime.getRuntime();
        ThreadMXBean bean = ManagementFactory.getThreadMXBean();

        runtime.gc(); // clean up memory before measuring

        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();
        long cpuBefore = bean.isCurrentThreadCpuTimeSupported()
                ? bean.getCurrentThreadCpuTime()
                : 0;
        long startTime = System.nanoTime();

        List<Integer> primes = generatePrimes(limit);

        long endTime = System.nanoTime();
        long cpuAfter = bean.isCurrentThreadCpuTimeSupported()
                ? bean.getCurrentThreadCpuTime()
                : 0;
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        double wallClockSeconds = (endTime - startTime) / 1_000_000_000.0;
        double cpuSeconds = (cpuAfter - cpuBefore) / 1_000_000_000.0;
        long memoryUsedBytes = memoryAfter - memoryBefore;

        System.out.println("\n30-Wheel Primes up to " + limit + ":");
        System.out.println(primes);
        System.out.println("\nNumber of primes: " + primes.size());
        System.out.printf("Wall-clock time: %.6f seconds%n", wallClockSeconds);
        System.out.printf("CPU time: %.6f seconds%n", cpuSeconds);
        System.out.printf("Memory change: %.2f KB%n", memoryUsedBytes / 1024.0);

        sc.close();
    }
}
