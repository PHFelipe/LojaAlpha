package com.alpha.demo.repositories;

import com.alpha.demo.entities.ItemVenda;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemVendaRepository extends JpaRepository<ItemVenda,Long> {

    List<ItemVenda> findByVendaId(Long idVenda);
}
