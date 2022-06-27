/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.TDA;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.annotations.Test;

/**
 *
 * @author Maria Celeste
 */
public class DoublyLinkedListNGTest {
    
    public DoublyLinkedListNGTest() {
    }

    @Test
    public void testSomeMethod() {
        
        DoublyLinkedList list = new DoublyLinkedList();
        try {
            for (int i = 0; i < 10; i++) {
                list.add(util.Utility.random(40));
            }

             System.out.println(list.toString());
            int counter = 0;
            while (counter < 2) {

                int value = util.Utility.random(40);
                if (list.contains(value)) {
                    list.remove(value);
                    counter++;
                }
            }
             System.out.println("\n REMOVING ELEMENTS ....");
            System.out.println(list.toString());

        } catch (ListException ex) {
            Logger.getLogger(DoublyLinkedListNGTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
