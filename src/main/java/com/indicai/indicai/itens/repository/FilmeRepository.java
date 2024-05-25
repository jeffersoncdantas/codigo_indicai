package com.indicai.indicai.itens.repository;

import com.indicai.indicai.itens.entity.Filme;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FilmeRepository extends JpaRepository<Filme, Long> {
    List<Filme> findByGeneroNameStartingWith(String name);

    
  
}