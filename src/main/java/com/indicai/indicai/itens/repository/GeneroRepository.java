package com.indicai.indicai.itens.repository;

import com.indicai.indicai.itens.entity.Genero;
import com.indicai.indicai.itens.entity.Item;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GeneroRepository extends JpaRepository<Genero, Long> {

    @Query("SELECT a FROM Avaliacao a WHERE a.item.id = ?1 AND TYPE(a.item) = ?2")
    List<Genero> findByItemIdAndItemType(Long itemId, Class<? extends Item> itemType);
  
}