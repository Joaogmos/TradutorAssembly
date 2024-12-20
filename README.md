# Projeto: Simulador de Processador com Lista Encadeada

## Descrição
Este projeto é um simulador de processador que utiliza listas encadeadas para gerenciar, interpretar e executar instruções. Ele permite manipular arquivos de texto contendo códigos de montagem simples, possibilitando inserções, exclusões e execução de instruções armazenadas.

---

## Funcionalidades
- **Manipulação de código**:
  - **LOAD**: Carrega um arquivo contendo código para memória.
  - **LIST**: Exibe o código carregado na memória.
  - **INS**: Insere ou atualiza uma linha de código.
  - **DEL**: Remove uma ou mais linhas de código.
  - **SAVE**: Salva o código modificado em um arquivo.
  - **EXIT**: Encerra o programa com opção de salvar alterações não salvas.

- **Execução de código**:
  - Suporte a instruções básicas como:
    - `MOV`: Move valores para registradores.
    - `INC`, `DEC`: Incremento e decremento de valores.
    - `ADD`, `SUB`, `MUL`, `DIV`: Operações aritméticas.
    - `JNZ`: Salto condicional baseado em registradores.
    - `OUT`: Exibe valores de registradores.

---

## Arquitetura
O projeto é composto por três classes principais:
1. **Node**:
   - Representa os nós da lista encadeada, armazenando o número da linha e a instrução.
2. **LinkedList**:
   - Implementa a estrutura de dados lista encadeada, permitindo inserir, excluir e buscar nós.
3. **Main**:
   - Contém a interface principal para o usuário, gerenciando comandos e a execução do código carregado.

---

## Tecnologias Utilizadas
- **Linguagem**: Java
- **Bibliotecas**: 
  - `java.io` para manipulação de arquivos.
  - `java.util.Scanner` para leitura de entradas do usuário.

---

## Como Executar
1. Clone este repositório:
   ```bash
   git clone https://https://github.com/Joaogmos/TradutorAssembly

2. Compile o código 
  javac Main.java LinkedList.java Node.java

3. Execute o programa
  java Main
