package com.alpha.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemVendaDTO {

    private Long id;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser ao menos 1")
    private Integer quantidade;

    @NotNull(message = "Valor unitário é obrigatório")
    private BigDecimal valorUnitario;

    @NotNull(message = "ID do produto é obrigatório")
    private Long idProduto;

    private Long idVenda;
}