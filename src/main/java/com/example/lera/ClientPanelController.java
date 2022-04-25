package com.example.lera;

import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientPanelController {

    Client client;
    Client allclients[];

    @FXML
    private Label nameLabel;
    @FXML
    private Label moneyLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label ibanLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private TextField searchTextField;
    @FXML
    private Button searchBtn;
    @FXML
    private ListView list;
    @FXML
    private Button transactionsBtn;
    @FXML
    private Button sendmoneyBtn;


    void client_init(Client client,Client allclients[]) {
        this.client = client;
        this.allclients = allclients;
        nameLabel.setText(client.getSubname() +" "+ client.getName());
        moneyLabel.setText(client.getMoney()+" $");
        typeLabel.setText(client.getType());
        ibanLabel.setText("IBAN: "+client.getIBAN());
        addressLabel.setText(client.getAddress());
        block_interface();

    }

    void block_interface(){
        if(client.getBlockStatus().equals("BLOCKED")) {
            sendmoneyBtn.setVisible(false);
            transactionsBtn.setVisible(false);
            ObservableList<String> langs = FXCollections.observableArrayList("BLOCKED");
            list.setItems(langs);
        }
        else if(client.getBlockStatus().equals("UNBLOCKED")) {
            sendmoneyBtn.setVisible(true);
            transactionsBtn.setVisible(true);
            ObservableList<String> langs = FXCollections.observableArrayList("");
            list.setItems(langs);
        }
    }

    @FXML
    void send_money_dialog_show(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sendmoney.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            sendmoneyController pc = fxmlLoader.getController();
            pc.client_init(client, allclients);
            stage.show();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void settings_dialog_show(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Settings.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            SettingsController sc = fxmlLoader.getController();
            sc.client_init(client, allclients);
            stage.show();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void transactionsClick() {
        String[] temp = new String[5];
        for(int j = 0;j<client.getTransactions().length;j++) {
            temp[j] =client.getTransactions()[j];
        }

        temp = transactions_translate_to_list(temp);
        ObservableList<String> langs = FXCollections.observableArrayList();
        for(int j = 0;j < temp.length;j++) {
            if(!temp[j].equals("0:0:0")) {
                langs.add(temp[j]);
            }
        }

        list.setItems(langs);

    }

    static String[] transactions_translate_to_list(String[] temp) {
        for(int i =0;i<temp.length;i++) {
            if(!temp[i].equals("0:0:0")) {
                String[] str = temp[i].split(":");
                if(str[1].equals("0")) {
                    temp[i] = "SEND " + str[0]+" $"+" client IBAN :"+str[2];

                }
                else if(str[1].equals("1")) {
                    temp[i] = "RECEIVED " + str[0]+" $"+" client IBAN :"+str[2];
                }
            }
        }

        return temp;


    }


    @FXML
    void searchClick() {
        if(!searchTextField.getText().equals("")) {
            for(int i=0;i<allclients.length;i++) {
                if(searchTextField.getText().equals(allclients[i].getIBAN())){
                    String str = allclients[i].getSubname() +" "+ allclients[i].getName()+" IBAN:" + allclients[i].getIBAN();
                    ObservableList<String> langs = FXCollections.observableArrayList(str);
                    list.setItems(langs);
                    break;
                }
                else if(searchTextField.getText().equals(allclients[i].getSubname() + " "+allclients[i].getName())){
                    String str = allclients[i].getSubname() +" "+ allclients[i].getName()+" IBAN:" + allclients[i].getIBAN();
                    ObservableList<String> langs = FXCollections.observableArrayList(str);
                    list.setItems(langs);
                    break;
                }
                else {
                    list.getItems().clear();
                }
            }
        }
    }

}

