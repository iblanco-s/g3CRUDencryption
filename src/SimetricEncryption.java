import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.BadPaddingException;

import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class SimetricEncryption {


    private static byte[] salt = "g3 CRUD is salt!".getBytes(); 

    /**
     * Cifra un texto con AES, modo CBC y padding PKCS5Padding (simétrica) y lo
     * retorna
     * 
     * @param clave   La clave del usuario
     * @param mensaje El mensaje a cifrar
     * @return Mensaje cifrado
     */
    public String cifrarTexto(String clave, String mensaje) {
        String ret = null;
        KeySpec derivedKey = null;
        SecretKeyFactory secretKeyFactory = null;
        try {

            derivedKey = new PBEKeySpec(clave.toCharArray(), salt, 65536, 128); 
	    	// AES tiene los siguientes tmños de key: 128, 192, or 256 bits
            
            // SecretKeyFactory is used to obtain a secret key from the key specification using the PBKDF2 algorithm 
            secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
             
            byte[] derivedKeyPBK = secretKeyFactory.generateSecret(derivedKey).getEncoded();
                     
            SecretKey derivedKeyPBK_AES = new SecretKeySpec(derivedKeyPBK, 0, derivedKeyPBK.length, "AES");

          
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");	    
            cipher.init(Cipher.ENCRYPT_MODE, derivedKeyPBK_AES);
            byte[] encodedMessage = cipher.doFinal(mensaje.getBytes()); // Mensaje cifrado !!!
            byte[] iv = cipher.getIV(); // vector de inicializaci�n  
             
            // Añadimos el vector de inicialización
            byte[] combined = concatArrays(iv, encodedMessage);

            fileWriter("C:\\Users\\2dam\\Desktop\\keys\\mailCredentials.properties", combined);

            ret = new String(encodedMessage);

        } catch (InvalidKeyException | NoSuchAlgorithmException | InvalidKeySpecException | BadPaddingException | IllegalBlockSizeException | NoSuchPaddingException e) {
            
        }
        return ret;
    }

    /**
     * Retorna una concatenaci�n de ambos arrays
     * 
     * @param array1
     * @param array2
     * @return Concatenaci�n de ambos arrays
     */
    private byte[] concatArrays(byte[] array1, byte[] array2) {
        byte[] ret = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, ret, 0, array1.length);
        System.arraycopy(array2, 0, ret, array1.length, array2.length);
        return ret;
    }

    /**
     * Escribe un fichero
     * 
     * @param path Path del fichero
     * @param text Texto a escibir
     */
    private void fileWriter(String path, byte[] text) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
                fos.write(text);
        } catch (IOException e) {
                e.printStackTrace();
        }
    }

}