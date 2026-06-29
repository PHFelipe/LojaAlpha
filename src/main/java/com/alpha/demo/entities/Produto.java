package com.alpha.demo.entities;

import com.alpha.demo.enums.StatusProduto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "produtos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;
    @Column(nullable = false)
    private String descricao;
    @Column
    private LocalDate validade;
    @Column
    private StatusProduto status;
    @ManyToOne
    @JoinColumn(name = "id_categoria")
    private Categoria categoria;

    @OneToMany(mappedBy = "produto")
    private List<ItemVenda> itens = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "produto_fornecedor",
                joinColumns = @JoinColumn(name = "id_produto"),
                inverseJoinColumns = @JoinColumn(name = "id_fornecedor")
    )
    private List<Fornecedor> fornecedores = new ArrayList<>();
}
