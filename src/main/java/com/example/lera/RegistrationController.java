package com.example.lera;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Objects;

public class RegistrationController {
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
    private Label onetimecodeLabel;
    @FXML
    private ChoiceBox typeChoice;

    void client_init(Client allclients[]) {
        this.allclients = allclients;
    }

    public void initialize() {
        typeChoice.setItems(FXCollections.observableArrayList("DEBIT", "CREDIT"));
        typeChoice.setValue("DEBIT");
        generate_onetimecode();
    }

    @FXML
    void click_save(ActionEvent event) throws IOException {
        client = new Client();
        if (!usernameTextField.getText().equals("")) {
            client.setUsername(usernameTextField.getText());
        }
        if (!passwordTextField.getText().equals("")) {
            client.setPassword(passwordTextField.getText());
        }
        if (!nameTextField.getText().equals("")) {
            client.setName(nameTextField.getText());
        }
        if (!subnameTextField.getText().equals("")) {
            client.setSubname(subnameTextField.getText());
        }
        if (!typeChoice.getValue().toString().equals("")) {
            client.setType(typeChoice.getValue().toString());
        }
        if (!addressTextField.getText().equals("")) {
            client.setAddress(addressTextField.getText());
        }
        client.setBlockStatus("UNBLOCKED");
        client.setIBAN(String.valueOf((int)(Math.random()*100000+100000)));
        String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
        client.setChangeDate(timeStamp);
        client.setTransactions(new String[]{"0:0:0","0:0:0","0:0:0","0:0:0","0:0:0"});
        Client[] temp = Arrays.copyOf(allclients,allclients.length*2);
        for (int i=0;i<allclients.length;i++){
            temp[i] = allclients[i];
        }
        temp[allclients.length] = client;
        allclients = Arrays.copyOf(temp,temp.length);
        write_CSV();
        ClientPanel clientpanel = new ClientPanel(client, allclients);
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
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
                if (allclients[i] != null){
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
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
