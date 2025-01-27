package locar.Controller;

import locar.Model.Cliente;
import locar.Model.DAO.ClienteDAO;

/**
 *
 * @author vinic
 */
public class ClienteController {

    private static ClienteController instance;
    private final ClienteDAO gerenciador;

    private ClienteController() {
        gerenciador = new ClienteDAO();
        gerenciador.carregar();
    }

    public static ClienteController getInstance() {
        if (instance == null) {
            instance = new ClienteController();
        }
        return instance;
    }

    public void cadastrarCliente(String nome, String cpf, String senha) {
        Cliente novoCliente = new Cliente(nome, cpf, senha);
        gerenciador.adicionar(novoCliente);
    }

    public Cliente buscarCliente(String cpf) {
        return gerenciador.buscar(cpf);
    }
}
