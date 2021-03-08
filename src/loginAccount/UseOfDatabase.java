package loginAccount;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;

public class UseOfDatabase /* extends LoginAccount */{

    protected static Connection conn;
    protected static String dateMonthLater;

    public static void insertEmp() throws SQLException, IOException, ClassNotFoundException {
        // Customer || Employee Account insert
        String account = "INSERT INTO Account(AccountID,Username, Password, FirstName, LastName, Address, Phone, Email, CreditCard, AccountType) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(account)) {

            pstmt.setInt(1, LoginAccount.accountId);
            pstmt.setString(2, LoginAccount.loginName);
            pstmt.setString(3, LoginAccount.Password);
            pstmt.setString(4, LoginAccount.FirstName);
            pstmt.setString(5, LoginAccount.LastName);
            pstmt.setString(6, LoginAccount.Address);
            pstmt.setLong(7, LoginAccount.Phone);
            pstmt.setString(8, LoginAccount.Email);
            pstmt.setLong(9, LoginAccount.CreditCard);
            pstmt.setString(10, LoginAccount.AccountType);
            pstmt.executeUpdate();
        }
    }

    public static void insertPurchase() throws SQLException, IOException, ClassNotFoundException {
        // Purchase information insert
        String purchase = "INSERT INTO Purchase(PurchaseID,AccountID,PurchaseDate, PurchasePrice, PurchaseSummary) VALUES (?,?,?,?,?)";
        try (PreparedStatement pstmt1 = conn.prepareStatement(purchase)) {

            pstmt1.setInt(1, ShoppingCart.purchaseId);
            pstmt1.setInt(2, LoginAccount.accountId);
            pstmt1.setString(3, LoginAccount.date);
            pstmt1.setDouble(4, ShoppingCart.plusShip);
            pstmt1.setString(5, ShoppingCart.orderSummary);
            pstmt1.executeUpdate();
        }
    }

    public static void insertProduct() throws SQLException, IOException, ClassNotFoundException {
        // Product information insert
        String product = "INSERT INTO Product(ProductID,ProductAnimal,ProductName,ProductPrice,ProductExpDate,ProductDescription) VALUES(?,?,?,?,?,?)";
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthLater = now.plusMonths(1);
        dateMonthLater = oneMonthLater.toString();

        try (PreparedStatement pstmt2 = conn.prepareStatement(product)) {

            pstmt2.setInt(1,10001);
            pstmt2.setString(2, "Beef");
            pstmt2.setString(3, "Gom");
            pstmt2.setDouble(4,ShoppingCart.gomP);
            pstmt2.setString(5,LoginAccount.dateMonthLater);
            pstmt2.setString(6, "Gom soup is beef broth made with Brisket, Plate and Flank.");
            pstmt2.executeUpdate();

            pstmt2.setInt(1,10002);
            pstmt2.setString(2, "Beef");
            pstmt2.setString(3, "GomTang");
            pstmt2.setDouble(4,ShoppingCart.gomTangP);
            pstmt2.setString(5,LoginAccount.dateMonthLater);
            pstmt2.setString(6, "GomTang soup is beef broth made with Chuck, Rib and Flank.");
            pstmt2.executeUpdate();

            pstmt2.setInt(1,10003);
            pstmt2.setString(2, "Pork");
            pstmt2.setString(3, "Bulgo");
            pstmt2.setDouble(4,ShoppingCart.bulgoP);
            pstmt2.setString(5,LoginAccount.dateMonthLater);
            pstmt2.setString(6, "Bulgo soup is beef broth made with porkbelly, neck, leg.");
            pstmt2.executeUpdate();

            pstmt2.setInt(1,10004);
            pstmt2.setString(2, "Pork");
            pstmt2.setString(3, "Spicy");
            pstmt2.setDouble(4,ShoppingCart.spicyP);
            pstmt2.setString(5,LoginAccount.dateMonthLater);
            pstmt2.setString(6, "Spicy soup is beef broth made with shoulder and loin.");
            pstmt2.executeUpdate();
        }
    }

    private static void queryName(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        String sql =
                "SELECT FirstName, LastName FROM Account" + " ORDER BY FirstName DESC";
        ResultSet results = statement.executeQuery(sql);
        while (results.next()){
            String FirstName = results.getString(1);
            String LastName = results.getString(2);
            System.out.println(FirstName+ " "+LastName);
        }
        System.out.println();
    }

    private static void queryJoin(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        String sql =
                "SELECT FirstName, LastName, PurchasePrice FROM Account" +
                " JOIN Purchase ON Purchase.AccountID = Account.AccountID" +
                " WHERE PurchasePrice > 50.00" +
                " ORDER BY PurchaseSummary ASC";
        ResultSet results = statement.executeQuery(sql);
        while (results.next()){
            String FirstName = results.getString(1);
            String LastName = results.getString(2);
            double PurchasePrice = results.getDouble(3);
            String pp = Double.toString(PurchasePrice);
            System.out.println(FirstName+" "+LastName+" $"+pp);
        }
        System.out.println();
    }

    private static void queryAgg(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        String sql =
                "SELECT ProductAnimal, min(ProductPrice) AS MinPrice" +
                " FROM Product" +
                " GROUP BY ProductAnimal";
        ResultSet results = statement.executeQuery(sql);
        while (results.next()) {
            String ProductAnimal = results.getString(1);
            double MinPrice = results.getDouble(2);
            String le = Double.toString(MinPrice);
            System.out.println(ProductAnimal + " $" + le);
        }
    }

    public static void main(String [] args) throws ClassNotFoundException, IOException, SQLException {
        String url = "jdbc:sqlite:D:\\_20-0402 - JY Folders\\Folders\\BU\\CS622 - Advanced Programming Techniques\\bosp6\\Invoice Database.db";
        conn = DriverManager.getConnection(url);
        LoginAccount.createAccountFile();

        try {
            //insertProduct();
            queryName(conn);
            queryJoin(conn);
            queryAgg(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
