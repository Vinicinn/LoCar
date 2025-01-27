package locar.Model;

import java.io.Serializable;

/**
 *
 * @author vinic
 */
public class Carro implements Serializable {

    private int codigo;
    private int ano;
    private String marca;
    private String modelo;
    private String placa;
    private String cor;
    private double valorDiaria;
    private boolean disponivel;
    private Configuracao configuracao;

    public Carro(int codigo, int ano, String marca, String modelo, String placa, String cor, double valorDiaria, boolean disponivel, Configuracao configuracao) {
        this.codigo = codigo;
        this.ano = ano;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.cor = cor;
        this.valorDiaria = valorDiaria;
        this.disponivel = disponivel;
        this.configuracao = configuracao;
    }

    public Carro() {}

    public int getCodigo() {
        return codigo;
    }

    public int getAno() {
        return ano;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public String getCor() {
        return cor;
    }

    public double getValorDiaria() {
        return valorDiaria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public Configuracao getConfiguracao() {
        return configuracao;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setValorDiaria(double valorDiaria) {
        this.valorDiaria = valorDiaria;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setConfiguracao(Configuracao configuracao) {
        this.configuracao = configuracao;
    }
}
