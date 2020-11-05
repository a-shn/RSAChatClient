package ru.itis.services;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Cryptor {
    private final static BigInteger one = new BigInteger("1");
    private final static SecureRandom random = new SecureRandom();

    private final BigInteger privateKey;
    private final BigInteger publicKey;
    private final BigInteger myPublicKey;
    private final BigInteger modulus;

    // generate an N-bit (roughly) public and private key
    public Cryptor(int N, BigInteger publicKey, BigInteger myPublicKey) {
        this.publicKey = publicKey;
        this.myPublicKey = myPublicKey;
        BigInteger p = BigInteger.probablePrime(N, random);
        BigInteger q = BigInteger.probablePrime(N, random);
        modulus = p.multiply(q);
        BigInteger phi = p.subtract(one).multiply(q.subtract(one));
        privateKey = myPublicKey.modInverse(phi);
    }


    public BigInteger encrypt(BigInteger message) {
        return message.modPow(publicKey, modulus);
    }

    public BigInteger decrypt(BigInteger encrypted) {
        return encrypted.modPow(privateKey, modulus);
    }
}
