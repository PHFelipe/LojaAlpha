package com.alpha.demo.entities;

import com.alpha.demo.enums.StatusNotaFiscal;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "notas_fiscais")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotaFiscal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private LocalDate dataEmissao;
    @Column(nullable = false)
    private BigDecimal valorTotal;
    @Column(nullable = true)
    private String chaveAcesso;
    @Enumerated(EnumType.STRING)
    private StatusNotaFiscal status;
    @OneToOne
    @JoinColumn(name = "id_venda", nullable= false, unique = true)
    private Venda venda;
}
