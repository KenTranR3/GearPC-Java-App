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
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Enumeration;
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

    public static String targetChat = "";

    public static int portNoGlobal = 0;
    public static String PortIDGlobal = "";

    public static List<ItemCategory> lsItemCategory = new ArrayList<>();
    public static List<AdminHistoryCategory> lsClientHistoryCategory = new ArrayList<>();
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
                AdminHistoryCategory temp = new AdminHistoryCategory(tempIDUser,tempIDItem,tempIDHistory,tempStat,tempQuantity,tempNameItem,tempTotalPrice,
                        tempAddress,tempPaymentMethod,BankAccount);
                lsClientHistoryCategory.add(temp);
            }
            for (AdminHistoryCategory s : lsClientHistoryCategory)
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
    public static void postCommunication() {
        String newChatId = "";
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback() || !iface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();
                    if (addr.getHostAddress().contains(".")) {
                        newChatId=addr.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            System.out.println("Error getting IP address: " + e.getMessage());
        }

        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database");

            String selectSql = "SELECT PortNo,PortID FROM ChatID WHERE Serial = 1";
            PreparedStatement selectStatement = conn.prepareStatement(selectSql);
            ResultSet resultSet = selectStatement.executeQuery();
            String portIdToUpdate = null;
            if (resultSet.next()) {
                portNoGlobal = resultSet.getInt("PortNo");
                portIdToUpdate = resultSet.getString("PortID");
            }

            if (portIdToUpdate != null) {
                String updateSql = "UPDATE ChatID SET PortID = ? WHERE Serial = 1";
                PreparedStatement updateStatement = conn.prepareStatement(updateSql);
                updateStatement.setString(1, newChatId);
                int rowsAffected = updateStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("PortID updated successfully");
                } else {
                    System.out.println("PortID not updated");
                }
            }

            resultSet.close();
            selectStatement.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Connector/J driver not found");
            e.printStackTrace();
        } finally {
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
                ItemCategory temp = new ItemCategory(tempIDItem,tempNameItem,tempImageItem,tempItemPrice,tempItemTag,tempItemDesc);
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
    public static void deleteItem(int itemId) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database");

            String deleteSql = "DELETE FROM Item WHERE IDItem = ?";
            PreparedStatement deleteStatement = conn.prepareStatement(deleteSql);
            deleteStatement.setInt(1, itemId);
            int rowsDeleted = deleteStatement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Item with ID " + itemId + " deleted successfully");
            } else {
                System.out.println("No item found with ID " + itemId);
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Connector/J driver not found");
            e.printStackTrace();
        } finally {
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
    public static void insertItem(int newItemId, String newName, String newImage, String newDesc, String newPrice, String newTag)
    {
        String insertSql = "INSERT INTO Item (IDItem, NameItem, ImageItem, ItemDesc, ItemPrice, ItemTag) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             PreparedStatement statement = connection.prepareStatement(insertSql)) {
            // Set the parameter values for the insert statement
            statement.setInt(1, newItemId);
            statement.setString(2, newName);
            statement.setString(3, newImage);
            statement.setString(4, newDesc);
            statement.setString(5, newPrice);
            statement.setString(6, newTag);

            // Execute the insert statement
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new item was inserted successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting new item: " + e.getMessage());
        }

    }
    public static void updateItem(int itemId, String newName, String newImage, String newDesc, int newPrice, String newTag) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database");

            String updateSql = "UPDATE Item SET NameItem = ?, ImageItem = ?, ItemDesc = ?, ItemPrice = ?, ItemTag = ? WHERE IDItem = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateSql);
            updateStatement.setString(1, newName);
            updateStatement.setString(2, newImage);
            updateStatement.setString(3, newDesc);
            updateStatement.setInt(4, newPrice);
            updateStatement.setString(5, newTag);
            updateStatement.setInt(6, itemId);
            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Item with ID " + itemId + " updated successfully");
            } else {
                System.out.println("No item found with ID " + itemId);
            }

        } catch (SQLException e) {
            System.out.println("Error connecting to the database");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Connector/J driver not found");
            e.printStackTrace();
        } finally {
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
    public static void SwitchToDashBoardGlobal(ActionEvent event) throws IOException {
        Stage stage;
        Parent root = FXMLLoader.load(GlobalAcessible.class.getResource("DashBoardAdmin.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
    }

}
