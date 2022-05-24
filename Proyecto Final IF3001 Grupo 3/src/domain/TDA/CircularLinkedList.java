/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.TDA;

import domain.*;

/**
 *
 * @author Usuario
 */
public class CircularLinkedList implements List{
    
      private Node first; //apunta al inicio de la lista
     private Node last; //apunta al ult nodo de la lista

    //Constructor
    public CircularLinkedList(){
        this.first = this.last = null;
    }

    @Override
    public int size() throws ListException {
        if(isEmpty())
            throw new ListException("Circular Linked List is empty");
        Node aux = first;
        int counter = 0;
        while(aux!=last){//last es como el utilimo nodo
           counter++;
           aux = aux.next;
        }
        //sale del while cuando aux esta en el ult nodo
        return counter+1;//counter-1 por que no cuenta el ultimo nodod ya que el last es el mismo nodo
    }

    @Override
    public void clear() {
        this.first = this.last = null;
    }

    @Override
    public boolean isEmpty() {
        return first==null;
    }

    @Override
    public boolean contains(Object element) throws ListException {
         if(isEmpty())
            throw new ListException("Circular Linked List is empty");
        Node aux = first;
        while(aux!=last){
            if(util.Utility.equals(aux.data, element))
                return true;
           aux = aux.next;
        }
        //sale del while cuando aux esta en el ult nodo
        return util.Utility.equals(aux.data, element);//validacion si la contiene
    }

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            first = last = newNode;//se ve el primer nodo
        }
        else{
            last.next = newNode;
            last = newNode;
            
            //hago el enlace circular
            last.next = first;//en donde conecto el apuntador de last el ultimo nodo
        }
    }

    @Override
    public void addFirst(Object element) {
        Node newNode = new Node(element);
        if(isEmpty())
            first = newNode;
        else{
           newNode.next = first; 
           first = newNode;
        }
        //hacemos que la lista sea circular
        last.next = first;//hay que mover los apuntadores
    }

    @Override
    public void addLast(Object element) {
        add(element);
    }

      @Override
    public void addInSortedList(Object element) {
        Node newNode = new Node(element);
        //CASO 1. LA LISTA ESTA VACIA
        if(isEmpty()){
            first = last = newNode;
        }else{
            //CASO 2. first.next es nulo, o no es nulo
            //y el elemento a insertar es menor al del inicio
            if(util.Utility.greaterT(first.data, element)){
                newNode.next = first;
                first = newNode;
            }else{
                //CASE 3. TODO LO DEMAS
                Node prev = first;
                Node aux = first.next;
                boolean added=false;
                while(aux!=last&&!added){
                    if(util.Utility.lessT(element, aux.data)){
                        prev.next = newNode;
                        newNode.next = aux;
                        added = true;
                    }
                    prev = aux;
                    aux = aux.next;
                }
                //si llega aqui, se enlaza cuando aux==last
                if(util.Utility.lessT(element, aux.data)&& !added){
                    prev.next = newNode;
                    newNode.next = aux;
                }else //en este caso, se enlaza al final
                    if(!added){
                        aux.next = newNode;
                        //muevo last al ult nodo
                        last = newNode;
                    }
            }
        }
        //hago el enlace circular
        last.next = first;
    }

    @Override
    public void remove(Object element) throws ListException {
        if(isEmpty())
            throw new ListException("Circular Linked List is empty");
        //caso 1. El elemento a suprimir es el primero
        if(util.Utility.equals(first.data, element)){
            first = first.next;
        }  
        //case 2. El elemento a suprimir puede estar donde sea
        else{
            Node prev = first; //elemento anterior
            Node aux = first.next; //elemento sgte
            while(aux!=last&&!util.Utility.equals(aux.data, element)){
                prev = aux; //actualizo anterior
                aux = aux.next;
            }
            //se sale cuando alcanza aux==last
            //o cuando encuentra el elemento a suprimir
            if(util.Utility.equals(aux.data, element)){
                //desenlanza el nodo
                prev.next = aux.next;
                   //debo asegurarme q last apunte al ultimo nodo
                   //entendiendo que aux esta en el nodo a suprimir
                if(aux==last){ //estamos en el ultimo nodo
                    last=prev;
                }
            }
        }
        //mantengo el enlace circular
        last.next = first;
        //q pasa si solo queda un nodo
        //y es el q quiero eliminar
        if(first==last&&util.Utility.equals(first.data, element)){
            clear(); //anulo la lista
        }
    }

    @Override
    public Object removeFirst() throws ListException {
        if(isEmpty())
            throw new ListException("Circular Linked List is empty");
        Object element = first.data;
        first = first.next;
        //hago el enlace circular
        last.next = first;
        return element;
    }

     @Override
    public Object removeLast() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Linked List is empty");
        }
        Node aux = first;
        Node prev = first; //para dejar rastro, apunta al anterior de aux
        while(aux!=last){
            prev = aux; //un nodo atras de aux
            aux = aux.next;
        }
        //se sale del while cuando esta en el ultimo nodo
        Object element = aux.data;
        //prev.next = first; //desconecto el ultimo nodo
        last = prev;
        //mantengo el enlace circular
        last.next = first;
        if(first==last){
            clear(); //anulo la lista
        }
        
        return element;
    }
 

    @Override
    public void sort() throws ListException {
         
        Node current = first, index = null;
        Object temp;
        if(isEmpty()){
            throw new ListException("Circular Linked List is empty");
        } else {
            do {
                 
                index = current.next;
                while (index != first) {
               
                    if (util.Utility.lessT(index.data, current.data)) {
                        temp = current.data;
                        current.data = index.data;
                        index.data = temp;
                    }
                    index = index.next;
                }
                current = current.next;
            } while (current.next != first);
        }  
    }
    
    public void sortName() throws ListException{
         Node current = first, index = null;
        Object temp;
        if(isEmpty()){
            throw new ListException("Circular Linked List is empty");
        } else {
            do {
                 
                index = current.next;
                while (index != first) {
               
                    if (util.Utility.lessTName(index.data, current.data)) {
                        temp = current.data;
                        current.data = index.data;
                        index.data = temp;
                    }
                    index = index.next;
                }
                current = current.next;
            } while (current.next != first);
        }  
        
    }

    @Override
    public int indexOf(Object element) throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Linked List is empty");
        }
        Node aux = first;
        int index=1;
        while(aux!=last){
            if(util.Utility.equals(aux.data, element)){
                return index;
            }
            index++;
            aux = aux.next; //lo movemos al sgte nodo
        }
        //se sale cuando aux apunta a last (al ultimo nodo)
        if(util.Utility.equals(aux.data, element)){
            return index;
        }
        return -1; //indica q el elemento no existe
    }

    @Override
    public Object getFirst() throws ListException {
        if(isEmpty())
            throw new ListException("Circular Linked List is empty");
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Linked List is empty");
        }
        return last.data; //es el ultimo en la lista
    }

    @Override
    public Object getPrev(Object element) throws ListException {
       if(this.isEmpty()){
            throw new ListException("Circular Linked List is Empty");
        }
        //Caso 1. Si es el inicio, no tiene anterior
        if(util.Utility.equals(first.data, element)){
            return "Es el inicio, no tiene anterior";
        }
        //Caso 2. Todo lo demas
        Node aux = first;
        while(aux.next!=last){
            if(util.Utility.equals(aux.next.data, element)){
                return aux.data;
            }
            aux = aux.next;
        }
        //se sale cuando aux.next apunta a last
        if(util.Utility.equals(aux.next.data, element)){
                return aux.data;
        }
        return "El elemento no existe en la lista";
    }

    @Override
    public Object getNext(Object element) throws ListException {
        if (this.isEmpty()) {
            throw new ListException("Circular Linked List is Empty");
        }
        Node aux = first;
        while (aux != last) {
            if (util.Utility.equals(aux.data, element)) {
                if (aux.next != null) {
                    return aux.next.data;
                } else {
                    return "no tiene";
                }
            }
            aux = aux.next;
        }
        //se sale cuando aux==last
        if (util.Utility.equals(aux.data, element)) {
            return aux.next.data;
        }
        return "El elemento no existe en la lista";
    }

    @Override
    public Node getNode(int index) throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Linked List is empty");
        }
        Node aux = first;
        int i = 1; //posicion del primer nodo
        while(aux!=last){
            if(util.Utility.equals(i, index)){
                return aux;
            }
            i++;
            aux = aux.next; //lo movemos al sgte nodo
        }
        //se sale cuando aux==last
        if(util.Utility.equals(i, index)){
            return aux;
        }
        return null; //si llega aqui no encontro el nodo
    }

    @Override
    public String toString() {
        String result = "Circular Linked List Content\n";
        Node aux = first;
        while(aux!=last){
           result+=aux.data+"\n";
           aux = aux.next;
        }
        //agregamos la info del ultimo nodo
        return result+"\n"+aux.data;//poruqe cunado es las no lo toma en cuenta
    }
    
    
}
