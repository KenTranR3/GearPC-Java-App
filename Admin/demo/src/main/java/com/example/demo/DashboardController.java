package com.example.demo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.demo.GlobalAcessible.*;

public class DashboardController {
    public void Display(List<ItemCategory> inputlsItemCategory, boolean isInit) {
        nextlineBox.getChildren().clear();
        int itemCounter = 0;
        HBox row = new HBox();
        for(ItemCategory item : inputlsItemCategory) {
            try {
                System.out.println("CALL");
                if(isInit)
                {
                    NextIdGlobal++;
                }
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ItemCategoryFinal.fxml"));
                VBox itemBox = loader.load();
                ItemController itemController = loader.getController();
                itemController.setData(item);
                row.getChildren().add(itemBox); // Add the item to the current row
                itemCounter++;

                if (itemCounter % 3 == 0) { // If we have added three items, start a new row
                    nextlineBox.getChildren().add(row);
                    row = new HBox(); // Start a new row
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!row.getChildren().isEmpty()) {
            nextlineBox.getChildren().add(row);
        }
        if(isInit)
        {
            NextIdGlobal++;
        }
    }
    @FXML
    public void initialize(){
        nextlineBox.getChildren().clear();
        NextIdGlobal = 0;
        vboxAddItem.setVisible(false);
        lsItemCategory.clear();
        getdataItem();
        Display(lsItemCategory, true);

        System.out.println(NextIdGlobal);
    }
    public void SelectFromTag(String inputTag)
    {
        List<ItemCategory> lsItemCategoryFromTag = new ArrayList<>();
        nextlineBox.getChildren().clear();
        for(ItemCategory item : lsItemCategory) {
            if(item.ItemTag.equals(inputTag))
            {
                lsItemCategoryFromTag.add(item);
            }
        }
        Display(lsItemCategoryFromTag, false);
    }
    public void SelectFromSearch(String inputString)
    {
        nextlineBox.getChildren().clear();
        List<ItemCategory> lsItemCategoryFromSearch = new ArrayList<>();
        for(ItemCategory item : lsItemCategory) {
            if(item.NameItem.contains(inputString))
            {
                lsItemCategoryFromSearch.add(item);
            }
        }
        Display(lsItemCategoryFromSearch, false);
    }
    // chat Region

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
        Display(lsItemCategory,false);
    }
    public void FindButtonPressed()
    {
        String input = searchField.getText();
        SelectFromSearch(input);
    }
    public void History(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("HistoryAdminFinal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.show();
    }
    public void AdditemButton(ActionEvent event) throws IOException
    {
        TagItem.getItems().add("Laptop");
        TagItem.getItems().add("HeadPhone");
        TagItem.getItems().add("Speaker");
        TagItem.getItems().add("Keyboard");
        TagItem.getItems().add("USB");
        TagItem.getItems().add("Chip");
        TagItem.getItems().add("RAM");
        TagItem.getItems().add("CPU");
        TagItem.getItems().add("VGA");
        nextlineBox.setVisible(false);
        vboxAddItem.setVisible(true);
    }
    public void AdditemConfirm(ActionEvent event) throws IOException
    {
        insertItem(NextIdGlobal,ItemName.getText(),linkImage.getText(),descItem.getText(),priceItem.getText(),TagItem.getValue());
        nextlineBox.setVisible(true);
        vboxAddItem.setVisible(false);
        initialize();
    }
    public void AdditemCancel(ActionEvent event) throws IOException
    {
        vboxAddItem.setVisible(false);
        nextlineBox.setVisible(true);
    }
    public void previewImage(ActionEvent event)
    {
        Image image = new Image(linkImage.getText());
        displayItemImage.setImage(image);
    }
    public Stage stage;
    @FXML
    private HBox box;
    @FXML
    private TextField searchField;
    @FXML
    private VBox vboxAddItem;
    @FXML
    private ImageView displayItemImage;
    @FXML
    private TextField linkImage;
    @FXML
    private TextField ItemName;
    @FXML
    private TextField priceItem;
    @FXML
    private TextArea descItem;
    @FXML
    private ComboBox<String> TagItem;

    @FXML
    private VBox nextlineBox;

    static Socket socket = null;
    public static PrintWriter out = null;
    public static BufferedReader in = null;
    @FXML
    private TextArea txtMessage;
    @FXML
    private TextField responseField;
    public void ImReadyToChat(ActionEvent event)
    {
        postCommunication();
        Thread chatServerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                startChatServer();
            }
        });
        System.out.println("newChatIdGlobal");
        chatServerThread.start();
        Thread chatServerThread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                createAdminChat();
            }
        });
        chatServerThread1.start();
    }
    public static volatile ServerThreadBus serverThreadBus;
    public static Socket socketOfServer;

    private List<String> onlineList;
    @FXML
    public Label CurrentTalkingWith;
    @FXML
    public VBox userDisplayBox;
    public void createAdminChat()
    {
        onlineList = new ArrayList<>();
        try {
            socket = new Socket(PortIDGlobal, portNoGlobal);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Socket connect");
            AtomicInteger ignore = new AtomicInteger();
            Thread thread = new Thread(() -> {
                try {
                    while (true) {
                        CurrentTalkingWith.setText(targetChat);
                        String message = in.readLine();
                        String[] messageSplit = message.split(",");
                        if(message.contains("update-online-list"))
                        {
                            String[] onlineSplit = messageSplit[1].split("-");
                            Platform.runLater(() -> {
                                userDisplayBox.getChildren().clear();
                                for(String e : onlineSplit)
                                {
                                    if(!e.equals("Admin"))
                                    {
                                        try {
                                            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChatThreadCategory.fxml"));
                                            Button userIdentify = loader.load();
                                            userIdentifyController userIdentifyController = loader.getController();
                                            userIdentifyController.setdata(e);
                                            userDisplayBox.getChildren().add(userIdentify);
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                        ignore.getAndIncrement();
                        if(message.contains("TerminateME"))
                        {
                            System.out.println(message);
                            int startIndex = message.indexOf("global-message,Client ") + "global-message,Client ".length();
                            int endIndex = message.indexOf(" (tới bạn): TerminateME");
                            String result = message.substring(startIndex, endIndex);
                            DashboardController.serverThreadBus.remove(result);
                            DashboardController.serverThreadBus.sendOnlineList();
                            DashboardController.serverThreadBus.mutilCastSend("global-message"+","+"---Client "+result+" đã thoát---");
                        }
                        if (message != null && ignore.get() >= 4) {
                            Platform.runLater(() -> {
                                String subString = message.substring(message.indexOf("Client")).trim();
                                txtMessage.appendText(subString + "\n");
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

    public OutputStream outputStream = null;
    public void SendChat(ActionEvent event)
    {
        if(!targetChat.equals(""))
        {
            try{
                String message = responseField.getText();
                txtMessage.appendText("You chat to " + targetChat+ ": " + message + "\n");
                responseField.clear();

                outputStream = socket.getOutputStream();
                PrintWriter out = new PrintWriter(outputStream, true);

                String send = "send-to-person,"+message+","+"Admin"+","+targetChat;
                System.out.println(send);
                out.println(send);

                out.flush();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    public static void startChatServer() {
        int nextConnection = 0;
        ServerSocket listener = null;
        serverThreadBus = new ServerThreadBus();
        System.out.println("Server is waiting to accept user...");
        String clientNumber = "Admin";

        try {
            listener = new ServerSocket(7777);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,
                100,
                10,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(8)
        );
        try {
            while (true) {
                if(nextConnection > 0)
                {
                    socketOfServer = listener.accept();
                    PrintWriter out = new PrintWriter(socketOfServer.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));

                    out.println("***");
                    clientNumber = in.readLine();
                    System.out.println("Chao"+clientNumber);

                    ServerThread serverThread = new ServerThread(socketOfServer, clientNumber);
                    serverThreadBus.add(serverThread);
                    System.out.println("Số thread đang chạy là: "+serverThreadBus.getLength());
                    executor.execute(serverThread);
                }
                else
                {
                    socketOfServer = listener.accept();
                    ServerThread serverThread = new ServerThread(socketOfServer, clientNumber);
                    serverThreadBus.add(serverThread);
                    System.out.println("Số thread đang chạy là: "+serverThreadBus.getLength());
                    executor.execute(serverThread);
                }
                nextConnection++;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                listener.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
