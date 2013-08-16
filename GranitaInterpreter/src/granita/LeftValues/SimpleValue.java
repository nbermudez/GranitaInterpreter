/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.LeftValues;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SimpleValue extends LeftValue{
    String id;
    
    public SimpleValue(int line) {
        super(line);
    }

    public SimpleValue(String id, int line) {
        super(line);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
}
