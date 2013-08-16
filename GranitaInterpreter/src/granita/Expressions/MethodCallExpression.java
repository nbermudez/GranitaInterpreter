/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Expressions;

import granita.Statements.Statement;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class MethodCallExpression extends Expression {
    
    ArrayList<Statement> arguments;
    
    public MethodCallExpression(int line) {
        super(line);
    }
    
}
