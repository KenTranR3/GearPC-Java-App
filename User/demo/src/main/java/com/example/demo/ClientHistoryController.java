package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.demo.GlobalAcessible.*;
public class ClientHistoryController {
    private int deleteIDHistory;
    public void setdata(ClientHistoryCategory clientHistoryCategory)
    {
        if(!clientHistoryCategory.Stat.equals("Cho xac nhan"))
        {
            CancelButton.setVisible(false);
        }
        deleteIDHistory = clientHistoryCategory.IDHistory;
        IdHistory.setText("Your ID: "+clientHistoryCategory.IDHistory);
        ItemName.setText(clientHistoryCategory.NameItem);
        Quantity.setText(""+clientHistoryCategory.Quantity);
        TotalPrice.setText(""+clientHistoryCategory.TotalPrice);
        Address.setText(clientHistoryCategory.Address);
        PaymentMethod.setText(clientHistoryCategory.PaymentMethod);
        BankAccount.setText(clientHistoryCategory.BankAccount);
        Status.setText(clientHistoryCategory.Stat);
    }
    public void CancelButtonClicked(ActionEvent event) throws IOException {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            String sql = "UPDATE Historical SET Stat = 'Da Huy' WHERE IDHistory = " + deleteIDHistory;
            stmt.executeUpdate(sql);
            System.out.println("Data updated successfully");
        } catch (SQLException e) {
            System.out.println("Error updating data: " + e.getMessage());
        }
        SwitchToHistory(event);
    }
    private Stage stage;
    public void SwitchToHistory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HistoryUserFinal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private Label IdHistory;
    @FXML
    private Label ItemName;
    @FXML
    private Label Quantity;
    @FXML
    private Label TotalPrice;
    @FXML
    private Label Address;
    @FXML
    private Label PaymentMethod;
    @FXML
    private Label BankAccount;
    @FXML
    private Label Status;
    @FXML
    private Button CancelButton;
}
