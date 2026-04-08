
/*****************************************************
   CS 326 - Spring 2026 - Assignment #4

   Student's full name: Olalekan Abdulsalam
   Student's full name: Kyle Johnson
   Student's full name: Dasha Coates

*****************************************************/

import java.util.*;
import java.nio.*;
import java.io.*;

class RSAcracker {

   /* feel free to add your own helper methods here if needed */

   /*
    * This method takes in a file name (with no extension). Then, it:
    * 1. opens the corresponding file (with the ".bin" extension added)
    * 2. reads the values of n and e
    * 3. uses these values to compute p, q, phi(n) and then d
    * 4. uses RSA to decrypt SKL and then each value of the key (this step
    * will send to the standard output the values of the RSA parameters)
    * 5. prints the values of SKL and then the key itself (see format below)
    * 6. uses the previously decrypted key to decrypt the rest of the file
    * (that is, the bytes of the message itself) and sends each byte of the
    * decrypted message to the standard output
    * 
    * The following shell command:
    * prompt> java RSAcracker ./test3
    * produces the following output:
    * p=11 q=17 n=187 phi(n)=160 e=7 d=23
    * SKL = 3
    * key = 12 34 56
    * This is a test for RSA cracker
    * End of test!
    */
   static void decrypt(String fileName) {
      fileName = fileName + ".bin";
      try (FileInputStream in = new FileInputStream(new File(fileName))) {
         byte[] b = new byte[4];
         int n = in.read(b);
         int e = in.read(b);
         
         int skl = in.read(b);
         int[] k = new int[skl];
         int i = 0;
         while (i < skl) {
            k[i++] = in.read(b);
         }

      } catch (Exception e) {
         System.out.println("Error");
      }
   }// decrypt method

   /*
    * This is this driver program I'll use for testing your code.
    * Do NOT modify.
    */
   public static void main(String[] args) {
      Primes.loadPrimes();
      RSAcracker.decrypt(args[0]);
   }// main method
   // For the tests shown in the handout to run unmodified, 
   // you must add the string "EncryptedCBC" + EXT to the input file 
   // for CBC decryption, etc.
}// RSAcracker class
