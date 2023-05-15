package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import static com.example.demo.GlobalAcessible.ItemIdGlobal;

public class ItemController {
    @FXML
    private ImageView displayItemImage;

    @FXML
    private Label displayItemName;

    @FXML
    private Label displayItemPrice;

    @FXML
    private VBox box;
    private int Id;
    public void setData(ItemCategory item) {
        Image image = new Image(item.ImageItem);
        displayItemImage.setImage(image);
        displayItemName.setText(item.NameItem);
        String temp = "" + item.ItemPrice;
        displayItemPrice.setText(temp);
        Id = item.IDItem;
    }
    public Node selectedNode;
    @FXML
    public void handleNodeClick(MouseEvent event)throws IOException {
        ItemIdGlobal = Id;
        selectedNode = (Node) event.getTarget();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemBuyAdminFinal.fxml"));
        Parent newRoot = loader.load();
        Scene currentScene = selectedNode.getScene();
        currentScene.setRoot(newRoot);
    }
}
