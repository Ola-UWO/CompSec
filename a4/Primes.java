/*****************************************************
   CS 326 - Spring 2026 - Assignment 4

   Student's full name: Olalekan Abdulsalam
   Student's full name: Kyle Johnson
   Student's full name: Dasha Coates

 *****************************************************/

import java.util.*;
import java.io.*;

class Primes
{
    /* in this assignment, we will only work with the first 1000 primes */
    static int NUM_PRIMES = 1000;
    
    /* the square of the value of the largest prime number used in this 
       assignment */
    static int SKY = -1;

    /* the values of the first NUM_PRIMES
    "prime numbers */
    static int[] primes =  new int[NUM_PRIMES];

    /* load the first 1000 prime numbers from the file "primes.txt"
       into the static array 'primes'. This method must also initialize
       the 'SKY' static variable.
    */
    public static void loadPrimes()
    {
        try (var sc = new Scanner(new File("primes.txt"));)
        {
            for (int i = 0; sc.hasNextInt(); i++) {
                primes[i] = sc.nextInt();
                if (i == primes.length - 1) {
                    SKY = primes[i] * primes[i];
                }
            }
        } catch (Exception e) {
            System.out.println("Error");
        } 
    }// loadPrimes method

    /* return true if and only if its input is one of the first 1000
       prime numbers.
    */
    public static boolean isPrime(int n)
    {
        if (n > primes[primes.length - 1]) return false;
        if (n != 2 && n % 2 == 0) return false;
        if (Arrays.binarySearch(primes, n) > 0) return true;
        return false;
    }// isPrime method

    /* given an integer, return a 2-element array containing its two prime
       factors if the input is the product of two primes (with the smallest 
       prime in the first array location); otherwise return an empty 
       (0-element) array.
     */
    public static int[] factor(int n)
    {
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < primes.length; i++) {
            if (n % primes[i] != 0) continue;
            else {
                ans.add(primes[i]);
            }
            if (ans.size() > 2) {
                ans.removeAll(ans);
                break;
            }
        }
        return ans.stream().mapToInt(Integer::intValue).toArray();
    }// factor method

    /* return the greatest common divisor of the two input integers, which
       are assumed to be positive. The implementation of this method must be
       iterative. No recursion allowed!  You are also not allowed to use any
       'gcd' method in any of the Java API classes. You may not use any helper
       methods. You must implement this method from first principles.
    */
    public static int gcd(int m, int n)
    {   
        if (n < m) {
            var temp = m;
            m = n;
            n = temp;
        }
        for (int r = 0;;) {
            r = n % m;
            if (r == 0) return m;
            n = m;
            m = r;
        }
    }// gcd method
}// Primes class
