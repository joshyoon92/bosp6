package loginAccount;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.io.*;
import java.util.Scanner;
import java.util.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.Random;

public class LoginAccount extends UseOfDatabase{
	private static Formatter accountFile;
	static String[] soupName = {"Gom", "GomTang", "Bulgo", "Spicy"};
	protected static int accountId;
	protected static String loginName;
	protected static String Password;
	protected static String FirstName;
	protected static String LastName;
	protected static String Address;
	protected static long Phone;
	protected static String Email;
	protected static String AccountType;
	protected static long CreditCard;
	protected static int order1;
	protected static int order2;
	protected static int order3;
	protected static int order4;
	protected static String date;


	public static String setTitle(String login){
		loginName = login;
		return login;
	}

	public static void createAccountFile() throws IOException, ClassNotFoundException, SQLException {
		try {
			FileWriter fileWriter = new FileWriter("AccountInformation.txt",true);
			accountFile = new Formatter(fileWriter);
		}
		catch (FileNotFoundException ex) {
			System.err.println("Cannot open file AccountInformation.txt ... quitting");
		}
		loginCreateAccount();
	}

	public static void loginCreateAccount() throws IOException, ClassNotFoundException, SQLException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		date = dtf.format(now);

		Scanner input = new Scanner(System.in);
		System.out.print("Login, New Account or Continue as Guest: ");
		String answer1 = input.nextLine();

		if (answer1.equals("Login")) {
			System.out.print("Your Login: ");
			String yourLogin = input.nextLine();
			//Set the login so that it can be written in the shoppingCart text file for reference.
			setTitle(yourLogin);
			System.out.println("Your login is "+yourLogin);
			System.out.print("Your Password: ");
			String yourPassword = input.nextLine();
			System.out.println("You have successfully logged in.");

			//After logging in, there is a choice of 1. Products 2. Shopping Cart
			System.out.print("1. Products  2. Shopping Cart: ");
			String yourChoice = input.nextLine();
			if (yourChoice.equals("Products")) {
				products(soupName);
			}
			else if (yourChoice.equals("Shopping Cart")) {
				ShoppingCart.getPrice();
			}
			else {
				System.out.print("1. Products  2. Shopping Cart");
				yourChoice = input.nextLine();
			}
		}
		else if (answer1.equals("New Account")) {

			String yesNo = "no";
			while(yesNo.equals("no")) {
				Random rand = new Random();
				accountId = rand.nextInt(100);
				System.out.print("Account Type: Employee or Customer: ");
				String yourAccount = input.nextLine();
				AccountType = yourAccount;
				System.out.print("Your First name: ");
				String yourName = input.nextLine();
				FirstName = yourName;
				System.out.print("Your Last name: ");
				String lastName = input.nextLine();
				LastName = lastName;
				System.out.print("New Login: ");
				String yourNewLogin = input.nextLine();
				loginName = yourNewLogin;
				System.out.print("Your password: ");
				String yourNewPass = input.nextLine();
				Password = yourNewPass;
				System.out.print("Your email: ");
				String youEmail = input.nextLine();
				Email = youEmail;
				System.out.print("Your address: ");
				String yourAddress = input.nextLine();
				Address = yourAddress;
				System.out.print("Phone Number: (xxxxxxxxxx) ");
				long yourPhone = input.nextLong();
				Phone = yourPhone;
				System.out.print("Credit card number: ");
				long yourCreditCard = input.nextLong();
				CreditCard = yourCreditCard;
				System.out.println("Create? Yes or No?: ");
				String yes1No = input.next();
				yesNo = yes1No;

				//put in the new account information to the database.
				try {UseOfDatabase.insertEmp();}
				catch (SQLException e){	e.printStackTrace();}

				if (yesNo.equals("yes")) {
					accountFile.format("%s %s %s %s %s %d!%n",yourName,yourNewLogin,yourNewPass,yourAddress,yourPhone,yourCreditCard);
					System.out.println("Your account has been created! Welcome to BOSP");
				}
			}
		}
		else if (answer1.equals("Continue as Guest")) {
			System.out.print("Your name: ");
			String yName = input.nextLine();
			setTitle(yName);
			System.out.println("Please continue to browse our products");
			System.out.print("1. Products  2. Shopping Cart: ");
			String yourChoice = input.nextLine();

			if (yourChoice.equals("Products")) {
				products(soupName);
			}
			else if (yourChoice.equals("Shopping Cart")) {
				ShoppingCart.getPrice();
			}
		}
		input.close();
		accountFile.close();
	}

	public static <T> void products(T[] a) throws IOException, ClassNotFoundException, SQLException {
		Scanner input = new Scanner(System.in);
		System.out.print("Select Products: 1.Gom, 2.GomTang, 3.Bulgo, 4.Spicy");
		System.out.println("  (For example: 3 Gom 2 GomTang 0 Bulgo 2 Spicy): ");
		String order = input.nextLine();

		List<String> orderList = new ArrayList<String>();
		for (String i: order.split(" ")) {
			orderList.add(i);
		}
		for (int i=1; i<orderList.size(); i+=2){
			String orderItem = orderList.get(i);
			for (T elem: a) {
				if (orderItem.equals(elem)) {
					continue;
				} else {break;}
			}
		}

		int orderNum = Integer.parseInt(orderList.get(0));
		order1 = orderNum;
		int orderNum1 = Integer.parseInt(orderList.get(2));
		order2 = orderNum1;
		int orderNum2 = Integer.parseInt(orderList.get(4));
		order3 = orderNum2;
		int orderNum3 = Integer.parseInt(orderList.get(6));
		order4 = orderNum3;

		//Writing to two different files concurrently.
		EmployeeRunnable firstR = new EmployeeRunnable();
		Thread firstThread = new Thread(firstR);
		ClientRunnable secondR = new ClientRunnable();
		Thread secondThread = new Thread(secondR);
		firstThread.start();
		secondThread.start();


		System.out.println(loginName+", please review your products in the shopping cart.");
		System.out.println("1. Products  2. Shopping Cart: ");

		String pro_shop = input.nextLine();
		if (pro_shop.equals("Shopping Cart")) {
			ShoppingCart.getPrice();
		}
		input.close();

	}
}
