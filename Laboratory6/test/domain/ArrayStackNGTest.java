/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/EmptyTestNGTest.java to edit this template
 */
package domain;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.testng.annotations.Test;

/**
 *
 * @author Profesor Gilberth Chaves A <gchavesav@ucr.ac.cr>
 */
public class ArrayStackNGTest {
    
    public ArrayStackNGTest() {
    }

    @Test
    public void test() {
        try {
            ArrayStack s = new ArrayStack(10);
            s.push(10); s.push(3); s.push(5);
            s.push(20); s.push(30); s.push(40);
            System.out.println(s.toString());
            System.out.println(s.toString());
             System.out.println(s.toString());
        } catch (StackException ex) {
            Logger.getLogger(ArrayStackNGTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
