package model;

public class MovimentacaoEstoque {
    private int id, tecidoId;
    private String tecidoNome, tipo, data, observacao; // tipo: ENTRADA ou SAIDA
    private double quantidade;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getTecidoId() { return tecidoId; }
    public void setTecidoId(int tecidoId) { this.tecidoId = tecidoId; }
    public String getTecidoNome() { return tecidoNome; }
    public void setTecidoNome(String tecidoNome) { this.tecidoNome = tecidoNome; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public double getQuantidade() { return quantidade; }
    public void setQuantidade(double quantidade) { this.quantidade = quantidade; }
    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
}
