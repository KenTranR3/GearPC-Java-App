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
import org.json.simple.JSONArray;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.GlobalAcessible.*;

public class SignUpController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    public void SwitchToSignUpInformationFinalScene(ActionEvent event,String acc, String pass){
        try {
            SignUpAccGlobal = acc;
            SignUpPassGlobal = pass;
            Parent root = FXMLLoader.load(getClass().getResource("SignUpInformationFinal.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
            stage.setScene(scene);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public  void SwitchToSignInScene(ActionEvent event) throws IOException {
        SwitchToSignInSceneGlobal(event);
    }
    @FXML
    protected void OnSignUpButtonClick(ActionEvent event) throws IOException {
        getdataUser();
        String SignUpAccValue = SignUpAcc.getText();
        String SignUpPassValue = SignUpPass.getText();

        boolean Valid = true;
        for(UserAccPass s : lsUserAccPass)
        {
            if(s.Account.equals(SignUpAccValue))
            {
                Valid = false;
                break;
            }
            NextIdGlobal = s.IDUser + 1;
        }
        if(Valid)
        {
            SwitchToSignUpInformationFinalScene(event,SignUpAccValue,SignUpPassValue);
        }
        else
        {
            SignUpStatus.setText("Tài khoản đã được sử dụng");
        }
    }
    @FXML
    private TextField SignUpAcc;
    @FXML
    private PasswordField SignUpPass;
    @FXML
    private Label SignUpStatus;
}
