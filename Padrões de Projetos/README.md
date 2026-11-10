
# GitHub Onboarding Agent

Um agente de IA que acompanha um desenvolvedor aprendendo Git e GitHub. Ele **ensina os
conceitos, monta um plano, executa operações Git de verdade, observa o resultado e decide o
próximo passo**. Quando não tem certeza do que fazer, para e pergunta ao usuário em vez de
chutar.

Projeto acadêmico da disciplina de **Padrões de Projeto** da FATEC. O foco é a arquitetura:
cinco padrões GoF trabalhando juntos **no mesmo fluxo de execução**.

---

## O que o agente faz

O usuário escreve um objetivo em linguagem natural, por exemplo:

> "Quero criar uma branch feature/login"
> "Quero enviar minha alteração para revisão"

O agente então:

1. **Planeja** os passos com um LLM local (ex.: `gitStatus` → `gitBranch` → `gitCheckout`).
2. **Executa** cada passo no repositório Git de trabalho.
3. **Observa** o resultado e decide: continuar, replanejar, pedir ajuda ao humano ou falhar.
4. **Ensina** pelo caminho, consultando uma base de conhecimento sobre Git/GitHub.
5. **Pede aprovação** antes de qualquer ação sensível, como abrir um pull request.

Se o pedido for vago ("Arruma aí o meu repositório"), se o plano vier com baixa confiança
ou se os comandos falharem repetidamente, o agente **troca de estratégia em tempo de
execução** e passa a conduzir a conversa com o usuário (Human-in-the-Loop).

---

## Arquitetura: os cinco padrões

> Strategy planeja · State avança · Command executa · Observer testemunha · Composite mostra

| Padrão | Papel no projeto | Classes principais |
|---|---|---|
| **Strategy** | Escolhe e troca o planejador em runtime | `ReActStrategy`, `PlanThenExecuteStrategy`, `HumanInTheLoopStrategy`, `StrategySelector` |
| **State** | Controla as fases da tarefa | `InitState`, `PlanningState`, `ExecutingState`, `ObservingState`, `WaitingApprovalState`, `ErrorState`, `CompletedState`, `AgentStateMachine` |
| **Command** | Encapsula cada ferramenta que o agente pode usar | `GitStatus`, `GitBranch`, `GitCheckout`, `GitAdd`, `GitCommit`, `GitPush`, `CreatePullRequest`, `KnowledgeSearch` + `CommandInvoker` |
| **Observer** | Registra a trilha de raciocínio, erros e progresso | `AgentEventPublisher` (implementado à mão), `TraceObserver`, `ErrorObserver`, `ProgressObserver`, `GuiObserver` |
| **Composite** | Monta a interface gráfica como uma árvore de painéis | `UIComponent`, `UIComposite`, `GoalPanel`, `PlanPanel`, `StatePanel`, `TraceLogPanel`, `ProgressPanel`, `ApprovalPanel` |

Fluxo de estados:

```
INIT → PLANNING → EXECUTING → OBSERVING ─┬─ CONTINUE → EXECUTING
                                          ├─ REPLAN   → PLANNING
                                          ├─ ESCALATE → WAITING_APPROVAL → PLANNING
                                          └─ FAIL     → ERROR
                                                     └→ COMPLETED
```

Os diagramas UML (classes, sequência e estados) estão em `docs/`.

---

## Ferramentas e tecnologias

| Categoria | Ferramenta | Uso no projeto |
|---|---|---|
| Linguagem | **Java 21** | records, sealed interfaces, pattern matching |
| Framework | **Spring Boot 3.4.5** | injeção de dependência e configuração |
| IA | **Spring AI 1.0.0** | `ChatClient` para o LLM e `SimpleVectorStore` para o RAG |
| LLM local | **llama.cpp** + **Gemma 2 (2B)** | modelo aberto rodando na máquina, via API compatível com OpenAI |
| Git | **JGit 6.10** | executa as operações Git direto do Java |
| GitHub | **API REST do GitHub** (`RestClient`) | abre pull requests |
| Interface | **Swing** | janela gráfica (também existe modo console) |
| Build | **Maven** | build, dependências e testes |
| Testes | **JUnit 5** + **AssertJ** | 86 testes que rodam sem o LLM |
| Documentação | **PlantUML** | diagramas de classes, sequência e estados |

Nenhum serviço pago é necessário: o modelo roda localmente, e o GitHub só é usado para
abrir pull requests.

---

## Como rodar

```bash
# 1. Suba o modelo local
llama-server -m gemma-2-2b-it-Q4_K_M.gguf --port 8080 -c 4096

# 2. Crie um repositório de teste para o agente operar
mkdir -p ~/agent-workspace && cd ~/agent-workspace && git init
echo "# teste" > README.md && git add . && git commit -m "commit inicial"

# 3. Rode o agente
mvn spring-boot:run                                               # modo console
mvn spring-boot:run -Dspring-boot.run.arguments=--agent.ui=swing  # janela Swing

# Testes
mvn test
```

As configurações ficam em `src/main/resources/application.yml` (workspace, limites de
tentativas, limiar de confiança e dados do GitHub).

---

## Minha contribuição: base de conhecimento com RAG

Fiquei responsável pela parte do agente que **ensina**: a base de conhecimento sobre Git e
GitHub e a busca que o agente usa para explicar os conceitos ao usuário.

**O que implementei:**

- **`KnowledgeService`**: o Receiver do comando `KnowledgeSearchCommand`. Ele faz busca
  semântica (RAG) num banco vetorial com o `SimpleVectorStore` do Spring AI.
- **`VectorStoreConfig`**: a configuração do banco vetorial e do documento de conhecimento.
- **`knowledge/git-github.md`**: o conteúdo didático que o agente consulta.
- **Fatiamento por seção**: o documento é dividido em trechos a cada título `##`, e não
  por número de caracteres. Assim cada trecho trata de um assunto só, e a busca devolve uma
  explicação completa em vez de meio parágrafo.
- **Ingestão preguiçosa**: o documento só é carregado no banco vetorial na primeira
  consulta, e uma única vez.
- **Fallback para acervo em memória**: nem todo modelo rodando no llama.cpp oferece
  embeddings. Quando o banco vetorial não está disponível, o serviço passa a usar um acervo
  local de conceitos (status, branch, checkout, commit, add, push, pull request, merge,
  remoto, clone). O agente continua ensinando; só a qualidade da busca muda.
- **`KnowledgeServiceTest`**: testes dos dois modos (com e sem banco vetorial). Criei um
  modelo de embeddings falso, baseado em palavras-chave, para que o teste seja
  determinístico e não dependa do LLM estar no ar.

**Por que essa parte importa:** o objetivo do agente é fazer onboarding, não só executar
comandos. É a base de conhecimento que permite a ele explicar *por que* cada passo está
sendo feito. Com o fallback, essa função continua de pé mesmo numa máquina modesta, onde o
modelo local não suporta embeddings.
