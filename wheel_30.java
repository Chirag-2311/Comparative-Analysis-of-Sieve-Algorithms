import java.util.ArrayList;
import java.util.List;

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
        int limit = 100;
        System.out.println("30-Wheel Primes up to " + limit + ":");
        System.out.println(generatePrimes(limit));
    }
}
