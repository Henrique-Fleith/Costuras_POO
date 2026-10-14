package model;

public class Tecido {
    private int id;
    private String nome, cor, composicao;
    private double largura, quantidadeEstoque, custoPorMetro;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    public String getComposicao() { return composicao; }
    public void setComposicao(String composicao) { this.composicao = composicao; }
    public double getLargura() { return largura; }
    public void setLargura(double largura) { this.largura = largura; }
    public double getQuantidadeEstoque() { return quantidadeEstoque; }
    public void setQuantidadeEstoque(double q) { this.quantidadeEstoque = q; }
    public double getCustoPorMetro() { return custoPorMetro; }
    public void setCustoPorMetro(double c) { this.custoPorMetro = c; }

    @Override public String toString() {
        return nome + (cor == null || cor.isBlank() ? "" : " - " + cor)
                + " (" + quantidadeEstoque + " m)";
    }
}
