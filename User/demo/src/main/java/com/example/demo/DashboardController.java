package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.demo.GlobalAcessible.*;

public class DashboardController {
    @FXML
    public void initialize(){
        lsItemCategory.clear();
        getdataItem();
        clientName.setText(NameUserGlobal);
        for(ItemCategory item : lsItemCategory) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemCategoryFinal.fxml"));
                VBox itemBox = loader.load();
                ItemController itemController = loader.getController();
                itemController.setData(item);
                box.getChildren().add(itemBox);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    public void SelectFromTag(String inputTag)
    {
        box.getChildren().clear();
        for(ItemCategory item : lsItemCategory) {
            if(item.ItemTag.equals(inputTag))
            {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemCategoryFinal.fxml"));
                    VBox itemBox = loader.load();
                    ItemController itemController = loader.getController();
                    itemController.setData(item);
                    box.getChildren().add(itemBox);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void SelectFromSearch(String inputString)
    {
        box.getChildren().clear();
        for(ItemCategory item : lsItemCategory) {
            if(item.NameItem.contains(inputString))
            {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemCategoryFinal.fxml"));
                    VBox itemBox = loader.load();
                    ItemController itemController = loader.getController();
                    itemController.setData(item);
                    box.getChildren().add(itemBox);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @FXML
    public void SelectHeadPhoneTag()
    {
        SelectFromTag("HeadPhone");
    }
    @FXML
    public void SelectLaptopTag()
    {
        SelectFromTag("Laptop");
    }
    @FXML
    public void SelectSpeakerTag()
    {
        SelectFromTag("Speaker");
    }
    @FXML
    public void SelectKeyboardTag()
    {
        SelectFromTag("Keyboard");
    }
    @FXML
    public void SelectUSBTag()
    {
        SelectFromTag("USB");
    }
    @FXML
    public void SelectChipTag()
    {
        SelectFromTag("Chip");
    }
    @FXML
    public void SelectRAMTag()
    {
        SelectFromTag("RAM");
    }
    @FXML
    public void SelectCPUTag()
    {
        SelectFromTag("CPU");
    }
    @FXML
    public void SelectVGATag()
    {
        SelectFromTag("VGA");
    }
    @FXML
    public void SelectTatCa()
    {
        box.getChildren().clear();
        for(ItemCategory item : lsItemCategory)
        {
            {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemCategoryFinal.fxml"));
                    VBox itemBox = loader.load();
                    ItemController itemController = loader.getController();
                    itemController.setData(item);
                    box.getChildren().add(itemBox);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void FindButtonPressed()
    {
        String input = searchField.getText();
        SelectFromSearch(input);
    }
    public Stage stage;
    public void SwitchToUserInfo(ActionEvent event) throws IOException {
        if(socket != null)
        {
            String send = "send-to-person,"+"TerminateME"+","+NameUserGlobal+",Admin";
            System.out.println(send);
            out.println(send);
            out.flush();
            socket = null;
        }
        SwitchToUserInfoGlobal(event);
    }
    public void SwitchToHistory(ActionEvent event) throws IOException {
        if(socket != null)
        {
            String send = "send-to-person,"+"TerminateME"+","+NameUserGlobal+",Admin";
            System.out.println(send);
            out.println(send);
            out.flush();
            socket = null;
        }
        Parent root = FXMLLoader.load(getClass().getResource("HistoryUserFinal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private HBox box;
    @FXML
    private Button clientName;
    @FXML
    private TextField searchField;

    //Chat region
    public TextField txtMessageSentFromClient;
    public TextArea txtMessage;
    static Socket socket = null;
    public OutputStream outputStream;
    public PrintWriter out = null;
    public BufferedReader in = null;
    public BufferedWriter os;
    public int MyChatId = -1;
    public void IWantToChat(ActionEvent event) {
        getdataChat();
        try {
            System.out.println(PortIDGlobal);
            socket = new Socket(PortIDGlobal, portNoGlobal);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Socket connect");
            AtomicInteger ignore = new AtomicInteger();
            String response = in.readLine();
            if (response.equals("***")) {
                out.println(NameUserGlobal);
            }

            Thread thread = new Thread(() -> {
                try {
                    while (true) {
                        String message = in.readLine();
                        if(message.contains("global-message,---Client "))
                        {
                            Pattern pattern = Pattern.compile("\\d+");
                            Matcher matcher = pattern.matcher(message);
                            if (MyChatId == -1 && matcher.find()) {
                                MyChatId = Integer.parseInt(matcher.group());
                            }
                        }
                        ignore.getAndIncrement();
                        if (message != null && ignore.get() >= 4) {
                            Platform.runLater(() -> {
                                String subString = message.substring(message.indexOf(":") + 1).trim();
                                txtMessage.appendText("Server: " + subString + "\n");
                            });
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.setDaemon(true);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void btnSendOnAction(ActionEvent actionEvent) {
        try{
            String message = txtMessageSentFromClient.getText();
            txtMessage.appendText("You: " + message + "\n");
            txtMessageSentFromClient.clear();

            outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);

            String send = "send-to-person,"+message+","+NameUserGlobal+",Admin";
            System.out.println(send);
            out.println(send);
            out.flush();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
