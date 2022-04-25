package com.example.lera;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class allusersController {
    Client clients[];
    String path_to_csv = "src/data.csv";

    @FXML
    private ListView allusersList;

    public void initialize() throws IOException {
        File file = new File(path_to_csv);
        int count = getLineCount(file);
        clients = new Client[count];
        readCSV();
        ObservableList<String> langs = FXCollections.observableArrayList();
        String[] strings = new String[count];
        for(int i = 0;i<count;i++) {
            strings[i] = clients[i].getUsername() + " " + clients[i].getPassword() + " " + clients[i].getIBAN();
        }
        langs.addAll(strings);
        allusersList.setItems(langs);

    }

    @FXML
    void authorize_click(ActionEvent event) throws IOException {
        int index = allusersList.getSelectionModel().getSelectedIndex();
        ClientPanel clientpanel = new ClientPanel(clients[index],clients);
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

    }

    public static int getLineCount(File file) throws IOException {
        try (Stream<String> lines = Files.lines(file.toPath(),Charset.forName("windows-1251"))) {
            return (int) lines.count();
        }
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

}