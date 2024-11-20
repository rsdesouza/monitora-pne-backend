package school.cesar.monitora_pne_backend.controller;

import org.springframework.web.bind.annotation.*;
import school.cesar.monitora_pne_backend.model.Estrategia;
import school.cesar.monitora_pne_backend.service.EstrategiaService;

import java.util.List;

import java.io.IOException;

@RestController
@RequestMapping("/api/estrategias")
public class EstrategiaController {

    private final EstrategiaService estrategiaService;

    public EstrategiaController(EstrategiaService estrategiaService) {
        this.estrategiaService = estrategiaService;
    }

    @GetMapping
    public List<Estrategia> listar() throws IOException {
        return estrategiaService.listarEstrategias();
    }

    @PostMapping
    public void adicionar(@RequestBody Estrategia estrategia) throws IOException {
        estrategiaService.adicionarEstrategia(estrategia);
    }

    @PutMapping("/{id}")
    public void atualizar(@PathVariable int id, @RequestBody Estrategia estrategia) throws IOException {
        estrategiaService.atualizarEstrategia(id, estrategia);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable int id) throws IOException {
        estrategiaService.excluirEstrategia(id);
    }
}