package locar.Controller;

import java.util.Map;
import locar.Model.Carro;
import locar.Model.Configuracao;
import locar.Model.DAO.CarroDAO;

/**
 *
 * @author vinic
 */
public class CarroController {

    private final CarroDAO gerenciador;

    public CarroController() {
        gerenciador = new CarroDAO();
        gerenciador.carregar();
    }

    public void cadastrarCarro(int codigo, int ano, String marca, String modelo, String placa, String cor, double valorDiaria, boolean disponivel, Configuracao configuracao) {
        Carro novoCarro = new Carro(codigo, ano, marca, modelo, placa, cor, valorDiaria, disponivel, configuracao);
        gerenciador.adicionar(novoCarro);
    }

    public void buscarCarro(int codigo) {
        Carro busca = gerenciador.buscar(codigo);
        if (busca == null) {
            System.out.println("Carro não encontrado");
        } else {
            System.out.println("Carro encontrado");
        }
    }

    public Map<Integer, Carro> buscarTodos() {
        return gerenciador.buscarTodos();
    }

    public void ocuparCarro(Carro carro) {
        carro.setDisponivel(false);
        gerenciador.atualizar(carro);
    }

    public void disponibilizarCarro(Carro carro) {
        carro.setDisponivel(true);
        gerenciador.atualizar(carro);
    }
}
