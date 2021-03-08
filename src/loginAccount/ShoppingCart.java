package loginAccount;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.sql.SQLException;
import java.util.*;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ShoppingCart extends LoginAccount {

    private static double gom;
    private static double gomTang;
    private static double bulgo;
    private static double spicy;
    protected static double tax = 1.08, gomP=4.50, gomTangP=5.25, bulgoP=4.25,spicyP=5.50, shipping=3.50;
    private static String[] order = new String[5];
    private static Double[] orderQuant = new Double[4];
    private static List<Double> orderDouble = new ArrayList<>();
    protected static DecimalFormat df = new DecimalFormat("0.00");
    public static double plusShip;
    protected static String orderSummary;
    protected static int purchaseId;

    public static void getPrice() throws IOException, ClassNotFoundException, SQLException {
        try (ObjectInputStream infile = new ObjectInputStream(new FileInputStream("ShoppingCartStream.dat"))) {
            String readO = String.valueOf(infile.readObject());
            order = readO.split(" ");
        }
        catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        calculatePrice();
    }
    interface lambdaOperation {
        double operation(double arg1, double arg2);
    }

    public static void calculatePrice() throws IOException, ClassNotFoundException, SQLException {
        Scanner input = new Scanner(System.in);
        lambdaOperation multiplication = (x, y) -> (x * y);
        lambdaOperation addition = (x, y) -> x + y;

        //Filling in double array with only order quantities
        for (int i=1; i<order.length; i++){
            double oDouble= Double.parseDouble(order[i]);
            orderQuant[i-1]=oDouble;
            orderDouble.add(oDouble);
        }

        //calculate each product by quantity.
        for (int i = 0; i < orderQuant.length; i++) {
            //Gom GomTang Bulgo Spicy
            if (i == 0) {
                gom = multiplication.operation(orderQuant[i], gomP);
            } else if (i == 1) {
                gomTang = multiplication.operation(orderQuant[i], gomTangP);
            } else if (i == 2) {
                bulgo = multiplication.operation(orderQuant[i], bulgoP);
            } else if (i == 3) {
                spicy = multiplication.operation(orderQuant[i], spicyP);
            }
        }
        //add all products by cost + tax +shipping
        Random random = new Random();
        purchaseId = random.nextInt(900)+100;

        double p1p2 = addition.operation(gom, gomTang);
        double p3p4 = addition.operation(bulgo, spicy);
        double p1234 = addition.operation(p1p2, p3p4);
        //if total quantity ordered is 10 or more, there is a 10% discount.
        double sum = orderDouble.stream().filter(i-> i>2).mapToDouble(i->i.doubleValue()).sum();
        if (sum>=10){
            p1234 = p1234*.9;
        }
        double plusTax = multiplication.operation(p1234, tax);
        double justTax = multiplication.operation(p1234,tax-1);
        String plusShipString = df.format(addition.operation(plusTax, shipping));
        plusShip = Double.parseDouble(plusShipString);
        String customer = order[0];

        orderSummary = customer + ", you have ordered " + order[1] + " Gom " + order[2] + " GomTang " + order[3] + " Bulgo " + order[4] + " Spicy";
        System.out.println(orderSummary);
        System.out.println("Gom: "+order[1]+" x "+gomP+" = $" +gom);
        System.out.println("GomTang: "+order[2]+" x "+gomTangP+" = $"+gomTang);
        System.out.println("Bulgo: "+order[3]+" x "+bulgoP+" = $"+bulgo);
        System.out.println("Spicy: "+order[4]+" x "+spicyP+" = $"+spicy);
        if (sum>=10) {
            System.out.println(customer+", you have received a 10% discount!");
        }
        System.out.println("Plus Tax is $"+df.format(p1234)+" + tax $"+df.format(justTax)+" = $"+df.format(plusTax));
        System.out.println("Your total with shipping is $"+df.format(plusShip));

        System.out.println("1. Purchase  2. Shopping Cart");
        String choice = input.nextLine();
        if (choice.equals("Purchase")) {
            Purchase.performPurchase();
        }
        else if (choice.equals("Product")){
            LoginAccount.products(soupName);
        }

    }
}


