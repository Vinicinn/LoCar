package locar.Model;

import java.io.Serializable;

/**
 *
 * @author vinic
 */
public class Contrato implements Serializable {

    private int codigo;
    private Carro carro;
    private Cliente cliente;
    private int diasDeAluguel;
    private double valorTotal;

    public Contrato(int codigo, Carro carro, Cliente cliente, int diasDeAluguel, double valorTotal) {
        this.codigo = codigo;
        this.carro = carro;
        this.cliente = cliente;
        this.diasDeAluguel = diasDeAluguel;
        this.valorTotal = valorTotal;
    }

    public int getCodigo() {
        return codigo;
    }

    public Carro getCarro() {
        return carro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getDiasDeAluguel() {
        return diasDeAluguel;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setDiasDeAluguel(int diasDeAluguel) {
        this.diasDeAluguel = diasDeAluguel;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
