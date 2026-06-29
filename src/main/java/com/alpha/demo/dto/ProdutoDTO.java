package com.alpha.demo.dto;

import com.alpha.demo.enums.StatusProduto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {

    private Long id;

    @NotBlank(message = "Nome do produto é obrigatório")
    private String nome;

    @NotBlank(message = "Descrição do produto é obrigatória")
    private String descricao;

    private LocalDate validade;

    private StatusProduto status;

    @NotNull(message = "ID da categoria é obrigatório")
    private Long idCategoria;

    // Somente leitura — preenchido nas respostas
    private String nomeCategoria;

    private List<Long> idsFornecedores;
}