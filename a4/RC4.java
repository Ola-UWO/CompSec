/*****************************************************
   CS 326 - Spring 2026 - Assignment #4

   Student's full name: Olalekan Abdulsalam
   Student's full name: Kyle Johnson
   Student's full name: Dasha Coates

 *****************************************************/

import java.util.*;
import java.io.*;

class RC4
{
    /* do NOT modify the instance variables of this class */

    /* local state needed by the RC4 algorithm */    
    int[] S;  
    int i,j;

    /* this constructor takes in a key as an array of integers of unknown 
       positive length, initializes the instance variables as needed by KSA
       and PRGA, and executes the KSA algorithm exactly as specified on 
       Slide 7.6
    */
    RC4(int[] K)
    {
        var keylen = 256;
        S = new int[keylen];
        int[] T = new int[keylen];
        for (i = 0; i < keylen; i++) {
            S[i] = i;
            T[i] = K[i % K.length];
        }
        j = 0;
        for (i = 0; i < keylen; i++) {
            j = (j + S[i] + T[i]) % keylen;
            swap(i, j);
        }
        i = 0;
        j = 0;
    }// constructor

    /* this method returns the next random integer by executing one
       iteration of the while loop in PRGA (see Slide 7.8)
     */
    int nextRnd()
    {
        var keylen = 256;
        i = (i + 1) % keylen;
        j = (j + S[i]) % keylen;
        swap(i, j);

        return S[(S[i] + S[j]) % keylen];
    }// PRGA method

    /* this method swaps elements at position i and j in the S array, where
       i and j may be equal 
    */
    void swap(int i, int j)
    {
        int tmp = S[i];
        S[i] = S[j];
        S[j] = tmp;
    }// swap method

    /* this method takes the name of two files, opens them as input
       and output files, respectively, and copies, byte by byte, the
       RC4-encrypted contents of the first file to the second file.
    */
    void encrypt(String inFileName, String outFileName)
    {
        try (FileInputStream in = new FileInputStream(new File(inFileName));
            FileOutputStream out = new FileOutputStream(new File(outFileName)))
        {
            int b;
            while ((b = in.read()) != -1) 
            {
                out.write(nextRnd() ^ b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }// encrypt method

    /* this method takes the name of two files, opens them as input
       and output files, respectively, and copies, byte by byte, the
       RC4-decrypted contents of the first file to the second file.
     */
    void decrypt(String inFileName, String outFileName)
    {
        encrypt(inFileName, outFileName);
    }// decrypt method
}// RC4 class
