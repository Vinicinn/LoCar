package locar.Model.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import locar.Model.Cliente;

/**
 *
 * @author vinic
 */
public class ClienteDAO implements IDAO<Cliente, String> {

    private Map<String, Cliente> clientes;
    private static final String NOME_ARQUVIO = "clientes.bin";
    private static final String CAMINHO_ARQUIVO = "src/locar/Resources/";

    public ClienteDAO() {
        clientes = new HashMap<>();
    }

    @Override
    public void salvar() {
        File arquivo = new File(CAMINHO_ARQUIVO + NOME_ARQUVIO);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(clientes);
        } catch (IOException e) {
            System.err.println("erro ao salvar arquivo: " + e.getMessage());
        }
    }

    @Override
    public void carregar() {
        File arquivo = new File(CAMINHO_ARQUIVO + NOME_ARQUVIO);
        if (arquivo.exists() && arquivo.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                clientes = (Map<String, Cliente>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("erro ao carregar arquivo: " + e.getMessage());
                System.out.println("inicializando mapa vazio");
                clientes = new HashMap<>();
            }
        } else {
            System.out.println("arquivo nao existe ou vazio, inicializando mapa vazio");
            clientes = new HashMap<>();
        }
    }

    @Override
    public void adicionar(Cliente cliente) {
        clientes.put(cliente.getCpf(), cliente);
        salvar();
    }

    @Override
    public Cliente buscar(String cpf) {
        return clientes.get(cpf);
    }

    @Override
    public Map<String, Cliente> buscarTodos() {
        return clientes;
    }

    @Override
    public void atualizar(Cliente cliente) {
        if (clientes.containsKey(cliente.getCpf())) {
            clientes.put(cliente.getCpf(), cliente);
            salvar();
        }
    }

    @Override
    public void deletar(String cpf) {
        if (clientes.containsKey(cpf)) {
            clientes.remove(cpf);
            salvar();
        }
    }
}
