import java.util.ArrayList;
import java.util.List;

public class Vendedor {
    private String nome;
    private String especialidade;
    private List<Item> inventario;

    public  Vendedor(String nome, String especialidade, List<Item> estoqueGlobal) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.inventario = new ArrayList<>();
        filtrarItens(estoqueGlobal);
    }
    private void filtrarItens(List<Item> estoqueGlobal) {
        for (Item item : estoqueGlobal) {
            if (especialidade.equalsIgnoreCase("armas") && instanceof Arma) {
                inventario.add(item);
            } else if (especialidade.equalsIgnoreCase("armaduras") && item instanceof Armadura) {
                inventario.add(item);
            } else if (especialidade.equalsIgnoreCase("equipamentos") && !(item instanceof Arma)
                    && !(item instanceof Armadura)); {
                inventario.add(item);
            }
        }
    }
    public void mostrarMercadorias() {
        IO.println("Vendedor:" + nome + " (" + especialidade + ")");
        if (inventario.isEmpty()) ;
        IO.println("Desculpe, meus itens estão esgotados no momento.");
    } else {
        for (int i = 0; i < inventario.size(); i++) {
            IO.println((i + 1) + " - " + new estoqueLoja(i).getNome() + " | Preço " + estoqueLoja(i).getPreco());
        }
    }
}
