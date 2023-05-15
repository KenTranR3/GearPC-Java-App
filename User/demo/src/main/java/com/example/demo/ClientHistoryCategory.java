package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import static com.example.demo.GlobalAcessible.*;
public class ClientHistoryCategory {
    int IDUser;
    int IDItem;
    int IDHistory;
    String Stat;
    int Quantity,TotalPrice;
    String NameItem,Address,PaymentMethod,BankAccount;
    ClientHistoryCategory(int IDUser, int IDItem,int IDHistory,String Stat, int Quantity, String NameItem,int TotalPrice,String Address,String PaymentMethod,String BankAccount)
    {
        this.IDUser = IDUser;
        this.IDItem = IDItem;
        this.IDHistory = IDHistory;
        this.Stat = Stat;
        this.Quantity = Quantity;
        this.NameItem = NameItem;
        this.TotalPrice = TotalPrice;
        this.Address = Address;
        this.PaymentMethod = PaymentMethod;
        this.BankAccount = BankAccount;
    }
    @FXML
    private Button cancelButton;
}
