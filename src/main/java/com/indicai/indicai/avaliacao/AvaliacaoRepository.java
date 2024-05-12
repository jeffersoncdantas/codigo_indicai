package com.indicai.indicai.avaliacao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.indicai.indicai.itens.entity.Item;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    @Query("SELECT a FROM Avaliacao a WHERE a.item.id = ?1 AND TYPE(a.item) = ?2")
    List<Avaliacao> findByItemIdAndItemType(Long itemId, Class<? extends Item> itemType);

    @Query("SELECT a FROM Avaliacao a WHERE a.usuario.id = ?1")
    List<Avaliacao> findByUserId(Long userId);
  
}