package school.cesar.monitora_pne_backend.service;

import org.springframework.stereotype.Service;
import school.cesar.monitora_pne_backend.model.Estrategia;
import school.cesar.monitora_pne_backend.repository.EstrategiaRepository;

import java.util.List;

@Service
public class EstrategiaService {

    private final EstrategiaRepository repository;

    public EstrategiaService(EstrategiaRepository repository) {
        this.repository = repository;
    }

    public List<Estrategia> findAll() {
        return repository.findAll();
    }

    public Estrategia save(Estrategia estrategia) {
        return repository.save(estrategia);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
