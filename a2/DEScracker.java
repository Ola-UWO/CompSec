import java.util.HashMap;

/*****************************************************
 * CS 326 - Spring 2026 - Assignment #2
 * 
 * Student's full name: Olalekan Abdulsalam
 * Student's full name: Kyle Johnson
 * Student's full name: Dasha Coates
 *****************************************************/

class DEScracker {

    /* feel free to declare helper methods here, if needed */

    static String intToHexLetter(int num) {
        if (num == 10)
            return "A";
        else if (num == 11)
            return "B";
        else if (num == 12)
            return "C";
        else if (num == 13)
            return "D";
        else if (num == 14)
            return "E";
        else if (num == 15)
            return "F";
        else
            return num + "";
    }

    /*
     * complete the body of this method in between the provided code which
     * you may NOT modify.
     * This method takes a single command-line argument, namely a 16-character
     * string (in hexadecimal format) that represents a DES ciphertext block.
     * The output of this method is the most likely corresponding plaintext
     * block. For example:
     * 
     * > java DEScracker c737d897c2f1ebe3
     * 
     * would yield the output:
     * 
     * Loading dictionary... done
     * Most likely plaintext block: eggplant
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("This program should be invoked with the " +
                    "following argument:");
            System.out.println("  java DEScracker <16-hex-char ciphertext>");
            System.exit(1);
        }
        // you may edit this line to fit your local setup; but if you do so,
        // make sure to restore it to the following line in your submission
        Dict dict = new Dict("/usr/share/dict/words");
        String answer = "";
        String ciphertext = args[0];
        System.out.println(Utils.hexToBinString(ciphertext, 64));
        var cipherArr = Utils.binStringToIntArray(
                Utils.hexToBinString(ciphertext, 64));
        HashMap<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                var key = (intToHexLetter(i).repeat(8) +
                        intToHexLetter(j).repeat(8));
                var des = new DES(DES.getSubKeys(key));
                var plaintext = Utils.hexToText(
                        Utils.binStringToHex(
                                Utils.intArrayToBinString(
                                        des.decryptDES(cipherArr)))
                                .toUpperCase());
                System.out.println(key + " " + plaintext);
                int count = dict.countWords(plaintext.toUpperCase());
                map.put(count, plaintext);
            }
        }
        answer = map.get(
                map.keySet().stream().max(Integer::compare).orElse(0));
        System.out.println("Most likely plaintext block: " + answer);
    }// main method

}// class DEScracker
