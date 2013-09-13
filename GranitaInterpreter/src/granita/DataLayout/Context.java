/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.DataLayout;

import granita.Interpreter.DataLayout.RE_Variable;
import granita.SymbolTable.SymbolTableEntry;
import granita.SymbolTable.SymbolTableTree;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Context {
    private HashMap<String, Variable> variables;
    private Context parentContext;
    private RE_Variable[] re_variables;
    private int variableIndex = 0, size;

    public Context(Context parentContext) {
        this.variables = new HashMap<String, Variable>();
        this.parentContext = parentContext;
    }

    public Context() {
        this.variables = new HashMap<String, Variable>();
    }
    
    public void initialize(int size) {
        this.size = size;
        this.re_variables = new RE_Variable[size];
    }
    
    public boolean hasParent() {
        return this.parentContext != null;
    }
    
    public Context getParent() {
        return this.parentContext;
    }
    
    public void setParent(Context parent) {
        this.parentContext = parent;
    }
    
    public void add(String key, Variable value) {
        if (!this.variables.containsKey(key)) {
            this.variables.put(key, value);
        }
    }
    
    public void add(int key, RE_Variable value) {
        this.re_variables[key] = value;
    }
    
    public void set(String key, Object value) {
        if (this.variables.containsKey(key)) {
            Variable v = this.variables.get(key);
            v.type.setValue(value);
        } else if (parentContext != null){
            parentContext.set(key, value);
        }
    }
    
    public void set(String key, int index, Object value) {
        ArrayVariable v = (ArrayVariable) SymbolTableTree.getInstance().getGlobal().getEntry(key);
        v.items[index].setValue(value);
    }
    
    private Variable get(String key) {
        return this.variables.get(key);
    }
    
    public Variable find(String key) {
        Variable var = this.get(key);
        if (var == null && parentContext != null) {
            var = parentContext.find(key);
        } else if (var == null && parentContext == null) {
            SymbolTableEntry s = SymbolTableTree.getInstance().getGlobal().getEntry(key);
            if (s instanceof Variable) {
                var = (Variable) s;
            }
        }
        return var;
    }
    
    public Variable findLocally(String key) {
        return this.get(key);
    }
    
    public int getVariableIndex() {
        int value = this.variableIndex;
        this.variableIndex ++;
        return value;
    }
    
    public void setVariableIndexInitValue(int value) {
        this.variableIndex = value;
    }
    
    public int getSize() {
        return this.size;
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
        
        //<editor-fold defaultstate="collapsed" desc="Nuevo">
        copy.initialize(size);
        for (int i = 0; i < re_variables.length; i++) {
            RE_Variable rE_Variable = re_variables[i];
            copy.add(i, rE_Variable);
        }
        //</editor-fold>
    }
    
    public void copyFrom(Context copy) {
        this.clear();
        Set<String> keys = copy.variables.keySet();
        
        for (String key : keys) {
            this.add(key, copy.get(key).getCopy());
        }
        
        this.parentContext = copy.parentContext;     
        
        //<editor-fold defaultstate="collapsed" desc="Nuevo">
        this.initialize(copy.size);
        for (int i = 0; i < copy.re_variables.length; i++) {
            RE_Variable rE_Variable = copy.re_variables[i];
            this.add(i, rE_Variable);
        }
        //</editor-fold>
    }
    
    public void merge(Context other) {
        Set<String> keys = other.variables.keySet();
        
        for (String key : keys) {
            this.add(key, other.get(key).getCopy());
        }
        
        if (other.re_variables == null) {
            return;
        }
        for (int i = 0; i < other.re_variables.length; i++) {
            RE_Variable rE_Variable = other.re_variables[i];
            this.add(i, rE_Variable);
        }
    }
    
    public void print() {
        Set<String> keys = this.variables.keySet();
        
        for (String key : keys) {
            System.out.println(key + ", " + get(key).getType().getValue());
        }
    }
}
