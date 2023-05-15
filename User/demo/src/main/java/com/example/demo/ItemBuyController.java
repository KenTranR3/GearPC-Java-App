package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.sql.*;
import java.util.PrimitiveIterator;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static com.example.demo.GlobalAcessible.*;
public class ItemBuyController {
    private Stage stage;
    private String ItemName;
    private int Quantity;
    private int ItemPrice;
    private int TotalPrice;



    public void SwitchToDashBoard(ActionEvent event) throws IOException {
        SwitchToDashBoardGlobal(event);
    }
    public void BuyItem(ActionEvent event)
    {
        try
        {
            Quantity = Integer.parseInt(SoLuong.getText());
            TotalPrice = Quantity * ItemPrice;

            int count = 0;
            lsClientHistoryCategory.clear();
            getdataHistory();
            for(ClientHistoryCategory s : lsClientHistoryCategory)
            {
                if(s.IDUser == IDUserGlobal)
                {
                    count ++;
                }
            }
            count++;
            String newIDHistory = IDUserGlobal + "0" + count;
            String newNameItem = ItemName;
            String newQuantity = ""+Quantity;
            String newTotalPrice = ""+ TotalPrice;
            String newPaymentMethod = "Thanh toan E-payment";
            String newStat = "Cho xac nhan";

            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement()) {
                String sql = "INSERT INTO Historical (IDUser, IDItem, IDHistory, Stat, Quantity, NameItem, TotalPrice, Address, PaymentMethod, BankAccount) " +
                        "VALUES (" + IDUserGlobal + ", " + ItemIdGlobal + ", " + newIDHistory + ", '" + newStat + "', " + newQuantity + ", '" + newNameItem + "', " + newTotalPrice + ", '" + AddressUserGlobal + "', '" + newPaymentMethod + "', '" + CardUserGlobal + "')";
                stmt.executeUpdate(sql);
                System.out.println("Data inserted successfully");
            } catch (SQLException e) {
                System.out.println("Error inserting data: " + e.getMessage());
            }
        }catch (NumberFormatException e)
        {
            e.printStackTrace();
            System.out.println("Vui long nhap so luong hop le");
        }


    }
    public void initialize() {
        getdataItem();
        for(ItemCategory s : lsItemCategory)
        {
            if(s.IDItem == ItemIdGlobal)
            {
                ItemName = s.NameItem;
                ItemPrice = s.ItemPrice;

                ItemBuyItemDescription.setText(s.ItemDesc);
                Image image = new Image(s.ImageItem);
                ItemBuyItemImage.setImage(image);
                ItemBuyItemName.setText(s.NameItem);
                SoLuong.setText("0");
                ItemBuyItemPrice.setText(""+s.ItemPrice);
            }
        }
    }
    @FXML
    private Text ItemBuyItemDescription;

    @FXML
    private ImageView ItemBuyItemImage;

    @FXML
    private Label ItemBuyItemName;

    @FXML
    private TextField SoLuong;
    @FXML
    private Label ItemBuyItemPrice;
}
