package school.cesar.monitora_pne_backend.service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;
import school.cesar.monitora_pne_backend.model.PlanoAcao;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanoAcaoService {

    private final GCPStorageService storageService;
    private final String bucketName = "monitora_pne_15_streamlit";
    private final String fileName = "planoacao.csv";

    public PlanoAcaoService(GCPStorageService storageService) {
        this.storageService = storageService;
    }

    public List<PlanoAcao> listarPlanos() throws IOException {
        String content = storageService.readFile(bucketName, fileName);

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("index")
                .addColumn("indicador")
                .addColumn("estrategia")
                .addColumn("planoDeAcao")
                .addColumn("observacao")
                .addColumn("status")
                .addColumn("dataInicio")
                .addColumn("dataFim")
                .build()
                .withHeader()
                .withColumnSeparator(';');

        MappingIterator<PlanoAcao> it = csvMapper.readerFor(PlanoAcao.class)
                .with(schema)
                .readValues(content);

        return it.readAll();
    }

    public void adicionarPlano(PlanoAcao plano) throws IOException {
        List<PlanoAcao> planos = listarPlanos();
        planos.add(plano);
        salvarPlanos(planos);
    }

    public void atualizarPlano(Long index, PlanoAcao planoAtualizado) throws IOException {
        List<PlanoAcao> planos = listarPlanos();
        planos = planos.stream()
                .map(plano -> plano.getIndex().equals(index) ? planoAtualizado : plano)
                .collect(Collectors.toList());
        salvarPlanos(planos);
    }

    public void excluirPlano(Long index) throws IOException {
        List<PlanoAcao> planos = listarPlanos();
        planos.removeIf(plano -> plano.getIndex().equals(index));
        salvarPlanos(planos);
    }

    private void salvarPlanos(List<PlanoAcao> planos) throws IOException {
        CsvMapper csvMapper = new CsvMapper();
        CsvSchema schema = CsvSchema.builder()
                .addColumn("index")
                .addColumn("indicador")
                .addColumn("estrategia")
                .addColumn("planoDeAcao")
                .addColumn("observacao")
                .addColumn("status")
                .addColumn("dataInicio")
                .addColumn("dataFim")
                .build()
                .withHeader()
                .withColumnSeparator(';');

        String csvContent = csvMapper.writer(schema).writeValueAsString(planos);
        storageService.writeFile(bucketName, fileName, csvContent);
    }
}
