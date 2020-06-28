package com.sxk.gu;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;

public class GuavaHash {

  public static void main(String[] args) {
    final HashFunction hf = Hashing.murmur3_128();

    HashCode hc = hf.hashBytes("a".getBytes());
    System.out.println(hc.toString());

    Hasher hasher = hf.newHasher();
    hasher.putBytes("a".getBytes());
    System.out.println(hasher.hash().toString());

  }

}
