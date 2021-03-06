/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Types;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class StringType extends Type {
    
    String value;

    public StringType(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
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
        if (value instanceof String){
            this.value = (String) value;
        }
    }

    @Override
    public Type getCopy() {
        return new StringType(value);
    }
    
}
