/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.DataLayout;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Context {
    private HashMap<String, Variable> variables;
    private Context parentContext;

    public Context(Context parentContext) {
        this.variables = new HashMap<String, Variable>();
        this.parentContext = parentContext;
    }

    public Context() {
        this.variables = new HashMap<String, Variable>();
    }
    
    public boolean hasParent() {
        return this.parentContext != null;
    }
    
    public void setParent(Context parent) {
        this.parentContext = parent;
    }
    
    public void add(String key, Variable value) {
        this.variables.put(key, value);
    }
    
    private Variable get(String key) {
        return this.variables.get(key);
    }
    
    public Variable find(String key) {
        Variable var = this.get(key);
        if (var == null && parentContext != null) {
            var = parentContext.find(key);
        }
        return var;
    }
    
    public void clear() {
        this.variables.clear();
    }
    
    public void copyTo(Context copy) {
        copy.clear();
        Set<String> keys = this.variables.keySet();
        
        for (String key : keys) {
            copy.add(key, this.get(key).getCopy());
        }
        
        copy.parentContext = this.parentContext;
    }
    
    public void copyFrom(Context copy) {
        this.clear();
        Set<String> keys = copy.variables.keySet();
        
        for (String key : keys) {
            this.add(key, copy.get(key).getCopy());
        }
        
        this.parentContext = copy.parentContext;
    }
    
    public void print() {
        Set<String> keys = this.variables.keySet();
        
        for (String key : keys) {
            System.out.println(key + ", " + get(key));
        }
    }
}
