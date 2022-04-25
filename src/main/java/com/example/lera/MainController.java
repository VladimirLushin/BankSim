package com.example.lera;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class MainController {

    Client clients[];
    String path_to_csv = "src/data.csv";

    @FXML
    private Button loginBtn;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Button allusersBtn;


    @FXML
    void allusers_show(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("allusers.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            stage.show();
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void registerClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Registration.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            RegistrationController rc = fxmlLoader.getController();
            File file = new File(path_to_csv);
            int count = getLineCount(file);
            clients = new Client[count];
            readCSV();
            rc.client_init(clients);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void loginClick(ActionEvent event) throws IOException {
        File file = new File(path_to_csv);
        int count = getLineCount(file);
        clients = new Client[count];
        readCSV();
        for(int i = 0;i<clients.length;i++) {
            String temp = usernameTextField.getText();
            if (clients[i].getUsername().equals(usernameTextField.getText()))  {
                try {
                    if (clients[i].getPassword().equals(passwordTextField.getText())) {
                        ClientPanel clientpanel = new ClientPanel(clients[i],clients);
                        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    String md5_hash(String plaintext) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest m=MessageDigest.getInstance("MD5");
        m.reset();
        m.update(plaintext.getBytes("utf-8"));
        String s2 = new BigInteger(1,m.digest()).toString(16);
        while(s2.length() < 32 ){
            s2 = "0"+s2;
        }
        return s2;
    }

    void readCSV() {
        String line = "";
        String separator = ",";

        try {
            BufferedReader br = new BufferedReader(new FileReader(path_to_csv));
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] country = line.split(separator);
                String[] temp = new String[5];
                for(int j = 0;j<temp.length;j++) {
                    temp[j] = country[j+10];
                }
                clients[i] = new Client(country[0],country[1],country[2],country[3],country[4],country[5],country[6],country[7],country[8],country[9],temp,country[15]);
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    public static int getLineCount(File file) throws IOException {
        try (Stream<String> lines = Files.lines(file.toPath(),Charset.forName("windows-1251"))) {
            return (int) lines.count();
        }
    }
}

