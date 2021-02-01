package covid.plattform.consumer.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


@Component
public class Encryptor {

    // Method for decrypting data with AES128
    public String decode(String data){
        String key="0123456789123456";
        String initVector="0123456789123456";
        byte[] decrypted = new byte[0];
        try {
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE , skeySpec, iv);
        decrypted = cipher.doFinal(Base64.decodeBase64(data));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new String(decrypted);

    }
    // Method for encrypting data with AES128
    public String encrypt(String data){
        String key="0123456789123456";
        String initVector="0123456789123456";
        byte[] encrypted = new byte[0];
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE , skeySpec, iv);

            encrypted = cipher.doFinal(data.getBytes());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Base64.encodeBase64String(encrypted);
    }




}
