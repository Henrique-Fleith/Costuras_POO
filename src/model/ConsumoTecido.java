package model;

public class ConsumoTecido {
    private int id, servicoId, tecidoId;
    private String tecidoNome;
    private double quantidadeMetros;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getServicoId() { return servicoId; }
    public void setServicoId(int servicoId) { this.servicoId = servicoId; }
    public int getTecidoId() { return tecidoId; }
    public void setTecidoId(int tecidoId) { this.tecidoId = tecidoId; }
    public String getTecidoNome() { return tecidoNome; }
    public void setTecidoNome(String tecidoNome) { this.tecidoNome = tecidoNome; }
    public double getQuantidadeMetros() { return quantidadeMetros; }
    public void setQuantidadeMetros(double q) { this.quantidadeMetros = q; }
}
