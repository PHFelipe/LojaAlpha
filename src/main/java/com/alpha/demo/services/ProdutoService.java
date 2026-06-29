package com.alpha.demo.services;

import com.alpha.demo.dto.ProdutoDTO;
import com.alpha.demo.entities.Categoria;
import com.alpha.demo.entities.Fornecedor;
import com.alpha.demo.entities.Produto;
import com.alpha.demo.enums.StatusProduto;
import com.alpha.demo.repositories.FornecedorRepository;
import com.alpha.demo.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaService categoriaService;
    private final FornecedorRepository fornecedorRepository;

    public List<ProdutoDTO> getTodosProdutos() {
        return produtoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO buscarProdutoPorId(Long id) {
        return toDTO(buscarEntidade(id));
    }

    public List<ProdutoDTO> buscarProdutoPorCategoria(Long idCategoria) {
        return produtoRepository.findByCategoriaId(idCategoria)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO salvarProduto(ProdutoDTO dto) {
        Produto produto = toEntity(dto);
        verificarValidade(produto);
        return toDTO(produtoRepository.save(produto));
    }

    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO dto) {
        Produto produto = buscarEntidade(id);

        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setValidade(dto.getValidade());
        produto.setStatus(dto.getStatus());
        produto.setCategoria(categoriaService.buscarEntidade(dto.getIdCategoria()));

        if (dto.getIdsFornecedores() != null) {
            List<Fornecedor> fornecedores = fornecedorRepository.findAllById(dto.getIdsFornecedores());
            produto.setFornecedores(fornecedores);
        }

        verificarValidade(produto);
        return toDTO(produtoRepository.save(produto));
    }

    public void deletar(Long id){
        if(!produtoRepository.existsById(id)) {
            throw new EntityNotFoundException("Produto não encontrado com id: " + id);
        }

        produtoRepository.deleteById(id);
    }

    // ---------- Acesso interno ----------

    public Produto buscarEntidade(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com id: " + id));
    }

    // ---------- Regra de negócio ----------

    private void verificarValidade(Produto produto) {
        if (produto.getValidade() != null && produto.getValidade().isBefore(LocalDate.now())) {
            produto.setStatus(StatusProduto.VENCIDO);
        }
    }

    // ---------- Mapeamentos ----------

    public ProdutoDTO toDTO(Produto p) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setId(p.getId());
        dto.setNome(p.getNome());
        dto.setDescricao(p.getDescricao());
        dto.setValidade(p.getValidade());
        dto.setStatus(p.getStatus());

        if (p.getCategoria() != null) {
            dto.setIdCategoria(p.getCategoria().getId());
            dto.setNomeCategoria(p.getCategoria().getNome());
        }

        if (p.getFornecedores() != null) {
            dto.setIdsFornecedores(
                    p.getFornecedores().stream()
                            .map(f -> f.getId())
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    public Produto toEntity(ProdutoDTO dto) {
        Produto p = new Produto();
        p.setNome(dto.getNome());
        p.setDescricao(dto.getDescricao());
        p.setValidade(dto.getValidade());
        p.setStatus(dto.getStatus());

        Categoria categoria = categoriaService.buscarEntidade(dto.getIdCategoria());
        p.setCategoria(categoria);

        if (dto.getIdsFornecedores() != null) {
            List<Fornecedor> fornecedores = fornecedorRepository.findAllById(dto.getIdsFornecedores());
            p.setFornecedores(fornecedores);
        }

        return p;
    }
}