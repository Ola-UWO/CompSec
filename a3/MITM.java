
/*****************************************************
   CS 326 - Spring 2026 - Assignment #3

   Student's full name: Olalekan Abdulsalam
   Student's full name: Kyle Johnson
   Student's full name: Dasha Coates

*****************************************************/

import java.util.*;

class MITM {
    /*
     * Given two plaintext-ciphertext pairs and the number numBits of
     * significant bits in each key, perform a MITM attack of DoubleDES,
     * that is, try all key pairs from 0 up (in which each key is numBits long)
     * and output ALL of the key pairs that produce the two given ciphertexts
     * given the respective plaintexts. This method also records the time in
     * seconds to find each matching key pair.
     * This method sends to the console window some output formatted as follows:
     * 
     * 000000000000000f 000000000000000d time = 0.021s
     * 000000000000000f 000000000000000e time = 0.021s
     * ....
     * 0000000000000007 0000000000000006 time = 0.029s
     * 0000000000000007 0000000000000007 time = 0.030s
     * # key pairs = 7
     * 
     * where each line contains a matching key pair followed by the runtime
     * since the start of the execution of the method. In the example above,
     * numBits is 4 (only the 4 least significant digit of each key can be equal
     * to 1). Therefore, the MITM attack in this case tried all
     * key pairs in which each key value ranged from 0 to 15 (or 0 to f in hex).
     * In this made-up example, there were 7 key pairs (only 4 are shown) that
     * produced the ciphertext block from the corresponding plaintext block in
     * both input pairs.
     */

    static void meetInTheMiddle(String P1, String C1,
            String P2, String C2,
            int numBits) {
        long startTime, elapsedTime;
        startTime = System.currentTimeMillis();
        var keySpace = Math.pow(2, numBits);
        int numKeyPairs = 0;
        HashMap<String, List<String>> map1 = new HashMap<>();
        HashMap<String, List<String>> map2 = new HashMap<>();
        getLookupTable(P1, keySpace, map1);
        getLookupTable(P2, keySpace, map2);
        for (int i = 0; i < keySpace; i++) {
            var key2 = getKeyHex(i);
            var des1 = new DES(DES.getSubKeys(key2));
            var ans1 = Utils.getHex(des1.decryptDES(
                    Utils.getBitVectorFromHex(C1)));
            var ans2 = Utils.getHex(des1.decryptDES(
                    Utils.getBitVectorFromHex(C2)));
            if (map1.containsKey(ans1) && map2.containsKey(ans2)) {
                for (String key1 : map1.get(ans1)) {
                    System.out.printf("%29s %s  time = ", key1, key2);
                    elapsedTime = System.currentTimeMillis() - startTime;
                    System.out.print((elapsedTime / 1000.0) + "s");
                    numKeyPairs++;
                    System.out.println();
                }
            }
        }
        System.out.printf(" # key pairs = %d%n", numKeyPairs);
    }// meetInTheMiddle method

    /**
     * Helper Method
     * 
     * @param P1
     * @param keySpace
     * @param map
     */
    private static void getLookupTable(String P1,
            double keySpace,
            HashMap<String, List<String>> map) {
        for (int i = 0; i < keySpace; i++) {
            var key1 = getKeyHex(i);
            var des1 = new DES(DES.getSubKeys(key1));
            var temp = Utils.getHex(
                    des1.encryptDES(
                            Utils.getBitVectorFromHex(P1)));
            map.computeIfAbsent(temp, keys -> new ArrayList<>()).add(key1);
        }
    }

    /**
     * Helper Method
     * 
     * @param i
     * @return
     */
    private static String getKeyHex(int i) {
        var bin = Integer.toBinaryString(i);
        return Utils.binStringToHex("0".repeat(64 - bin.length()) +
                Integer.toBinaryString(i));
    }

    /*
     * Given a test number and a number of bits, this method returns
     * an array of two keys, each of which has only numBits
     * significant bits and has a specific value hardcoded in the test
     * case number. This method also outputs to the console window the
     * value of the two keys it returns.
     * 
     * This method will be called to produce the two keys that
     * DoubleDES uses to encrypt two plaintext blocks and before
     * invoking the meetInTheMiddle method. Therefore, the key pair that
     * the MITM attack is trying to find is printed just above
     * the output of the search.
     * 
     * Do NOT modify this method.
     */
    static String[] getKeyPair(int testNumber, int numBits) {
        String key1 = "", key2 = "";
        switch (testNumber) {
            case 1:
                key1 = "1";
                key2 = "1";
                for (int i = 1; i <= numBits - 1; i++) {
                    key1 += "1";
                    key2 += "0";
                }
                break;
            case 2:
                key1 = "1";
                key2 = "1";
                for (int i = 1; i <= numBits - 1; i++) {
                    key1 += "1";
                    key2 += (i % 2) + "";
                }
                break;
        }
        while (key1.length() % 4 != 0) {
            key1 = "0" + key1;
            key2 = "0" + key2;
        }
        key1 = Utils.binStringToHex(key1);
        key2 = Utils.binStringToHex(key2);
        while (key1.length() < 16) {
            key1 = "0" + key1;
            key2 = "0" + key2;
        }
        System.out.println("Actual keys: " + key1 + " " + key2 +
                "  numBits = " + numBits);

        return new String[] { key1, key2 };
    }// getKeyPair method

    /*
     * This code is used for testing purposes. This driver code
     * invokes the meetInTheMiddle method repeatedly with numBits equal to
     * 2, then to 3, then to 4, etc. You will have to terminate this
     * program (e.g., with a CTRL-C) as soon as the TOTAL runtime of
     * the last MITM attack has exceeded 60 seconds.
     * 
     * Do NOT modify this method.
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java MITM [test1 or test2]" +
                    "<P1> <P2>");
            System.exit(1);
        }
        String testCase = args[0];
        String P1 = args[1];
        String P2 = args[2];
        String key1 = "", key2 = "";
        int numBits;
        String[] keys = null;
        for (numBits = 2; true; numBits++) {
            // generate an actual key pair
            if (testCase.equals("test1")) {
                keys = getKeyPair(1, numBits);
            } else if (testCase.equals("test2")) {
                keys = getKeyPair(2, numBits);
            } else {
                System.out.println("This test case is not implemented.");
                System.exit(1);
            }
            key1 = keys[0];
            key2 = keys[1];
            // encrypt the plaintexts
            DoubleDES ddes = new DoubleDES(key1, key2);
            String C1 = Utils.getHex(ddes.encrypt(Utils.getBitVectorFromHex(P1)));
            String C2 = Utils.getHex(ddes.encrypt(Utils.getBitVectorFromHex(P2)));
            meetInTheMiddle(P1, C1, P2, C2, numBits);
        } // loop on numBits
    }// main method
}// class MITM
