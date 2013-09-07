/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.IR.LeftValues.D_LeftValue;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_Read extends D_Statement {
    ArrayList<D_LeftValue> variables;

    public D_Read(ArrayList<D_LeftValue> variables) {
        this.variables = variables;
    }
    
    @Override
    public void execute() {
        
    }
    
}
