package loginAccount;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class Purchase extends ShoppingCart {

    public static void performPurchase() throws SQLException, IOException, ClassNotFoundException {
        Scanner input = new Scanner(System.in);

        System.out.println("Please enter in your credit card. For example xxxx-xxxx-xxxx");
        String creditCard = input.nextLine();
        if (creditCard.length()==14) {
            System.out.println("Your ordering processing. Give us a moment");
            System.out.println("===========================================");
            System.out.println();
        }
        else {
            System.out.println("Please enter in your credit card. For example xxxx-xxxx-xxxx");
            creditCard = input.nextLine();
        }

        System.out.println("$"+ df.format(plusShip) +" has been processed " +loginName);
        System.out.println("Your order was successful.");
        try {
            UseOfDatabase.insertPurchase();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
