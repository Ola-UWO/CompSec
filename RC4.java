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
        S = new int[256];
        int[] T = new int[256];
        for (i = 0; i <= 255; i++) {
            S[i] = i;
            T[i] = K[i % K.length];
        } 
        // end for 
        j = 0;
        for (i = 0; i <= 255; i++) {
            j = (j + S[i] + T[i]) % 256;
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
        i = (i + 1) % 256;
        j = ( j + S[i]) % 256;
        swap(i, j);

        return S[(S[i] + S[j]) % 256]; 
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
        try (FileInputStream in = new FileInputStream(inFileName);
            FileOutputStream out = new FileOutputStream(outFileName)) {
            int data;
            
            // Read byte by byte until the end of the file (-1 is returned)
            while ((data = in.read()) != -1) {
                int rnd = nextRnd();
                int encrypted = data ^ rnd;
                out.write(encrypted);
            }

        } catch (IOException e) {
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
