/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.Interpreter.Interpreter;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_Continue extends D_Statement{

    @Override
    public void execute() {
        Interpreter.continueWasReached(true);
    }
    
}
