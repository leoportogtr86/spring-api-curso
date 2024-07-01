A estrutura de um model em Spring Boot, que é uma representação de uma entidade no contexto de um banco de dados, é
fundamental para a construção de aplicações robustas e escaláveis. Vou explicar detalhadamente a estrutura de um model
usando um exemplo prático com a entidade `Curso`.

### 1. **Definição da Classe**

A classe que representa o model é anotada com `@Entity`, indicando que ela é uma entidade JPA (Java Persistence API).
Esta classe geralmente reside no pacote `model` ou `domain` do seu projeto.

### 2. **Anotações de Entidade**

#### `@Entity`

A anotação `@Entity` define que a classe é uma entidade e será mapeada para uma tabela no banco de dados.

```java

@Entity
public class Curso {
    // atributos e métodos
}
```

#### `@Table` (opcional)

A anotação `@Table` pode ser usada para especificar detalhes da tabela, como o nome, esquema e índices. Se omitida, o
nome da tabela será o nome da classe.

```java

@Entity
@Table(name = "cursos")
public class Curso {
    // atributos e métodos
}
```

### 3. **Definição do Identificador**

#### `@Id`

A anotação `@Id` define o identificador único da entidade. Este campo mapeia a chave primária da tabela.

#### `@GeneratedValue`

A anotação `@GeneratedValue` especifica como o valor do identificador será gerado. A estratégia mais comum
é `GenerationType.IDENTITY`, que indica que o banco de dados deve gerar o valor do identificador automaticamente.

```java

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

### 4. **Definição dos Atributos**

Os atributos da classe representam as colunas da tabela. Cada atributo pode ser anotado com `@Column` para especificar
detalhes da coluna, como nome, tipo e restrições. Se `@Column` for omitida, o nome da coluna será o nome do atributo.

```java

@Column(name = "nome", nullable = false)
private String nome;

@Column(name = "descricao")
private String descricao;
```

### 5. **Construtores**

É comum ter dois tipos de construtores:

#### Construtor sem argumentos

Necessário para a criação de instâncias da entidade pelo JPA.

```java
public Curso() {
}
```

#### Construtor com argumentos

Facilita a criação de instâncias com atributos definidos.

```java
public Curso(String nome, String descricao) {
    this.nome = nome;
    this.descricao = descricao;
}
```

### 6. **Getters e Setters**

Os métodos getters e setters são usados para acessar e modificar os atributos da entidade.

```java
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
```

### 7. **Métodos `toString`, `equals` e `hashCode`**

Implementar estes métodos é uma boa prática para facilitar a depuração e comparação de entidades.

#### `toString`

Retorna uma string representando a entidade.

```java

@Override
public String toString() {
    return "Curso{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", descricao='" + descricao + '\'' +
            '}';
}
```

#### `equals` e `hashCode`

São usados para comparar entidades e calcular o hashcode, respectivamente. Devem ser baseados no identificador
único (`id`).

```java

@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Curso curso = (Curso) o;
    return Objects.equals(id, curso.id);
}

@Override
public int hashCode() {
    return Objects.hash(id);
}
```

### Exemplo Completo da Classe `Curso`

```java
package com.exemplo.curso.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cursos")
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    // Construtor sem argumentos
    public Curso() {
    }

    // Construtor com argumentos
    public Curso(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

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

    // toString
    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }

    // equals e hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return Objects.equals(id, curso.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
```

Esta é a estrutura básica e completa de um model no Spring Boot. Ela inclui todos os componentes essenciais para
representar e manipular uma entidade no contexto de um banco de dados relacional.