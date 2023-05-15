package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONArray;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static com.example.demo.GlobalAcessible.*;
import static com.example.demo.GlobalAcessible.SwitchToSignInSceneGlobal;

public class SignUpInfoController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public void setNewUserLogin(ActionEvent event) throws IOException {
        try{
            String url = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12605882";
            String user = "sql12605882";
            String password = "jt97IKal9W";
            String SignUpUserNameValue = SignUpUserName.getText();
            String SignUpUserPhoneValue = SignUpPhone.getText();
            String SignUpUserCardValue = SignUpCard.getText();
            String SignUpUserAddressValue = SignUpAddress.getText();
            Connection connection = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO User (IDUser, Account, Password,NameUser,PhoneUser,CardUser,AddressUser) VALUES (" + NextIdGlobal +",'" + SignUpAccGlobal +"','" + SignUpPassGlobal+"','" + SignUpUserNameValue+"','"+SignUpUserPhoneValue+"','" + SignUpUserCardValue +"','" + SignUpUserAddressValue +"')";
            Statement statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            statement.close();
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        SwitchToSignInSceneGlobal(event);
    }
    @FXML
    private TextField SignUpUserName;
    @FXML
    private TextField SignUpPhone;
    @FXML
    private TextField SignUpCard;
    @FXML
    private TextField SignUpAddress;
}
