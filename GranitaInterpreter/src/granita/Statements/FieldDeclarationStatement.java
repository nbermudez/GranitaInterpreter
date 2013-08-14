/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granita.Statements.Statement;
import granitainterpreter.GranitaException;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class FieldDeclarationStatement extends Statement{

    public ArrayList<Statement> declarations;

    public FieldDeclarationStatement(int line) {
        super(line);
    }    
    
    
}
