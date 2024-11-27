package school.cesar.monitora_pne_backend.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
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
        System.out.println("Conteúdo do CSV:");
        System.out.println(content);

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("index")
                .addColumn("indicador")
                .addColumn("nomeEstrategia")
                .addColumn("mes")
                .addColumn("valor")
                .addColumn("ano")
                .addColumn("status")
                .build()
                .withHeader()
                .withColumnSeparator(';');

        try {
            MappingIterator<Estrategia> it = csvMapper.readerFor(Estrategia.class)
                    .with(schema)
                    .readValues(content);

            List<Estrategia> estrategias = it.readAll();
            System.out.println("Estrategias carregadas: " + estrategias.size());
            return estrategias;

        } catch (Exception e) {
            System.err.println("Erro ao processar o CSV:");
            e.printStackTrace();
            throw e;
        }
    }

    public void adicionarEstrategia(Estrategia estrategia) throws IOException {
        List<Estrategia> estrategias = listarEstrategias();
        estrategias.add(estrategia);
        salvarEstrategias(estrategias);
    }

    public void atualizarEstrategia(int id, Estrategia estrategia) throws IOException {
        List<Estrategia> estrategias = listarEstrategias();
        estrategias = estrategias.stream().map(e -> e.getIndex() == id ? estrategia : e).collect(Collectors.toList());
        salvarEstrategias(estrategias);
    }

    public void excluirEstrategia(int id) throws IOException {
        List<Estrategia> estrategias = listarEstrategias();
        estrategias.removeIf(e -> e.getIndex() == id);
        salvarEstrategias(estrategias);
    }

    private void salvarEstrategias(List<Estrategia> estrategias) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("index")
                .addColumn("indicador")
                .addColumn("nomeEstrategia")
                .addColumn("mes")
                .addColumn("valor")
                .addColumn("ano")
                .addColumn("status")
                .build()
                .withHeader()
                .withColumnSeparator(';'); // Configura o separador como ponto-e-vírgula

        String csvContent = csvMapper.writer(schema).writeValueAsString(estrategias);
        storageService.writeFile(bucketName, fileName, csvContent);
    }

}
