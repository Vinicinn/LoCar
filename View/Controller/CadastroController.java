package locar.View.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import locar.Controller.ClienteController;
import locar.Model.Cliente;

/**
 * FXML Controller class
 *
 * @author vinic
 */
public class CadastroController implements Initializable {

    private ClienteController controladorClientes;

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtCPF;

    @FXML
    private TextField txtSenha1;

    @FXML
    private TextField txtSenha2;

    @FXML
    private Text errorNome;

    @FXML
    private Text errorCPF;

    @FXML
    private Text errorSenha1;

    @FXML
    private Text errorSenha2;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controladorClientes = ClienteController.getInstance();
    }

    @FXML
    public void fazerCadastro() {
        String nome = txtNome.getText();
        String CPF = txtCPF.getText();
        String senha = txtSenha1.getText();
        String senha2 = txtSenha2.getText();

        errorNome.setText("");
        errorCPF.setText("");
        errorSenha1.setText("");
        errorSenha2.setText("");

        if (nome.isEmpty() || CPF.isEmpty() || senha.isEmpty() || senha2.isEmpty()) {
            if (nome.isEmpty()) {
                errorNome.setText("Campo Nome nao pode estar vazio");
            }
            if (CPF.isEmpty()) {
                errorCPF.setText("Campo CPF nao pode estar vazio");
            }
            if (senha.isEmpty()) {
                errorSenha1.setText("Campo senha nao pode estar vazio");
            }
            if (senha2.isEmpty()) {
                errorSenha2.setText("Confirme sua senha");
            }
            return;
        }

        Cliente cliente = controladorClientes.buscarCliente(CPF);
        if (cliente != null) {
            errorCPF.setText("CPF JA EM USO!");
            return;
        }
        if (senha.equals(senha2)) {
            controladorClientes.cadastrarCliente(nome, CPF, senha);
            voltar();
        } else {
            errorSenha1.setText("Senhas diferentes");
            errorSenha2.setText("Senhas diferentes");
        }
    }

    @FXML
    public void voltar() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/locar/View/Login.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) txtNome.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Falha no carregamento");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possivel carregar tela de login. " + e.getMessage());
            alert.showAndWait();
            Platform.exit();
        }

    }
}
