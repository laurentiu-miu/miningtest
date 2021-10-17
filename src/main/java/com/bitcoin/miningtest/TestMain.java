package com.bitcoin.miningtest;

import com.bitcoin.miningtest.core.MerkleRootUtils;
import com.bitcoin.miningtest.model.Transaction;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.List;

public class TestMain {
    public static String reverseHex(String originalHex) {
        // TODO: Validation that the length is even
        int lengthInBytes = originalHex.length() / 2;
        char[] chars = new char[lengthInBytes * 2];
        for (int index = 0; index < lengthInBytes; index++) {
            int reversedIndex = lengthInBytes - 1 - index;
            chars[reversedIndex * 2] = originalHex.charAt(index * 2);
            chars[reversedIndex * 2 + 1] = originalHex.charAt(index * 2 + 1);
        }
        return new String(chars);
    }

    public static String encode(byte[] hash){
        return new BigInteger(1, hash).toString(16);
    }

    /**
     * INFO https://bitcoindev.network/calculating-the-merkle-root-for-a-block/
     * @param args
     */
    public static void main(String[] args) throws Exception{
        //MerkleRootUtils utils = new MerkleRootUtils();
        String tx1 = "3bd3a1309a518c381248fdc26c3a6bd62c35db7705069f59206684308cc237b3";
        String tx2 = "a99011a19e9894753d6c65c8fa412838ea8042886537588e7205734d5de8956d";
        System.out.println("tx1:"+tx1);
        System.out.println("tx2:"+tx2);
        String reverseTx1 = reverseHex(tx1);
        String reverseTx2 = reverseHex(tx2);
        System.out.println("revers tx1:"+reverseTx1);
        System.out.println("revers tx2:"+reverseTx2);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String concat = reverseTx1+reverseTx2;
        System.out.println("concat reverse tx1 + tx2:"+concat);
        byte[] hash = digest.digest(concat.getBytes(StandardCharsets.UTF_8));
        String sha256 = encode(hash);
        System.out.println("concat sha256:"+sha256);
        byte[] hash2 = digest.digest(sha256.getBytes(StandardCharsets.UTF_8));
        String sha2562 = encode(hash2);
        System.out.println("binary and sha256:"+sha2562);

        /**/
        MerkleRootUtils utils = new MerkleRootUtils();
        List<Transaction> transactions = List.of(Transaction.builder().hash(tx1).build(),
                Transaction.builder().hash(tx2).build());
        String merkleRoot = utils.calculateMerkleRoot(transactions).toString();

        System.out.println(merkleRoot);
    }
}
