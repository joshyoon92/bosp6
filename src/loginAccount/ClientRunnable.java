package loginAccount;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ClientRunnable extends LoginAccount implements Runnable{

    private static int SLEEP_TIME_1 = 500;

    public void run() {

        try (ObjectOutputStream shoppingFile = new ObjectOutputStream(new FileOutputStream("ShoppingCartStream.dat"))){

            shoppingFile.writeObject(new Order(loginName, order1, order2, order3, order4));

            Thread.sleep(ClientRunnable.SLEEP_TIME_1);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
