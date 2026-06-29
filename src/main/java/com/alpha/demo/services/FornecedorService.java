package com.alpha.demo.services;

import com.alpha.demo.dto.FornecedorDTO;
import com.alpha.demo.entities.Fornecedor;
import com.alpha.demo.repositories.FornecedorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public List<FornecedorDTO> listarTodos() {
        return fornecedorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public FornecedorDTO buscarPorId(Long id) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com id: " + id));
        return toDTO(fornecedor);
    }

    public FornecedorDTO criar(FornecedorDTO dto) {
        Fornecedor fornecedor = toEntity(dto);
        return toDTO(fornecedorRepository.save(fornecedor));
    }

    public FornecedorDTO atualizar(Long id, FornecedorDTO dto) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Fornecedor não encontrado com id: " + id));

        fornecedor.setNomeFantasia(dto.getNomeFantasia());
        fornecedor.setEndereco(dto.getEndereco());
        fornecedor.setCnpj(dto.getCnpj());

        return toDTO(fornecedorRepository.save(fornecedor));
    }

    public void deletar(Long id) {
        if (!fornecedorRepository.existsById(id)) {
            throw new EntityNotFoundException("Fornecedor não encontrado com id: " + id);
        }
        fornecedorRepository.deleteById(id);
    }

    // ---------- Mapeamentos ----------

    private FornecedorDTO toDTO(Fornecedor f) {
        FornecedorDTO dto = new FornecedorDTO();
        dto.setId(f.getId());
        dto.setNomeFantasia(f.getNomeFantasia());
        dto.setEndereco(f.getEndereco());
        dto.setCnpj(f.getCnpj());
        return dto;
    }

    private Fornecedor toEntity(FornecedorDTO dto) {
        Fornecedor f = new Fornecedor();
        f.setNomeFantasia(dto.getNomeFantasia());
        f.setEndereco(dto.getEndereco());
        f.setCnpj(dto.getCnpj());
        return f;
    }
}