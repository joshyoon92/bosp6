package loginAccount;

import static org.junit.Assert.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

import org.junit.*;


public class LoginAccountTest {
	private static Formatter accountFile;
	@Test
	public void testCreateFile() throws IOException {
		
		FileWriter fileWriter = new FileWriter("AccountInformation.txt",true);
		accountFile = new Formatter(fileWriter);
		accountFile.format("%s %s","josh","joshyoon93");
				
		Scanner file = new Scanner(new File("AccountInformation.txt"));
		String name = file.next();
		String id = file.next();
		System.out.println(name);
		System.out.println(id);
		assertEquals("josh", name);
		assertEquals("joshyoon93", id);
		
		file.close();
	}

	@Test
	public void testLoginCreateAccount() throws FileNotFoundException {
		
		Scanner file = new Scanner(new File("AccountInformation.txt"));
		String name = file.next();
		String id = file.next();
		System.out.println(name);
		System.out.println(id);
		assertEquals("josh", name);
		assertEquals("joshyoon93", id);
		
	}

}
