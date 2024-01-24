import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
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

public class AsimetricEncryption {

	    public static void keyPairGenerator() {
        try {
            // Si no existe el archivo de clave privada o pública, se generan
			File publicKeyFile = new File("C:\\Users\\2dam\\Desktop\\keys\\keyspublicKey.der");
			File privateKeyFile = new File("C:\\Users\\2dam\\Desktop\\keys\\keysprivateKey.der");
            if (!publicKeyFile.exists() || !privateKeyFile.exists()) {
                publicKeyFile.createNewFile();
				privateKeyFile.createNewFile();

				KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
                keyPairGenerator.initialize(256);
                KeyPair key = keyPairGenerator.generateKeyPair();

                PublicKey publicKey = key.getPublic();
                byte[] publicKeyBytes = publicKey.getEncoded();

                try (FileOutputStream publicKeyStream = new FileOutputStream("C:\\Users\\2dam\\Desktop\\keys\\keyspublicKey.der")) {
                    publicKeyStream.write(publicKeyBytes);
                }

                PrivateKey privateKey = key.getPrivate();
                byte[] privateKeyBytes = privateKey.getEncoded();
                try (FileOutputStream privateKeyStream = new FileOutputStream("C:\\Users\\2dam\\Desktop\\keys\\keysprivateKey.der")) {
                    privateKeyStream.write(privateKeyBytes);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
