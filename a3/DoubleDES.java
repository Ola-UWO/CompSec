/*****************************************************
   CS 326 - Spring 2026 - Assignment #3

   Student's full name: Olalekan Abdulsalam
   Student's full name: Kyle Johnson
   Student's full name: Dasha Coates

*****************************************************/

class DoubleDES
{
    /* add any instance variables needed by objects of this class */
    int[][] subkey1;
    int[][] subkey2;
    /* given two 64-bit keys represented as strings of hex digits,
       instantiate a DoubleDES object
    */
    DoubleDES(String key1, String key2)
    {
        subkey1 = DES.getSubKeys(key1);
        subkey2 = DES.getSubKeys(key2);
    }// constructor

    /* given a 64-bit plaintext block, return the ciphertext block
       obtained by DoubleDES encryption of the input block
    */
    int[] encrypt(int[] block)
    {
        block = new DES(subkey1).encryptDES(block);
        return new DES(subkey2).encryptDES(block);            
    }// encrypt method

    /* given a 64-bit ciphertext block, return the plaintext block
       obtained by DoubleDES decryption of the input block
    */
    int[] decrypt(int[] block)
    {
        block = new DES(subkey2).decryptDES(block);
        return new DES(subkey1).decryptDES(block);             
    }// decrypt method

    /* This method is used for testing. Do NOT modify. */
    public static void main(String[] args)
    {       
        if (args.length != 3) {
            System.out.println("Usage: java DoubleDES <key1> <key2> <P>");
            System.exit(1);
        }

        String key1 = args[0];
        String key2 = args[1];
        String plaintext = args[2];
        DoubleDES ddes = new DoubleDES(key1,key2);
        int[] ciphertext = ddes.encrypt(Utils.getBitVectorFromHex(plaintext));
        int[] decPlaintext = ddes.decrypt(ciphertext);
        
        System.out.format("plaintext  = %s\nciphertext = %s\ndecrypted  = %s\n",
                          plaintext,
                          Utils.getHex(ciphertext),
                          Utils.getHex(decPlaintext));
    }// main method
}// class DoubleDES
