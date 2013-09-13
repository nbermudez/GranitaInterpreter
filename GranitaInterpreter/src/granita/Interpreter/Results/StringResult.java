/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Interpreter.Results;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class StringResult extends Result {
    String value;

    public StringResult(String value) {
        this.value = value;
    }
    
    @Override
    public Object getValue() {
        return this.value;
    }
    
}
