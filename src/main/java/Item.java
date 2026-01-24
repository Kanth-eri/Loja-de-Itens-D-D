import com.google.gson.annotations.SerializedName;

public abstract class Item {
    protected String nome, raridade, descb;
    @SerializedName("propriedade") protected String descex;
    protected int qntLoja;
    @SerializedName("preco") protected double precoBase;
    protected double peso;
    protected boolean sintonizacao;

    public Item(String nome, String raridade, int qntLoja, String descb, String descex, double precoBase, double peso, boolean sintonizacao) {
        this.nome = nome;
        this.raridade = raridade;
        this.qntLoja = qntLoja;
        this.descb = descb;
        this.descex = descex;
        this.precoBase = precoBase;
        this.peso = peso;
        this.sintonizacao = sintonizacao;
    }

    public abstract String getEspecifico();
    public abstract double calculoPreco();

    @Override
    public String toString() {
        return String.format("%s | Raridade: %s | Estoque: %d | %s | Preço: %.2f PO | Peso: %.2f Kg | Sintonização: %s",
                nome, raridade, qntLoja, getEspecifico(), calculoPreco(), peso, (sintonizacao ? "Sim" : "Não"));
    }
}

class Arma extends Item {
    private String dano;

    public Arma(String nome, String raridade, int qntLoja, String descb, String descex, double precoBase, double peso, boolean sintonizacao, String dano) {
        super(nome, raridade, qntLoja, descb, descex, precoBase, peso, sintonizacao);
        this.dano = dano;
    }

    @Override
    public String getEspecifico() { return "Dano: " + dano; }

    @Override
    public double calculoPreco() { return precoBase; }
}

class Armadura extends Item {
    private int CA;

    public Armadura(String nome, String raridade, int qntLoja, String descb, String descex, double precoBase, double peso, boolean sintonizacao, int CA) {
        super(nome, raridade, qntLoja, descb, descex, precoBase, peso, sintonizacao);
        this.CA = CA;
    }

    @Override
    public String getEspecifico() { return "CA: " + CA; }

    @Override
    public double calculoPreco() { return precoBase; }
}

class Equipamento extends Item {
    public Equipamento(String nome, String raridade, int qntLoja, String descb, String descex, double precoBase, double peso, boolean sintonizacao) {
        super(nome, raridade, qntLoja, descb, descex, precoBase, peso, sintonizacao);
    }

    @Override
    public String getEspecifico() { return "Equipamento Geral"; }

    @Override
    public double calculoPreco() { return precoBase; }
}

class Pocao extends Item {
    private String tipoEfeito;
    private int nivelPot;

    public Pocao(String nome, String raridade, int qntLoja, String descb, String descex, double precoBase, double peso, boolean sintonizacao, String tipoEfeito, int nivelPot) {
        super(nome, raridade, qntLoja, descb, descex, precoBase, peso, sintonizacao);
        this.tipoEfeito = tipoEfeito;
        this.nivelPot = nivelPot;
    }

    @Override
    public String getEspecifico() { return "Efeito: " + tipoEfeito + " (Nível " + nivelPot + ")"; }

    @Override
    public double calculoPreco() { return precoBase + (nivelPot * 2); }
}

class itemMaravilhoso extends Item {
    private String categoria;

    public itemMaravilhoso(String nome, String raridade, int qntLoja, String descb, String descex, double precoBase, double peso, boolean sintonizacao, String categoria) {
        super(nome, raridade, qntLoja, descb, descex, precoBase, peso, sintonizacao);
        this.categoria = categoria;
    }

    @Override
    public String getEspecifico() { return "Categoria: " + categoria; }

    @Override
    public double calculoPreco() { return precoBase; }
}

class Tatuagem extends Item {
    private String magia;

    public Tatuagem(String nome, String raridade, int qntLoja, String descb, String descex, double precoBase, double peso, boolean sintonizacao, String magia) {
        super(nome, raridade, qntLoja, descb, descex, precoBase, peso, sintonizacao);
        this.magia = magia;
    }

    @Override
    public String getEspecifico() { return "Magia: " + magia; }

    @Override
    public double calculoPreco() { return precoBase; }
}