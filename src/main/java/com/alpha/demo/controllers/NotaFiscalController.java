package com.alpha.demo.controllers;

import com.alpha.demo.dto.NotaFiscalDTO;
import com.alpha.demo.services.NotaFiscalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notas-fiscais")
@RequiredArgsConstructor
public class NotaFiscalController {

    private final NotaFiscalService notaFiscalService;

    @GetMapping("/{id}")
    public ResponseEntity<NotaFiscalDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(notaFiscalService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotaFiscalDTO> atualizar(@PathVariable Long id,
                                                   @Valid @RequestBody NotaFiscalDTO dto) {
        return ResponseEntity.ok(notaFiscalService.atualizar(id, dto));
    }

}