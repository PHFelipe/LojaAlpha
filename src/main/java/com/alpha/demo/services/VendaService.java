package com.alpha.demo.services;

import com.alpha.demo.dto.ProdutoDTO;
import com.alpha.demo.entities.ItemVenda;
import com.alpha.demo.entities.NotaFiscal;
import com.alpha.demo.enums.StatusNotaFiscal;
import com.alpha.demo.repositories.ItemVendaRepository;
import com.alpha.demo.repositories.NotaFiscalRepository;
import com.alpha.demo.repositories.ProdutoRepository;
import com.alpha.demo.repositories.VendaRepository;
import com.alpha.demo.dto.ItemVendaDTO;
import com.alpha.demo.dto.NotaFiscalDTO;
import com.alpha.demo.dto.VendaDTO;
import com.alpha.demo.entities.Venda;
import com.alpha.demo.entities.Produto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final NotaFiscalRepository notaFiscalRepository;
    private final NotaFiscalService notaFiscalService;
    private final ItemVendaService itemVendaService;
    private final ProdutoRepository produtoRepository;
    private final ItemVendaRepository itemVendaRepository;

    public List<VendaDTO> listarTodas() {
        return vendaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public VendaDTO buscarPorId(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com id: " + id));
        return toDTO(venda);
    }

    public VendaDTO criar(VendaDTO dto) {
        Venda venda = new Venda();
        venda.setDataVenda(dto.getDataVenda());

        venda = vendaRepository.save(venda);
        List<ItemVenda> itens = new ArrayList<>();
        BigDecimal valorTotal = BigDecimal.ZERO;

        for(ItemVendaDTO itemDTO : dto.getItens()) {

            Produto produto = produtoRepository.findById(itemDTO.getIdProduto())
                    .orElseThrow(() ->
                            new RuntimeException("Produto não encontrado"));
            ItemVenda item = new ItemVenda();

            item.setQuantidade(itemDTO.getQuantidade());
            item.setValorUnitario(itemDTO.getValorUnitario());
            item.setProduto(produto);
            item.setVenda(venda);

            valorTotal = valorTotal.add(
                    itemDTO.getValorUnitario()
                            .multiply(BigDecimal.valueOf(itemDTO.getQuantidade()))
            );

            itens.add(item);
        }

        itemVendaRepository.saveAll(itens);
        venda.setItens(itens);

        NotaFiscal nota = new NotaFiscal();
        nota.setNumero(UUID.randomUUID().toString().substring(0, 8));
        nota.setChaveAcesso(UUID.randomUUID().toString().substring(0, 8));
        nota.setDataEmissao(LocalDate.now());
        nota.setValorTotal(valorTotal);
        nota.setStatus(StatusNotaFiscal.EMITIDA);
        nota.setVenda(venda);

        nota = notaFiscalRepository.save(nota);

        venda.setNotaFiscal(nota);

        vendaRepository.save(venda);

        return toDTO(venda);
    }

    public VendaDTO atualizar(Long id, VendaDTO dto) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com id: " + id));

        venda.setDataVenda(dto.getDataVenda());
        return toDTO(vendaRepository.save(venda));
    }

    public void deletar(Long id) {
        Venda venda = vendaRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Venda não encontrada com id: " + id));

        vendaRepository.delete(venda);
    }

    // ---------- Mapeamentos ----------

    public VendaDTO toDTO(Venda v) {
        VendaDTO dto = new VendaDTO();
        dto.setId(v.getId());
        dto.setDataVenda(v.getDataVenda());

        if (v.getItens() != null) {
            List<ItemVendaDTO> itensDTO = v.getItens()
                    .stream()
                    .map(itemVendaService::toDTO)
                    .collect(Collectors.toList());
            dto.setItens(itensDTO);
        }

        if (v.getNotaFiscal() != null) {
            dto.setNotaFiscal(notaFiscalService.toDTO(v.getNotaFiscal()));
        }

        return dto;
    }

    public Venda buscarEntidade(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venda não encontrada com id: " + id));
    }
}