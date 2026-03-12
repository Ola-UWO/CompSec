import java.math.BigInteger;
/*****************************************************
 * CS 326 - Spring 2026 - Assignment #3
 * 
 * Student's full name: Olalekan Abdulsalam
 * Student's full name: Kyle Johnson
 * Student's full name: Dasha Coates
 * 
 *****************************************************/

class Utils {
    /*
     * given a character string, return the sequence of ASCII codes (in
     * hexadecimal) for the characters in the string. Sample input/output:
     * input: "ABC" output: "414243"
     * input: "\nA\nB\n" output: "0A410A420A"
     * Note that each input character always yields exactly two hex digits.
     */
    static String textToHex(String s) {
        StringBuilder sb = new StringBuilder();
        for (char c : s.toCharArray()) {
            sb.append(String.format("%02X", (int) c));
        }
        return sb.toString();
    }// textToHex method

    /*
     * given a string of ascii codes (in hexadecimal), return the string of
     * the corresponding characters.
     * input: "414243" output: "ABC"
     * input: "0A410A420A" output: "\nA\nB\n"
     * Note that all input strings have an even length.
     */
    static String hexToText(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length() - 1; i += 2) {
            sb.append((char) Integer.parseInt(s.substring(i, i + 2), 16));
        }
        return sb.toString();
    }// hexTotext method

    /*
     * given a binary string, return the integer array of the same length as
     * the bit string and in which each element is the integer value of the
     * bit in the corresponding position in the string. Sample input/output:
     * input: "01101" output: [0, 1, 1, 0, 1]
     */
    static int[] binStringToIntArray(String bits) {
        return bits.chars().map(c -> c - '0').toArray();
    }// bitStringToIntArray method

    /*
     * given an integer array containing 0s and 1s exclusively, return
     * the binary string of the same length in which each element is the
     * character ('0' or '1') of the corresponding element in the input array.
     * input: [0, 1, 1, 0, 1] output: "01101"
     */
    static String intArrayToBinString(int[] data) {
        StringBuilder sb = new StringBuilder();
        for (int i : data)
            sb.append(i);
        return sb.toString();
    }// intArrayToBinString method

    /*
     * given an arbitrary long string of hexadecimal digits and a number
     * of bits, return the binary string of the given length corresponding
     * to the first input. Sample input/output:
     * input: "ABC" 16 output: "0000101010111100"
     * input: "01F3" 16 output: "0000000111110011"
     * Note: You must assume that numBits is always larger than or equal to
     * 4 times the number of hexadecimal digits in the first argument.
     */
    static String hexToBinString(String s, int numBits) {
        BigInteger bigInteger = new BigInteger(s, 16);
        String binaryStr = bigInteger.toString(2);
        int diff = numBits - binaryStr.length();
        if (diff > 0) {
            binaryStr = "0".repeat(diff) + binaryStr;
        }
        return binaryStr;
    }// hexToBinString method

    /*
     * given a binary string, return the hexadecimal representation of the
     * input as a String. Sample input/output:
     * input: "01101110" output: "6E"
     * Note: you must assume that the length of the input is a multiple of 4.
     */
    static String binStringToHex(String bits) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bits.length() - 3; i += 4) {
            sb.append(Integer.toHexString(
                    Integer.parseInt(bits.substring(i, i + 4),
                            2)));
        }
        return sb.toString();
    }// binStringToHex method

    /*
     * given two arrays of the same size each containing n integer values
     * equal to 0 or 1 exclusively, return an n-element array containing the
     * bitwise XOR of the pairs of input bits. Sample input/output:
     * input: [0, 0, 1, 1] and [0, 1, 0, 1] output: [0, 1, 1, 0]
     */
    static int[] XOR(int[] a, int[] b) {
        int[] xor = new int[a.length];
        for (int i = 0; i < a.length; i++) xor[i] = a[i] ^ b[i];
        return xor;
    }// XOR method

    /*
     * given an n-long permutation of bit positions ranging from 1 to m and
     * an m-bit vector, return the n-bit vector resulting from applying the
     * permutation to the second vector. Sample input/output:
     * input: [1, 1, 2, 1, 1, 2, 2] and [0, 1]
     * output: [0, 0, 1, 0, 0, 1, 1]
     * Note that the values in the permutation are position indexes
     * starting at 1, not 0. Therefore, the value of the bit at position 1 in
     * the second argument is 0, not 1.
     */
    static int[] applyPermut(int[] perm, int[] data) {
        int[] result = new int[perm.length];
        for (int i = 0; i < perm.length; i++) result[i] = data[perm[i] - 1];
        return result;
    }// applyPermut method

    /*
     * given a text string (in ASCII), return the array containing the
     * bits that, when concatenated together, make up the binary representation
     * of the ASCII codes in the input. Sample input/output:
     * input: "ABC"
     * output: [0, 1, 0, 0, 0, 0, 0, 1,
     * 0, 1, 0, 0, 0, 0, 1, 0,
     * 0, 1, 0, 0, 0, 0, 1, 1]
     * since 01000001 (base 2) = 65 (base 10) = ASCII code of A
     * 01000010 (base 2) = 66 (base 10) = ASCII code of B
     * 01000011 (base 2) = 67 (base 10) = ASCII code of C
     * 
     * This method does not need to handle the case where the input is ""
     */
    static int[] getBitVectorFromText(String text) {
        return binStringToIntArray(
                hexToBinString(textToHex(text), text.length() * 8));
    }// getBitVectorFromText method

    /*
     * given a string of hex digits, return the array containing the
     * bits that, when concatenated together, make up the binary
     * representation of the hex digits in the input. Sample input/output:
     * input: "abc"
     * output: [1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0]
     * since 1010 (base 2) = 10 (base 10) = a (base 16)
     * 1011 (base 2) = 11 (base 10) = b (base 16)
     * 1100 (base 2) = 12 (base 10) = c (base 16)
     * 
     * This method does not need to handle the case where the input is ""
     */
    static int[] getBitVectorFromHex(String hex) {
        return binStringToIntArray(hexToBinString(hex, hex.length()*4));
    }// getBitVectorFromHex method

    /*
     * given an array of bits (0 or 1), return the string of hex digits
     * whose binary representation is encoded in the input array.
     * Sample input/output:
     * input: [0,1,1,0,1,1,1,1]
     * output: "6f"
     * since 0110 (base 2) = 6 (base 10) = 6 (base 16)
     * 1111 (base 2) = 15 (base 10) = f (base 16)
     * 
     * This method only need handle cases where the length of the
     * input array is a multiple of 4.
     */
    static String getHex(int[] bits) {
        return binStringToHex(intArrayToBinString(bits));
    }// getHex method

}// class Utils
