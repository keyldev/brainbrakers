package com.keyldev.brakerspodcast.Utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encoder {
    public static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(input.getBytes());
        byte[] digest = md.digest();

        // Преобразуем массив байт в строку
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
