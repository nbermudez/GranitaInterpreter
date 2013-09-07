/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.DataLayout;

import java.util.HashMap;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Context {
    private HashMap<String, Variable> variables;
    
    public void add(String key, Variable value) {
        this.variables.put(key, value);
    }
    
    public Variable get(String key) {
        return this.variables.get(key);
    }
    
    public void remove(String key) {
        this.variables.remove(key);
    }
    
    public Context copy() {
        Context copy = new Context();
        
        return copy;
    }
}
