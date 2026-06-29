package com.alpha.demo.services;

import com.alpha.demo.dto.CategoriaDTO;
import com.alpha.demo.entities.Categoria;
import com.alpha.demo.repositories.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository repository;

    public List<CategoriaDTO> getCategorias() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO buscarCategoriaPorId(Long id) {
        Categoria categoria = buscarEntidade(id);
        return toDTO(categoria);
    }

    public CategoriaDTO buscarCategoriaPorNome(String nome) {
        Categoria categoria = repository.findByNomeIgnoreCase(nome)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada: " + nome));
        return toDTO(categoria);
    }

    public CategoriaDTO criarCategoria(CategoriaDTO dto) {
        if (repository.findByNomeIgnoreCase(dto.getNome()).isPresent()) {
            throw new RuntimeException("Categoria já cadastrada");
        }
        return toDTO(repository.save(toEntity(dto)));
    }

    public CategoriaDTO atualizarCategoria(Long id, CategoriaDTO dto) {
        Categoria categoria = buscarEntidade(id);
        categoria.setNome(dto.getNome());
        return toDTO(repository.save(categoria));
    }

    // ---------- Acesso interno (usado por outros services) ----------

    public Categoria buscarEntidade(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada com id: " + id));
    }

    // ---------- Mapeamentos ----------

    public CategoriaDTO toDTO(Categoria c) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(c.getId());
        dto.setNome(c.getNome());
        return dto;
    }

    private Categoria toEntity(CategoriaDTO dto) {
        Categoria c = new Categoria();
        c.setNome(dto.getNome());
        return c;
    }
}