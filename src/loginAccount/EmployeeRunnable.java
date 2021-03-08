package loginAccount;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class EmployeeRunnable extends LoginAccount implements Runnable {

    private static int SLEEP_TIME = 300;

    public void run() {

        try (ObjectOutputStream shoppingFile = new ObjectOutputStream(new FileOutputStream("invoice.dat"))){

            shoppingFile.writeObject(new Order(loginName, order1, order2, order3, order4));

            Thread.sleep(EmployeeRunnable.SLEEP_TIME);
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
