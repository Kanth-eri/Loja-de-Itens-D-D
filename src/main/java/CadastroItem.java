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
        String nome = null, raridade = null, descb = null, descex = null, dano, propriedades = null;
        double preco = 0, peso = 0;
        int qntLoja = 0, CA, opcaoRar = 0;
        boolean sintonizacao = false;
        Item novoItem = null;
        String tipo = null;
        String nomeCategoria = null;
        do {
            IO.println("Selecione o tipo do item que você deseja criar:");
            IO.println("1 - Arma");
            IO.println("2 - Armadura");
            IO.println("3 - Equipamento");
            IO.println("4 - Poção");
            IO.println("5 - Item Maravilhoso");
            IO.println("6 - Tatuagem");
            IO.println("0 - Voltar");
            opcao = sc.nextInt();
            sc.nextLine();
            if (opcao == 0) return;

            switch (opcao) {
                case 1 -> {
                    tipo = "armas";
                    escolherCategoria(tipo, sc);
                    dadosBase base = coletarDados(sc);
                    IO.println("Dano (ex: 1d6):");
                    dano = sc.nextLine();

                    novoItem = new Arma(base.nome, base.raridade, base.qntLoja,
                            base.descb, base.descex, base.preco, base.peso,
                            base.sintonizacao, dano);
                }
                case 2 -> {
                    tipo = "armaduras";
                    escolherCategoria(tipo, sc);
                    dadosBase base = coletarDados(sc);
                    IO.println("CA (ex: 18):");
                    CA = sc.nextInt();
                    sc.nextLine();

                    novoItem = new Armadura(base.nome, base.raridade, base.qntLoja,
                            base.descb, base.descex, base.preco, base.peso,
                            base.sintonizacao, CA);
                }
            }
            if (novoItem != null) {
                adicionarItem(novoItem, tipo, nomeCategoria);
                IO.println("Cadastro realizado com sucesso!");

            }
        } while (opcao != 0);
    }
    public String escolherCategoria(String tipo, Scanner sc) {
        String nomeCategoria = null;
        switch (tipo) {
            case "armas" -> {
                IO.println("Categoria:");
                IO.println("1 - Simples");
                IO.println("2 - Marcial");
                int sub = sc.nextInt();
                sc.nextLine();
                nomeCategoria = (sub == 1) ? "Simples" : "Marciais";
            }
            case "armaduras" -> {
                IO.println("Categoria:");
                IO.println("1 - Leve");
                IO.println("2 - Média");
                IO.println("3 - Pesada");
                int sub = sc.nextInt();
                sc.nextLine();
                nomeCategoria = (sub == 1) ? "Leve" :
                        (sub == 2) ? "Média" : "Pesada";
            }
            case "equipamentos" -> {
                IO.println("Categoria:");
                IO.println("1 - Geral");
                IO.println("2 - Focos Arcanos");
                IO.println("3 - Focos Druídicos");
                IO.println("4 - Símbolos Sagrados:");
                IO.println("5 - Munições:");
                IO.println("6 - Instrumentos Musicais:");
                IO.println("7 - Kits:");
                IO.println("8 - Kits de Jogos:");
                IO.println("9 - Animais:");
                IO.println("10 - Arreios e Veículos de Tração:");
                int sub = sc.nextInt();
                sc.nextLine();
                String[] lista = {"Geral", "Focos arcanos", "Focos druídicos", "Símbolos sagrados", "Munições",
                        "Instrumentos musicais", "Kits", "Kits de jogo", "Animais", "Arreios e Veículos de tração"};
                nomeCategoria = (sub >= 1 && sub <= 10) ? lista[sub - 1] : "Geral";
            }
            case "pocoes", "itens maravilhosos", "tatuagem" -> {
                nomeCategoria = "Geral";
            }
                default ->  nomeCategoria = "Geral";
        }
        return nomeCategoria;
    }
    class dadosBase {
        String nome, raridade, descb, descex;
        double preco, peso;
        int qntLoja;
        boolean sintonizacao;
    public dadosBase(String nome, String raridade, double preco, double peso, String descb, String descex, int qntLoja, boolean sintonizacao) {
        this.nome = nome; this.raridade = raridade; this.preco = preco;
        this.peso = peso; this.descb = descb; this.descex = descex;
        this.qntLoja = qntLoja; this.sintonizacao = sintonizacao;
    }
    }
    public dadosBase coletarDados(Scanner sc) {
        IO.println("Nome:");
        String nome = sc.nextLine();
        String raridade = menuRaridade(sc);
        IO.println("Preço:");
        double preco = sc.nextDouble();
        IO.println("Peso:");
        double peso = sc.nextDouble();
        sc.nextLine();
        IO.println("Descrição curta:");
        String descb = sc.nextLine();
        IO.println("Propriedades (Descrição Extensa):");
        String descex = sc.nextLine();
        IO.println("Quantidade no Estoque:");
        int qntLoja = sc.nextInt();
        sc.nextLine();
        boolean sintonizacao = menuSintonizacao(sc);

        return new dadosBase(nome, raridade, preco, peso, descb, descex, qntLoja, sintonizacao);
    }
    public boolean menuSintonizacao(Scanner sc) {
        int opcaoSint;
        boolean sintLocal = false;
        do {
            IO.println("O item requer sintonização?");
            IO.println("1 - Sim");
            IO.println("2 - Não");
            opcaoSint = sc.nextInt();
            sc.nextLine();

            if (opcaoSint == 1) {
                sintLocal = true;
            } else if (opcaoSint == 2) {
                sintLocal = false;
            } else {
                IO.println("Opção inválida. Escolha 1 ou 2.");
            }
        } while (opcaoSint < 1 || opcaoSint > 2);
        return sintLocal;
    }
    public String menuRaridade(Scanner sc) {
        int opcaoRar;
        String raridadeLocal = null;
        do {
            IO.println("1 - Comum");
            IO.println("2 - Incomum");
            IO.println("3 - Raro");
            IO.println("4 - Muito Raro");
            IO.println("5 - Lendário");
            IO.println("6 - Artefato");
            opcaoRar = sc.nextInt();
            sc.nextLine();
            if (opcaoRar == 1) {
                raridadeLocal = "Comum";
            } else if (opcaoRar == 2) {
                raridadeLocal = "Incomum";
            } else if (opcaoRar == 3) {
                raridadeLocal = "Raro";
            } else if (opcaoRar == 4) {
                raridadeLocal = "Muito Raro";
            } else if (opcaoRar == 5) {
                raridadeLocal = "Lendário";
            } else if (opcaoRar == 6) {
                raridadeLocal = "Artefato";
            }
        } while (opcaoRar < 1 || opcaoRar > 6);
        return raridadeLocal;
    }
}