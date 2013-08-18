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
public class ParameterDeclaration {
    String type, name;
    int line;
    
    public ParameterDeclaration(int line) {
        this.line = line;
    }
    
    public ParameterDeclaration(String type, String name, int line) {
        this.line = line;
        this.type = type;
        this.name = name;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
    
}
