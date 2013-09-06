/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.IR.Functions.D_Argument;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_Print extends D_Statement {
    ArrayList<D_Argument> arguments;

    public D_Print(ArrayList<D_Argument> arguments) {
        this.arguments = arguments;
    }
    
    @Override
    public void execute() {
        for (D_Argument argument : arguments) {
            System.out.print(argument.evaluate());
        }
    }
    
}
