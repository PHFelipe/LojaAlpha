package com.alpha.demo.dto;

import com.alpha.demo.enums.StatusNotaFiscal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotaFiscalDTO {

    private Long id;

    @NotBlank(message = "Número da nota fiscal é obrigatório")
    private String numero;

    @NotNull(message = "Data de emissão é obrigatória")
    private LocalDate dataEmissao;

    @NotNull(message = "Valor total é obrigatório")
    private BigDecimal valorTotal;

    private String chaveAcesso;

    private StatusNotaFiscal status;

    @NotNull(message = "ID da venda é obrigatório")
    private Long idVenda;
}