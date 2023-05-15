package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import org.json.simple.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import static com.example.demo.GlobalAcessible.*;

public class SignInController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void SwitchToSignUpScene(ActionEvent event) throws IOException {
        SwitchToSignUpSceneGlobal(event);
    }
    public void SwitchToDashBoard(ActionEvent event) throws IOException {
        getdataUser();
        String SignInAccValue = SignInAcc.getText();
        String SignInPassValue = SignInPass.getText();
        for(UserAccPass s : lsUserAccPass)
        {
            if(SignInAccValue.equals(s.Account))
            {
                if(SignInPassValue.equals(s.Password))
                {
                    IDUserGlobal = s.IDUser;
                    AccountGlobal = s.Account;
                    PasswordGlobal = s.Password;
                    NameUserGlobal = s.NameUser;
                    PhoneUserGlobal = s.PhoneUser;
                    CardUserGlobal = s.CardUser;
                    AddressUserGlobal = s.AddressUser;
                    SwitchToDashBoardGlobal(event);
                }
                else
                {
                    break;
                }
            }
        }
        LoginStatus.setText("Account hoặc password không hợp lệ");
    }

    @FXML
    private TextField SignInAcc;
    @FXML
    private PasswordField SignInPass;
    @FXML
    private Label LoginStatus;
}
