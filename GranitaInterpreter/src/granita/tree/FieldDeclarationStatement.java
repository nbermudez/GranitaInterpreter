/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.tree;

import granitainterpreter.GranitaException;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class FieldDeclarationStatement extends Statement{

    public ArrayList<Statement> declarations;

    public FieldDeclarationStatement() {
    }    
    
    @Override
    public void execute() throws GranitaException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
