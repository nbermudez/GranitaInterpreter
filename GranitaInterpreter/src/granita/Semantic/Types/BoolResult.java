/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.Types;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class BoolResult extends Result {
    Boolean value;

    public BoolResult(Boolean value) {
        this.value = value;
    }
    
    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }
    
}
