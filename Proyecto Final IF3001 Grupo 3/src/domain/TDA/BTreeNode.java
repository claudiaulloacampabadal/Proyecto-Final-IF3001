/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain.TDA;

/**
 *
 * @author Profesor Gilberth Chaves A <gchavesav@ucr.ac.cr>
 */
public class BTreeNode {
    public Object data;
    public BTreeNode left, right;
    public String sequence; //representa la ruta donde agrego el elemento
    
    //Constructor
    public BTreeNode(Object data){
        this.data = data;
        this.left=this.right=null;
    }
    
    //Constructor 2
    public BTreeNode(Object data, String sequence){
        this.data = data;
        this.sequence = sequence;
        this.left=this.right=null;
    }
}
