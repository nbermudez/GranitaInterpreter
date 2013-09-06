/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.General;

import granita.IR.Statements.D_Statement;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_Program extends D_Statement {
    ArrayList<D_Statement> methodPrototypes;

    public D_Program() {
        this.methodPrototypes = new ArrayList<D_Statement>();
    }

    public D_Program(ArrayList<D_Statement> methodPrototypes) {
        this.methodPrototypes = methodPrototypes;
    }
    
    @Override
    public void execute() {
        
    }
    
}
