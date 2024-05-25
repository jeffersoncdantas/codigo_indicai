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

import com.indicai.indicai.itens.entity.Livro;
import com.indicai.indicai.itens.repository.LivroRepository;

@RestController
@RequestMapping(value = "/api/livros")
public class LivroController {

  @Autowired
  private LivroRepository repository;

  public LivroController() {
  }

  @GetMapping
  public List<Livro> getLivros() {
    return repository.findAll();
  }

  @GetMapping("/{id}")
  public Optional<Livro> getLivro(@PathVariable long id) {
    Optional<Livro> opt = repository.findById(id);

    if (opt.isPresent()) {
      return opt;
    }
    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao buscar livro com id " + id);
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public Livro postLivro(@RequestBody Livro livro) {
    return repository.save(livro);
  }

  @PutMapping("/{livroId}")
  @PreAuthorize("hasRole('ADMIN')")
  public Livro updateLivro(@RequestBody Livro livro, @PathVariable(value = "livroId") long livroId) {
    Optional<Livro> optLivro = repository.findById(livroId);
    if (optLivro.isPresent()) {
      Livro livroExistente = optLivro.get();
      if (livroExistente.getId() == livroId) {
        // Atualiza as propriedades do livro existente com as do livro fornecido
        livroExistente.setTitulo(livro.getTitulo());
        livroExistente.setAnoLancamento(livro.getAnoLancamento());
        livroExistente.setPais(livro.getPais());
        livroExistente.setUrlCapa(livro.getUrlCapa());
        livroExistente.setAutores(livro.getAutores());
        livroExistente.setEditora(livro.getEditora());
        livroExistente.setGenero(livro.getGenero());

        return repository.save(livroExistente); // Salva o livro existente com as alterações
      } else {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
            "O ID do livro fornecido não corresponde ao ID na URL.");
      }
    } else {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro com ID " + livroId + " não encontrado.");
    }
  }

  @DeleteMapping(value = "/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public void deleteLivro(@PathVariable long id) {
    if (repository.findById(id) == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND,
          "O servidor não encontrou nada que corresponda ao request.");
    }
    repository.deleteById(id);
  }

  @RequestMapping(params = "genero", method = RequestMethod.GET)
  public ResponseEntity<List<Livro>> getByGenero(@RequestParam("genero") String genero) {
    List<Livro> livros = repository.findByGeneroNameStartingWith(genero);
    if (livros.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao buscar livros com o genêro " + genero);
    }
    return ResponseEntity.ok(livros);
  }

}
