package com.indicai.indicai.itens.repository;
import com.indicai.indicai.itens.entity.Livro;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByGeneroNameStartingWith(String name);
  
}