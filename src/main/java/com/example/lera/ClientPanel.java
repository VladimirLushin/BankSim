package com.example.lera;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ClientPanel {
    Client client;
    Client allclients[];

    public ClientPanel(Client client,Client allclients[]) throws IOException {
        this.client = client;
        this.allclients = allclients;
        show_panel();
    }

    void show_panel() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClientPanel.fxml"));
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        ClientPanelController pc = fxmlLoader.getController();
        pc.client_init(client, allclients);
        stage.show();

    }

}

