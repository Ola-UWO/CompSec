import java.util.ArrayList;
import java.util.Arrays;

/*****************************************************
 * CS 326 - Spring 2026 - Assignment #2
 * 
 * Student's full name: Olalekan Abdulsalam
 * Student's full name: Kyle Johnson
 * Student's full name: Dasha Coates
 *****************************************************/

class ComputeSubKeys {
    // the "Permuted Choice 1" permutation for the left (C) half-block
    static int[] PC1c = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36
    };

    // the "Permuted Choice 1" permutation for the right (D) half-block
    static int[] PC1d = {
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4
    };

    // the "Permuted Choice 2" permutation
    static int[] PC2 = {
            14, 17, 11, 24, 1, 5, 3, 28,
            15, 6, 21, 10, 23, 19, 12, 4,
            26, 8, 16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55, 30, 40,
            51, 45, 33, 48, 44, 49, 39, 56,
            34, 53, 46, 42, 50, 36, 29, 32
    };

    /*
     * return the bit vector obtained by applying a cyclic rotation
     * to the left by one bit to the bit vector given as input. Sample
     * input/output:
     * input: [0, 0, 0, 1, 1] output: [0, 0, 1, 1, 0]
     * You must assume that the length of the input vector is >= 1.
     */
    static int[] shiftLeftBy1(int[] data) {
        int[] result = new int[data.length];
        result[data.length - 1] = data[0];
        System.arraycopy(data, 1, result, 0, data.length - 1);
        return result; // here to please the compiler
    }// shiftLeftBy1 method

    /*
     * return the bit vector obtained by applying a cyclic rotation
     * to the left by two bits to the bit vector given as input. Sample
     * input/output:
     * input: [0, 0, 0, 1, 1] output: [0, 1, 1, 0, 0]
     * You can assume that the length of the input vector is >= 2.
     */
    static int[] shiftLeftBy2(int[] data) {
        int[] result = new int[data.length];
        result[data.length - 1] = data[1];
        result[data.length - 2] = data[0];
        System.arraycopy(data, 2, result, 0, data.length - 2);
        return result; // here to please the compiler
    }// shiftLeftBy2 method

    /*
     * sends to the output stream a Java declaration for the 17x48 2D array
     * in which the first row is empty and each subsequent row contains a round
     * subkey represented by the sequence of 48 bit positions in the DES key
     * where the bits of this subkey are to be found.
     * 
     * This method implements the key schedule algorithm that takes in a
     * 64-bit key and outputs an array of 16 48-bit subkeys in which each bit
     * is one of the bits in the DES key. Recall that bit b in sub-key k
     * changes with the DES key but is ALWAYS obtained by copying a
     * bit at a fixed position in the DES key. Therefore, instead
     * of taking in a DES key and outputting a set of specific sub-keys for
     * that DES key, this method returns the array of *positions* at which
     * each bit in each sub-key will be found once (later) a DES key is
     * provided. This is why this method does not take the DES key
     * as input. Note that, in the DES standard and thus also in your
     * implementation, the bit positions in the key schedule are numbered
     * starting at 1, not 0. In other words, the leftmost (most significant)
     * bit of the key is at position 1.
     * 
     * The output of this method (in the console window, not as a returned
     * value) is thus an array of 16 position vectors, each of which is
     * 48 long. Actually, since we want to refer to each round through an
     * integer ranging from 1 to 16, the output is an array of 17 position
     * vectors, the first one of which (index 0) is left empty.
     * 
     * Finally, since the output of this method will later be used by the DES
     * algorithm to compute the actual sub-keys, the format of the output must
     * be a valid 2D Java array declaration/initialization that you will
     * manually copy into another Java class. Here is the required format of
     * the output of this method:
     * 
     * int[][] ks = {
     * {}, // unused
     * {10,51, ... }, // round 1
     * {2,43, ... }, // round 2
     * { ... },
     * { ... ,63,39} // round 16
     * };
     */
    static void outputHardwiredSchedule() {
        StringBuilder sb = new StringBuilder();
        ArrayList<int[]> ks = new ArrayList<>();
        int[] c = PC1c;
        int[] d = PC1d;
        for (int i = 1; i <= 16; i++) {
            if (i == 1 || i == 2 || i == 9 || i == 16) {
                c = shiftLeftBy1(c);
                d = shiftLeftBy1(d);
            } else {
                c = shiftLeftBy2(c);
                d = shiftLeftBy2(d);
            }

            int[] f = new int[c.length + d.length];
            System.arraycopy(c, 0, f, 0, c.length);
            System.arraycopy(d, 0, f, c.length, d.length);

            int[] subkey = new int[PC2.length];
            for (int j = 0; j < PC2.length; j++) {
                subkey[j] = f[PC2[j]-1];
            }
            ks.add(subkey);
        }
        ks.addFirst(new int[] {});
        sb.append("int[][] ks = {\n"); 
        for (int[] i : ks) {
            var str = Arrays.toString(i)
                    .replace("[", "{").replace("]", "}");
            sb.append(str.replaceAll(" ", "")+",\n");
        }
        sb.deleteCharAt(sb.length() - 2);
        sb.append("};");
        System.out.println(sb.toString());
    }// outputHardwiredSchedule method

}// class ComputeSubKeys