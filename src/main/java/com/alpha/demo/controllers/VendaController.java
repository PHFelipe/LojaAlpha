package com.alpha.demo.controllers;

import com.alpha.demo.dto.VendaDTO;
import com.alpha.demo.services.VendaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {

    private final VendaService vendaService;

    @GetMapping
    public ResponseEntity<List<VendaDTO>> listarTodas() {
        return ResponseEntity.ok(vendaService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VendaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(vendaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<VendaDTO> criar(@Valid @RequestBody VendaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vendaService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VendaDTO> atualizar(@PathVariable Long id,
                                              @Valid @RequestBody VendaDTO dto) {
        return ResponseEntity.ok(vendaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        vendaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}