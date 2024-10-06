import java.util.ArrayList;
import java.util.List;

public class ServiceVeiculo {
    private List<Veiculo> frota = new ArrayList<>();

    public Veiculo save(Veiculo veiculo) throws Exception {
        // Verificar se já existe um veículo com a mesma placa
        for (Veiculo v : frota) {
            if (v.getPlaca().equalsIgnoreCase(veiculo.getPlaca())) {
                throw new Exception("Erro: Veículo com a placa " + veiculo.getPlaca() + " já foi cadastrado.");
            }
        }

        frota.add(veiculo);
        return veiculo;
    }

    public List<Veiculo> findAll() {
        return frota;
    }

    public Veiculo findByPlaca(String placa) throws Exception {
        Veiculo veiculoRet = null;
        for (Veiculo veiculo : frota) {
            if (veiculo.getPlaca().equalsIgnoreCase(placa)) {
                veiculoRet = veiculo;
                break;
            }
        }
        if (veiculoRet == null)
            throw new Exception("Veículo não encontrado com a placa informada.");
        return veiculoRet;
    }
}