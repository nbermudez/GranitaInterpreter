/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Tests;

import granita.DataLayout.ArrayVariable;
import granita.DataLayout.Context;
import granita.IR.Expressions.D_LitInt;
import granita.DataLayout.ContextStack;
import granita.Types.IntType;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ContextStackTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ContextStack stack = new ContextStack();
        Context c1 = new Context(null);
        Context c2 = new Context(c1);
        
        c1.add("x", new ArrayVariable(new IntType(), new D_LitInt(10)));
        c1.print();
        System.out.println("-----------------------------------------");
        
        stack.push(c1);
        stack.saveContext();
        stack.peek().print();
        System.out.println("-----------------------------------------");
        
        c1.add("y", new ArrayVariable(new IntType(), new D_LitInt(7)));
        stack.peek().print();
        System.out.println("-----------------------------------------");
        
        stack.loadContext();
        stack.peek().print();
        System.out.println("-----------------------------------------");
        
        stack.push(c2);
        stack.saveContext();
        c1.add("y", new ArrayVariable(new IntType(), new D_LitInt(7)));
        c1.print();
        System.out.println("-----------------------------------------");
        
        if (c2.find("y") != null) {
            System.out.println("y found!!");
        }
    }
}
