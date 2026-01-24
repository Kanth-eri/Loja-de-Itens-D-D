import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

        if (jsonCompleto.has("armaduras")) {
            JsonArray categorias = jsonCompleto.getAsJsonArray("armaduras");

            for (JsonElement catElement : categorias) {
                JsonArray itens = catElement.getAsJsonObject().getAsJsonArray("itens");

                for (JsonElement itemElement : itens) {
                    JsonObject obj = itemElement.getAsJsonObject();

                    Armadura novaArmadura = new Armadura(
                            obj.get("nome").getAsString(),
                            "Comum",
                            10,
                            "Item do Livro",
                            "PHB",
                            obj.get("preco").getAsDouble(),
                            obj.get("peso").getAsDouble(),
                            false,
                            obj.get("ca").getAsInt()
                    );

                    listaItens.add(novaArmadura);
                }
            }
        }
        IO.println(listaItens.size());

    } catch (IOException e) {
        IO.println("Erro ao ler o arquivo JSON: " + e.getMessage());
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