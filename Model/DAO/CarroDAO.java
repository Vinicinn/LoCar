package locar.Model.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import locar.Model.Carro;

/**
 *
 * @author vinic
 */
public class CarroDAO implements IDAO<Carro, Integer> {

    private Map<Integer, Carro> carros;
    private static final String NOME_ARQUVIO = "carros.bin";
    private static final String CAMINHO_ARQUIVO = "src/locar/Resources/";

    public CarroDAO() {
        carros = new HashMap<>();
    }

    @Override
    public void salvar() {
        File arquivo = new File(CAMINHO_ARQUIVO + NOME_ARQUVIO);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(carros);
        } catch (IOException e) {
            System.err.println("erro ao salvar arquivo: " + e.getMessage());
        }
    }

    @Override
    public void carregar() {
        File arquivo = new File(CAMINHO_ARQUIVO + NOME_ARQUVIO);
        if (arquivo.exists() && arquivo.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                carros = (Map<Integer, Carro>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("erro ao carregar arquivo: " + e.getMessage());
                System.out.println("inicializando mapa vazio");
                carros = new HashMap<>();
            }
        } else {
            System.out.println("arquivo nao existe ou vazio, inicializando mapa vazio");
            carros = new HashMap<>();
        }
    }

    @Override
    public void adicionar(Carro carro) {
        carros.put(carro.getCodigo(), carro);
        salvar();
    }

    @Override
    public Carro buscar(Integer codigo) {
        return carros.get(codigo);
    }

    @Override
    public Map<Integer, Carro> buscarTodos() {
        return carros;
    }

    @Override
    public void atualizar(Carro carro) {
        if (carros.containsKey(carro.getCodigo())) {
            carros.put(carro.getCodigo(), carro);
            salvar();
        }
    }

    @Override
    public void deletar(Integer codigo) {
        if (carros.containsKey(codigo)) {
            carros.remove(codigo);
            salvar();
        }
    }
}
