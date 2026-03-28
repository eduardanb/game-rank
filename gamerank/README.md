# 🎮 Game Rank

Sistema de gerenciamento de ranking de jogadores desenvolvido em Java, implementando estruturas de dados do zero: Lista Encadeada, Pilha, Fila, Árvore AVL, MaxHeap e Tabela Hash com Chaining.

---

## 📋 Índice

- [Sobre o Projeto](#sobre-o-projeto)
- [Estruturas de Dados Implementadas](#estruturas-de-dados-implementadas)
- [Arquitetura do Projeto](#arquitetura-do-projeto)
- [Funcionalidades](#funcionalidades)
- [Como Executar](#como-executar)
- [Testes](#testes)
- [Decisões de Implementação](#decisões-de-implementação)
- [Tecnologias](#tecnologias)

---

## Sobre o Projeto

O **Game Rank** é um mini-sistema funcional de gerenciamento de partidas e ranking de jogadores. O sistema permite cadastrar jogadores, gerenciar uma fila de espera para partidas, sortear vencedores, atualizar pontuações e consultar o ranking — tudo utilizando estruturas de dados implementadas manualmente, sem uso de bibliotecas prontas como `java.util`.

---

## Estruturas de Dados Implementadas

### 🔗 Lista Simplesmente Encadeada
**Arquivo:** `LinkedList.java`

Implementação iterativa com nó sentinela NIL. Serve de base para a Pilha.

| Operação | Complexidade |
|---|---|
| `insert` | O(n) |
| `remove` | O(n) |
| `search` | O(n) |
| `toArray` | O(n) |

---

### 📚 Pilha (Stack)
**Arquivo:** `StackLinkedList.java`

Implementada utilizando a Lista Simplesmente Encadeada internamente. O topo da pilha corresponde ao último elemento da lista.

| Operação | Complexidade |
|---|---|
| `push` | O(n) |
| `pop` | O(n) |
| `top` | O(n) |

> Lança `StackOverflowException` ao tentar inserir em pilha cheia e `StackUnderflowException` ao tentar remover de pilha vazia.

---

### 🚶 Fila com Duas Pilhas (Queue)
**Arquivo:** `QueueWithStacks.java`

Implementada utilizando **duas instâncias de `StackLinkedList`**: `stackEntrada` e `stackSaida`. Todas as operações da fila usam exclusivamente as duas pilhas.

**Lógica de funcionamento:**
- `enqueue` → sempre empilha em `stackEntrada`
- `dequeue` → retira de `stackSaida`; se vazia, transfere todos os elementos de `stackEntrada` para `stackSaida` antes

```
enqueue(1), enqueue(2), enqueue(3)
stackEntrada: [1, 2, 3]   stackSaida: []

dequeue() → transfere
stackEntrada: []           stackSaida: [3, 2, 1]
retorna 1  ✓ (ordem FIFO mantida)
```

| Operação | Complexidade |
|---|---|
| `enqueue` | O(1) amortizado |
| `dequeue` | O(n) amortizado |
| `contains` | O(n) |

> `contains` percorre as duas pilhas para evitar que o mesmo jogador entre na fila mais de uma vez.

---

### 🌳 Árvore AVL
**Arquivo:** `AVLTree.java`

BST balanceada com fator de balanceamento mantido automaticamente. Suporta as 4 rotações: LL, RR, LR e RL.

**Rotações implementadas:**

```
LL (rotação direita):        RR (rotação esquerda):
    30                           10
   /                               \
  20      →    20                  20    →    20
 /            /  \                   \       /  \
10           10  30                  30     10  30

LR (dupla):                  RL (dupla):
  30                           10
 /                               \
10      →    20                  30    →    20
  \         /  \                /          /  \
  20       10  30              20         10  30
```

| Operação | Complexidade |
|---|---|
| `insert` | O(log n) |
| `remove` | O(log n) |
| `contains` | O(log n) |
| `inOrder` | O(n) |

> O percurso **in-order** retorna os jogadores em ordem crescente de pontuação, sendo usado diretamente para listar o ranking.

---

### 🏔️ MaxHeap (Fila de Prioridade)
**Arquivo:** `MaxHeap.java`

Implementada sobre array. Garante que o jogador com maior pontuação sempre esteja na raiz (índice 0). Utilizada como fila de prioridade para encontrar o top jogador em O(1).

**Propriedade da MaxHeap:**
```
Pai sempre ≥ filhos
heap[i] ≥ heap[2i+1]  (filho esquerdo)
heap[i] ≥ heap[2i+2]  (filho direito)
```

| Operação | Complexidade |
|---|---|
| `insert` | O(log n) |
| `extractMax` | O(log n) |
| `peek` | O(1) |

---

### #️⃣ Tabela Hash com Chaining
**Arquivo:** `HashTableChaining.java`

Tabela com capacidade 100, função hash baseada em `hashCode() % capacity`. Colisões resolvidas por encadeamento com `HashNode`.

```
hash(id) = id % 100

id=1  → bucket[1]  → [Player(1, "Ana")]
id=101→ bucket[1]  → [Player(101,"Bob")] → [Player(1,"Ana")]  ← chaining
```

| Operação | Complexidade Média |
|---|---|
| `put` | O(1) |
| `get` | O(1) |
| `remove` | O(1) |
| `containsKey` | O(1) |

---

## Arquitetura do Projeto

```
gamerank/
└── src/
    ├── main/java/br/com/gamerank/
    │   ├── app/
    │   │   └── Main.java                  # ponto de entrada, loop do menu
    │   ├── exceptions/
    │   │   ├── ElementNotFoundException.java
    │   │   ├── EmptyStructureException.java
    │   │   ├── StackOverflowException.java
    │   │   └── StackUnderflowException.java
    │   ├── model/
    │   │   ├── Game.java                  # representa uma partida
    │   │   ├── Player.java                # jogador (Comparable por pontuação)
    │   │   └── Score.java                 # pontuação de um jogador
    │   ├── service/
    │   │   └── RankingService.java        # lógica de negócio
    │   ├── structures/
    │   │   ├── implementations/
    │   │   │   ├── hash/HashTableChaining.java
    │   │   │   ├── heap/MaxHeap.java
    │   │   │   ├── list/LinkedList.java
    │   │   │   ├── queue/QueueWithStacks.java
    │   │   │   ├── stack/StackLinkedList.java
    │   │   │   └── tree/AVLTree.java
    │   │   ├── interfaces/
    │   │   │   ├── HashTableInterface.java
    │   │   │   ├── HeapInterface.java
    │   │   │   ├── ListInterface.java
    │   │   │   ├── QueueInterface.java
    │   │   │   ├── StackInterface.java
    │   │   │   └── TreeInterface.java
    │   │   └── nodes/
    │   │       ├── AVLNode.java
    │   │       ├── HashNode.java
    │   │       └── ListNode.java
    │   └── util/
    │       └── ConsoleUtils.java          # leitura segura do Scanner
    └── test/java/br/com/gamerank/structures/
        ├── AVLTreeTest.java
        ├── HeapTest.java
        ├── LinkedListTest.java
        ├── QueueTest.java
        └── StackTest.java
```

---

## Funcionalidades

| Opção | Função | Estruturas utilizadas |
|---|---|---|
| 1 | Cadastrar jogador | Hash + Heap + AVL |
| 2 | Buscar jogador | Hash |
| 3 | Listar ranking | AVL (in-order) |
| 4 | Mostrar top jogador | Heap (peek) |
| 5 | Entrar na fila | Fila (enqueue) |
| 6 | Iniciar partida | Fila (dequeue×2) + AVL + Heap |
| 7 | Atualizar pontuação | AVL (remove + insert) + Heap |
| 8 | Remover jogador | Hash + AVL + Heap |
| 0 | Sair | — |

---

## Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.8+

### Rodando o sistema

```bash
# clonar o repositório
git clone https://github.com/seu-usuario/game-rank.git
cd game-rank/gamerank

# compilar
mvn compile

# executar
mvn exec:java -Dexec.mainClass="br.com.gamerank.app.Main"
```

### Exemplo de uso

```
====== GAME RANK ======
1 - Cadastrar jogador
2 - Buscar jogador
...

Escolha: 1
ID: 1
Nome: Ana
Jogador cadastrado: Ana

Escolha: 1
ID: 2
Nome: Bob
Jogador cadastrado: Bob

Escolha: 5
ID do jogador: 1
Ana entrou na fila.

Escolha: 5
ID do jogador: 2
Bob entrou na fila.

Escolha: 6
Partida iniciada: Ana vs Bob
Vencedor: Ana | Nova pontuação: 10
```

---

## Testes

Testes JUnit 5 cobrindo todas as estruturas implementadas.

```bash
# rodar todos os testes
mvn test
```

| Classe de Teste | Testes | O que cobre |
|---|---|---|
| `LinkedListTest` | 15 | insert, search, remove, toArray, null |
| `StackTest` | 16 | push/pop LIFO, overflow, underflow, toArray |
| `QueueTest` | 20 | FIFO, transferência entre pilhas, contains |
| `AVLTreeTest` | 16 | 4 rotações, remove com 2 filhos, in-order |
| `HeapTest` | 15 | peek, extractMax, propriedade max-heap |
| **Total** | **82** | |

---

## Decisões de Implementação

**Fila com duas Pilhas** — a transferência de `stackEntrada` para `stackSaida` é feita de forma *lazy* (só quando `stackSaida` está vazia), garantindo custo amortizado O(1) por operação.

**AVL com `setData` no remove** — ao remover um nó com dois filhos, o dado do sucessor é copiado diretamente no nó atual via `setData`, evitando a criação de um novo nó e a perda dos filhos esquerdos.

**Heap reconstruída em mudanças de pontuação** — como a MaxHeap não suporta remoção arbitrária, a heap é reconstruída a partir da Hash Table sempre que uma pontuação é alterada. Isso mantém a consistência sem aumentar a complexidade algorítmica do ponto de vista prático do sistema.

**Player como Comparable** — `Player` implementa `Comparable<Player>` por pontuação, permitindo que tanto a Heap quanto a AVL comparem jogadores de forma uniforme.

**Hash como fonte de verdade** — a Hash Table é a estrutura primária de armazenamento. AVL e Heap são estruturas auxiliares derivadas dela, reconstruídas quando necessário.

---

## Tecnologias

- **Java 24**
- **Maven** — gerenciamento de dependências e build
- **JUnit Jupiter 5.10.0** — testes unitários