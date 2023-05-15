package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.json.simple.JSONArray;

import java.io.IOException;


import static com.example.demo.GlobalAcessible.*;
public class ItemBuyController {
    private Stage stage;
    private String ItemTag;
    private int deleteID = 0;

    public void SwitchToDashBoard(ActionEvent event) throws IOException {
        SwitchToDashBoardGlobal(event);
    }
    public void initialize() {
        getdataItem();
        for(ItemCategory s : lsItemCategory)
        {
            if(s.IDItem == ItemIdGlobal)
            {
                deleteID = s.IDItem;
                ItemTag = s.ItemTag;

                displayItemDescriptionText.setText(s.ItemDesc);
                displayItemImageText.setText(s.ImageItem);
                Image image = new Image(s.ImageItem);
                displayItemImage.setImage(image);
                displayItemNameText.setText(s.NameItem);
                displayItemPriceText.setText(""+s.ItemPrice);
            }
        }
    }
    @FXML
    public void DeleteItem(ActionEvent event)
    {
        deleteItem(deleteID);
    }
    @FXML
    public void UpdateItem(ActionEvent event) throws IOException {
        updateItem(deleteID,displayItemNameText.getText(),displayItemImageText.getText(),displayItemDescriptionText.getText(), Integer.parseInt(displayItemPriceText.getText()),ItemTag);
        SwitchToDashBoardGlobal(event);
    }
    @FXML
    private TextField displayItemDescriptionText;

    @FXML
    private ImageView displayItemImage;

    @FXML
    private TextField displayItemImageText;

    @FXML
    private TextField displayItemNameText;

    @FXML
    private TextField displayItemPriceText;
}
