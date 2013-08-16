/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.LeftValues;

import granita.Expressions.Expression;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ArrayIndexLeftValue extends LeftValue{

    String id;
    Expression index;
    
    public ArrayIndexLeftValue(int line) {
        super(line);
    }

    public ArrayIndexLeftValue(String id, Expression index, int line) {
        super(line);
        this.id = id;
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Expression getIndex() {
        return index;
    }

    public void setIndex(Expression index) {
        this.index = index;
    }
    
}
