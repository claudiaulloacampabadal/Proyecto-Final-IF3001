/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.TDA;

import domain.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class HeaderLinkedQueue implements Queue {
    
      private Node front;//apuntaodr al anterior
    private Node rear;//apuntador al posterior
    private int counter;//contador de la lista

    public HeaderLinkedQueue() {
       this.front = this.rear = new Node();
       this.counter = 0;
    }
    
    
    @Override
    public int size() {
        return counter;
    }

    @Override
    public void clear() {
       this.front = this.rear = new Node();
       this.counter = 0;
    }

    @Override
    public boolean isEmpty() {
        return front == rear;//front y rear apunten al mismo nodo
    }

    @Override
    public int indexOf(Object element) throws QueueException {
         if(isEmpty()){
            throw new QueueException("Array Queue is Empty");
        }
        HeaderLinkedQueue aux = new HeaderLinkedQueue();
        int i  = 0;//index
        int j = -1; //si es -1 no existe
        while(!isEmpty()){
            if(util.Utility.equals(front(), element)){
                j = i;
            }
            aux.enQueue(deQueue());
            i++;
        }
        //Devolvemeos la lista a su estado original con el aux
        while(!aux.isEmpty()){
            enQueue(aux.deQueue());
        }
        
        return j;//retorna -1 si el elemento no esta en la cola
   
    }

    @Override
    public void enQueue(Object element) throws QueueException {
        Node newNode = new Node(element);//creo que nuevo nodo de elemento
        rear.next = newNode;
        rear = newNode;
        //actualizo el counter
        counter++;
    }

    @Override
    public Object deQueue() throws QueueException {
        if(isEmpty())
            throw new QueueException("Header Linked Queue is Empty!");
        Object element = front.next.data;//sigue laflehxa donde esta el lemetno por que el front es un nodo vacion
           //Caso1. Cuando solo hay un elemento
           if(front.next == rear){//.next para saber si tiene un elemento
               clear();//anulo la cola
           }else{//caso2. Hay mas de un elemento
               front.next = front.next.next;
               counter--;//counter debe estar adentro.
           }
        
         return element;  
    }

    @Override
    public boolean contains(Object element) throws QueueException {
        if(isEmpty()){
            throw new QueueException("Array Queue is Empty");
        }
        HeaderLinkedQueue aux = new HeaderLinkedQueue();
        boolean find = false;
        while(!isEmpty()){
            if(util.Utility.equals(front(), element)){
                find = true;
            }
            aux.enQueue(deQueue());
        }
        //Devolvemos la lista a su estado original con el aux
        while(!aux.isEmpty()){
            enQueue(aux.deQueue());
        }
        
        return find;
      
        
    }

    @Override
    public Object peek() throws QueueException {
        if(isEmpty())
            throw new QueueException("Linked Queue is Empty!");
            
        return front.next.data;//porque hay un nodo vacio
    }

    @Override
    public Object front() throws QueueException {
          if(isEmpty())
            throw new QueueException("Linked Queue is Empty!");
            
        return front.next.data;
    }
    
    
       @Override
    public String toString() {
        String result = "Header Linked Queue Content\n";
         HeaderLinkedQueue aux = new HeaderLinkedQueue();
      
        try {
           while(!isEmpty()){
                result += front()+"\n";
                aux.enQueue(deQueue());
            }
        //Devolvemeos la lista a su estado original con el aux
            while(!aux.isEmpty()){
                enQueue(aux.deQueue());
            }
       
       } catch (QueueException ex) {
           Logger.getLogger(HeaderLinkedQueue.class.getName()).log(Level.SEVERE, null, ex);
       }
           
        
        return result;
         
    }
    
    
    
    
    
    
    
}
