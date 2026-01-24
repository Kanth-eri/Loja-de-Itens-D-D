import com.google.gson.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CadastroItem{
    Scanner sc = new Scanner(System.in);
    private List<Item> itens;

    public CadastroItem(Item item){
        this.itens = new ArrayList<>();
    }
public void adicionarItem (Item novoItem, String nomeCategoria) throws IOException {
    itens.add(novoItem);
    JsonObject obj = null;
    try (FileReader reader = new FileReader("Itens.json")){
        obj = JsonParser.parseReader(reader).getAsJsonObject();
        if (obj == null){
            obj = new JsonObject();
        } else {
        JsonArray categoria = obj.getAsJsonArray(nomeCategoria);
        if(categoria != null){
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        gson.toJson(novoItem);
        JsonElement elemento = gson.toJsonTree(novoItem);
        categoria.add(elemento);
        } else {
            IO.println("Erro ao ler categoria");
        }
    } try(FileWriter escrever = new FileWriter("Itens.json")) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            gson.toJson(obj, escrever);
    } catch (Exception e) {
        throw new RuntimeException(e);
    }
}

public void exibirMenu(){
    int opcao;
    do {
        IO.println("Selecione o tipo do item que você deseja criar:");
        IO.println("1 - Arma");
        IO.println("2 - Armadura");
        IO.println("3 - Equipamento");
        IO.println("4 - Venda");
        IO.println("5 - Poção");
        IO.println("6 - Item Maravilhoso");
        IO.println("7 - Tatuagem");
        IO.println("0 - Sair");
        opcao = sc.nextInt();
        sc.nextLine();

        switch(opcao){
            case 1 ->
        }
    }

    }
}