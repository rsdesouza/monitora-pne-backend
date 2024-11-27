package school.cesar.monitora_pne_backend.service;

import org.springframework.stereotype.Service;
import school.cesar.monitora_pne_backend.model.Estrategia;
import school.cesar.monitora_pne_backend.model.PlanoAcao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    private final EstrategiaService estrategiaService;
    private final PlanoAcaoService planoAcaoService;

    public DashboardService(EstrategiaService estrategiaService, PlanoAcaoService planoAcaoService) {
        this.estrategiaService = estrategiaService;
        this.planoAcaoService = planoAcaoService;
    }

    public Map<String, Object> calculateMetrics() {
        try {
            // Dados
            List<Estrategia> estrategias = estrategiaService.listarEstrategias();
            List<PlanoAcao> planosAcao = planoAcaoService.listarPlanos();

            // Total de Estratégias
            int totalEstrategias = estrategias.size();

            // Estratégias concluídas
            long estrategiasConcluidas = estrategias.stream()
                    .filter(e -> "CONCLUIDO".equalsIgnoreCase(e.getStatus().toString()))
                    .count();

            // Estratégias descontinuadas
            long estrategiasDescontinuadas = estrategias.stream()
                    .filter(e -> "DESCONTINUADO".equalsIgnoreCase(e.getStatus().toString()))
                    .count();

            // Estratégias não concluídas
            long estrategiasNaoConcluidas = totalEstrategias - estrategiasConcluidas - estrategiasDescontinuadas;

            // Progresso
            double progresso = (double) estrategiasConcluidas / totalEstrategias * 100;

            // Dias faltantes
            long diasFaltantes = planosAcao.stream()
                    .filter(plano -> plano.getDataFim() != null)
                    .mapToLong(plano -> ChronoUnit.DAYS.between(LocalDate.now(), LocalDate.parse(plano.getDataFim())))
                    .filter(dias -> dias > 0)
                    .min()
                    .orElse(0);

            // Resultados
            return Map.of(
                    "progresso", progresso,
                    "diasFaltantes", diasFaltantes,
                    "totalEstrategias", totalEstrategias,
                    "estrategiasDescontinuadas", estrategiasDescontinuadas,
                    "estrategiasConcluidas", estrategiasConcluidas,
                    "estrategiasNaoConcluidas", estrategiasNaoConcluidas
            );

        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular as métricas do dashboard", e);
        }
    }
}
