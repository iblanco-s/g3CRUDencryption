import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
	static String readFile(String path) throws IOException
	{
  		byte[] encoded = Files.readAllBytes(Paths.get(path));
  		return new String(encoded);
	}
	public static void main(String[] args) {
		AsimetricEncryption.keyPairGenerator();
		SimetricEncryption simetricEncryption = new SimetricEncryption();
		try {
			simetricEncryption.cifrarTexto(readFile("C:\\Users\\2dam\\Desktop\\keys\\keysprivateKey.der") , readFile("C:\\Users\\2dam\\Desktop\\keys\\emailCredentials.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
