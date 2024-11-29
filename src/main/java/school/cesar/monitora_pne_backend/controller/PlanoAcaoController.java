package school.cesar.monitora_pne_backend.controller;

import org.springframework.web.bind.annotation.*;
import school.cesar.monitora_pne_backend.model.PlanoAcao;
import school.cesar.monitora_pne_backend.service.PlanoAcaoService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/planos")
public class PlanoAcaoController {

    private final PlanoAcaoService planoAcaoService;

    public PlanoAcaoController(PlanoAcaoService planoAcaoService) {
        this.planoAcaoService = planoAcaoService;
    }

    @GetMapping
    public List<PlanoAcao> listarPlanos() throws IOException {
        return planoAcaoService.listarPlanos();
    }

    @PostMapping
    public void adicionarPlano(@RequestBody PlanoAcao plano) throws IOException {
        planoAcaoService.adicionarPlano(plano);
    }

    @PutMapping("/{index}")
    public void atualizarPlano(@PathVariable Long index, @RequestBody PlanoAcao plano) throws IOException {
        planoAcaoService.atualizarPlano(index, plano);
    }

    @DeleteMapping("/{index}")
    public void excluirPlano(@PathVariable Long index) throws IOException {
        planoAcaoService.excluirPlano(index);
    }

    @GetMapping("/resumo")
    public Map<String, Long> obterResumoPlanosAcao() throws IOException {
        List<PlanoAcao> planos = planoAcaoService.listarPlanos();

        long total = planos.size();
        long concluidos = planos.stream()
                .filter(p -> "CONCLUIDO".equalsIgnoreCase(String.valueOf(p.getStatus())))
                .count();
        long emAndamento = planos.stream()
                .filter(p -> "EM_ANDAMENTO".equalsIgnoreCase(String.valueOf(p.getStatus())))
                .count();
        long atrasados = planos.stream()
                .filter(p -> "ATRASADO".equalsIgnoreCase(String.valueOf(p.getStatus())))
                .count();

        return Map.of(
                "total", total,
                "concluidos", concluidos,
                "emAndamento", emAndamento,
                "atrasados", atrasados
        );
    }

    @GetMapping("/indicadores")
    public Map<String, List<PlanoAcao>> obterPlanosPorIndicador() throws IOException {
        List<PlanoAcao> planos = planoAcaoService.listarPlanos();

        // Filtra planos sem indicador ou define um valor padrão
        return planos.stream()
                .filter(plano -> plano.getIndicador() != null && !plano.getIndicador().isBlank()) // Filtra nulos ou valores em branco
                .collect(Collectors.groupingBy(plano -> plano.getIndicador() != null ? plano.getIndicador() : "SEM_INDICADOR"));
    }

}