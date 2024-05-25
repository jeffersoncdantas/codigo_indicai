package com.indicai.indicai.itens.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.indicai.indicai.itens.entity.Filme;
import com.indicai.indicai.itens.repository.FilmeRepository;

@RestController
@RequestMapping(value = "/api/filmes")
public class FilmeController {

  @Autowired
  private FilmeRepository repository;

  public FilmeController() {
  }

  @GetMapping
  public List<Filme> getFilmes() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public Optional<Filme> getFilme(@PathVariable long id) {
    Optional<Filme> opt = repository.findById(id);

    if (opt.isPresent()) {
      return opt;
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao buscar filme com id " + id);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public Filme postFilme(@RequestBody Filme filme) {
    return repository.save(filme);
  }

  @PutMapping("/{filmeId}")
  @PreAuthorize("hasRole('ADMIN')")
  public Filme updateFilme(@RequestBody Filme filme, @PathVariable(value = "filmeId") long filmeId) {
    Optional<Filme> optFilme = repository.findById(filmeId);
    if (optFilme.isPresent()) {
      Filme filmeExistente = optFilme.get();
      if (filmeExistente.getId() == filmeId) {
        // Atualiza as propriedades do filme existente com as do filme fornecido
        filmeExistente.setTitulo(filme.getTitulo());
        filmeExistente.setAnoLancamento((filme.getAnoLancamento()));
        filmeExistente.setDiretor(filme.getDiretor());
        filmeExistente.setElencoPrincipal(filme.getElencoPrincipal());
        filmeExistente.setPais(filme.getPais());
        filmeExistente.setUrlCapa(filme.getUrlCapa());
        filmeExistente.setGenero(filme.getGenero());

        return repository.save(filmeExistente); // Salva o filme existente com as alterações
      } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "O ID do filme fornecido não corresponde ao ID na URL.");
      }
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Filme com ID " + filmeId + " não encontrado.");
    }
  }

  @DeleteMapping(value = "/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteFilme(@PathVariable long id) {
    if (repository.findById(id) == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "O servidor não encontrou nada que corresponda ao request.");
    }
    repository.deleteById(id);
  }

  @RequestMapping(params = "genero", method = RequestMethod.GET)
  public ResponseEntity<List<Filme>> getByGenero(@RequestParam("genero") String genero) {
    List<Filme> filmes = repository.findByGeneroNameStartingWith(genero);
    if (filmes.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao buscar filmes com o genêro " + genero);
    }
    return ResponseEntity.ok(filmes);
  }

  

}
