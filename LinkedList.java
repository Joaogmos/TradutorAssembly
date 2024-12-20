// Nome: Fellipe Jardanovski RA: 10395847
// Nome: João Guilherme Messias de Olveira Santos RA: 10426110
// Classe que cria a lista encadeada que vai ser usada no codigo na classe Main

public class LinkedList {
    private Node head;

    public LinkedList() {
        this.head = null;
    }

    public void insert(int lineNumber, String instruction) {
        Node newNode = new Node(lineNumber, instruction);

        if (head == null || head.lineNumber > lineNumber) {
            newNode.next = head;
            head = newNode;
            return;
        }

        Node current = head;
        while (current.next != null && current.next.lineNumber < lineNumber) {
            current = current.next;
        }

        if (current.lineNumber == lineNumber) {
            current.instruction = instruction; 
        } else {
            newNode.next = current.next;
            current.next = newNode;
        }
    }

    public boolean delete(int lineNumber) {
        if (head == null) return false;

        if (head.lineNumber == lineNumber) {
            head = head.next;
            return true;
        }

        Node current = head;
        while (current.next != null && current.next.lineNumber != lineNumber) {
            current = current.next;
        }

        if (current.next == null) return false;

        current.next = current.next.next;
        return true;
    }

    public void print() {
        Node current = head;
        while (current != null) {
            System.out.println(current.lineNumber + " " + current.instruction);
            current = current.next;
        }
    }

    public void clear() {
        head = null;
    }

    public Node getHead() {
        return head;
    }
}
