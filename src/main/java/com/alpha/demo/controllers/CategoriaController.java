package com.alpha.demo.controllers;

import com.alpha.demo.dto.CategoriaDTO;
import com.alpha.demo.services.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getCategorias() {
        return ResponseEntity.ok(categoriaService.getCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarCategoriaPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<CategoriaDTO> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(categoriaService.buscarCategoriaPorNome(nome));
    }

    @PostMapping
    public ResponseEntity<CategoriaDTO> criar(@Valid @RequestBody CategoriaDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.criarCategoria(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> atualizar(@PathVariable Long id,
                                                  @Valid @RequestBody CategoriaDTO dto) {
        return ResponseEntity.ok(categoriaService.atualizarCategoria(id, dto));
    }
}