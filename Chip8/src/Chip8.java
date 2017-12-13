import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
public class Chip8 {

	public static void main(String[] args) {
		Path rom = Paths.get("");
		byte[] romMem = null;
		try {
			romMem = Files.readAllBytes(rom);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(romMem);
	}
}
