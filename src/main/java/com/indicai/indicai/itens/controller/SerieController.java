package com.indicai.indicai.itens.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
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

import com.indicai.indicai.itens.entity.Serie;
import com.indicai.indicai.itens.repository.SerieRepository;

@RestController
@RequestMapping(value = "/api/series")
public class SerieController {

  @Autowired
  private SerieRepository repository;

  public SerieController() {
  }

  @GetMapping
  public List<Serie> getSeries() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public Optional<Serie> getSerie(@PathVariable long id) {
    Optional<Serie> opt = repository.findById(id);

    if (opt.isPresent()) {
      return opt;
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao buscar serie com id " + id);
  }

  @PostMapping
  // @PreAuthorize("hasRole('ADMIN')")
  public Serie postSerie(@RequestBody Serie serie) {
    return repository.save(serie);
  }

  @PutMapping("/{serieId}")
  // @PreAuthorize("hasRole('ADMIN')")
  public Serie updateSerie(@RequestBody Serie serie, @PathVariable(value = "serieId") long serieId) {
    Optional<Serie> optSerie = repository.findById(serieId);
    if (optSerie.isPresent()) {
      Serie serieExistente = optSerie.get();
      if (serieExistente.getId() == serieId) {
        // Atualiza as propriedades da série existente com as da série fornecida
        serieExistente.setTitulo(serie.getTitulo());
        serieExistente.setAnoLancamento(serie.getAnoLancamento());
        serieExistente.setPais(serie.getPais());
        serieExistente.setUrlCapa(serie.getUrlCapa());
        serieExistente.setDiretor(serie.getDiretor());
        serieExistente.setElencoPrincipal(serie.getElencoPrincipal());
        serieExistente.setNumeroTemporadas(serie.getNumeroTemporadas());
        serieExistente.setGenero(serie.getGenero());

        return repository.save(serieExistente); // Salva a série existente com as alterações
      } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "O ID da série fornecido não corresponde ao ID na URL.");
      }
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Série com ID " + serieId + " não encontrada.");
    }
  }

  @DeleteMapping(value = "/{id}")
  // @PreAuthorize("hasRole('ADMIN')")
  public void deleteSerie(@PathVariable long id) {
    if (repository.findById(id) == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "O servidor não encontrou nada que corresponda ao request.");
    }
    repository.deleteById(id);
  }

  @RequestMapping(params = "genero", method = RequestMethod.GET)
  public ResponseEntity<List<Serie>> getByGenero(@RequestParam("genero") String genero) {
    List<Serie> series = repository.findByGeneroNameStartingWith(genero);
    if (series.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao buscar series com o genêro " + genero);
    }
    return ResponseEntity.ok(series);
  }

}