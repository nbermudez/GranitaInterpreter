/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.DataLayout;

import granita.Interpreter.DataLayout.RE_Variable;
import granita.Interpreter.Results.Result;
import granita.Semantic.SymbolTable.SymbolTableEntry;
import granita.Semantic.SymbolTable.SymbolTableTree;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class Context {

    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    private HashMap<String, Variable> variables;
    private Context parentContext;
    private RE_Variable[] re_variables;
    private int variableIndex = 0, size;
    private int contextId;
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Context(Context parentContext) {
        this.variables = new HashMap<String, Variable>();
        this.parentContext = parentContext;
    }

    public Context() {
        this.variables = new HashMap<String, Variable>();
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Shared">
    public int getVariableIndex() {
        int value = this.variableIndex;
        this.variableIndex++;
        return value;
    }

    public void setVariableIndexInitValue(int value) {
        this.variableIndex = value;
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
    //</editor-fold>     
    
    //<editor-fold defaultstate="collapsed" desc="Semantic Analysis usage">
    public void add(String key, Variable value) {
        if (!this.variables.containsKey(key)) {
            this.variables.put(key, value);
        }
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

    public int findInRE(String key) {
        for (int i = 0; i < re_variables.length; i++) {
            RE_Variable rE_Variable = re_variables[i];
            if (rE_Variable != null && rE_Variable.getName().equals(key)) {
                return i;
            }
        }
        if (parentContext != null) {
            return parentContext.findInRE(key);
        }
        return -1;
    }

    public Variable findLocally(String key) {
        return this.get(key);
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Copy utils">
    public void copyTo(Context copy) {
        copy.clear();
        Set<String> keys = this.variables.keySet();

        for (String key : keys) {
            copy.add(key, this.get(key).getCopy());
        }

        copy.parentContext = this.parentContext;
        copy.contextId = this.contextId;

        //<editor-fold defaultstate="collapsed" desc="Nuevo">
        copy.initialize(re_variables.length);
        for (int i = 0; i < re_variables.length; i++) {
            RE_Variable rE_Variable = re_variables[i].getCopy();
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
        this.contextId = copy.contextId;

        //<editor-fold defaultstate="collapsed" desc="Nuevo">
        this.initialize(copy.re_variables.length);
        for (int i = 0; i < copy.re_variables.length; i++) {
            RE_Variable rE_Variable = copy.re_variables[i].getCopy();
            this.add(i, rE_Variable);
        }
        //</editor-fold>
    }
    
    public Context getCopy() {
        Context copy = new Context();
        this.copyTo(copy);
        return copy;
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
            RE_Variable rE_Variable = other.re_variables[i].getCopy();
            this.add(i, rE_Variable);
        }
    }
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Context ID">
    public int getContextId() {
        return contextId;
    }

    public void setContextId(int contextId) {
        this.contextId = contextId;
    }
    
    public int getContextId(String name) {
        if (this.findLocally(name) != null) {
            return this.contextId;
        } else if (this.parentContext != null){
            return parentContext.getContextId(name);
        } else {
            return -1;
        }
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Interpretation usage">
    
    //<editor-fold defaultstate="collapsed" desc="Return Value">
    private int returnValueContext = -1;

    public int getReturnValueContext() {
        return returnValueContext;
    }

    public void setReturnValueContext(int returnValueContext) {
        this.returnValueContext = returnValueContext;
    }

    public void hasReturnValue(boolean value) {
        if (value) {
            returnValueContext = contextId;
        }
    }
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="RE_Variable related">
    public void add(int key, RE_Variable value) {
        this.re_variables[key] = value;
    }
    
    public void setRuntimeEnvironment(RE_Variable[] array) {
        this.re_variables = array;
    }
    
    public RE_Variable getVariableRE(int index) {
        return this.re_variables[index];
    }
    
    public void setVariableInRE(int contextId, int index, Result value) {
        if (this.contextId == contextId) {
            re_variables[index].setValue(value);
        } else if (this.parentContext != null) {
            parentContext.setVariableInRE(contextId, index, value);
        }
    }
    
    public RE_Variable findVariableInRE(int contextId, int index) {
        if (this.contextId == contextId) {
            return re_variables[index];
        } else if (this.parentContext != null) {
            return parentContext.findVariableInRE(contextId, index);
        } else {
            return null;
        }
    }
    
    public void setArrayItemInRE(int contextId, int index, int arrayIndex, Result value) {
        if (this.contextId == contextId) {
            granita.Interpreter.DataLayout.ArrayVariable var = (granita.Interpreter.DataLayout.ArrayVariable)re_variables[index];
            
            RE_Variable item = var.getItem(arrayIndex);
            if (item != null) {
                item.setValue(value);
            } else {
                var.setItemValue(arrayIndex, value);
            }
        } else if (parentContext != null) {
            parentContext.setArrayItemInRE(contextId, index, arrayIndex, value);
        }
    }
    //</editor-fold>
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Misc">
    public int getSize() {
        return this.size;
    }

    public void clear() {
        this.variables.clear();
    }
    //</editor-fold>
}
