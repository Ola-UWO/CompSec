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

    /* the values of the first NUM_PRIMES prime numbers */
    static int[] primes =  new int[NUM_PRIMES];

    /* load the first 1000 prime numbers from the file "primes.txt"
       into the static array 'primes'. This method must also initialize
       the 'SKY' static variable.
    */
    public static void loadPrimes()
    {
        String fileName = "primes.txt";

        try (Scanner scan = new Scanner(new File(fileName))) {
            int i = 0;
            while (scan.hasNext()) {
                int primeNum = scan.nextInt();
                primes[i] = primeNum;
                i++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found at " + fileName);
            e.printStackTrace();
        }

        SKY = (int) Math.pow(primes[primes.length - 1], 2);
    }// loadPrimes method

    /* return true if and only if its input is one of the first 1000
       prime numbers.
    */
    public static boolean isPrime(int n)
    {
        for (int prime : primes) {
            if (prime == n) {
                return true;
            }
        }
        return false;
    }// isPrime method

    /* given an integer, return a 2-element array containing its two prime
       factors if the input is the product of two primes (with the smallest 
       prime in the first array location); otherwise return an empty 
       (0-element) array.
     */
    public static int[] factor(int n)
    {
        int[] twoPrimeFactors = new int[2];
        int[] emptyArray = new int[0];
        int j;
        boolean isFound = false;
        for (int i = 2; i <= n && !isFound; i++) {
            if (n % i == 0) {
                j = n / i;
                if (j * i == n && isPrime(i) && isPrime(j)) {
                    twoPrimeFactors[0] = i;
                    twoPrimeFactors[1] = j;
                    isFound = true;
                }
            }
        }
        if (twoPrimeFactors[0] != 0 && twoPrimeFactors[1] != 0) {
            return twoPrimeFactors;
        }
        return emptyArray;
    }// factor method

    /* return the greatest common divisor of the two input integers, which
       are assumed to be positive. The implementation of this method must be
       iterative. No recursion allowed!  You are also not allowed to use any
       'gcd' method in any of the Java API classes. You may not use any helper
       methods. You must implement this method from first principles.
    */
    public static int gcd(int m,int n)
    {   
        int num1 = Math.max(n,m);
        int num2 = Math.min(n,m);
        int curr = num1;
        int q = -1;
        int r = num1 % num2;

        while (r != 0) {
            num1 = num2;
            num2 = r;
            q = num1 / num2;
            r = num1 % num2;

            curr = num2 * q + r;
        }

        return num2; 
    }// gcd method

}// Primes class
