package locar.View.Controller;

import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import locar.Controller.CarroController;
import locar.Controller.ContratoController;
import locar.Model.Carro;
import locar.Model.Cliente;
import locar.Model.Contrato;

/**
 * FXML Controller class
 *
 * @author vinic
 */
public class PrincipalController implements Initializable {

    private Cliente clienteLogado;
    private CarroController controladorCarros;
    private ContratoController controladorContratos;

    @FXML
    private Text txtMenu;

    @FXML
    private ComboBox comboMarca;

    @FXML
    private ComboBox comboAno;

    @FXML
    private ComboBox comboCor;

    @FXML
    private ComboBox comboPreco;

    @FXML
    private ComboBox comboDias;

    @FXML
    private ComboBox comboValor;

    @FXML
    private VBox vboxCarros;

    @FXML
    private VBox vboxContratos;
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        controladorContratos = new ContratoController();
        preencherFiltrosContratos();
        carregarListaContratos();

        controladorCarros = new CarroController();
        preencherFiltrosCarros();
        carregarListaCarros();
    }

    @FXML
    public void setCliente(Cliente cliente) {
        this.clienteLogado = cliente;
        txtMenu.setText("Bem vindo, " + clienteLogado.getNome() + "!");
    }

    public void preencherFiltrosCarros() {
        Map<Integer, Carro> carros = controladorCarros.buscarTodos();

        Set<String> marcas = new HashSet<>();
        Set<Integer> anos = new HashSet<>();
        Set<String> cores = new HashSet<>();
        Set<Double> precos = new HashSet<>();

        for (Carro carro : carros.values()) {
            if (carro.isDisponivel()) {
                marcas.add(carro.getMarca());
                anos.add(carro.getAno());
                cores.add(carro.getCor());
                precos.add(carro.getValorDiaria());
            }
        }

        comboMarca.getItems().clear();
        comboAno.getItems().clear();
        comboCor.getItems().clear();
        comboPreco.getItems().clear();

        comboMarca.getItems().addAll(marcas);
        comboAno.getItems().addAll(anos);
        comboCor.getItems().addAll(cores);
        comboPreco.getItems().addAll(precos);

        comboMarca.valueProperty().addListener((observable, oldValue, newValue) -> filtrarCarros());
        comboAno.valueProperty().addListener((observable, oldValue, newValue) -> filtrarCarros());
        comboCor.valueProperty().addListener((observable, oldValue, newValue) -> filtrarCarros());
        comboPreco.valueProperty().addListener((observable, oldValue, newValue) -> filtrarCarros());
    }

    public void carregarListaCarros() {
        Map<Integer, Carro> carros = controladorCarros.buscarTodos();

        vboxCarros.getChildren().clear();

        for (Carro carro : carros.values()) {
            if (carro.isDisponivel()) {
                vboxCarros.getChildren().add(criarItemCarro(carro));
            }
        }
    }

    public HBox criarItemCarro(Carro carro) {
        HBox itemLista = new HBox();
        itemLista.setId("ItemLista");
        HBox.setHgrow(itemLista, javafx.scene.layout.Priority.ALWAYS);

        Label marca = new Label(carro.getMarca());
        Label modelo = new Label(carro.getModelo());
        Label ano = new Label(String.valueOf(carro.getAno()));
        Label cor = new Label(carro.getCor());
        Label placa = new Label(carro.getPlaca());
        Label preco = new Label(String.format("R$ %.2f", carro.getValorDiaria()));
        Button alugar = new Button("Alugar");
        
        marca.setId("ItemText");
        modelo.setId("ItemText");
        ano.setId("ItemText");
        cor.setId("ItemText");;
        placa.setId("ItemText");
        preco.setId("ItemText");
        alugar.setId("Button");        

        modelo.setPrefWidth(80);
        marca.setPrefWidth(90);
        ano.setPrefWidth(50);
        placa.setPrefWidth(80);
        cor.setPrefWidth(80);
        preco.setPrefWidth(80);
        alugar.setPrefWidth(80);

        alugar.setOnAction(event -> abrirJanelaAluguel(carro));

        itemLista.getChildren().addAll(modelo, marca, ano, placa, cor, preco, alugar);
        return itemLista;
    }

    public void filtrarCarros() {
        Map<Integer, Carro> carros = controladorCarros.buscarTodos();

        String marcaSelecionada = (String) comboMarca.getValue();
        Integer anoSelecionado = (Integer) comboAno.getValue();
        String corSelecionada = (String) comboCor.getValue();
        Double precoSelecionado = (Double) comboPreco.getValue();

        Map<Integer, Carro> carrosFiltrados = carros.entrySet().stream()
                .filter(entry -> marcaSelecionada == null || entry.getValue().getMarca().equals(marcaSelecionada))
                .filter(entry -> anoSelecionado == null || entry.getValue().getAno() == anoSelecionado)
                .filter(entry -> corSelecionada == null || entry.getValue().getCor().equals(corSelecionada))
                .filter(entry -> precoSelecionado == null || entry.getValue().getValorDiaria() == precoSelecionado)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        vboxCarros.getChildren().clear();
        for (Carro carro : carrosFiltrados.values()) {
            if (carro.isDisponivel()) {
                vboxCarros.getChildren().add(criarItemCarro(carro));
            }
        }
    }

    public void limparFiltrosCarros() {
        comboMarca.getSelectionModel().clearSelection();
        comboAno.getSelectionModel().clearSelection();
        comboCor.getSelectionModel().clearSelection();
        comboPreco.getSelectionModel().clearSelection();

        comboMarca.setPromptText("Marca");
        comboAno.setPromptText("Ano");
        comboCor.setPromptText("Cor");
        comboPreco.setPromptText("Preço");

        carregarListaCarros();
    }

    public void abrirJanelaAluguel(Carro carro) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/locar/View/Aluguel.fxml"));
            Parent root = loader.load();

            AluguelController alugel = loader.getController();
            alugel.setCarro(carro);
            alugel.setControladorPrincipal(this);

            Stage stage = new Stage();
            stage.setTitle("Alugar Veiculo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Falha no carregamento");
            alert.setHeaderText(null);
            alert.setContentText("Não foi possivel carregar tela de aluguel. " + e.getMessage());
            alert.showAndWait();
            Platform.exit();
        }

    }

    public void criarContrato(Carro carro, int dias) {
        double valor = dias * carro.getValorDiaria();
        int codigo = new Random().nextInt(999) + 1;
        controladorContratos.cadastrarContrato(codigo, carro, clienteLogado, dias, valor);
        controladorCarros.ocuparCarro(carro);
        carregarTudo();
    }

    public void carregarListaContratos() {
        Map<Integer, Contrato> contratos = controladorContratos.buscarTodos();

        vboxContratos.getChildren().clear();

        for (Contrato contrato : contratos.values()) {
            vboxContratos.getChildren().add(criarItemContrato(contrato));
        }
    }

    public void limparFiltrosContratos() {
        comboDias.getSelectionModel().clearSelection();
        comboValor.getSelectionModel().clearSelection();

        comboDias.setPromptText("Dias");
        comboValor.setPromptText("Valor");

        carregarListaCarros();
    }

    public HBox criarItemContrato(Contrato contrato) {
        HBox itemLista = new HBox();
        itemLista.setId("ItemLista");
        HBox.setHgrow(itemLista, javafx.scene.layout.Priority.ALWAYS);

        Label modelo = new Label(contrato.getCarro().getModelo());
        Label placa = new Label(contrato.getCarro().getPlaca());
        Label dias = new Label(String.valueOf(contrato.getDiasDeAluguel()));
        Label valor = new Label(String.format("R$ %.2f", contrato.getValorTotal()));
        Button cancelar = new Button("Cancelar");
        
        modelo.setId("ItemText");
        placa.setId("ItemText");
        dias.setId("ItemText");
        valor.setId("ItemText");
        cancelar.setId("Button");

        modelo.setPrefWidth(100);
        placa.setPrefWidth(100);
        dias.setPrefWidth(80);
        valor.setPrefWidth(100);
        cancelar.setPrefWidth(80);

        cancelar.setOnAction(event -> cancelarContrato(contrato));

        itemLista.getChildren().addAll(modelo, placa, dias, valor, cancelar);
        return itemLista;
    }

    public void preencherFiltrosContratos() {
        Map<Integer, Contrato> contratos = controladorContratos.buscarTodos();

        Set<Integer> dias = new HashSet<>();
        Set<Double> valores = new HashSet<>();

        for (Contrato contrato : contratos.values()) {
            dias.add(contrato.getDiasDeAluguel());
            valores.add(contrato.getValorTotal());
        }

        comboDias.getItems().clear();
        comboValor.getItems().clear();

        comboDias.getItems().addAll(dias);
        comboValor.getItems().addAll(valores);

        comboDias.valueProperty().addListener((observable, oldValue, newValue) -> filtrarContratos());
        comboValor.valueProperty().addListener((observable, oldValue, newValue) -> filtrarContratos());
    }

    public void filtrarContratos() {
        Map<Integer, Contrato> contratos = controladorContratos.buscarTodos();

        Integer diasSelecionado = (Integer) comboDias.getValue();
        Double valorSelecionado = (Double) comboValor.getValue();

        Map<Integer, Contrato> contratosFiltrados = contratos.entrySet().stream()
                .filter(entry -> diasSelecionado == null || entry.getValue().getDiasDeAluguel() == diasSelecionado)
                .filter(entry -> valorSelecionado == null || entry.getValue().getValorTotal() == valorSelecionado)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        vboxContratos.getChildren().clear();
        for (Contrato contrato : contratosFiltrados.values()) {
            vboxContratos.getChildren().add(criarItemContrato(contrato));
        }
    }

    public void cancelarContrato(Contrato contrato) {
        controladorCarros.disponibilizarCarro(contrato.getCarro());
        controladorContratos.cancelarContrato(contrato);
        carregarTudo();
    }

    public void carregarTudo() {
        carregarListaCarros();
        limparFiltrosCarros();
        preencherFiltrosCarros();
        carregarListaContratos();
        limparFiltrosContratos();
        preencherFiltrosContratos();
    }
}
