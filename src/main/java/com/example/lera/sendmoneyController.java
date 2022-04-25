package com.example.lera;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class sendmoneyController {
    Client client;
    Client allclients[];
    @FXML
    TextField moneyField;
    @FXML
    TextField oneTimeCodeField;
    @FXML
    TextField clientField;
    @FXML
    Label oneTimeCodeLabel;

    void client_init(Client client,Client allclients[]) {
        this.client = client;
        this.allclients = allclients;
        generate_onetimecode();
    }
    @FXML
    public void search(ActionEvent event) throws IOException {
        if(!clientField.getText().equals("")) {
            for(int i=0;i<allclients.length;i++) {
                if(clientField.getText().equals(allclients[i].getIBAN())){
                    if (Integer.parseInt(moneyField.getText()) <= Integer.parseInt(client.getMoney()) && oneTimeCodeLabel.getText().equals(oneTimeCodeField.getText()))
                    {
                        client.setMoney(String.valueOf(Integer.parseInt(client.getMoney())-Integer.parseInt(moneyField.getText())));
                        allclients[i].setMoney(String.valueOf(Integer.parseInt(allclients[i].getMoney())+Integer.parseInt(moneyField.getText())));
                        for (int j=client.getTransactions().length-1;j>0;j--){
                            client.getTransactions()[j] = client.getTransactions()[j-1];
                        }
                        client.getTransactions()[0] = moneyField.getText() +":0:" + allclients[i].getIBAN();
                        for (int j=allclients[i].getTransactions().length-1;j>0;j--){
                            allclients[i].getTransactions()[j] = allclients[i].getTransactions()[j-1];
                        }
                        allclients[i].getTransactions()[0] = moneyField.getText() +":1:" + client.getIBAN();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Деньги отправлены");
                        alert.setHeaderText("Операция прошла успешно");
                        alert.setTitle("Notification");
                        if (alert.showAndWait().get() == ButtonType.OK) alert.close();
                        write_CSV();
                        break;
                    }
                    else if (oneTimeCodeLabel.getText().equals(oneTimeCodeField.getText())){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Введите сумму меньшую или равную баланса");
                        alert.setHeaderText("Неверно введеная сумма денег");
                        alert.setTitle("Ошибка");
                        if (alert.showAndWait().get() == ButtonType.OK) alert.close();
                    }
                }
                else if(clientField.getText().equals(allclients[i].getSubname() + " "+allclients[i].getName())){
                    if (Integer.parseInt(moneyField.getText()) <= Integer.parseInt(client.getMoney()) && oneTimeCodeLabel.getText().equals(oneTimeCodeField.getText()))
                    {
                        client.setMoney(String.valueOf(Integer.parseInt(client.getMoney())-Integer.parseInt(moneyField.getText())));
                        allclients[i].setMoney(String.valueOf(Integer.parseInt(allclients[i].getMoney())+Integer.parseInt(moneyField.getText())));
                        for (int j=client.getTransactions().length;j>0;j--){
                            client.getTransactions()[j] = client.getTransactions()[j-1];
                        }
                        client.getTransactions()[0] = "0:" + moneyField.getText() +":" + allclients[i].getIBAN();
                        for (int j=allclients[i].getTransactions().length;j>0;j--){
                            allclients[i].getTransactions()[j] = allclients[i].getTransactions()[j-1];
                        }
                        allclients[i].getTransactions()[0] =  "1:" + moneyField.getText() +":" + client.getIBAN();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Деньги отправлены");
                        alert.setHeaderText("Операция прошла успешно");
                        alert.setTitle("Notification");
                        if (alert.showAndWait().get() == ButtonType.OK) alert.close();
                        write_CSV();
                        break;
                    }
                    else if (oneTimeCodeLabel.getText().equals(oneTimeCodeField.getText())){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Введите сумму меньшую или равную баланса");
                        alert.setHeaderText("Неверно введеная сумма денег");
                        alert.setTitle("Ошибка");
                        if (alert.showAndWait().get() == ButtonType.OK) alert.close();
                    }
                    break;
                }
            }
        }
        ClientPanel clientpanel = new ClientPanel(client,allclients);
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }
    void generate_onetimecode() {
        String temp="";
        for(int i =0;i<6;i++) {
            int a = (int) (Math.random()*10);
            temp +=Integer.toString(a);
        }
        oneTimeCodeLabel.setText(temp);
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
