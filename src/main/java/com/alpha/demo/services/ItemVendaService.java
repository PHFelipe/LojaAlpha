package com.alpha.demo.services;

import com.alpha.demo.dto.ItemVendaDTO;
import com.alpha.demo.entities.ItemVenda;
import com.alpha.demo.entities.Produto;
import com.alpha.demo.entities.Venda;
import com.alpha.demo.repositories.ItemVendaRepository;
import com.alpha.demo.repositories.ProdutoRepository;
import com.alpha.demo.repositories.VendaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemVendaService {

    private final ItemVendaRepository itemVendaRepository;
    private final VendaRepository vendaRepository;
    private final ProdutoRepository produtoRepository;

    public List<ItemVendaDTO> listarPorVenda(Long idVenda) {
        return itemVendaRepository.findByVendaId(idVenda)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ItemVendaDTO atualizar(Long id, ItemVendaDTO dto) {
        ItemVenda item = itemVendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ItemVenda não encontrado com id: " + id));

        item.setQuantidade(dto.getQuantidade());
        item.setValorUnitario(dto.getValorUnitario());

        Venda venda = vendaRepository.findById(dto.getIdVenda())
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com id: " + dto.getIdVenda()));
        item.setVenda(venda);

        Produto produto = produtoRepository.findById(dto.getIdProduto())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com id: " + dto.getIdProduto()));
        item.setProduto(produto);

        return toDTO(itemVendaRepository.save(item));
    }

    public void deletarItemVenda(Long id) {
        if (!itemVendaRepository.existsById(id)) {
            throw new EntityNotFoundException("ItemVenda não encontrado com id: " + id);
        }
        itemVendaRepository.deleteById(id);
    }

    // ---------- Mapeamentos ----------

    public ItemVendaDTO toDTO(ItemVenda i) {
        ItemVendaDTO dto = new ItemVendaDTO();
        dto.setId(i.getId());
        dto.setQuantidade(i.getQuantidade());
        dto.setValorUnitario(i.getValorUnitario());
        dto.setIdVenda(i.getVenda() != null ? i.getVenda().getId() : null);
        dto.setIdProduto(i.getProduto() != null ? i.getProduto().getId() : null);
        return dto;
    }

    private ItemVenda toEntity(ItemVendaDTO dto) {
        ItemVenda item = new ItemVenda();
        item.setQuantidade(dto.getQuantidade());
        item.setValorUnitario(dto.getValorUnitario());

        Venda venda = vendaRepository.findById(dto.getIdVenda())
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com id: " + dto.getIdVenda()));
        item.setVenda(venda);

        Produto produto = produtoRepository.findById(dto.getIdProduto())
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com id: " + dto.getIdProduto()));
        item.setProduto(produto);

        return item;
    }
}