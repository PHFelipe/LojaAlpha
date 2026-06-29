package com.alpha.demo.controllers;

import com.alpha.demo.dto.ProdutoDTO;
import com.alpha.demo.services.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> getTodosProdutos() {
        return ResponseEntity.ok(produtoService.getTodosProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.buscarProdutoPorId(id));
    }

    @GetMapping("/categoria/{idCategoria}")
    public ResponseEntity<List<ProdutoDTO>> buscarPorCategoria(@PathVariable Long idCategoria) {
        return ResponseEntity.ok(produtoService.buscarProdutoPorCategoria(idCategoria));
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> salvar(@Valid @RequestBody ProdutoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produtoService.salvarProduto(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id,
                                                @Valid @RequestBody ProdutoDTO dto) {
        return ResponseEntity.ok(produtoService.atualizarProduto(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}