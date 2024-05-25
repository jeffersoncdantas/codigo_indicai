package com.indicai.indicai.itens.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.indicai.indicai.itens.entity.Genero;
import com.indicai.indicai.itens.repository.GeneroRepository;

@RestController
@RequestMapping(value = "/api/generos")
public class GeneroController {

  @Autowired
  private GeneroRepository repository;

  public GeneroController() {
  }

  @GetMapping
  public List<Genero> getGeneros() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public Optional<Genero> getGenero(@PathVariable long id) {
    Optional<Genero> opt = repository.findById(id);

    if (opt.isPresent()) {
      return opt;
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao buscar genero com id " + id);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public Genero postGenero(@RequestBody Genero genero) {
    return repository.save(genero);
  }

  @PutMapping("/{generoId}")
  @PreAuthorize("hasRole('ADMIN')")
  public Genero updateGenero(@RequestBody Genero genero, @PathVariable(value = "generoId") long generoId) {
    Optional<Genero> optGenero = repository.findById(generoId);
    if (optGenero.isPresent()) {
      Genero generoExistente = optGenero.get();
      if (generoExistente.getId() == generoId) {
        generoExistente.setName(genero.getName());
        return repository.save(generoExistente);
      } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "O ID do genero fornecido não corresponde ao ID na URL.");
      }
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Genero com ID " + generoId + " não encontrado.");
    }
  }

  @DeleteMapping(value = "/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteGenero(@PathVariable long id) {
    if (repository.findById(id) == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "O servidor não encontrou nada que corresponda ao request.");
    }
    repository.deleteById(id);
  }
  
}