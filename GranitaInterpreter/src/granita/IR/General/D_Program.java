/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.General;

import granita.IR.Statements.D_Block;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_Program {
    D_Block startPoint;

    public D_Program(D_Block startPoint) {
        this.startPoint = startPoint;
    }
    
    public void execute() {
        if (startPoint != null) {
            startPoint.execute();
        }
    }
    
}
