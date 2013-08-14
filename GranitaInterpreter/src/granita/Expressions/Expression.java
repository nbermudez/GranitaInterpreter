/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Expressions;

import granitainterpreter.AstNode;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public abstract class Expression extends AstNode{

    public Expression(int line) {
        super(line);
    }
}
