package locar.Model.DAO;

import java.util.Map;

/**
 *
 * @author vinic
 * @param <T>
 * @param <U>
 */
public interface IDAO<T, U> {

    void salvar();

    void carregar();

    void adicionar(T obj);

    T buscar(U id);

    Map<U, T> buscarTodos();

    void atualizar(T obj);

    void deletar(U id);
}
