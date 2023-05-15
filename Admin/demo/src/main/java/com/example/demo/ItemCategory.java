package com.example.demo;

import javafx.fxml.FXML;

public class ItemCategory {
    int IDItem;
    String NameItem;
    String ImageItem;
    int ItemPrice;
    String ItemTag;
    String ItemDesc;
    ItemCategory(int IDItem, String NameItem, String ImageItem, int ItemPrice, String ItemTag,String ItemDesc) {
        this.IDItem = IDItem;
        this.NameItem = NameItem;
        this.ImageItem = ImageItem;
        this.ItemPrice = ItemPrice;
        this.ItemTag = ItemTag;
        this.ItemDesc = ItemDesc;
    }
}
