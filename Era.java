/* 
1. Use boolean array and mark all as prime
2. Start from 2, mark all multiples of 2 as non-prime
3. Move to the next number that is still marked as prime
4. Repeat until you reach the square root of n
5. All remaining numbers in the array are prime
*/

import java.util.Scanner;
public class Era {

    public static int sieve(int n){
        boolean[] isComposite = new boolean[n + 1];

        for(int i =2; i<= Math.sqrt(n); i++){
            if(!isComposite[i]) {
                for(int j = i * i; j <= n; j += i) {
                    isComposite[j] = true;
                }
            }
        
        }
        int count = 0;
        for(int i = 2; i <= n; i++) {
            if(!isComposite[i]) {
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
