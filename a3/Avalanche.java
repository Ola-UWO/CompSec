
/*****************************************************
   CS 326 - Spring 2026 - Assignment #3

   Student's full name: Olalekan Abdulsalam
   Student's full name: Kyle Johnson
   Student's full name: Dasha Coates

 *****************************************************/
import java.util.*;

class Avalanche extends Feistel {
    /* do NOT modify this constructor */
    Avalanche(int[][] K) {
        super(32, 16, new DESround(), K);
    }// constructor

    /*
     * Takes in two plaintext blocks and encrypts them with DES
     * while producing (in the console window) the detailed output
     * described in the handout for this assignment.
     */
    void testEffect(int[] block, int[] block2) {
        block = Utils.applyPermut(DES.IP, block);
        block2 = Utils.applyPermut(DES.IP, block2);
        for (int i = 1; i <= n; i++) {
            int[] leftSave = Arrays.copyOfRange(block, 0, w);
            int[] leftSave2 = Arrays.copyOfRange(block2, 0, w);
            System.arraycopy(block, w, block, 0, w);
            System.arraycopy(block2, w, block2, 0, w);
            leftSave = Utils.XOR(leftSave,
                    F.round(Arrays.copyOfRange(block, 0, w), K[i]));
            leftSave2 = Utils.XOR(leftSave2,
                    F.round(Arrays.copyOfRange(block2, 0, w), K[i]));
            System.arraycopy(leftSave, 0, block, w, w);
            System.arraycopy(leftSave2, 0, block2, w, w);
            printRound(String.format("Round %02d", i), block, block2);
        }
        for (int i = 0; i < w; i++) {
            int t = block[i];
            block[i] = block[i + w];
            block[i + w] = t;
            t = block2[i];
            block2[i] = block2[i + w];
            block2[i + w] = t;
        }
        block = Utils.applyPermut(DES.IPinv, block);
        block2 = Utils.applyPermut(DES.IPinv, block2);
        printRound("IPinv", block, block2);
    }// testEffect method

    /**
     * Printer helper 
     * @param label
     * @param b1
     * @param b2
     */
    private void printRound(String label, int[] b1, int[] b2) {
        String bin1 = Utils.intArrayToBinString(b1);
        String bin2 = Utils.intArrayToBinString(b2);
        StringBuilder stars = new StringBuilder();
        int count = 0;
        for (int j = 0; j < 2 * w; j++) {
            if (j == w)
                stars.append(" ");
            if (bin1.charAt(j) == bin2.charAt(j))
                stars.append(" ");
            else {
                stars.append("*");
                count++;
            }
        }
        String hex1 = String.join("   ", Utils.binStringToHex(bin1).split(""));
        String hex2 = String.join("   ", Utils.binStringToHex(bin2).split(""));
        hex1 = hex1.substring(0, 29) + " " + hex1.substring(29, 61);
        hex2 = hex2.substring(0, 29) + " " + hex2.substring(29, 61);
        System.out.printf("%-9s%s %s%n",
                label, bin1.substring(0, w), bin1.substring(w));
        System.out.printf("%41s %s%n",
                bin2.substring(0, w), bin2.substring(w));
        System.out.printf("%74s %d%n", stars, count);
        System.out.printf("%74s%n%74s%n", hex1, hex2);
    }

    /* This method will be used for testing purposes. Do NOT modify. */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Avalanche <key> <P1> <P2>");
            System.exit(1);
        }

        String key = args[0];
        String plaintext1 = args[1];
        String plaintext2 = args[2];
        Avalanche av = new Avalanche(DES.getSubKeys(key));
        av.testEffect(Utils.getBitVectorFromHex(plaintext1),
                Utils.getBitVectorFromHex(plaintext2));
    }// main method

}// class Avalanche
