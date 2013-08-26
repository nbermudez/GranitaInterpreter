/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.Types;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class StringType extends Type {
    
    StringResult value;

    public StringType(StringResult value) {
        this.value = value;
    }

    public StringResult getValue() {
        return value;
    }

    public void setValue(StringResult value) {
        this.value = value;
    }
    
    @Override
    public boolean equivalent(Type t) {
        return t instanceof StringType;
    }

    @Override
    public String toString() {
        return "string";
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof StringResult){
            this.value = (StringResult) value;
        }
    }
    
}
