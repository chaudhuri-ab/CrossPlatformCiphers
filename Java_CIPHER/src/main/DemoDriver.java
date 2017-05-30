/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import static ciphers.Java_AES_Cipher.decrypt;
import static ciphers.Java_AES_Cipher.encrypt;

/**
 *
 * @author abc1986
 */
public class DemoDriver {

    public static void main(String[] args) {

        String key = "0123456789abcdef"; // 128 bit key
        String initVector = "fedcba9876543210"; // 16 bytes IV

        System.out.println(decrypt(key,
                encrypt(key, initVector, "Hi")));
        
         System.out.println(decrypt(key, "Kx+c85B7tWjre4j6cdXHJQ==:ZmVkY2JhOTg3NjU0MzIxMA==")); // output from PHP code Testing
    }
}
