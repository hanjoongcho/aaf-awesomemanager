package me.blog.korn123.utils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Formatter;

import me.blog.korn123.log.AAFLogger;

/**
 * Created by CHO HANJOONG on 2016-10-17.
 */

public class DigestUtils {

    public static boolean isSupportAlgorithm(String algorithm) {
        boolean isSupport = algorithm.toUpperCase().matches("SHA-1|SHA-256|SHA-512|MD5");
        return isSupport;
    }

    public static String getHashCode(String message, String algorithm) {
        MessageDigest md = null;
        byte[] digest = null;
        if (!isSupportAlgorithm(algorithm)) {
            return null;
        }

        try {
            md = MessageDigest.getInstance(algorithm);
            md.update(message.getBytes("UTF-8"));
            digest = md.digest();
        } catch (Exception e) {
            AAFLogger.error("DigestUtils-getHashCode ERROR: " + e.getMessage() , DigestUtils.class);
        }

        return convertByteArrayToHex(digest);
    }

    public static String getHashCode(File targetFile, String algorithm) {

        if (!isSupportAlgorithm(algorithm)) {

            return null;
        }

        MessageDigest messageDigest = null;

        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DigestInputStream dis = null;

        try {
            messageDigest = MessageDigest.getInstance(algorithm);
            fis = new FileInputStream(targetFile);
            bis = new BufferedInputStream(fis);
            dis = new DigestInputStream(bis, messageDigest);
            // read the file and update the hash calculation
            while (dis.read() != -1) { };

        } catch (Exception e) {
            AAFLogger.error("DigestUtils-getHashCode ERROR: " + e.getMessage() , DigestUtils.class);
        } finally {
            IOUtils.closeQuietly(dis);
            IOUtils.closeQuietly(bis);
            IOUtils.closeQuietly(fis);
        }

        // get the hash value as byte array
        byte[] hash = messageDigest.digest();
        return convertByteArrayToHex(hash);
    }

    private static String convertByteArrayToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

}
