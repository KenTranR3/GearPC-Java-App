package com.example.demo;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import static com.example.demo.GlobalAcessible.*;

public class userIdentifyController {
    public void setdata(String input)
    {
        name.setText(input);
    }
    @FXML
    Button name;
    public void setTargetChat(ActionEvent event)
    {
        targetChat = name.getText();
    }
}
