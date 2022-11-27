package hu.petrik.kerecsenyimark_javafxrestclientdolgozat;


import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdatePlayersController extends Controller {

    @FXML
    private TextField nevInput;
    @FXML
    private TextField osztalyInput;
    @FXML
    private Spinner<Integer> performanceSpinner;
    @FXML
    private ChoiceBox<Boolean> choiceBoxNoob;
    @FXML
    private Button updateButton;
    private Wow wow;

    public void setWow(Wow wow) {
        this.wow = wow;
        this.nevInput.setText(wow.getNev());
        this.osztalyInput.setText(wow.getOsztaly());
        this.performanceSpinner.getValueFactory().setValue(wow.getLv());
        this.choiceBoxNoob.setValue(wow.isNoob());
    }

    @FXML
    private void initialize() {
        choiceBoxNoob.getItems().add(Boolean.TRUE);
        choiceBoxNoob.getItems().add(Boolean.FALSE);
        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 1200, 300);
        performanceSpinner.setValueFactory(valueFactory);

    }

    @FXML
    private void updateClick(ActionEvent actionEvent) {
        String nev = this.nevInput.getText();
        String osztaly = this.osztalyInput.getText();
        int perf = this.performanceSpinner.getValueFactory().getValue();
        boolean noob =  choiceBoxNoob.getValue();
        if (nev.isEmpty()) {
            warning("Név megadása kötelező");
            return;
        }
        if (osztaly.isEmpty()) {
            warning("Class megadása kötelező");
            return;
        }
        this.wow.setNev(nev);
        this.wow.setOsztaly(osztaly);
        this.wow.setLv(perf);
        this.wow.setNoob(noob);
        Gson gson = new Gson();
        String json = gson.toJson(this.wow);
        try {
            String url = App.BASE_URL + "/" + this.wow.getId();
            Response response = RequestHandler.put(url, json);
            if(response.getResponseCode() == 200) {
                Stage stage = (Stage) this.updateButton.getScene().getWindow();
                stage.close();
            }else{
                error("Hiba történt a módosítás során", response.getContent());
            }
        } catch (IOException e) {
            error("Nem sikerült a szerverhez csatlakozni");
        }


    }
}


