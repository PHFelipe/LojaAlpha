package com.alpha.demo.controllers;

import com.alpha.demo.dto.ItemVendaDTO;
import com.alpha.demo.services.ItemVendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-venda")
@RequiredArgsConstructor
public class ItemVendaController {

    private final ItemVendaService itemVendaService;

    // Endpoint extra útil: buscar itens por venda
    @GetMapping("/venda/{idVenda}")
    public ResponseEntity<List<ItemVendaDTO>> listarPorVenda(@PathVariable Long idVenda) {
        return ResponseEntity.ok(itemVendaService.listarPorVenda(idVenda));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemVendaDTO> atualizar(@PathVariable Long id,
                                                  @Valid @RequestBody ItemVendaDTO dto) {
        return ResponseEntity.ok(itemVendaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarItemVenda(@PathVariable Long id) {
        itemVendaService.deletarItemVenda(id);
        return ResponseEntity.noContent().build();
    }
}