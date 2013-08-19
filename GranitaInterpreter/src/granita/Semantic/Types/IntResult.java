/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.Types;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class IntResult extends Result {
    Integer value;

    public IntResult(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
    
}
