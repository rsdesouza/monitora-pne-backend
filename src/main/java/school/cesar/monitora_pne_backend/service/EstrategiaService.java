package school.cesar.monitora_pne_backend.service;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import org.springframework.stereotype.Service;
import school.cesar.monitora_pne_backend.model.Estrategia;

import java.util.Collections;
import java.util.List;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
public class EstrategiaService {

    private final GCPStorageService storageService;
    private final String bucketName = "monitora_pne_15_streamlit";
    private final String fileName = "estrategias.csv";

    public EstrategiaService(GCPStorageService storageService) {
        this.storageService = storageService;
    }

    public List<Estrategia> listarEstrategias() throws IOException {
        String content = storageService.readFile(bucketName, fileName);
        // Converta o CSV em uma lista de objetos
        return Collections.singletonList(new CsvMapper().convertValue(content, Estrategia.class));
    }

    public void adicionarEstrategia(Estrategia estrategia) throws IOException {
        List<Estrategia> estrategias = listarEstrategias();
        estrategias.add(estrategia);
        salvarEstrategias(estrategias);
    }

    public void atualizarEstrategia(int id, Estrategia estrategia) throws IOException {
        List<Estrategia> estrategias = listarEstrategias();
        estrategias = estrategias.stream().map(e -> e.getId() == id ? estrategia : e).collect(Collectors.toList());
        salvarEstrategias(estrategias);
    }

    public void excluirEstrategia(int id) throws IOException {
        List<Estrategia> estrategias = listarEstrategias();
        estrategias.removeIf(e -> e.getId() == id);
        salvarEstrategias(estrategias);
    }

    private void salvarEstrategias(List<Estrategia> estrategias) throws IOException {
        String csvContent = new CsvMapper().writeValueAsString(estrategias);
        storageService.writeFile(bucketName, fileName, csvContent);
    }
}
