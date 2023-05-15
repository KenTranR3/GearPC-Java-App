package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.example.demo.GlobalAcessible.*;
public class InfoUserController {
    @FXML
    public void initialize(){
        Account.setText(AccountGlobal);
        AddressUser.setText(AddressUserGlobal);
        CardUser.setText(CardUserGlobal);
        IDUser.setText("Your ID: "+IDUserGlobal);
        NameUser.setText(NameUserGlobal);
        Password.setText(PasswordGlobal);
        PhoneUser.setText(PhoneUserGlobal);
    }
    @FXML
    public void UpdateUserInformation(Event event, String newAccount,String newAddressUser,String newCardUser,String newNameUser
            ,String newPassword,String newPhoneUser) throws SQLException {
        String url = "jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12605882";
        String user = "sql12605882";
        String password = "jt97IKal9W";
        Connection connection = DriverManager.getConnection(url, user, password);
        String sql = "UPDATE User SET Account='" + newAccount + "', Password='" + newPassword + "', NameUser='" + newNameUser +
                "', PhoneUser='" + newPhoneUser + "', CardUser='" + newCardUser + "', AddressUser='" + newAddressUser + "' WHERE IDUser=" + IDUserGlobal;;
        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate(sql);
        statement.close();
    }
    public void ChangeInformation(Event event) throws SQLException {
        String newAccount = Account.getText();
        String newAddressUser = AddressUser.getText();
        String newCardUser = CardUser.getText();
        String newNameUser = NameUser.getText();
        String newPassword = Password.getText();
        String newPhoneUser = PhoneUser.getText();
        boolean Valid = true;
        for(UserAccPass s : lsUserAccPass)
        {
            if(s.Account.equals(newAccount))
            {
                Valid = false;
                break;
            }
        }
        if(Valid) {
            UpdateUserInformation(event,newAccount,newAddressUser,newCardUser,newNameUser,newPassword,newPhoneUser);
            Log.setText("Lưu thành công");
        }
        else
        {
            Log.setText("Đã có lỗi, Tài khoản đã tồn tại");
        }
    }
    private Stage stage;
    private Scene scene;
    public void SwitchToSignInScene(ActionEvent event) throws IOException {
        SwitchToSignInSceneGlobal(event);
    }
    public void SwitchToDashboard(ActionEvent event) throws IOException {
        SwitchToDashBoardGlobal(event);
    }
    @FXML
    private TextField Account;

    @FXML
    private Label Log;
    @FXML
    private TextField AddressUser;

    @FXML
    private TextField CardUser;

    @FXML
    private Label IDUser;

    @FXML
    private TextField NameUser;

    @FXML
    private TextField Password;

    @FXML
    private TextField PhoneUser;

}
