/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Interpreter.Results;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class BoolResult extends Result {
    Boolean value;

    public BoolResult(Boolean value) {
        this.value = value;
    }
    
    @Override
    public Boolean getValue() {
        return this.value;
    }
    
    public void setValue(Boolean value) {
        this.value = value;
    }
    
}
