import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class estoqueLoja {
    private List<Item> listaItens = new ArrayList<>();
    public estoqueLoja() {
        iniciarEstoque();
}
public void iniciarEstoque() {
    String caminho = "itens.json";

    try (FileReader reader = new FileReader(caminho)) {
        Gson gson = new Gson();
        JsonObject jsonCompleto = gson.fromJson(reader, JsonObject.class);

        if (jsonCompleto == null) return;

        if (jsonCompleto.has("armas")) {
            for (JsonElement catElement : jsonCompleto.getAsJsonArray("armas")) {
                JsonObject catObj = catElement.getAsJsonObject();
                if (catObj.has("itens")) {
                    for (JsonElement itemElement : catObj.getAsJsonArray("itens")) {
                        JsonObject obj = itemElement.getAsJsonObject();
                        if (obj.has("nome")) {
                            listaItens.add(new Arma(obj.get("nome").getAsString(),
                                    "Comum",
                                    0,
                                    ".",
                                    obj.has("propriedades") ? obj.get("propriedades").getAsString() : "...",
                                    obj.get("preco").getAsDouble(),
                                    obj.get("peso").getAsDouble(),
                                    false,
                                    obj.get("dano").getAsString()
                            ));
                        }
                    }
                }
            }
        }
        if (jsonCompleto.has("armaduras")) {
            for (JsonElement catElement : jsonCompleto.getAsJsonArray("armaduras")) {
                JsonObject catObj = catElement.getAsJsonObject();
                if (catObj.has("itens")) {
                    for (JsonElement itemElement : catObj.getAsJsonArray("itens")) {
                        JsonObject obj = itemElement.getAsJsonObject();
                        if (obj.has("nome")) {
                            listaItens.add(new Armadura(
                                    obj.get("nome").getAsString(),
                                    "Comum", 0, ".", "...",
                                    obj.get("preco").getAsDouble(),
                                    obj.get("peso").getAsDouble(),
                                    false,
                                    obj.get("ca").getAsInt()
                            ));
                        }
                    }
                }
            }
        }
    } catch (IOException e) {
        IO.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        e.printStackTrace();
    }
}
public void exibirEstoque() {
        IO.println("==========ESTOQUE DA LOJA==========");
        for (Item item : listaItens) {
            IO.println(item);
            IO.println("-----------------------------------------------------------------------------------");
        }
}
}