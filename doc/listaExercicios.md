Aqui está uma lista de 10 exercícios baseados no desenvolvimento de uma aplicação CRUD usando Spring Boot,
PostgreSQL e os conceitos que discutimos:

### Exercícios

1. **Configuração do Projeto**:
    - Crie um novo projeto Spring Boot usando o Spring Initializr com as
      dependências `Spring Web`, `Spring Data JPA`, `PostgreSQL Driver` e `Spring Boot DevTools`. Configure o projeto
      para se conectar a um banco de dados PostgreSQL.

2. **Criação da Entidade**:
    - Crie uma entidade `Aluno` com os campos `id` (Long), `nome` (String), `email` (String) e `curso` (String). Use as
      anotações JPA apropriadas para mapear esta entidade para uma tabela de banco de dados.

3. **Configuração do Repositório**:
    - Crie um repositório `AlunoRepository` que estenda `JpaRepository` para a entidade `Aluno`. Adicione um método
      personalizado para encontrar alunos por curso.

4. **Implementação do Serviço**:
    - Crie um serviço `AlunoService` com métodos para listar todos os alunos, encontrar um aluno por ID, salvar um novo
      aluno, atualizar um aluno existente e deletar um aluno por ID.

5. **Implementação do Controlador**:
    - Crie um controlador `AlunoController` com endpoints REST para listar todos os alunos, encontrar um aluno por ID,
      criar um novo aluno, atualizar um aluno existente e deletar um aluno por ID. Utilize as
      anotações `@GetMapping`, `@PostMapping`, `@PutMapping` e `@DeleteMapping`.

6. **Validação de Dados**:
    - Adicione validações aos campos da entidade `Aluno` usando anotações como `@NotNull`, `@Email` e `@Size`. Atualize
      o controlador para lidar com exceções de validação e retornar respostas apropriadas.

7. **Paginação e Ordenação**:
    - Adicione suporte para paginação e ordenação na listagem de alunos. Atualize o método `findAll` no serviço e
      controlador para aceitar parâmetros de paginação e ordenação.

8. **Tratamento de Exceções**:
    - Crie uma classe global de tratamento de exceções para capturar e lidar com exceções específicas,
      como `EntityNotFoundException` e `DataIntegrityViolationException`. Retorne respostas apropriadas com mensagens de
      erro claras.

9. **Autenticação e Autorização**:
    - Integre o Spring Security para adicionar autenticação e autorização à sua aplicação. Crie uma entidade `Usuario`
      com campos `username`, `password` e `roles`. Configure o Spring Security para proteger os endpoints da API.

10. **Testes Unitários e de Integração**:
    - Escreva testes unitários para o serviço `AlunoService` e testes de integração para o controlador `AlunoController`
      usando JUnit e Mockito. Garanta que todos os métodos CRUD sejam testados adequadamente.

### Detalhes dos Exercícios

1. **Configuração do Projeto**:
    - Utilize o [Spring Initializr](https://start.spring.io/) para criar o projeto.
    - Configure o `application.properties` com as informações do banco de dados PostgreSQL.

2. **Criação da Entidade `Aluno`**:
   ```java
   @Entity
   public class Aluno {
       @Id
       @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;

       @NotNull
       @Size(min = 2, max = 100)
       private String nome;

       @NotNull
       @Email
       private String email;

       @NotNull
       private String curso;

       // Getters e Setters
   }
   ```

3. **Configuração do Repositório**:
   ```java
   public interface AlunoRepository extends JpaRepository<Aluno, Long> {
       List<Aluno> findByCurso(String curso);
   }
   ```

4. **Implementação do Serviço `AlunoService`**:
   ```java
   @Service
   public class AlunoService {
       @Autowired
       private AlunoRepository alunoRepository;

       public List<Aluno> findAll() {
           return alunoRepository.findAll();
       }

       public Optional<Aluno> findById(Long id) {
           return alunoRepository.findById(id);
       }

       public Aluno save(Aluno aluno) {
           return alunoRepository.save(aluno);
       }

       public void deleteById(Long id) {
           alunoRepository.deleteById(id);
       }
   }
   ```

5. **Implementação do Controlador `AlunoController`**:
   ```java
   @RestController
   @RequestMapping("/api/alunos")
   public class AlunoController {
       @Autowired
       private AlunoService alunoService;

       @GetMapping
       public List<Aluno> getAllAlunos() {
           return alunoService.findAll();
       }

       @GetMapping("/{id}")
       public ResponseEntity<Aluno> getAlunoById(@PathVariable Long id) {
           Optional<Aluno> aluno = alunoService.findById(id);
           return aluno.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
       }

       @PostMapping
       public Aluno createAluno(@RequestBody Aluno aluno) {
           return alunoService.save(aluno);
       }

       @PutMapping("/{id}")
       public ResponseEntity<Aluno> updateAluno(@PathVariable Long id, @RequestBody Aluno alunoDetails) {
           Optional<Aluno> aluno = alunoService.findById(id);
           if (aluno.isPresent()) {
               Aluno alunoToUpdate = aluno.get();
               alunoToUpdate.setNome(alunoDetails.getNome());
               alunoToUpdate.setEmail(alunoDetails.getEmail());
               alunoToUpdate.setCurso(alunoDetails.getCurso());
               return ResponseEntity.ok(alunoService.save(alunoToUpdate));
           } else {
               return ResponseEntity.notFound().build();
           }
       }

       @DeleteMapping("/{id}")
       public ResponseEntity<Void> deleteAluno(@PathVariable Long id) {
           if (alunoService.findById(id).isPresent()) {
               alunoService.deleteById(id);
               return ResponseEntity.noContent().build();
           } else {
               return ResponseEntity.notFound().build();
           }
       }
   }
   ```

6. **Validação de Dados**:
    - Adicione as anotações de validação na entidade `Aluno`.
    - Atualize o método `createAluno` no controlador para lidar com exceções de validação.

7. **Paginação e Ordenação**:
    - Atualize o método `findAll` no serviço e controlador para aceitar parâmetros de paginação e ordenação.
      Use `Pageable` no repositório.

8. **Tratamento de Exceções**:
    - Crie uma classe anotada com `@ControllerAdvice` para capturar e tratar exceções globalmente.

9. **Autenticação e Autorização**:
    - Configure o Spring Security no projeto. Crie um `SecurityConfig` para definir as regras de segurança.

10. **Testes Unitários e de Integração**:
    - Escreva testes unitários para `AlunoService` e testes de integração para `AlunoController` usando JUnit e Mockito.

Esses exercícios ajudarão a consolidar seu entendimento sobre o desenvolvimento de aplicações CRUD usando Spring Boot e
PostgreSQL, abrangendo desde a configuração inicial até a implementação de segurança e testes.