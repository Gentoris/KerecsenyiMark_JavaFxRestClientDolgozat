package hu.petrik.kerecsenyimark_javafxrestclientdolgozat;

import com.google.gson.Gson;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class InsertPlayersController extends Controller{
    @javafx.fxml.FXML
    private TextField nevInput;
    @javafx.fxml.FXML
    private TextField osztalyInput;
    @javafx.fxml.FXML
    private Spinner<Integer> performanceSpinner;
    @FXML
    private ChoiceBox<Boolean> choiceBoxNoob;
    @javafx.fxml.FXML
    private Button insertButton;
    @FXML
    private void initialize() {
        choiceBoxNoob.getItems().add(Boolean.TRUE);
        choiceBoxNoob.getItems().add(Boolean.FALSE);
        choiceBoxNoob.setValue(Boolean.FALSE);

        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(50, 1200, 300);
        performanceSpinner.setValueFactory(valueFactory);


    }


    @javafx.fxml.FXML
    public void InsertClick(ActionEvent actionEvent) {
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
        Wow wow = new Wow(0, nev, osztaly, perf, noob);
        Gson gson = new Gson();
        String json = gson.toJson(wow);
        try {
            Response response = RequestHandler.post(App.BASE_URL, json);
            if(response.getResponseCode() == 201) {
                nevInput.setText("");
                osztalyInput.setText("");
                choiceBoxNoob.setValue(Boolean.FALSE);

                performanceSpinner.getValueFactory().setValue(1);
            }else{
                error("Hiba történt a Hozzáadás során", response.getContent());
            }
        } catch (IOException e) {
            error("Nem sikerült a szerverhez csatlakozni");
        }

    }
}
