package hu.petrik.kerecsenyimark_javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;


public class WowController extends Controller {

    @FXML
    private Button insertButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableView<Wow> playerTable;
    @FXML
    private TableColumn<Wow, Integer> idCol;
    @FXML
    private TableColumn<Wow, String> nevCol;
    @FXML
    private TableColumn<Wow, String> osztalyCol;
    @FXML
    private TableColumn<Wow, Integer> performanceCol;
    @FXML
    private TableColumn<Wow, Boolean> noobCol;

    @FXML
    private void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nevCol.setCellValueFactory(new PropertyValueFactory<>("nev"));
        osztalyCol.setCellValueFactory(new PropertyValueFactory<>("osztaly"));
        performanceCol.setCellValueFactory(new PropertyValueFactory<>("lv"));
        noobCol.setCellValueFactory(new PropertyValueFactory<>("noob"));
        Platform.runLater(() -> {
            try {
                loadPlayersFromServer();
            } catch (IOException e) {
                error("Hiba történt az adatok lekérése során", e.getMessage());

                Platform.exit();
            }
        });
    }

    private void loadPlayersFromServer() throws IOException {
        Response response = RequestHandler.get(App.BASE_URL);
        String content = response.getContent();
        Gson converter = new Gson();
        Wow[] autok = converter.fromJson(content, Wow[].class);
        playerTable.getItems().clear();
        for (Wow wow : autok) {
            playerTable.getItems().add(wow);
        }
    }

    @FXML
    public void insertClick(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("insert-players-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            stage.setTitle("Add a new player");
            stage.setScene(scene);
            stage.setOnCloseRequest(event -> {
                try {
                    loadPlayersFromServer();
                } catch (IOException e) {
                    error("Nem sikerült kapcsolódni a szerverhez");
                }
            });
            stage.show();
        } catch (IOException e) {
            error("Hiba történt az űrlap betöltése során", e.getMessage());
        }
    }

    @FXML
    public void updateClick(ActionEvent actionEvent) {
        Wow SelectedWow =  playerTable.getSelectionModel().getSelectedItem();
        if (SelectedWow == null) {
            warning("Törléshez előbb válasszon ki egy elemet!");
            return;
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("update-players-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            Stage stage = new Stage();
            UpdatePlayersController controller = fxmlLoader.getController();
            controller.setWow(SelectedWow);
            stage.setTitle("Update "+ SelectedWow.getNev());
            stage.setScene(scene);
            stage.setOnHidden(event -> {
                try {
                    loadPlayersFromServer();
                } catch (IOException e) {
                    error("Nem sikerült kapcsolódni a szerverhez");
                }
            });
            stage.show();
        } catch (IOException e) {
            error("Hiba történt a betöltés során", e.getMessage());
        }
    }

    @FXML
    public void deleteClick(ActionEvent actionEvent) {
        Wow SelectedWow =  playerTable.getSelectionModel().getSelectedItem();
        if (SelectedWow == null) {
            warning("A törléshez előbb válasszon ki egy elemet!");
            return;
        }
        Optional<ButtonType> optionalButtonType =
                alert(Alert.AlertType.CONFIRMATION, "Biztos?",
                        "Biztos, hogy törölni szeretné ezt a játékost?: "
                                + SelectedWow.getNev() +  " osztaly: " + SelectedWow.getOsztaly(),
                        "");
        if (optionalButtonType.isPresent() &&
                optionalButtonType.get().equals(ButtonType.OK)
        ) {
            String url = App.BASE_URL + "/" + SelectedWow.getId();
            try {
                RequestHandler.delete(url);
                loadPlayersFromServer();
            } catch (IOException e) {
                error("Nem sikerült kapcsolódni a szerverhez");
            }
        }
    }
}