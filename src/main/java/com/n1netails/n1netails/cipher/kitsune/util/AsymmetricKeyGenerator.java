package com.n1netails.n1netails.cipher.kitsune.util;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class AsymmetricKeyGenerator {

    public static Map<String, String> generateRsaKeyPair(int keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(keySize);
        KeyPair pair = keyGen.generateKeyPair();

        Map<String, String> keys = new HashMap<>();
        keys.put("publicKey", Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()));
        keys.put("privateKey", Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded()));
        return keys;
    }

    public static Map<String, String> generateEcKeyPair(String curve) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
        keyGen.initialize(new ECGenParameterSpec(curve));
        KeyPair pair = keyGen.generateKeyPair();

        Map<String, String> keys = new HashMap<>();
        keys.put("publicKey", Base64.getEncoder().encodeToString(pair.getPublic().getEncoded()));
        keys.put("privateKey", Base64.getEncoder().encodeToString(pair.getPrivate().getEncoded()));
        return keys;
    }
}
