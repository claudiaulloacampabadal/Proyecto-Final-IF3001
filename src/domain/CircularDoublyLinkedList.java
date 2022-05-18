/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author Usuario
 */
public class CircularDoublyLinkedList implements List{
    
    private Node first; //apunta al inicio de la lista
     private Node last; //apunta al ult nodo de la lista

    //Constructor
    public CircularDoublyLinkedList(){
        this.first = this.last = null;
    }

    @Override
    public int size() throws ListException {
        if(isEmpty())
            throw new ListException("Circular Doubly Linked List is empty");
        Node aux = first;
        int counter = 0;
        while(aux!=last){
           counter++;
           aux = aux.next;
        }
        //sale del while cuando aux esta en el ult nodo
        return counter+1;
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
            throw new ListException("Circular Doubly Linked List is empty");
        Node aux = first;
        while(aux!=last){
            if(util.Utility.equals(aux.data, element))
                return true;
           aux = aux.next;
        }
        //sale del while cuando aux esta en el ult nodo
        return util.Utility.equals(aux.data, element);
    }

    @Override
    public void add(Object element) {
        Node newNode = new Node(element);
        if(isEmpty()){
            first = last = newNode;
        }
        else{
            last.next = newNode;
            last = newNode;
            
            //hago el enlace circular
            last.next = first;
            //hago el doble enlace
            first.prev = last;
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
        //hago el enlace circular y doble
        last.next = first;
        first.prev = last;
    }

    @Override
    public void addLast(Object element) {
        add(element);
    }

    @Override
    public void addInSortedList(Object element) {
       
    }

    @Override
    public void remove(Object element) throws ListException {
        if(isEmpty())
            throw new ListException("Circular Doubly Linked List is empty");
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
                //mantengo el doble enlace
                aux.next.prev = prev;//Opcion 2
                //otra alternativa
//                Node aux2 = aux.next;
//                aux2.prev = prev;//Opcion 2
                
                
                
                
                 //debo asegurarme q last apunte al ultimo nodo
                if(aux==last){ //estamos en el ultimo nodo
                    last=prev;
                }
            }
        }
        //mantengo el enlace circular y doble
        last.next = first;
        first.prev = last;
        //q pasa si solo queda un nodo
        //y es el q quiero eliminar
        if(first==last&&util.Utility.equals(first.data, element)){
            clear(); //anulo la lista
        }
    }

    @Override
    public Object removeFirst() throws ListException {
        if(isEmpty())
            throw new ListException("Circular Doubly Linked List is empty");
        Object element = first.data;
        first = first.next;
        //hago el enlace circular y doble
        last.next = first;
        first.prev = last;
        return element;
    }

    @Override
    public Object removeLast() throws ListException {
         if(isEmpty())
            throw new ListException("Circular Doubly Linked List is empty");
        //case 2. El elemento a suprimir puede estar donde sea
        Object element = last.data;
            Node prev = first; //elemento anterior
            Node aux = first.next; //elemento sgte
            while(aux!=last){
                prev = aux; //actualizo anterior
                aux = aux.next;
            }
            //se sale cuando alcanza aux==last
            //o cuando encuentra el elemento a suprimir
            if(util.Utility.equals(aux.data, element)){
                //desenlanza el nodo
                prev.next = aux.next;
                //mantengo el doble enlace
                aux.next.prev = prev;
              
                 //debo asegurarme q last apunte al ultimo nodo
                if(aux==last){ //estamos en el ultimo nodo
                    last=prev;
                }
        }
        //mantengo el enlace circular y doble
        last.next = first;
        first.prev = last;
        //q pasa si solo queda un nodo
        //y es el q quiero eliminar
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
            throw new ListException("Circular Doubly Linked List is empty");
        } else {
            do {
                 
                index = current.next;
                while (index != first.next) {
               
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
            throw new ListException("Circular Doubly Linked List is empty");
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
            throw new ListException("Circular Doubly Linked List is empty");
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
            throw new ListException("Circular Doubly Linked List is empty");
        return first.data;
    }

    @Override
    public Object getLast() throws ListException {
        if(isEmpty()){
            throw new ListException("Circular Doubly Linked List is empty");
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
            return last.data;//Esto hace que salta el elemento anterri a first que es last
        }
        //Caso 1. Todos los demas casos
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
            throw new ListException("Circular Doubly Linked List is Empty");
        }
        Node aux = first;
        while (aux != last) {
            if (util.Utility.equals(aux.data, element)) {
                if (aux.next != null) {
                    return aux.next.data;
                } else {
                    return first.data;
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
            throw new ListException("Circular Doubly Linked List is empty");
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
        String result = "Circular Doubly Linked List Content\n";
        Node aux = first;
        while(aux!=last){
           result+=aux.data+"\n";
           aux = aux.next;
        }
        //agregamos la info del ultimo nodo
        return result+"\n"+aux.data;
    }
    
    
}
