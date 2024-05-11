package com.indicai.indicai.itens.repository;

import com.indicai.indicai.itens.entity.Serie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
  
    List<Serie> findByGeneroNameStartingWith(String name);
}