import java.util.Scanner;

public class Sundaram {
    
    public static int sieve(int n) {

        int x = (n - 1) / 2;
        boolean[] marked = new boolean[x+1];
        
        for(int i =1; i<x; i++){
            for(int j =1; j<x; j++){
                int id = i + j + 2 * i * j;
                if (id <= x) {
                    marked[id] = true;
                }
            }
        }

        int count = 0;
        if (n >= 2) count++;

        for (int i =1; i <= x; i++) {
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
        long startTime = System.nanoTime();
        int result = sieve(n);
        long endTime = System.nanoTime();
        double durationInSeconds = (endTime - startTime) / 1_000_000_000.0;

        System.out.println("Number of primes ≤ " + n + ": " + result);
        System.out.printf("Execution time: %.6f seconds\n", durationInSeconds);
        
        sc.close();


    }


}
