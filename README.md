# Código Indicai - Back End

Este repositório contém o código do backend para o projeto Indicai. O Indicai é o projeto final da matéria Prática Profissional em Análise e Desenvolvimento de Sistemas. O sistema foi concebido e desenvolvido para permitir a indicação e avaliação de séries, filmes e livros. Os usuários podem avaliar esses itens, enquanto o administrador do sistema pode incluí-los.

# Tecnologias utilizadas:
 * Java
 * Springboot
 * Maven
 * Banco de dados SQL Postgres
 * Docker

Link e instruções para utilização da API:

# URL: https://indicai.onrender.com/api

**Sempre fique dando refresh na url acima, pois o render derruba rapido**

# Avaliação

| Método | URL | Ação |
| --- | --- | --- |
| GET | /avaliacoes | Devolve todas as avaliações |
| GET | /avaliacoes/{id} | Devolve a avaliação pelo ID |
| POST | /avaliacoes | Insere uma nova avaliação |
| DELETE | /avaliacoes/{id} | Deleta uma avaliação |
| PUT | /avaliacoes/{id} | Altera uma avaliação pelo ID |
| GET | /avaliacoes/{tipoItem}/{itemId} | Devolve a avaliação de um item pelo id dele (EX: filmes/1) |
| GET | /avaliacoes/usuario/{idDoUsuario} | Devolve todas as avaliações feitas pelo usuário |

# Item (filmes/series/livros)

| Método | URL | Ação |
| --- | --- | --- |
| GET | /{item} | Devolve tudo daquele item |
| GET | /{item}/{id} | Devolve um item pelo id |
| POST | /{item} | Insere uma novo item |
| DELETE | /{item}/{id} | Deleta um item |
| PUT | /{item}/{id} | Altera um item pelo ID |


**Lembrando:

- Qualquer um consegue avaliar um visualizar os itens
- Apenas o ADMIN consegue editar, deletar e incluir um novo item

# Usuário

| Método | URL | Ação |
| --- | --- | --- |
| GET | /usuarios | Devolve todos os usuários |
| GET | /usuarios/{id} | Devolve um usuário pelo id |
| POST | /usuarios | Insere uma novo usuário |
| GET | /usuarios/username/{username} | Devolve um usuário pelo username |

**Lembrando:

- Qualquer um consegue se cadastrar
- O usuário só consegue puxar os dados dele mesmo
- O admin consegue puxar os dados de qualquer um

# Autenticador

| Método | URL | Ação |
| --- | --- | --- |
| POST | /auth | Devolve no corpo o token de autenticação |

# Exemplos de JSON

### Filme

```json
{
    "titulo": "O Poderoso Chefão",
    "pais": "Estados Unidos",
    "anoLancamento": 1972,
    "urlCapa": "https://www.europanet.com.br/upload/id_produto/107___/107366g.jpg",
    "avaliacoes": null,
    "diretor": "Francis Ford Coppola",
    "elencoPrincipal": "Marlon Brando, Al Pacino, James Caan"
}
```

### Serie

```json
{
   "titulo": "Breaking Bad",
   "pais": "Estados Unidos" ,
   "anoLancamento": 2008,
   "urlCapa": "https://upload.wikimedia.org/wikipedia/pt/e/e1/reaking_bad_5_temporada_%28parte_1%29_poster.jpg",
   "diretor": "Vince Gilligan",
   "elencoPrincipal": "Bryan Cranston, Aaron Paul, Anna Gunn",
   "numeroTemporadas": 5
}
```

### Livro

```json
{
   "titulo": "1984",
   "pais": "Reino Unido",
   "anoLancamento": 1949,
   "urlCapa":"https://m.media-amazon.com/images/I/61HtBosDhwL._AC_UF1000,1000_QL80_.jpg",
   "autores":"George Orwell",
   "editora": "Secker & Warburg"
}
```

### Avaliação

```json
{
        "nota": 5,
        "comentario": "Filme Mediano",
        "usuario": {"id": 1},
        "item": {"id": 2}
}
```
