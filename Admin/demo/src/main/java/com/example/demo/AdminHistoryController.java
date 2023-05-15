package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.demo.GlobalAcessible.*;
public class AdminHistoryController {
    private int updateIDHistory;
    public void setdata(AdminHistoryCategory clientHistoryCategory)
    {
        if(clientHistoryCategory.Stat.equals("Da Huy") || clientHistoryCategory.Stat.equals("Da hoan tat"))
        {
            Status.setDisable(true);
            Commit.setVisible(false);
        }
        updateIDHistory = clientHistoryCategory.IDHistory;
        IdHistory.setText(""+clientHistoryCategory.IDHistory);
        ItemName.setText(clientHistoryCategory.NameItem);
        Quantity.setText(""+clientHistoryCategory.Quantity);
        TotalPrice.setText(""+clientHistoryCategory.TotalPrice);
        Address.setText(clientHistoryCategory.Address);
        PaymentMethod.setText(clientHistoryCategory.PaymentMethod);
        BankAccount.setText(clientHistoryCategory.BankAccount);
        Status.setValue(clientHistoryCategory.Stat);
        Status.getItems().add("Cho xac nhan");
        Status.getItems().add("Da xac nhan");
        Status.getItems().add("Da hoan tat");
    }
    private Stage stage;
    public void SwitchToHistory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HistoryAdminFinal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
    }
    public void CommitButtonClicked(ActionEvent event) throws IOException {
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {
            String sql = "UPDATE Historical SET Stat = '"+Status.getValue()+"' WHERE IDHistory = " + updateIDHistory;
            stmt.executeUpdate(sql);
            System.out.println("Data updated successfully");
        } catch (SQLException e) {
            System.out.println("Error updating data: " + e.getMessage());
        }
        SwitchToHistory(event);
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
    private ComboBox<String> Status;
    @FXML
    private Button Commit;
}
