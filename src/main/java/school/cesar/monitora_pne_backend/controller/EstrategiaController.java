package school.cesar.monitora_pne_backend.controller;

import org.springframework.web.bind.annotation.*;
import school.cesar.monitora_pne_backend.model.Estrategia;
import school.cesar.monitora_pne_backend.service.EstrategiaService;

import java.util.List;

@RestController
@RequestMapping("/api/estrategias")
public class EstrategiaController {

    private final EstrategiaService service;

    public EstrategiaController(EstrategiaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Estrategia> getAll() {
        return service.findAll();
    }

    @PostMapping
    public Estrategia create(@RequestBody Estrategia estrategia) {
        return service.save(estrategia);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
