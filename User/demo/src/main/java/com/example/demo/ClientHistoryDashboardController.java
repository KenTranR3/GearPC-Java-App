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

import static com.example.demo.GlobalAcessible.*;


public class ClientHistoryDashboardController {
    @FXML
    public void initialize(){
        IDUser.setText("Your ID: "+IDUserGlobal);
        clientName.setText(NameUserGlobal);
        getdataHistory();
        for(ClientHistoryCategory s : lsClientHistoryCategory)
        {
            if(s.IDUser == IDUserGlobal)
            {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("HistoryCategoryFinal.fxml"));
                    Pane itemBox = loader.load();
                    ClientHistoryController clientHistoryController = loader.getController();
                    clientHistoryController.setdata(s);
                    box.getChildren().add(itemBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void SwitchToUserInfo(ActionEvent event) throws IOException {
        SwitchToUserInfoGlobal(event);
    }
    public void SwitchToDashboard(MouseEvent event) throws IOException {
        Node selectedNode;
        selectedNode = (Node) event.getTarget();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("DashboardClientFinal.fxml"));
        Parent newRoot = loader.load();
        Scene currentScene = selectedNode.getScene();
        currentScene.setRoot(newRoot);
    }
    @FXML
    private VBox box;
    @FXML
    private Button clientName;
    @FXML
    private Label IDUser;
}
