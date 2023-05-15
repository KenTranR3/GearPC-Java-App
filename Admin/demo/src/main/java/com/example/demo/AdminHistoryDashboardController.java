package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.demo.GlobalAcessible.*;


public class AdminHistoryDashboardController {
    @FXML
    public void initialize(){
        IDUser.setText("Your ID: You are Admin");
        clientName.setText("Go Back To Dashboard");
        getdataHistory();
        for(AdminHistoryCategory s : lsClientHistoryCategory)
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HistoryCategoryAdminFinal.fxml"));
                Pane itemBox = loader.load();
                AdminHistoryController adminHistoryController = loader.getController();
                adminHistoryController.setdata(s);
                box.getChildren().add(itemBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void SwitchToDashboard(ActionEvent event) throws IOException {
        SwitchToDashBoardGlobal(event);
    }
    @FXML
    private VBox box;
    @FXML
    private Button clientName;
    @FXML
    private Label IDUser;
}
