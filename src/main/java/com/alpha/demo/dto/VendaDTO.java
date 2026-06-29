package com.alpha.demo.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendaDTO {

    private Long id;

    @NotNull(message = "Data da venda é obrigatória")
    private Date dataVenda;

    // Somente leitura — preenchido nas respostas
    private List<ItemVendaDTO> itens;

    private NotaFiscalDTO notaFiscal;
}