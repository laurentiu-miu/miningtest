package com.bitcoin.miningtest.core;

import org.bouncycastle.util.encoders.Hex;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * A Sha256Hash just wraps a byte[] so that equals and hashcode work correctly, allowing it to be used as keys in a
 * map. It also checks that the length is correct and provides a bit more type safety.
 */
public class Sha256Hash implements Serializable {
    private byte[] bytes;

    public static Sha256Hash ZERO_HASH = new Sha256Hash(new byte[32]);

    /** Creates a Sha256Hash by wrapping the given byte array. It must be 32 bytes long. */
    public Sha256Hash(byte[] bytes) {
        assert bytes.length == 32;
        this.bytes = bytes;
    }

    /** Creates a Sha256Hash by decoding the given hex string. It must be 64 characters long. */
    public Sha256Hash(String string) {
        assert string.length() == 64;
        this.bytes = Hex.decode(string);
    }

    /** Returns true if the hashes are equal. */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Sha256Hash)) return false;
        return Arrays.equals(bytes, ((Sha256Hash)other).bytes);
    }

    /**
     * Hash code of the byte array as calculated by {@link Arrays#hashCode()}. Note the difference between a SHA256
     * secure bytes and the type of quick/dirty bytes used by the Java hashCode method which is designed for use in
     * bytes tables.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(bytes);
    }

    @Override
    public String toString() {
        return Utils.bytesToHexString(bytes);
    }

    /** Returns the bytes interpreted as a positive integer. */
    public BigInteger toBigInteger() {
        return new BigInteger(1, bytes);
    }

    public byte[] getBytes() {
        return bytes;
    }
}
