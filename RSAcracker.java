/*****************************************************
   CS 326 - Spring 2026 - Assignment #4

   Student's full name: Olalekan Abdulsalam
   Student's full name: Kyle Johnson
   Student's full name: Dasha Coates

*****************************************************/

import java.util.*;
import java.nio.*;
import java.io.*;

class RSAcracker
{

    /* feel free to add your own helper methods here if needed */

    /* This method takes in a file name (with no extension). Then, it:
       1. opens the corresponding file (with the ".bin" extension added)
       2. reads the values of n and e
       3. uses these values to compute p, q, phi(n) and then d
       4. uses RSA to decrypt SKL and then each value of the key (this step
          will send to the standard output the values of the RSA parameters)
       5. prints the values of SKL and then the key itself (see format below) 
       6. uses the previously decrypted key to decrypt the rest of the file 
          (that is, the bytes of the message itself) and sends each byte of the
          decrypted message to the standard output

       The following shell command:
               prompt> java RSAcracker ./test3
       produces the following output:
               p=11 q=17 n=187 phi(n)=160 e=7 d=23 
               SKL = 3 
               key = 12 34 56 
               This is a test for RSA cracker
               End of test!
     */
    static void decrypt(String fileName)
    {
      try (DataInputStream in = new DataInputStream(
            new FileInputStream(fileName + ".bin"))) {
         
         int n = in.readInt();
         int e = in.readInt();
         int p = -1;
         int q = -1;
         int phiOfN = -1;
         int d = -1;
         int k = 1;

         //  compute p, q, phi(n) and then d
         int[] factors = Primes.factor(n);
         if (factors.length == 2) {
            p = factors[0];
            q = factors[1];
         }
         phiOfN = (p-1) * (q-1);
         while ( k < Primes.SKY && d == -1) {
            int curr = 1 + k * phiOfN;

            if (curr % e == 0) {
               int candidate = curr / e;

               if (Primes.isPrime(candidate) && Primes.gcd(candidate, phiOfN) == 1) {
                  d = candidate;
               }
            }
            k++;
         }

         System.out.println("p=" + p + " q=" + q + " n=" + n +
                        " phi(n)=" + phiOfN + " e=" + e + " d=" + d);

         int encryptedSKL = in.readInt();
         int SKL = RSA.modularExponent(encryptedSKL, d, n);

         int[] key = new int[SKL];
         for (int i = 0; i < SKL; i++) {
            int encryptedVal = in.readInt();
            key[i] = RSA.modularExponent(encryptedVal, d, n);
         }

         System.out.println("SKL = " + SKL);

         System.out.print("key = ");
         for (int i = 0; i < SKL; i++) {
            System.out.print(key[i] + " ");
         }
         System.out.println();

         RC4 rc4 = new RC4(key);

         int data;
         while ((data = in.read()) != -1) {
            int rnd = rc4.nextRnd();
            int decrypted = data ^ rnd;
            System.out.print((char) decrypted);
         }

      } catch (IOException e) {
            e.printStackTrace();
      }

    }//decrypt method

    /* This is this driver program I'll use for testing your code.
       Do NOT modify.
    */
    public static void main(String[] args)
    {
        Primes.loadPrimes();
        RSAcracker.decrypt(args[0]);
    }//main method

}// RSAcracker class
