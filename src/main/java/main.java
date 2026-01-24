import java.util.Scanner;

static Scanner sc = new Scanner(System.in);
static estoqueLoja estoqueLoja = new estoqueLoja();

void main() {
    exibirMenu();
}

static void exibirMenu() {
    int opcao;
    do

    {
        IO.println("LOJA DE ITENS WEST MARCHES");
        IO.println("1 - Ver Loja");
        IO.println("2 - Cadastrar Item");
        IO.println("0 - Sair");
        opcao = sc.nextInt();
        sc.nextLine();

        switch (opcao) {
            case 1 -> estoqueLoja.exibirEstoque();
            case 2 -> {
                CadastroItem cadastroItem = new CadastroItem();
                cadastroItem.exibirMenu();
            }
            case 0 -> IO.println("Saindo...");
            default -> IO.println("Opção Inválida.");
        }
    } while(opcao !=0);
}