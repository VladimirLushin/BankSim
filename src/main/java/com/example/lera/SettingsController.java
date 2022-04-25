package com.example.lera;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsController {

    Client client;
    Client allclients[];

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField subnameTextField;
    @FXML
    private TextField addressTextField;
    @FXML
    private TextField authOperationField;
    @FXML
    private Label onetimecodeLabel;
    @FXML
    private ChoiceBox typeChoice;

    void client_init(Client client,Client allclients[]) {
        this.client = client;
        this.allclients = allclients;
    }

    public void initialize() {
        typeChoice.setItems(FXCollections.observableArrayList("DEBIT","CREDIT"));
        typeChoice.setValue("DEBIT");
        generate_onetimecode();
    }

    @FXML
    void click_block(ActionEvent event) {
        if(client.getBlockStatus().equals("UNBLOCKED")) {
            client.setBlockStatus("BLOCKED");
        }
        else if(client.getBlockStatus().equals("BLOCKED")) {
            client.setBlockStatus("UNBLOCKED");
        }
        for(int i =0;i<allclients.length;i++) {
            if(allclients[i].getIBAN() == client.getIBAN()){
                allclients[i] = client;
            }
        }

        write_CSV();
    }


    @FXML
    void click_save(ActionEvent event) throws IOException {

        if(authOperationField.getText().equals(onetimecodeLabel.getText())) {
            if(!usernameTextField.getText().equals("")) {
                client.setUsername(usernameTextField.getText());
            }
            if(!passwordTextField.getText().equals("")) {
                client.setPassword(passwordTextField.getText());
            }
            if(!nameTextField.getText().equals("")) {
                client.setName(nameTextField.getText());
            }
            if(!subnameTextField.getText().equals("")) {
                client.setSubname(subnameTextField.getText());
            }
            if(!typeChoice.getValue().toString().equals("")) {
                client.setType(typeChoice.getValue().toString());
            }
            if(!addressTextField.getText().equals("")) {
                client.setAddress(addressTextField.getText());
            }
            String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
            client.setChangeDate(timeStamp);

            for(int i =0;i<allclients.length;i++) {
                if(allclients[i].getIBAN() == client.getIBAN()){
                    allclients[i] = client;
                }
            }
            write_CSV();
            ClientPanel clientpanel = new ClientPanel(client,allclients);
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        }
        else {
            generate_onetimecode();
        }



    }

    void generate_onetimecode() {
        String temp="";
        for(int i =0;i<6;i++) {
            int a = (int) (Math.random()*10);
            temp +=Integer.toString(a);
        }
        onetimecodeLabel.setText(temp);
    }

    void write_CSV() {
        try (FileWriter writer = new FileWriter("src/data.csv",false)){
            for (int i = 0; i < allclients.length; i++) {
                writer.append(allclients[i].getUsername());
                writer.append(',');
                writer.append(allclients[i].getPassword());
                writer.append(',');
                writer.append(allclients[i].getName());
                writer.append(',');
                writer.append(allclients[i].getSubname());
                writer.append(',');
                writer.append(allclients[i].getAddress());
                writer.append(',');
                writer.append(allclients[i].getMoney());
                writer.append(',');
                writer.append(allclients[i].getCreateDate());
                writer.append(',');
                writer.append(allclients[i].getChangeDate());
                writer.append(',');
                writer.append(allclients[i].getIBAN());
                writer.append(',');
                writer.append(allclients[i].getType());
                writer.append(',');
                writer.append(allclients[i].getTransactions()[0]);
                writer.append(',');
                writer.append(allclients[i].getTransactions()[1]);
                writer.append(',');
                writer.append(allclients[i].getTransactions()[2]);
                writer.append(',');
                writer.append(allclients[i].getTransactions()[3]);
                writer.append(',');
                writer.append(allclients[i].getTransactions()[4]);
                writer.append(',');
                writer.append(allclients[i].getBlockStatus());
                writer.append(System.lineSeparator());
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

