package com.alpha.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vendas")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Venda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "venda",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private NotaFiscal notaFiscal;

    @OneToMany(mappedBy = "venda",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemVenda> itens = new ArrayList<>();

    @Column(nullable = false)
    private Date dataVenda;
}
