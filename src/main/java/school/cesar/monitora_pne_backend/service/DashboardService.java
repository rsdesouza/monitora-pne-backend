package school.cesar.monitora_pne_backend.service;

import org.springframework.stereotype.Service;
import school.cesar.monitora_pne_backend.model.Estrategia;
import school.cesar.monitora_pne_backend.model.PlanoAcao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    private static final DateTimeFormatter CSV_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, CSV_DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new RuntimeException("Erro ao analisar a data: " + date, e);
        }
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
                    .filter(e -> e.getStatus() == Estrategia.Status.CONCLUIDO)
                    .count();

            // Estratégias descontinuadas
            long estrategiasDescontinuadas = estrategias.stream()
                    .filter(e -> e.getStatus() == Estrategia.Status.ATRASADO)
                    .count();

            // Estratégias não concluídas
            long estrategiasNaoConcluidas = totalEstrategias - estrategiasConcluidas - estrategiasDescontinuadas;

            // Progresso
            double progresso = (double) estrategiasConcluidas / totalEstrategias * 100;

            // Dias faltantes
            long diasFaltantes = planosAcao.stream()
                    .filter(plano -> plano.getDataFim() != null)
                    .mapToLong(plano -> ChronoUnit.DAYS.between(LocalDate.now(), parseDate(plano.getDataFim())))
                    .filter(dias -> dias > 0)
                    .min()
                    .orElse(0);

            // Total de planos por status
            long planosConcluidos = planosAcao.stream()
                    .filter(plano -> plano.getStatus() == PlanoAcao.Status.CONCLUIDO)
                    .count();

            long planosEmAndamento = planosAcao.stream()
                    .filter(plano -> plano.getStatus() == PlanoAcao.Status.EM_ANDAMENTO)
                    .count();

            long planosDescontinuados = planosAcao.stream()
                    .filter(plano -> plano.getStatus() == PlanoAcao.Status.DESCONTINUADO)
                    .count();

            // Resultados
            return Map.of(
                    "progresso", progresso,
                    "diasFaltantes", diasFaltantes,
                    "totalEstrategias", totalEstrategias,
                    "estrategiasDescontinuadas", estrategiasDescontinuadas,
                    "estrategiasConcluidas", estrategiasConcluidas,
                    "estrategiasNaoConcluidas", estrategiasNaoConcluidas,
                    "planosConcluidos", planosConcluidos,
                    "planosEmAndamento", planosEmAndamento,
                    "planosDescontinuados", planosDescontinuados
            );

        } catch (Exception e) {
            throw new RuntimeException("Erro ao calcular as métricas do dashboard", e);
        }
    }
}
