Vamos criar um guia passo a passo para configurar um projeto Spring Boot e criar um CRUD básico para uma entidade de "Curso" usando PostgreSQL.

### Passo 1: Configurar o Ambiente

1. **Instalar Java Development Kit (JDK)**:
    - Baixe e instale o JDK (versão 11 ou superior) do site oficial da Oracle ou do OpenJDK.

2. **Instalar Maven**:
    - Baixe e instale o Apache Maven do site oficial.

3. **Instalar PostgreSQL**:
    - Baixe e instale o PostgreSQL do site oficial. Lembre-se de anotar o nome de usuário e senha do banco de dados.

### Passo 2: Criar um Projeto Spring Boot

1. **Usar o Spring Initializr**:
    - Acesse [Spring Initializr](https://start.spring.io/).
    - Preencha os campos da seguinte forma:
        - **Project**: Maven Project
        - **Language**: Java
        - **Spring Boot**: versão estável mais recente
        - **Project Metadata**:
            - Group: com.exemplo
            - Artifact: curso-crud
            - Name: curso-crud
            - Package Name: com.exemplo.curso
            - Packaging: Jar
            - Java: 11 ou superior
    - Adicione as seguintes dependências:
        - Spring Web
        - Spring Data JPA
        - PostgreSQL Driver
        - Spring Boot DevTools (opcional)
    - Clique em "Generate" para baixar o projeto.

2. **Descompactar o projeto**:
    - Descompacte o arquivo gerado e abra o projeto em sua IDE favorita (IntelliJ IDEA, Eclipse, VSCode, etc.).

### Passo 3: Configurar o Banco de Dados

1. **Criar um banco de dados no PostgreSQL**:
    - Abra o pgAdmin ou o terminal e crie um banco de dados chamado `curso_db`:
      ```sql
      CREATE DATABASE curso_db;
      ```

2. **Configurar o application.properties**:
    - No projeto Spring Boot, abra o arquivo `src/main/resources/application.properties` e adicione as configurações do PostgreSQL:
      ```properties
      spring.datasource.url=jdbc:postgresql://localhost:5432/curso_db
      spring.datasource.username=seu_usuario
      spring.datasource.password=sua_senha
      spring.jpa.hibernate.ddl-auto=update
      spring.jpa.show-sql=true
      spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
      ```

### Passo 4: Criar a Entidade "Curso"

1. **Criar a classe de entidade**:
    - No pacote `com.exemplo.curso.model`, crie uma classe `Curso`:
      ```java
      package com.exemplo.curso.model;
 
      import javax.persistence.Entity;
      import javax.persistence.GeneratedValue;
      import javax.persistence.GenerationType;
      import javax.persistence.Id;
 
      @Entity
      public class Curso {
          @Id
          @GeneratedValue(strategy = GenerationType.IDENTITY)
          private Long id;
          private String nome;
          private String descricao;
 
          // Getters e Setters
          public Long getId() {
              return id;
          }
 
          public void setId(Long id) {
              this.id = id;
          }
 
          public String getNome() {
              return nome;
          }
 
          public void setNome(String nome) {
              this.nome = nome;
          }
 
          public String getDescricao() {
              return descricao;
          }
 
          public void setDescricao(String descricao) {
              this.descricao = descricao;
          }
      }
      ```

### Passo 5: Criar o Repositório

1. **Criar a interface de repositório**:
    - No pacote `com.exemplo.curso.repository`, crie uma interface `CursoRepository`:
      ```java
      package com.exemplo.curso.repository;
 
      import org.springframework.data.jpa.repository.JpaRepository;
      import com.exemplo.curso.model.Curso;
 
      public interface CursoRepository extends JpaRepository<Curso, Long> {
      }
      ```

### Passo 6: Criar o Service

1. **Criar a classe de serviço**:
    - No pacote `com.exemplo.curso.service`, crie uma classe `CursoService`:
      ```java
      package com.exemplo.curso.service;
 
      import com.exemplo.curso.model.Curso;
      import com.exemplo.curso.repository.CursoRepository;
      import org.springframework.beans.factory.annotation.Autowired;
      import org.springframework.stereotype.Service;
 
      import java.util.List;
      import java.util.Optional;
 
      @Service
      public class CursoService {
          @Autowired
          private CursoRepository cursoRepository;
 
          public List<Curso> findAll() {
              return cursoRepository.findAll();
          }
 
          public Optional<Curso> findById(Long id) {
              return cursoRepository.findById(id);
          }
 
          public Curso save(Curso curso) {
              return cursoRepository.save(curso);
          }
 
          public void deleteById(Long id) {
              cursoRepository.deleteById(id);
          }
      }
      ```

### Passo 7: Criar o Controller

1. **Criar a classe de controlador**:
    - No pacote `com.exemplo.curso.controller`, crie uma classe `CursoController`:
      ```java
      package com.exemplo.curso.controller;
 
      import com.exemplo.curso.model.Curso;
      import com.exemplo.curso.service.CursoService;
      import org.springframework.beans.factory.annotation.Autowired;
      import org.springframework.http.ResponseEntity;
      import org.springframework.web.bind.annotation.*;
 
      import java.util.List;
      import java.util.Optional;
 
      @RestController
      @RequestMapping("/api/cursos")
      public class CursoController {
          @Autowired
          private CursoService cursoService;
 
          @GetMapping
          public List<Curso> getAllCursos() {
              return cursoService.findAll();
          }
 
          @GetMapping("/{id}")
          public ResponseEntity<Curso> getCursoById(@PathVariable Long id) {
              Optional<Curso> curso = cursoService.findById(id);
              return curso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
          }
 
          @PostMapping
          public Curso createCurso(@RequestBody Curso curso) {
              return cursoService.save(curso);
          }
 
          @PutMapping("/{id}")
          public ResponseEntity<Curso> updateCurso(@PathVariable Long id, @RequestBody Curso cursoDetails) {
              Optional<Curso> curso = cursoService.findById(id);
              if (curso.isPresent()) {
                  Curso cursoToUpdate = curso.get();
                  cursoToUpdate.setNome(cursoDetails.getNome());
                  cursoToUpdate.setDescricao(cursoDetails.getDescricao());
                  return ResponseEntity.ok(cursoService.save(cursoToUpdate));
              } else {
                  return ResponseEntity.notFound().build();
              }
          }
 
          @DeleteMapping("/{id}")
          public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
              if (cursoService.findById(id).isPresent()) {
                  cursoService.deleteById(id);
                  return ResponseEntity.noContent().build();
              } else {
                  return ResponseEntity.notFound().build();
              }
          }
      }
      ```

### Passo 8: Testar a Aplicação

1. **Rodar a aplicação**:
    - Na sua IDE, execute a classe principal `CursoCrudApplication` (geralmente localizada em `src/main/java/com/exemplo/curso/CursoCrudApplication.java`).

2. **Testar os endpoints**:
    - Use ferramentas como Postman ou Insomnia para testar os endpoints CRUD:
        - `GET /api/cursos` - Listar todos os cursos
        - `GET /api/cursos/{id}` - Obter um curso por ID
        - `POST /api/cursos` - Criar um novo curso
        - `PUT /api/cursos/{id}` - Atualizar um curso existente
        - `DELETE /api/cursos/{id}` - Excluir um curso

Pronto! Agora você tem um CRUD básico para a entidade "Curso" usando Spring Boot e PostgreSQL.