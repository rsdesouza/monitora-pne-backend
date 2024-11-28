package school.cesar.monitora_pne_backend.controller;

import org.springframework.web.bind.annotation.*;
import school.cesar.monitora_pne_backend.model.Estrategia;
import school.cesar.monitora_pne_backend.service.EstrategiaService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/estrategias")
public class EstrategiaController {

    private final EstrategiaService estrategiaService;

    public EstrategiaController(EstrategiaService estrategiaService) {
        this.estrategiaService = estrategiaService;
    }

    @GetMapping
    public List<Estrategia> listarEstrategias() throws IOException {
        return estrategiaService.listarEstrategias();
    }

    @GetMapping("/resumo")
    public Map<String, Object> obterResumoEstrategias() throws IOException {
        List<Estrategia> estrategias = estrategiaService.listarEstrategias();

        long total = estrategias.size();
        long concluidas = estrategias.stream()
                .filter(e -> Estrategia.Status.CONCLUIDO.equals(e.getStatus()))
                .count();
        long emAndamento = estrategias.stream()
                .filter(e -> Estrategia.Status.EM_ANDAMENTO.equals(e.getStatus()))
                .count();
        long atrasadas = estrategias.stream()
                .filter(e -> Estrategia.Status.ATRASADO.equals(e.getStatus()))
                .count();
        long descontinuadas = estrategias.stream()
                .filter(e -> Estrategia.Status.DESCONTINUADO.equals(e.getStatus()))
                .count();

        return Map.of(
                "totalEstrategias", total,
                "concluidas", concluidas,
                "emAndamento", emAndamento,
                "atrasadas", atrasadas,
                "descontinuadas", descontinuadas
        );
    }

    @GetMapping("/indicadores")
    public Map<String, List<Estrategia>> obterEstrategiasPorIndicador() throws IOException {
        List<Estrategia> estrategias = estrategiaService.listarEstrategias();
        return estrategias.stream()
                .filter(e -> e.getIndicador() != null && !e.getIndicador().isEmpty()) // Ignora indicadores nulos ou vazios
                .collect(Collectors.groupingBy(Estrategia::getIndicador));
    }

    @PostMapping
    public void adicionarEstrategia(@RequestBody Estrategia estrategia) throws IOException {
        estrategiaService.adicionarEstrategia(estrategia);
    }

    @PutMapping("/{id}")
    public void atualizarEstrategia(@PathVariable Long id, @RequestBody Estrategia estrategia) throws IOException {
        estrategiaService.atualizarEstrategia(id.intValue(), estrategia);
    }

    @DeleteMapping("/{id}")
    public void excluirEstrategia(@PathVariable Long id) throws IOException {
        estrategiaService.excluirEstrategia(id.intValue());
    }
}
