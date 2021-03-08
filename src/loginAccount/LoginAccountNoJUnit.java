package loginAccount;

import java.io.IOException;

public class LoginAccountNoJUnit {
	
	static void loginAccountTest() {
		try {
			LoginAccount.createAccountFile();
		}
		catch(Exception e) {
			System.out.println(e);
			e.printStackTrace(System.out);
		}
		
	}
	static void loginCreateAccountTest() {
		try {
			LoginAccount.loginCreateAccount();
		}
		catch(Exception e) {
			System.out.println(e);
			e.printStackTrace(System.out);
		}
	}

	static void shoppingCartTest() {
		try {
			ShoppingCart.getPrice();
		}
		catch (Exception e){
			System.out.println(e);
			e.printStackTrace(System.out);
		}
	}
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		loginAccountTest();
		loginCreateAccountTest();
		shoppingCartTest();
	}
}
