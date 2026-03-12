
/*****************************************************
   CS 326 - Spring 2026 - Assignment #3

   Student's full name: Olalekan Abdulsalam
   Student's full name: Kyle Johnson
   Student's full name: Dasha Coates

*****************************************************/

import java.util.*;
import java.io.*;
import java.math.*;

class ImageCipher {

    // storage for the next plaintext block read from/written to the image file
    static int[] block = new int[64];

    // storage for the next ciphertext block read from/written to the image file
    static int[] cipherBlock = new int[64];

    // file extension for all image files in this assignment (you MUST use
    // this constant everywhere instead of the string itself)
    static String EXT = ".pgm";

    /*
     * Given a scanner object, read the next 8 integers from it and store
     * the 8 corresponding 8-bit patterns into the 64-bit instance variable
     * called 'block'
     */
    static void readBlock(Scanner s) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++)
            sb.append(String.format("%02X", s.nextInt()));
        block = Utils.getBitVectorFromHex(sb.toString());
    }// readBlock method

    /*
     * Given a PrintWriter object, write to it the 8 integers stored as the 8
     * corresponding 8-bit patterns in the 64-bit instance variable
     * called 'block'
     */
    static void writeBlock(PrintWriter w) throws Exception {
        
        for (int i = 0; i < 64; i += 8)
            w.println(Integer.parseInt(Utils.getHex(
                Arrays.stream(block).skip(i).limit(8).toArray()), 16));
    }// writeBlock method

    /*
     * Given a Scanner object and a PrintWriter object, copy to the latter
     * the first four lines of the former.
     */
    static void processHeader(Scanner s, PrintWriter w) throws Exception {
        for (int i = 0; i < 4; i++)
            w.println(s.nextLine());
    }// processHeader method

    /*
     * given a file name (with no extension) for a PGM image and a DES
     * key (in hex format), encrypt the image using DES in ECB mode and store
     * the result in a file whose name is obtained by adding to the input
     * file name the string "EncryptedECB" + EXT.
     */
    static void encryptECB(String filename, String key) {
        try (Scanner s = new Scanner(new File(filename + EXT));
                PrintWriter w = new PrintWriter(
                        new File(filename + "EncryptedECB" + EXT))) {
            processHeader(s, w);
            var des = new DES(DES.getSubKeys(key));
            while (s.hasNextInt()) {
                readBlock(s);
                block = des.encryptDES(block);
                writeBlock(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// encryptECB method

    /*
     * given a file name (with no extension) for a PGM image and a DES
     * key (in hex format), decrypt the image using DES in ECB mode and store
     * the result in a file whose name is obtained by adding to the input
     * file name the string "DecryptedECB" + EXT.
     */
    static void decryptECB(String filename, String key) {
        try (Scanner s = new Scanner(new File(filename + EXT));
                PrintWriter w = new PrintWriter(
                        new File(filename + "DecryptedECB" + EXT))) {
            processHeader(s, w);
            var des = new DES(DES.getSubKeys(key));
            while (s.hasNextInt()) {
                readBlock(s);
                block = des.decryptDES(block);
                writeBlock(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// decryptECB method

    /*
     * given a file name (with no extension) for a PGM image, a DES
     * key and an initialization vector (both in hex format), encrypt
     * the image using DES in CBC mode and store the result in a file
     * whose name is obtained by adding to the input file name the
     * string "EncryptedCBC" + EXT.
     */
    static void encryptCBC(String filename, String key, String IV) {
        try (Scanner s = new Scanner(new File(filename + EXT));
                PrintWriter w = new PrintWriter(
                        new File(filename + "EncryptedCBC" + EXT))) {
            processHeader(s, w);
            cipherBlock = Utils.getBitVectorFromHex(IV);
            var des = new DES(DES.getSubKeys(key));
            while (s.hasNextInt()) {
                readBlock(s);
                block = cipherBlock = des.encryptDES(
                        Utils.XOR(block, cipherBlock));
                writeBlock(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// encryptCBC method

    /*
     * given a file name (with no extension) for a PGM image, a DES
     * key and an initialization vector (both in hex format), decrypt
     * the image using DES in CBC mode and store the result in a file
     * whose name is obtained by adding to the input file name the
     * string "DecryptedCBC" + EXT.
     */
    static void decryptCBC(String filename, String key, String IV) {
        try (Scanner s = new Scanner(new File(filename + EXT));
                PrintWriter w = new PrintWriter(
                        new File(filename + "DecryptedCBC" + EXT))) {
            processHeader(s, w);
            cipherBlock = Utils.getBitVectorFromHex(IV);
            var des = new DES(DES.getSubKeys(key));
            while (s.hasNextInt()) {
                readBlock(s);
                var temp = cipherBlock;
                cipherBlock = block;
                block = Utils.XOR(temp, des.decryptDES(block));
                writeBlock(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// decryptCBC method

    /* This is the driver code used for testing purposes. Do NOT modify it. */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("The first argument must be -e or -d, while ");
            System.out.println("the second argument must be -ECB or -CBC");
            System.exit(1);
        }
        if (args[1].equals("-ECB")) {
            if (args.length == 4) {
                String filename = args[2];
                String key = args[3];
                if (args[0].equals("-e")) {
                    encryptECB(filename, key);
                } else if (args[0].equals("-d")) {
                    decryptECB(filename, key);
                } else {
                    System.out.println("The first argument must be -e or -d");
                    System.exit(1);
                }
            } else {
                System.out.println("Usage: java ImageCipher [-e or -d] -ECB " +
                        "<image file name without .pgm> <key>");
                System.exit(1);
            }
        } else if (args[1].equals("-CBC")) {
            if (args.length == 5) {
                String filename = args[2];
                String key = args[3];
                String IV = args[4];
                if (args[0].equals("-e")) {
                    encryptCBC(filename, key, IV);
                } else if (args[0].equals("-d")) {
                    decryptCBC(filename, key, IV);
                } else {
                    System.out.println("The first argument must be -e or -d");
                    System.exit(1);
                }
            } else {
                System.out.println("Usage: java ImageCipher [-e or -d] -ECB " +
                        "<image file name without .pgm> <key> <IV>");
                System.exit(1);
            }
        } else {
            System.out.println("The second argument must be -ECB or -CBC");
            System.exit(1);
        }
    }// main method
}// ImageCipher class
