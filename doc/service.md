Vou explicar detalhadamente a estrutura de um service no Spring Boot usando o exemplo do serviço `CursoService`.
O service é responsável pela lógica de negócios da aplicação e atua como uma camada intermediária entre o controller e o
repositório.

### 1. **Definição da Classe**

A classe do serviço é anotada com `@Service`, indicando que ela é um componente de serviço gerenciado pelo Spring. Esta
classe geralmente reside no pacote `service` do seu projeto.

```java
package com.exemplo.curso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exemplo.curso.repository.CursoRepository;
import com.exemplo.curso.model.Curso;

import java.util.List;
import java.util.Optional;

@Service
public class CursoService {
    // Código do serviço
}
```

### 2. **Injeção de Dependência**

#### `@Autowired`

A anotação `@Autowired` é usada para injetar automaticamente a dependência do repositório `CursoRepository` no serviço.
Isso permite que o serviço use os métodos do repositório para interagir com o banco de dados.

```java

@Autowired
private CursoRepository cursoRepository;
```

### 3. **Métodos do Serviço**

Os métodos do serviço encapsulam a lógica de negócios e delegam operações de acesso a dados ao repositório. Aqui estão
os métodos típicos de um serviço CRUD:

#### `findAll`

Este método recupera todos os cursos do banco de dados.

```java
public List<Curso> findAll() {
    return cursoRepository.findAll();
}
```

#### `findById`

Este método recupera um curso pelo seu ID. Ele retorna um `Optional<Curso>` para lidar com a possibilidade de o curso
não ser encontrado.

```java
public Optional<Curso> findById(Long id) {
    return cursoRepository.findById(id);
}
```

#### `save`

Este método salva um novo curso ou atualiza um curso existente no banco de dados.

```java
public Curso save(Curso curso) {
    return cursoRepository.save(curso);
}
```

#### `deleteById`

Este método exclui um curso pelo seu ID.

```java
public void deleteById(Long id) {
    cursoRepository.deleteById(id);
}
```

### Exemplo Completo da Classe `CursoService`

Aqui está a implementação completa da classe `CursoService` com todos os métodos CRUD mencionados:

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

    // Recupera todos os cursos
    public List<Curso> findAll() {
        return cursoRepository.findAll();
    }

    // Recupera um curso pelo ID
    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id);
    }

    // Salva um novo curso ou atualiza um curso existente
    public Curso save(Curso curso) {
        return cursoRepository.save(curso);
    }

    // Exclui um curso pelo ID
    public void deleteById(Long id) {
        cursoRepository.deleteById(id);
    }
}
```

### Detalhamento dos Métodos

- **`findAll`**: Este método chama o método `findAll` do repositório para obter uma lista de todos os cursos no banco de
  dados.

- **`findById`**: Este método chama o método `findById` do repositório e retorna um `Optional<Curso>`. O uso
  do `Optional` ajuda a evitar o problema de `NullPointerException` e fornece uma maneira clara de lidar com casos onde
  o curso não é encontrado.

- **`save`**: Este método chama o método `save` do repositório para salvar um curso no banco de dados. Se o curso já
  existir (tiver um ID), ele será atualizado; caso contrário, um novo curso será criado.

- **`deleteById`**: Este método chama o método `deleteById` do repositório para excluir um curso pelo seu ID.

Essa estrutura do service encapsula a lógica de negócios e fornece uma interface clara para o controlador, garantindo
que a lógica de negócios seja separada da lógica de acesso a dados e da lógica de controle.