package com.alpha.demo.repositories;

import com.alpha.demo.entities.Venda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendaRepository extends JpaRepository<Venda,Long> {
}
