/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Functions;

import granitainterpreter.AstNode;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Parameter extends AstNode {
    String type, id;
    
    public Parameter(int line) {
        super(line);
    }

    public Parameter(String type, String id, int line) {
        super(line);
        this.type = type;
        this.id = id;
    }
    
}
