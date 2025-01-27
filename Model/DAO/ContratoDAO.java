package locar.Model.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import locar.Model.Contrato;

/**
 *
 * @author vinic
 */
public class ContratoDAO implements IDAO<Contrato, Integer> {

    private Map<Integer, Contrato> contratos;
    private static final String NOME_ARQUVIO = "contratos.bin";
    private static final String CAMINHO_ARQUIVO = "src/locar/Resources/";

    public ContratoDAO() {
        this.contratos = new HashMap<>();
    }

    @Override
    public void salvar() {
        File arquivo = new File(CAMINHO_ARQUIVO + NOME_ARQUVIO);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(contratos);
        } catch (IOException e) {
            System.err.println("erro ao salvar arquivo: " + e.getMessage());
        }
    }

    @Override
    public void carregar() {
        File arquivo = new File(CAMINHO_ARQUIVO + NOME_ARQUVIO);
        if (arquivo.exists() && arquivo.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                contratos = (Map<Integer, Contrato>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("erro ao carregar arquivo: " + e.getMessage());
                System.out.println("inicializando mapa vazio");
                contratos = new HashMap<>();
            }
        } else {
            System.out.println("arquivo nao existe ou vazio, inicializando mapa vazio");
            contratos = new HashMap<>();
        }
    }

    @Override
    public void adicionar(Contrato contrato) {
        contratos.put(contrato.getCodigo(), contrato);
        salvar();
    }

    @Override
    public Contrato buscar(Integer codigo) {
        return contratos.get(codigo);
    }

    @Override
    public Map<Integer, Contrato> buscarTodos() {
        return contratos;
    }

    @Override
    public void atualizar(Contrato contrato) {
        if (contratos.containsKey(contrato.getCodigo())) {
            contratos.put(contrato.getCodigo(), contrato);
            salvar();
        }
    }

    @Override
    public void deletar(Integer codigo) {
        if (contratos.containsKey(codigo)) {
            contratos.remove(codigo);
            salvar();
        }
    }
}
