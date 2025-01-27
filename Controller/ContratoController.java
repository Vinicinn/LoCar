package locar.Controller;

import java.util.Map;
import locar.Model.Carro;
import locar.Model.Cliente;
import locar.Model.Contrato;
import locar.Model.DAO.ContratoDAO;

/**
 *
 * @author vinic
 */
public class ContratoController {

    private final ContratoDAO gerenciador;

    public ContratoController() {
        this.gerenciador = new ContratoDAO();
        gerenciador.carregar();
    }

    public void cadastrarContrato(int codigo, Carro carro, Cliente cliente, int diasDeAluguel, double valorTotal) {
        Contrato novoContrato = new Contrato(codigo, carro, cliente, diasDeAluguel, valorTotal);
        gerenciador.adicionar(novoContrato);
    }

    public Map<Integer, Contrato> buscarTodos() {
        return gerenciador.buscarTodos();
    }

    public void cancelarContrato(Contrato contrato) {
        gerenciador.deletar(contrato.getCodigo());
    }
}
