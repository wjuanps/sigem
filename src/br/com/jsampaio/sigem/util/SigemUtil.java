/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jsampaio.sigem.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Janilson
 */
public final class SigemUtil {
    
    /**
     *
     * @param string
     * @return
     */
    public static String criptografar(String string) {

        String aux = string;
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte messageDigest[] = md5.digest(aux.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : messageDigest) {
                hexString.append(String.format("%02X", 0xFF & b));
            }
            aux = hexString.toString();

        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            
        }

        return aux;
    }
    
}
