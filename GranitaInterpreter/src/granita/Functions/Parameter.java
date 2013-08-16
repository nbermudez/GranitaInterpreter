/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Functions;

import granita.Statements.Statement;



/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Parameter extends Statement {
    String type, name;
    
    public Parameter(int line) {
        super(line);
    }
    
    public Parameter(String type, String name, int line) {
        super(line);
        this.type = type;
        this.name = name;
    }
    
}
