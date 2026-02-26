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
        keys.put("publicKey", convertToPem("PUBLIC KEY", pair.getPublic().getEncoded()));
        keys.put("privateKey", convertToPem("PRIVATE KEY", pair.getPrivate().getEncoded()));
        return keys;
    }

    public static Map<String, String> generateEcKeyPair(String curve) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
        keyGen.initialize(new ECGenParameterSpec(curve));
        KeyPair pair = keyGen.generateKeyPair();

        Map<String, String> keys = new HashMap<>();
        keys.put("publicKey", convertToPem("PUBLIC KEY", pair.getPublic().getEncoded()));
        keys.put("privateKey", convertToPem("PRIVATE KEY", pair.getPrivate().getEncoded()));
        return keys;
    }

    public static String convertToPem(String type, byte[] encodedKey) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----BEGIN ").append(type).append("-----\n");
        String base64 = Base64.getEncoder().encodeToString(encodedKey);
        for (int i = 0; i < base64.length(); i += 64) {
            int end = Math.min(i + 64, base64.length());
            sb.append(base64, i, end).append("\n");
        }
        sb.append("-----END ").append(type).append("-----");
        return sb.toString();
    }

    public static String cleanPem(String pem) {
        if (pem == null) return null;
        if (!pem.contains("-----BEGIN")) {
            return pem.replaceAll("\\s", "");
        }
        return pem.replaceAll("-----BEGIN.*?-----", "")
                .replaceAll("-----END.*?-----", "")
                .replaceAll("\\s", "");
    }
}
