import java.util.Scanner;

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

        long startTime = System.nanoTime();
        int result = sieve(n);
        long endTime = System.nanoTime();

        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;

        System.out.println("Number of primes ≤ " + n + ": " + result);
        System.out.printf("Execution time: %.6f seconds\n", durationInSeconds);

        sc.close();
    }
}
