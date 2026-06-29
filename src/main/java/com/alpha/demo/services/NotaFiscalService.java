package com.alpha.demo.services;

import com.alpha.demo.dto.NotaFiscalDTO;
import com.alpha.demo.entities.NotaFiscal;
import com.alpha.demo.entities.Venda;
import com.alpha.demo.repositories.NotaFiscalRepository;
import com.alpha.demo.repositories.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotaFiscalService {

    private final NotaFiscalRepository notaFiscalRepository;
    private final VendaRepository vendaRepository;

    public NotaFiscalDTO buscarPorId(Long id) {
        NotaFiscal nf = notaFiscalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota Fiscal não encontrada com id: " + id));
        return toDTO(nf);
    }

    public NotaFiscalDTO atualizar(Long id, NotaFiscalDTO dto) {
        NotaFiscal nf = notaFiscalRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nota Fiscal não encontrada com id: " + id));

        nf.setNumero(dto.getNumero());
        nf.setDataEmissao(dto.getDataEmissao());
        nf.setValorTotal(dto.getValorTotal());
        nf.setChaveAcesso(dto.getChaveAcesso());
        nf.setStatus(dto.getStatus());

        Venda venda = vendaRepository.findById(dto.getIdVenda())
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com id: " + dto.getIdVenda()));
        nf.setVenda(venda);

        return toDTO(notaFiscalRepository.save(nf));
    }

    // ---------- Mapeamentos ----------

    public NotaFiscalDTO toDTO(NotaFiscal nf) {
        NotaFiscalDTO dto = new NotaFiscalDTO();
        dto.setId(nf.getId());
        dto.setNumero(nf.getNumero());
        dto.setDataEmissao(nf.getDataEmissao());
        dto.setValorTotal(nf.getValorTotal());
        dto.setChaveAcesso(nf.getChaveAcesso());
        dto.setStatus(nf.getStatus());
        dto.setIdVenda(nf.getVenda() != null ? nf.getVenda().getId() : null);
        return dto;
    }

    private NotaFiscal toEntity(NotaFiscalDTO dto) {
        NotaFiscal nf = new NotaFiscal();
        nf.setNumero(dto.getNumero());
        nf.setDataEmissao(dto.getDataEmissao());
        nf.setValorTotal(dto.getValorTotal());
        nf.setChaveAcesso(dto.getChaveAcesso());
        nf.setStatus(dto.getStatus());

        Venda venda = vendaRepository.findById(dto.getIdVenda())
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com id: " + dto.getIdVenda()));
        nf.setVenda(venda);

        return nf;
    }
}