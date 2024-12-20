// Nome: Fellipe Jardanovski RA: 10395847
// Nome: João Guilherme Messias de Olveira Santos RA: 10426110
// Classe que cria o Nó, que vai ser usado na classe LinkedList e na classe Main
public class Node {
    int lineNumber;
    String instruction;
    Node next;

    public Node(int lineNumber, String instruction) {
        this.lineNumber = lineNumber;
        this.instruction = instruction;
        this.next = null;
    }
}
