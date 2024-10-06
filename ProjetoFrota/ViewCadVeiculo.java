import java.util.Scanner;

public class ViewCadVeiculo {
    private static ServiceVeiculo service = new ServiceVeiculo();
    static Scanner input = new Scanner(System.in);

    public static void limparTela() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static void aguardarEnter() {
        System.out.print("Pressione enter para continuar.");
        input.nextLine();
    }

    private static int inputNumerico(String mensagem) {
        int valor = 0;
        boolean entradaValida = false;
        do {
            System.out.print(mensagem);
            String valorStr = input.nextLine();
            try {
                valor = Integer.parseInt(valorStr);
                entradaValida = true;
            } catch (Exception e) {
                System.out.println("Erro: Valor informado deve ser um número inteiro.");
            }
        } while (!entradaValida);
        return valor;
    }

    private static String inputString(String mensagem) {
        System.out.print(mensagem);
        return input.nextLine();
    }

    private static String inputPlaca() {
        String placa;
        boolean placaValida = false;
        do {
            placa = inputString("Placa (7 dígitos): ");
            if (placa.length() == 7) {
                placaValida = true;
            } else {
                System.out.println("Erro: A placa deve conter exatamente 7 caracteres.");
            }
        } while (!placaValida);
        return placa;
    }

    private static void cadastrarVeiculo() {
        System.out.println("Cadastro de Veículo");

        String tipo;
        do {
            tipo = inputString("Digite o tipo de veículo (Carro/Moto): ").toLowerCase();
            if (!tipo.equals("carro") && !tipo.equals("moto")) {
                System.out.println("Erro: Tipo de veículo inválido. Escolha entre 'Carro' ou 'Moto'.");
            }
        } while (!tipo.equals("carro") && !tipo.equals("moto"));

        Veiculo veiculo;
        if (tipo.equals("carro")) {
            veiculo = new Carro();
            int numeroPortas;
            do {
                numeroPortas = inputNumerico("Número de portas: ");
                if (numeroPortas <= 0) {
                    System.out.println("Erro: O número de portas deve ser maior que zero.");
                }
            } while (numeroPortas <= 0);
            ((Carro) veiculo).setNumeroPortas(numeroPortas);
        } else {
            veiculo = new Moto();
            ((Moto) veiculo).setPartidaEletrica(inputString("Possui partida elétrica? (S/N): ").equalsIgnoreCase("S"));
        }

        String marca;
        do {
            marca = inputString("Marca: ");
            if (marca.isEmpty()) {
                System.out.println("Erro: O campo 'Marca' não pode ficar em branco.");
            }
        } while (marca.isEmpty());

        String modelo;
        do {
            modelo = inputString("Modelo: ");
            if (modelo.isEmpty()) {
                System.out.println("Erro: O campo 'Modelo' não pode ficar em branco.");
            }
        } while (modelo.isEmpty());

        int ano;
        do {
            ano = inputNumerico("Ano: ");
            if (ano <= 0) {
                System.out.println("Erro: O ano deve ser maior que zero.");
            }
        } while (ano <= 0);

        String placa = inputPlaca();
        
        try {
            if (service.findByPlaca(placa) != null) {
                System.out.println("Erro: Já existe um veículo cadastrado com esta placa.");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setAno(ano);
        veiculo.setPlaca(placa);

        try {
            service.save(veiculo);
            System.out.println("Veículo cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        aguardarEnter();
    }

    private static void listarVeiculos() {
        System.out.println("Listagem de Veículos:");
        for (Veiculo veiculo : service.findAll()) {
            System.out.println(veiculo.toString());
        }
        aguardarEnter();
    }

    private static void pesquisarVeiculo() {
        System.out.println("Pesquisa de Veículo:");
        String placa = inputString("Digite a placa do veículo: ");
        try {
            Veiculo veiculo = service.findByPlaca(placa);
            System.out.println(veiculo.toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        aguardarEnter();
    }

    private static void removerVeiculo() {
        System.out.println("Remoção de Veículo:");
        String placa = inputString("Digite a placa do veículo: ");
        try {
            Veiculo veiculo = service.findByPlaca(placa);
            service.findAll().remove(veiculo);
            System.out.println("Veículo removido com sucesso!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        aguardarEnter();
    }

    public static void main(String[] args) {
        String menu = """
                SISTEMA DE GERENCIAMENTO DE FROTAS
                Menu de Opções:
                1 - Cadastrar novo Veículo;
                2 - Listar todos Veículos cadastrados;
                3 - Pesquisar Veículo pela placa;
                4 - Remover Veículo;
                0 - Sair;
                Digite a opção desejada:
                """;
        int opcao;
        do {
            limparTela();
            opcao = inputNumerico(menu);
            switch (opcao) {
                case 0:
                    System.out.println("VOLTE SEMPRE!!!");
                    break;
                case 1:
                    cadastrarVeiculo();
                    break;
                case 2:
                    listarVeiculos();
                    break;
                case 3:
                    pesquisarVeiculo();
                    break;
                case 4:
                    removerVeiculo();
                    break;
                default:
                    System.out.println("Opção Inválida!!!");
                    aguardarEnter();
                    break;
            }
        } while (opcao != 0);
    }
}