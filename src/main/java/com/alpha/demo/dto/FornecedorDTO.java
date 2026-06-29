package com.alpha.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FornecedorDTO {

    private Long id;

    @NotBlank(message = "Nome fantasia é obrigatório")
    private String nomeFantasia;

    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;

    private String cnpj;
}