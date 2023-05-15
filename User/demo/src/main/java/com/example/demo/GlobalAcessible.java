package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GlobalAcessible {
    public static String SignUpAccGlobal = "";
    public static String SignUpPassGlobal = "";
    public static int NextIdGlobal = 0;
    public static int ItemIdGlobal = 0;

    public static int IDUserGlobal = 0;
    public static String AccountGlobal = "";
    public static String PasswordGlobal = "";
    public static String NameUserGlobal = "";
    public static String PhoneUserGlobal = "";
    public static String CardUserGlobal = "";
    public static String AddressUserGlobal = "";

    public static int portNoGlobal = 0;
    public static String PortIDGlobal = "";

    public static List<UserAccPass> lsUserAccPass = new ArrayList<>();
    public static List<ItemCategory> lsItemCategory = new ArrayList<>();
    public static List<ClientHistoryCategory> lsClientHistoryCategory = new ArrayList<>();
    public static String url = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12605882";
    public static String user = "sql12605882";
    public static String password = "jt97IKal9W";
    public static void getdataHistory() {
        lsClientHistoryCategory.clear();
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database");

            String selectSql = "SELECT IDUser,IDItem,IDHistory,Stat,Quantity,NameItem,TotalPrice,Address,PaymentMethod,BankAccount FROM Historical";
            PreparedStatement selectStatement = conn.prepareStatement(selectSql);
            ResultSet resultSet = selectStatement.executeQuery();
            JSONArray array = new JSONArray();
            while (resultSet.next()) {
                int tempIDUser = resultSet.getInt("IDUser");
                int tempIDItem = resultSet.getInt("IDItem");
                int tempIDHistory = resultSet.getInt("IDHistory");
                String tempStat = resultSet.getString("Stat");
                int tempQuantity = resultSet.getInt("Quantity");
                String tempNameItem = resultSet.getString("NameItem");
                int tempTotalPrice = resultSet.getInt("TotalPrice");
                String tempAddress = resultSet.getString("Address");
                String tempPaymentMethod = resultSet.getString("PaymentMethod");
                String BankAccount = resultSet.getString("BankAccount");
                ClientHistoryCategory temp = new ClientHistoryCategory(tempIDUser,tempIDItem,tempIDHistory,tempStat,tempQuantity,tempNameItem,tempTotalPrice,
                        tempAddress,tempPaymentMethod,BankAccount);
                lsClientHistoryCategory.add(temp);
            }
            for (ClientHistoryCategory s : lsClientHistoryCategory)
            {
                System.out.println(s.IDHistory + " " + s.IDUser+ " "+ s.IDItem+ " " +s.Stat + " " + s.Quantity + s.NameItem + " " +
                        s.TotalPrice + " " + s.Address + " " + s.PaymentMethod + " " + s.BankAccount);
            }

        }catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Connector/J driver not found");
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Disconnected from the database");
                } catch (SQLException e) {
                    System.out.println("Error disconnecting from the database");
                    e.printStackTrace();
                }
            }
        }
    }
    public static void getdataUser(){
        lsUserAccPass.clear();
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database");

            String selectSql = "SELECT IDUser,Account,Password,NameUser,PhoneUser,CardUser,AddressUser FROM User";
            PreparedStatement selectStatement = conn.prepareStatement(selectSql);
            ResultSet resultSet = selectStatement.executeQuery();
            JSONArray array = new JSONArray();
            while (resultSet.next()) {
                int tempIDUser = resultSet.getInt("IDUser");
                String tempAccount = resultSet.getString("Account");
                String tempPassword = resultSet.getString("Password");
                String tempNameUser = resultSet.getString("NameUser");
                String tempPhoneUser = resultSet.getString("PhoneUser");
                String tempCardUser = resultSet.getString("CardUser");
                String tempAddressUser = resultSet.getString("AddressUser");

                UserAccPass temp = new UserAccPass(tempIDUser,tempAccount,tempPassword,tempNameUser,tempPhoneUser,tempCardUser,tempAddressUser);
                lsUserAccPass.add(temp);
            }
            for (UserAccPass s : lsUserAccPass)
            {
                System.out.println(s.Account);
            }
        }catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Connector/J driver not found");
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Disconnected from the database");
                } catch (SQLException e) {
                    System.out.println("Error disconnecting from the database");
                    e.printStackTrace();
                }
            }
        }
    }
    public static void getdataItem(){
        lsItemCategory.clear();
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database");

            String selectSql = "SELECT IDItem,NameItem,ImageItem,ItemDesc,ItemPrice,ItemTag FROM Item";
            PreparedStatement selectStatement = conn.prepareStatement(selectSql);
            ResultSet resultSet = selectStatement.executeQuery();
            JSONArray array = new JSONArray();
            while (resultSet.next()) {
                int tempIDItem = resultSet.getInt("IDItem");
                String tempNameItem = resultSet.getString("NameItem");
                String tempImageItem = resultSet.getString("ImageItem");
                String tempItemDesc = resultSet.getString("ItemDesc");
                int tempItemPrice = resultSet.getInt("ItemPrice");
                String tempItemTag = resultSet.getString("ItemTag");
                ItemCategory temp = new ItemCategory(tempIDItem,tempNameItem,tempImageItem,
                        tempItemPrice,tempItemTag,tempItemDesc);
                lsItemCategory.add(temp);
            }
        }catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Connector/J driver not found");
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Disconnected from the database");
                } catch (SQLException e) {
                    System.out.println("Error disconnecting from the database");
                    e.printStackTrace();
                }
            }
        }
    }
    public static void getdataChat(){
        lsItemCategory.clear();
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database");

            String selectSql = "SELECT PortNo,PortID FROM ChatID WHERE Serial = 1";
            PreparedStatement selectStatement = conn.prepareStatement(selectSql);
            ResultSet resultSet = selectStatement.executeQuery();
            JSONArray array = new JSONArray();
            while (resultSet.next()) {
                portNoGlobal = resultSet.getInt("PortNo");
                PortIDGlobal = resultSet.getString("PortID");
            }
        }catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Connector/J driver not found");
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Disconnected from the database");
                } catch (SQLException e) {
                    System.out.println("Error disconnecting from the database");
                    e.printStackTrace();
                }
            }
        }
    }
    public static void SwitchToUserInfoGlobal(ActionEvent event) throws IOException {
        Stage stage;
        Parent root = FXMLLoader.load(GlobalAcessible.class.getResource("InfoUserFinal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
    }
    public static void SwitchToDashBoardGlobal(ActionEvent event) throws IOException {
        Stage stage;
        Parent root = FXMLLoader.load(GlobalAcessible.class.getResource("DashboardClientFinal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
    }
    public static void SwitchToSignUpSceneGlobal(ActionEvent event) throws IOException {
        Stage stage;
        Parent root = FXMLLoader.load(GlobalAcessible.class.getResource("signUpFinal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
    }
    public static void SwitchToSignInSceneGlobal(ActionEvent event) throws IOException {
        Stage stage;
        Parent root = FXMLLoader.load(GlobalAcessible.class.getResource("signInFinal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
    }
}
