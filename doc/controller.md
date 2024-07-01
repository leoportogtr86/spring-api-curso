Vou explicar detalhadamente a estrutura de um controller no Spring Boot usando o exemplo do `CursoController`. O controller é responsável por lidar com as requisições HTTP, processar a entrada, chamar os serviços apropriados e retornar respostas.

### 1. **Definição da Classe**

A classe do controlador é anotada com `@RestController`, indicando que é um componente do Spring que manipula requisições RESTful. Esta classe geralmente reside no pacote `controller` do seu projeto.

### 2. **Anotações de Mapeamento**

#### `@RequestMapping`
A anotação `@RequestMapping` na classe define o mapeamento de URL base para todas as requisições tratadas por este controlador.

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
    // Código do controlador
}
```

### 3. **Injeção de Dependência**

#### `@Autowired`
A anotação `@Autowired` é usada para injetar automaticamente a dependência do serviço `CursoService` no controlador. Isso permite que o controlador chame os métodos do serviço para executar a lógica de negócios.

```java
@Autowired
private CursoService cursoService;
```

### 4. **Métodos do Controlador**

Os métodos do controlador são anotados para mapear requisições HTTP específicas (GET, POST, PUT, DELETE) para os métodos correspondentes. Eles processam a entrada, chamam os serviços e retornam as respostas apropriadas.

#### `@GetMapping`

Este método trata requisições GET para listar todos os cursos.

```java
@GetMapping
public List<Curso> getAllCursos() {
    return cursoService.findAll();
}
```

#### `@GetMapping("/{id}")`

Este método trata requisições GET para obter um curso pelo ID.

```java
@GetMapping("/{id}")
public ResponseEntity<Curso> getCursoById(@PathVariable Long id) {
    Optional<Curso> curso = cursoService.findById(id);
    return curso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
}
```

#### `@PostMapping`

Este método trata requisições POST para criar um novo curso.

```java
@PostMapping
public Curso createCurso(@RequestBody Curso curso) {
    return cursoService.save(curso);
}
```

#### `@PutMapping("/{id}")`

Este método trata requisições PUT para atualizar um curso existente.

```java
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
```

#### `@DeleteMapping("/{id}")`

Este método trata requisições DELETE para excluir um curso pelo ID.

```java
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteCurso(@PathVariable Long id) {
    if (cursoService.findById(id).isPresent()) {
        cursoService.deleteById(id);
        return ResponseEntity.noContent().build();
    } else {
        return ResponseEntity.notFound().build();
    }
}
```

### Exemplo Completo da Classe `CursoController`

Aqui está a implementação completa da classe `CursoController` com todos os métodos mencionados:

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

    // Recupera todos os cursos
    @GetMapping
    public List<Curso> getAllCursos() {
        return cursoService.findAll();
    }

    // Recupera um curso pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCursoById(@PathVariable Long id) {
        Optional<Curso> curso = cursoService.findById(id);
        return curso.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cria um novo curso
    @PostMapping
    public Curso createCurso(@RequestBody Curso curso) {
        return cursoService.save(curso);
    }

    // Atualiza um curso existente
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

    // Exclui um curso pelo ID
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

### Detalhamento dos Métodos

- **`getAllCursos`**: Este método retorna uma lista de todos os cursos. Ele mapeia para uma requisição GET na URL base `/api/cursos`.

- **`getCursoById`**: Este método retorna um curso específico pelo seu ID. Ele mapeia para uma requisição GET na URL `/api/cursos/{id}`. Se o curso não for encontrado, retorna um status 404 (Not Found).

- **`createCurso`**: Este método cria um novo curso. Ele mapeia para uma requisição POST na URL base `/api/cursos`.

- **`updateCurso`**: Este método atualiza um curso existente. Ele mapeia para uma requisição PUT na URL `/api/cursos/{id}`. Se o curso não for encontrado, retorna um status 404 (Not Found).

- **`deleteCurso`**: Este método exclui um curso pelo seu ID. Ele mapeia para uma requisição DELETE na URL `/api/cursos/{id}`. Se o curso não for encontrado, retorna um status 404 (Not Found).

Esta estrutura do controlador permite que você manipule requisições HTTP e integre facilmente com o serviço para executar a lógica de negócios, proporcionando uma interface RESTful completa para a entidade `Curso`.