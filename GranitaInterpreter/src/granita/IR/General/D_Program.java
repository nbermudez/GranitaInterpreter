/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.General;

import granita.IR.Statements.D_Block;
import granita.IR.Statements.D_Statement;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_Program extends D_Statement {
    D_Block startPoint;

    public D_Program(D_Block startPoint) {
        this.startPoint = startPoint;
    }
    
    @Override
    public void execute() {
        if (startPoint != null) {
            startPoint.execute();
        }
    }
    
}
