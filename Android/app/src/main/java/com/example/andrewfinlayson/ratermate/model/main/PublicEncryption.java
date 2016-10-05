package com.example.andrewfinlayson.ratermate.model.main;

/**
 * Created by andrewfinlayson on 5/10/2016.
 */

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

public class PublicEncryption implements Serializable{

    private BigInteger n;
    private BigInteger g;
    private int modLength;
    private BigInteger nsquare;

    public PublicEncryption(int modLength, BigInteger n, BigInteger g)
    {
        this.modLength = modLength;
        this.n = n;
        this.g = g;
        nsquare = n.multiply(n);
    }

    public BigInteger randomZStarN()
    {
        BigInteger r;
        do
        {
            r = new BigInteger(modLength, new Random());
        }
        while (r.compareTo(n) >= 0 || r.gcd(n).intValue() != 1);
        return r;
    }

    public BigInteger getN()
    {
        return n;
    }

    public BigInteger getNsquare()
    {
        return nsquare;
    }

    public BigInteger getG()
    {
        return g;
    }

    public BigInteger encrypt(BigInteger m) throws Exception
    {
        // if m is not in Z_n
        if (m.compareTo(BigInteger.ZERO) < 0 || m.compareTo(n) >= 0)
        {
            throw new Exception("Paillier.encrypt(BigInteger m): plaintext m is not in Z_n");
        }

        // generate r, a random integer in Z*_n
        BigInteger r = randomZStarN();

        // c = g^m * r^n mod n^2
        return (g.modPow(m, nsquare).multiply(r.modPow(n, nsquare))).mod(nsquare);
    }
}
