// Nome: Fellipe Jardanovski RA: 10395847
// Nome: João Guilherme Messias de Olveira Santos RA: 10426110
//
// Fontes: https://youtu.be/N6dOwBde7-M 
// https://joaoarthurbm.github.io/eda/posts/linkedlist/
//
// Importação de bibliotecas necessárias
import java.io.*;
import java.util.Scanner;

public class Main {
    // Criação de uma lista ligada para armazenar as instruções de código
    private static final LinkedList code = new LinkedList();

    // Método principal onde o programa é executado
    public static void main(String[] args) {
        // Scanner para leitura de comandos do usuário
        Scanner scanner = new Scanner(System.in);
        boolean modified = false; // Indica se houve modificações no código
        String currentFile = null; // Armazena o nome do arquivo atual

        // Loop infinito para leitura e processamento de comandos
        while (true) {
            System.out.print("> "); // Prompt para o usuário
            String input = scanner.nextLine().trim(); // Leitura do comando
            String[] parts = input.split(" ", 2); // Separação do comando e seus parâmetros

            if (parts.length < 1) {
                System.out.println("Erro: comando inválido.");
                continue;
            }

            String command = parts[0].toUpperCase(); // Comando em maiúsculas para facilitar a comparação
            switch (command) {
                // Comando LOAD para carregar um arquivo com o código
                case "LOAD":
                    if (parts.length > 1) {
                        currentFile = parts[1]; // Nome do arquivo
                        try {
                            loadFile(currentFile); // Carrega o arquivo
                            System.out.println("Arquivo '" + currentFile + "' carregado com sucesso.");
                            modified = false; // Após o carregamento, nenhuma modificação
                        } catch (IOException e) {
                            System.out.println("Erro ao carregar o arquivo: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Erro: nome do arquivo não especificado.");
                    }
                    break;

                // Comando LIST para imprimir o código na memória
                case "LIST":
                    code.print();
                    break;

                // Comando RUN para executar o código na memória
                case "RUN":
                    if (code.getHead() == null) {
                        System.out.println("Erro: nenhum código na memória.");
                    } else {
                        runCode();
                    }
                    break;

                // Comando INS para inserir ou atualizar uma linha de código
                case "INS":
                    if (parts.length > 1) {
                        handleInsert(parts[1]); // Chama o método de inserção
                        modified = true; // Marca que houve modificação
                    } else {
                        System.out.println("Erro: formato inválido para INS.");
                    }
                    break;

                // Comando DEL para excluir uma linha ou um intervalo de linhas
                case "DEL":
                    if (parts.length > 1) {
                        handleDelete(parts[1]); // Chama o método de exclusão
                        modified = true; // Marca que houve modificação
                    } else {
                        System.out.println("Erro: formato inválido para DEL.");
                    }
                    break;

                // Comando SAVE para salvar o código em um arquivo
                case "SAVE":
                    if (currentFile == null) {
                        System.out.println("Erro: nenhum arquivo foi carregado para salvar.");
                    } else {
                        try {
                            saveFile(currentFile); // Salva o arquivo
                            System.out.println("Arquivo '" + currentFile + "' salvo com sucesso.");
                            modified = false; // Após salvar, marca que não houve modificações
                        } catch (IOException e) {
                            System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
                        }
                    }
                    break;

                // Comando EXIT para encerrar o programa
                case "EXIT":
                    if (modified) {
                        System.out.println("Existem alterações não salvas. Deseja salvar? (S/N)");
                        String response = scanner.nextLine();
                        if (response.equalsIgnoreCase("S")) {
                            if (currentFile == null) {
                                System.out.println("Erro: nenhum arquivo foi carregado para salvar.");
                            } else {
                                try {
                                    saveFile(currentFile); // Salva o arquivo antes de sair
                                    System.out.println("Arquivo '" + currentFile + "' salvo com sucesso.");
                                } catch (IOException e) {
                                    System.out.println("Erro ao salvar o arquivo: " + e.getMessage());
                                }
                            }
                        }
                    }
                    System.out.println("Fim.");
                    scanner.close(); // Fecha o scanner
                    return; // Encerra o programa

                default:
                    // Caso o comando não seja reconhecido
                    System.out.println("Erro: comando inválido.");
            }
        }
    }

    // Método para carregar um arquivo e inserir o código na lista
    private static void loadFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        code.clear(); // Limpa o código anterior
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(" ", 2); // Divide a linha em número e instrução
            int lineNumber = Integer.parseInt(parts[0]); // Número da linha
            String instruction = parts[1]; // Instrução
            code.insert(lineNumber, instruction); // Insere a linha na lista
        }
        reader.close();
    }

    // Método para salvar o código em um arquivo
    private static void saveFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        Node current = code.getHead(); // Obtém o início da lista
        while (current != null) {
            writer.write(current.lineNumber + " " + current.instruction); // Escreve linha e instrução
            writer.newLine();
            current = current.next; // Avança para a próxima linha
        }
        writer.close();
    }

    // Método para inserir ou atualizar uma linha de código
    private static void handleInsert(String input) {
        String[] insParts = input.split(" ", 2); // Divide o input em número da linha e instrução
        if (insParts.length < 2) {
            System.out.println("Erro: formato inválido para INS.");
            return;
        }
        try {
            int line = Integer.parseInt(insParts[0]); // Número da linha
            if (line < 0) {
                System.out.println("Erro: linha inválida.");
                return;
            }
            String instruction = insParts[1]; // Instrução
            code.insert(line, instruction); // Insere ou atualiza a linha no código
            System.out.println("Linha inserida ou atualizada: " + line + " " + instruction);
        } catch (NumberFormatException e) {
            System.out.println("Erro: formato inválido para INS.");
        }
    }

    // Método para excluir uma linha ou um intervalo de linhas
    private static void handleDelete(String input) {
        String[] delParts = input.split(" ");
        try {
            if (delParts.length == 1) {
                int line = Integer.parseInt(delParts[0]); // Exclui uma linha específica
                if (code.delete(line)) {
                    System.out.println("Linha " + line + " removida.");
                } else {
                    System.out.println("Erro: linha " + line + " inexistente.");
                }
            } else if (delParts.length == 2) {
                int start = Integer.parseInt(delParts[0]);
                int end = Integer.parseInt(delParts[1]);
                if (start > end) {
                    System.out.println("Erro: intervalo inválido de linhas.");
                    return;
                }
                // Exclui um intervalo de linhas
                for (int i = start; i <= end; i++) {
                    if (code.delete(i)) {
                        System.out.println("Linha " + i + " removida.");
                    }
                }
            } else {
                System.out.println("Erro: formato inválido para DEL.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: formato inválido para DEL.");
        }
    }

    // Método para executar o código armazenado na lista
    private static void runCode() {
        Node current = code.getHead(); // Inicia a execução a partir do começo da lista
        int[] registers = new int[26]; // Registradores A-Z
        while (current != null) {
            try {
                String[] parts = current.instruction.split(" "); // Divide a instrução
                String command = parts[0].toLowerCase(); // Comando em minúsculas para facilitar a comparação

                switch (command) {
                    case "mov": {
                        // Move um valor para um registrador
                        int regIndex = parts[1].charAt(0) - 'a';
                        int value = parts[2].matches("-?\\d+") 
                                    ? Integer.parseInt(parts[2]) 
                                    : registers[parts[2].charAt(0) - 'a'];
                        registers[regIndex] = value;
                        break;
                    }
                    case "inc": {
                        // Incrementa o valor de um registrador
                        int regIndex = parts[1].charAt(0) - 'a';
                        registers[regIndex]++;
                        break;
                    }
                    case "dec": {
                        // Decrementa o valor de um registrador
                        int regIndex = parts[1].charAt(0) - 'a';
                        registers[regIndex]--;
                        break;
                    }
                    case "add": {
                        // Adiciona o valor de um registrador ao de outro
                        int regIndex = parts[1].charAt(0) - 'a';
                        int value = parts[2].charAt(0) - 'a';
                        registers[regIndex] += registers[value];
                        break;
                    }
                    case "sub": {
                        // Subtrai o valor de um registrador de outro
                        int regIndex = parts[1].charAt(0) - 'a';
                        int value = parts[2].charAt(0) - 'a';
                        registers[regIndex] -= registers[value];
                        break;
                    }
                    case "mul": {
                        // Multiplica o valor de um registrador por outro
                        int regIndex = parts[1].charAt(0) - 'a';
                        int value = parts[2].charAt(0) - 'a';
                        registers[regIndex] *= registers[value];
                        break;
                    }
                    case "div": {
                        // Divide o valor de um registrador por outro
                        int regIndex = parts[1].charAt(0) - 'a';
                        int value = parts[2].charAt(0) - 'a';
                        if (registers[value] == 0) {
                            throw new ArithmeticException("Divisão por zero na linha " + current.lineNumber);
                        }
                        registers[regIndex] /= registers[value];
                        break;
                    }
                    case "jnz": {
                        // Pula para uma linha se o valor do registrador não for zero
                        int regIndex = parts[1].charAt(0) - 'a';
                        if (registers[regIndex] != 0) {
                            int targetLine = Integer.parseInt(parts[2]);
                            current = findNodeByLine(targetLine); // Encontra a linha de destino
                            if (current == null) {
                                System.out.println("Erro: linha de destino inválida " + targetLine);
                                return;
                            }
                            continue;
                        }
                        break;
                    }
                    case "out": {
                        // Exibe o valor de um registrador
                        int regIndex = parts[1].charAt(0) - 'a';
                        System.out.println(registers[regIndex]);
                        break;
                    }
                    default:
                        System.out.println("Erro: instrução inválida na linha " + current.lineNumber);
                        return;
                }
            } catch (Exception e) {
                // Captura exceções e exibe erros durante a execução
                System.out.println("Erro na execução da linha " + current.lineNumber + ": " + e.getMessage());
            }
            current = current.next; // Avança para a próxima linha
        }
    }

    // Método para encontrar um nó na lista pelo número da linha
    private static Node findNodeByLine(int lineNumber) {
        Node current = code.getHead();
        while (current != null) {
            if (current.lineNumber == lineNumber) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
}
