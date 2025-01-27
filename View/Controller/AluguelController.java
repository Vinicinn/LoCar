package locar.View.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import locar.Model.Carro;

/**
 * FXML Controller class
 *
 * @author vinic
 */
public class AluguelController implements Initializable {

    private Carro carroEscolhido;

    private PrincipalController controladorPrincipal;

    @FXML
    private Text txtModelo;

    @FXML
    private Text txtMarca;

    @FXML
    private Text txtAno;

    @FXML
    private Text txtCor;

    @FXML
    private Text txtConfiguracao;

    @FXML
    private Text txtValor;

    @FXML
    private Text txtTotal;

    @FXML
    private TextField inputDias;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        inputDias.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (newValue.matches("\\d*")) {
                int dias;
                if (newValue.isEmpty()) {
                    dias = 0;
                } else {
                    dias = Integer.parseInt(newValue);
                }
                if (carroEscolhido != null) {
                    txtTotal.setText("Valor Total: " + (carroEscolhido.getValorDiaria() * dias));
                }
            }
        });
    }

    public void setControladorPrincipal(PrincipalController controlador) {
        this.controladorPrincipal = controlador;
    }

    public void setCarro(Carro carro) {
        this.carroEscolhido = carro;

        txtModelo.setText("Modelo: " + carro.getModelo());
        txtMarca.setText("Marca: " + carro.getMarca());
        txtAno.setText("Ano: " + carro.getAno());
        txtCor.setText("Cor: " + carro.getCor());
        txtConfiguracao.setText("Configuracao: " + carro.getConfiguracao());
        txtValor.setText("Valor diario: " + carro.getValorDiaria());
    }

    public void finalizarContrato() {
        if (controladorPrincipal != null) {
            String campo = inputDias.getText();
            if (campo.matches("\\d*")) {
                int dias = Integer.parseInt(campo);
                controladorPrincipal.criarContrato(carroEscolhido, dias);
                fecharJanela();
            }
        }
    }

    public void fecharJanela() {
        txtModelo.getScene().getWindow().hide();
    }
}
