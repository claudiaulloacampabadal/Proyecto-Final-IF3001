/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import static util.Utility.greaterT;

/**
 *
 * @author Usuario
 */
public class DoublyLinkedList implements List {
     private Node first; //apunta al inicio de la lista

    //Constructor
    public DoublyLinkedList() {
        this.first = null;
    }

    @Override
    public int size() throws ListException {//Tamaño
        if (isEmpty()) {
            throw new ListException("Is empty!");//si está vacio muestra la exc
        }
        Node aux = first;//Se le asigna al auxiliar el primero
        int counter = 0;//Se inicializa contador en 0
        while (aux != null) {//mientras el aux sea diferente de null
            counter++;//el contador se aumenta
            aux = aux.next;//Se le asigna el aux al siguiente del auxiliar 
        }
        return counter;//retornar contador
    }

    @Override
    public void clear() {
        this.first = null;//se pone el first en null
    }

    @Override
    public boolean isEmpty() {
        return first == null; //Se iguala el first en null
    }

    @Override
    public boolean contains(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Is empty!");//si está vacio da la exc
        }
        Node aux = first;//Se iguala el aux a first
        while (aux != null) {//mientras el aux sea diferente de nullo
            if (util.Utility.equals(aux.data, element)){//Se llama al equals del utility 
                return true;//retorna verdadero
            }
            aux = aux.next;//Se le asigna el aux al aux siguiente
        }
        return false;//Retorna falso
    }

    @Override
    public void add(Object element){
        Node newNode = new Node(element);
        if (isEmpty()) {//si esta vacio
            this.first = newNode;//Se le asigna el primero al nuevo nodo
        } else {
            Node aux = first;// si no el aux se iguala al primero
            while (aux.next != null) {//y mientras el siguiente del aux sea diferente del null
                aux = aux.next; //muevo el aux al siguiente nodo
            }            //se sale del while cuando aux.next == null
            aux.next = newNode;//Se le asigna 
            //hago el doble enlace
            newNode.prev = aux;
        }
    }//End add

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            first = newNode;//Se le asigna el primero al nuevo nodo
        } else {
            newNode.next = first;
            //hago el doble enlace
            first.prev = newNode;
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
        //Si la lista está vacia
        if (isEmpty()) {
            first = newNode;
        } else {
            //Si el first.next es nulo, o no es nulo
            //y el elemento a insertar es menor al del inicio
            if (util.Utility.greaterT(first.data, element)) {
                newNode.next = first;
                //hago el doble enlace
                first.prev = newNode;
                first = newNode;
            } else {
                //otros casos
                Node prev = first;
                Node aux = first.next;
                boolean added = false;
                while (aux != null && !added) {//si aux es diferente del null y dif de added(true)
                    if (util.Utility.lessT(element, aux.data)) {
                        prev.next = newNode;
                        //hago el doble enlace
                        newNode.prev = prev;

                        newNode.next = aux;
                        //hago el doble enlace
                        aux.prev = newNode;
                        added = true;
                    }
                    prev = aux;
                    aux = aux.next;
                }
                //si llega aqui, el elemento se agrega al final de la lista
                if (!added) {
                    prev.next = newNode;
                    //hago el doble enlace
                    newNode.prev = prev;
                }
            }
        }
    }//End addInSortedList

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
                //mantengo el doble enlace
                aux.next.prev = prev;
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
        //rompo el doble enlace
        first.prev = null;
        return element;
    }

    @Override
    public Object removeLast() throws ListException {
        if(isEmpty()){
            throw new ListException("SinglyLinkedList is empty");
        }
        Node aux = first;
        Node prev = first; //para dejar rastro, apunta al anterior de aux
        while(aux.next!=null){
            prev = aux; //un nodo atras de aux
            aux = aux.next;
        }
        //se sale del while cuando esta en el ultimo nodo
        Object element = aux.data;
        prev.next = null; //desconecto el ultimo nodo
        return element;
    }

    @Override
    public void sort()throws ListException {//ordenar
      if (isEmpty()) {
            throw new ListException("DoubyLinkedList is empty");//Exc si está vacia 
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
            }//end for
        }//end for
    }//end sort

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

        if (isEmpty()) {//Si está vacio
            throw new ListException("Is empty!");//muestra la excepcion
        }

        int posicion = indexOf(element);//pone la posicion como el indice del elmento que tiene por parametro

        return (posicion == -1) ? "El elemento " + element//si la posicion da -1 significa que no esta en la lista
                + " no existe en la lista"
                : (posicion == 1) ? "No existe un elemento anterior a: "//si da 1 s porque NO tiene previo
                        + element : getNode(posicion - 1).data;
    }

    @Override
    public Object getNext(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("SinglyLinkedList is empty");
        }

        int posicion = indexOf(element);

        return (posicion == -1) ? "El elemento " + element
                + " no existe en la lista"
                : (posicion == 1) ? "No existe un elemento anterior a: "
                        + element : getNode(posicion + 1).data;
    }

    @Override
    public Node getNode(int index) throws ListException {
         if(isEmpty())
            throw new ListException("Doubly Linked List is empty");
        int i = 1; //posicion del 1er elemento
        Node aux = first;
        while(aux!=null&&!util.Utility.equals(index, i)){
            aux = aux.next;
            i++;
        }
        //sale cuando aux==null o encontro el elemento
        if(aux!=null&&util.Utility.equals(index, i)){
            return aux;
        }
        return null; //el elemento no existe
    }

    @Override
    public String toString() {
        String result = "\nDoubly Linked List Content\n";
        Node aux = first;
        while (aux != null) {
            result += aux.data + "\n";
            aux = aux.next;
        }
        return result;
    }

    
    
}
