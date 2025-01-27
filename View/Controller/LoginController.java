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
import javafx.scene.control.Alert.AlertType;
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
public class LoginController implements Initializable {

    private ClienteController controladorClientes;

    @FXML
    private TextField txtUsuario;

    @FXML
    private TextField txtSenha;

    @FXML
    private Text cpfError;

    @FXML
    private Text senhaError;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controladorClientes = ClienteController.getInstance();
    }

    @FXML
    private void fazerLogin() {
        String cpf = txtUsuario.getText();
        String senha = txtSenha.getText();

        cpfError.setText("");
        senhaError.setText("");

        if (cpf.isEmpty() || senha.isEmpty()) {
            if (cpf.isEmpty()) {
                cpfError.setText("Campo CPF não pode estar vazio");
            }
            if (senha.isEmpty()) {
                senhaError.setText("Campo senha não pode estar vazio");
            }
            return;
        }

        Cliente cliente = controladorClientes.buscarCliente(cpf);
        if (cliente != null) {
            if (cliente.getSenha().equals(senha)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/locar/View/Principal.fxml"));
                    Parent root = loader.load();

                    PrincipalController telaPrincipal = loader.getController();
                    telaPrincipal.setCliente(cliente);

                    Scene scene = new Scene(root);
                    Stage stage = (Stage) txtUsuario.getScene().getWindow();

                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Falha no carregamento");
                    alert.setHeaderText(null);
                    alert.setContentText("Não foi possivel carregar tela principal. " + e.getMessage());
                    alert.showAndWait();
                    Platform.exit();
                }
            } else {
                txtSenha.setText("");
                senhaError.setText("Senha incorreta");
            }
        } else {
            txtUsuario.setText("");
            txtSenha.setText("");
            cpfError.setText("Usuario nao encontrado");
        }
    }

    @FXML
    private void fazerCadastro() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/locar/View/Cadastro.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) txtUsuario.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Falha no carregamento");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possivel carregar tela de cadastro. " + e.getMessage());
            alert.showAndWait();
            Platform.exit();
        }
    }
}
