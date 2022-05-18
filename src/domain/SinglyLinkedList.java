/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import static util.Utility.greaterT;

/**
 *
 * @author Fiorella
 */
public class SinglyLinkedList implements List {
    
    private Node first; //apunta al inicio de la lista

    //Constructor
    public SinglyLinkedList() {
        this.first = null;
    }

    @Override
    public int size() throws ListException {
        if (isEmpty()) {
            throw new ListException("Is empty!");
        }
        Node aux = first;
        int counter = 0;
        while (aux != null) {
            counter++;
            aux = aux.next;
        }
        return counter;
    }

    @Override
    public void clear() {
        this.first = null;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
    public boolean contains(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Is empty!");
        }
        Node aux = first;
        while(aux!=null){
            if(util.Utility.equals(aux.data, element)){
                return true;
            }
            aux = aux.next;
        }
        return false; //indica que el elemento no existe
    }


    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            this.first = newNode;
        } else {
            Node aux = first;
            while (aux.next != null) {
                aux = aux.next; //muevo el aux al sgte nodo
            }            //se sale del while cuando aux.next == null
            aux.next = newNode;
        }
    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            first = newNode;
        } else {
            newNode.next = first;
            first = newNode;
        }
    }

    @Override
    public void addLast(Object element) {
        add(element);
    }

    @Override
    public void addInSortedList(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            first = newNode;
        } else {
            //caso 2 es nulo o no es nulo
            if (util.Utility.greaterT(first.data, element)) {
                newNode.next = first;
                first = newNode;
            } else {
                //CASO 3
                Node prev = first;
                Node aux = first.next;
                boolean added = false;
                while (aux != null && added) {
                    if (util.Utility.lessT(element, aux.data)) {
                        prev.next = newNode;
                        newNode.next = aux;
                        added = true;
                    }
                    prev = aux;
                    aux = aux.next;
                }
                //si llega aqui se agrega al final de la lista
                if (!added) {
                    prev.next = newNode;
                }
            }
        }

    }

    @Override
    public void remove(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Is empty!");
        }
        //caso 1. El elemento a suprimir es el primero
        if (util.Utility.equals(first.data, element)) {
            first = first.next;
        } //case 2. El elemento a suprimir puede estar donde sea
        else {
            Node prev = first; //elemento anterior
            Node aux = first.next; //elemento sgte
            while (aux != null && !util.Utility.equals(aux.data, element)) {
                prev = aux; //actualizo anterior
                aux = aux.next;
            }
            //se sale del while cuando alcanza nulo
            //o cuando encuentra el elemento a suprimir
            if (aux != null && util.Utility.equals(aux.data, element)) {
                //ya lo encontro, lo puede suprimir
                //tengo que desenlazar el nodo
                prev.next = aux.next; //se lo salta
            }
        }
    }

    @Override
    public Object removeFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Is empty!");
        }
        Object element = first.data;
        first = first.next;
        return element;
    }

    @Override
    public Object removeLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Is empty!");
        }
        Node aux = first;
        Node prev = first; //para dejar rastro, apunta al anterior de aux
        while (aux.next != null) {
            prev = aux; //un nodo atras de aux
            aux = aux.next;
        }
        //se sale del while cuando esta en el ultimo nodo
        Object element = aux.data;
        prev.next = null; //desconecto el ultimo nodo
        return element;
    }

    @Override
    public void sort() throws ListException {
        if (isEmpty()) {
            throw new ListException("Is empty!");
        }

        Object temp;
        int n = size();

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n - i; j++) {
                if (getNode(j) == null || getNode(j + 1) == null) {
                    break;
                }
                if (greaterT(getNode(j).data, getNode(j + 1).data)) {
                    temp = getNode(j).data;
                    getNode(j).data = getNode(j + 1).data;
                    getNode(j + 1).data = temp;
                }
            }
        }
    }

    @Override
    public int indexOf(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Is empty!");
        }
        int i = 1; //posicion del 1er elemento
        Node aux = first;
        while (aux != null && !util.Utility.equals(aux.data, element)) {
            aux = aux.next;
            i++;
        }
        //sale cuando aux==null o encontro el elemento
        if (aux != null && util.Utility.equals(aux.data, element)) {
            return i;
        }
        return -1; //el elemento no existe
    }

    @Override
    public Object getFirst() throws ListException {
        if (isEmpty()) {
            throw new ListException("Is empty!");
        }
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if (isEmpty()) {
            throw new ListException("Is empty!");
        }
        Node aux = first;
        while (aux.next != null) {
            aux = aux.next;
        }
        //cuando sale del while es porque esta en el ult nodo
        return aux.data;
    }

    @Override
    public Object getPrev(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Is empty!");
        }

        int posicion = indexOf(element);

        return (posicion == -1) ? "El elemento " + element
                + " no existe en la lista"
                : (posicion == 1) ? "No existe un elemento anterior a: "
                        + element : getNode(posicion - 1).data;
    }

    @Override
    public Object getNext(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Is empty!");
        }

        int posicion = indexOf(element);

        return (posicion == -1) ? "El elemento " + element
                + " no existe en la lista"
                : (posicion == 1) ? "No existe un elemento anterior a: "
                        + element : getNode(posicion + 1).data;
    }

    @Override
    public Node getNode(int index) throws ListException {
        if (isEmpty()) {
            throw new ListException("Is empty!");
        }
        int i = 1; //posicion del 1er elemento
        Node aux = first;
        while (aux != null && !util.Utility.equals(index, i)) {
            aux = aux.next;
            i++;
        }
        //sale cuando aux==null o encontro el elemento
        if (aux != null && util.Utility.equals(index, i)) {
            return aux;
        }
        return null; //el elemento no existe
    }

    @Override
    public String toString() {
        String result = "Singly Linked List Content\n";
        Node aux = first;
        while (aux != null) {
            result += aux.data + " ";
            aux = aux.next;
        }
        return result;
    }
    
    
    

    //metodo de encriptacion
    public String encrypt(String pasword){
        try{
            java.security.MessageDigest md= java.security.MessageDigest.getInstance("MD5");
            //separamos la contraseña en un arreglo
            byte []array=md.digest(pasword.getBytes());
            //se crea un stringBuffer para guardar el cambio ya encriptado
            StringBuffer sb= new StringBuffer();
            for (int i = 0; i < array.length; i++) {
                //append concatena a la cadena original
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();            
            //posible error de que el algoritmo no esté en el entorno
        }catch(java.security.NoSuchAlgorithmException e){            
        }
        return null;
    }
}
