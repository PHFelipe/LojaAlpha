package com.alpha.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fornecedores")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeFantasia;
    @Column(nullable = false)
    private String endereco;
    @Column(nullable = true)
    private String cnpj;

    @ManyToMany(mappedBy = "fornecedores")
    private List<Produto> produtos = new ArrayList<>();

}
