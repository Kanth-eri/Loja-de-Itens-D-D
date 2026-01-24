import com.google.gson.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CadastroItem {
    Scanner sc = new Scanner(System.in);
    private List<Item> itens;

    public CadastroItem() {
        this.itens = new ArrayList<>();
    }

    public void adicionarItem(Item novoItem, String tipo, String nomeCategoria) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject obj = null;

        try (FileReader reader = new FileReader("Itens.json")) {
            obj = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (Exception e) {
            obj = new JsonObject();
        }
        if (!obj.has(tipo)) {
            obj.add(tipo, new JsonArray());
        }
        JsonArray listaCategorias = obj.getAsJsonArray(tipo);
        JsonObject categoriaEncontrada = null;
        for (JsonElement catElement : listaCategorias) {
            JsonObject catObj = catElement.getAsJsonObject();
            if (catObj.get("categoria").getAsString().equalsIgnoreCase(nomeCategoria)) {
                categoriaEncontrada = catObj;
                break;
            }
        }

        if (categoriaEncontrada == null) {
            categoriaEncontrada = new JsonObject();
            categoriaEncontrada.addProperty("categoria", nomeCategoria);
            categoriaEncontrada.add("itens", new JsonArray());
            listaCategorias.add(categoriaEncontrada);
        }
        JsonArray arrayItens = categoriaEncontrada.getAsJsonArray("itens");
        arrayItens.add(gson.toJsonTree(novoItem));

        try (FileWriter escrever = new FileWriter("Itens.json")) {
            gson.toJson(obj, escrever);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exibirMenu() {
        int opcao;
        String nome, raridade, descb, descex, dano, propriedades;
        double preco, peso;
        int qntLoja;
        boolean sintonizacao;
        Item novoItem = null;
        String tipo = null;
        String nomeCategoria = null;
        do {
            IO.println("Selecione o tipo do item que você deseja criar:");
            IO.println("1 - Arma");
            IO.println("2 - Armadura");
            IO.println("3 - Equipamento");
            IO.println("4 - Venda");
            IO.println("5 - Poção");
            IO.println("6 - Item Maravilhoso");
            IO.println("7 - Tatuagem");
            IO.println("0 - Voltar");
            opcao = sc.nextInt();
            sc.nextLine();
            if (opcao == 0) return;

            switch (opcao) {
                case 1 -> {
                    tipo = "armas";
                    IO.println("Categoria: 1 - Simples, 2 - Marcial");
                    int sub = sc.nextInt();
                    sc.nextLine();
                    nomeCategoria = (sub == 1) ? "Simples" : "Marciais";

                    IO.println("Nome:");
                    nome = sc.nextLine();
                    IO.println("Raridade:");
                    raridade = sc.nextLine();
                    IO.println("Preço:");
                    preco = sc.nextDouble();
                    IO.println("Peso:");
                    peso = sc.nextDouble();
                    sc.nextLine();
                    IO.println("Descrição curta:");
                    descb = sc.nextLine();
                    IO.println("Propriedades (Descrição Extensa):");
                    descex = sc.nextLine();
                    IO.println("Quantidade:");
                    qntLoja = sc.nextInt();
                    sc.nextLine();
                    IO.println("Sintonização (true/false):");
                    sintonizacao = sc.nextBoolean();
                    sc.nextLine();
                    IO.println("Dano (ex: 1d6):");
                    dano = sc.nextLine();

                    novoItem = new Arma(nome, raridade, qntLoja, descb, descex, preco, peso, sintonizacao, dano);
                }
            }
            if (novoItem != null) {
                adicionarItem(novoItem, tipo, nomeCategoria);
                IO.println("Cadastro realizado com sucesso!");

            }
        } while (opcao != 0) ;
    }
}