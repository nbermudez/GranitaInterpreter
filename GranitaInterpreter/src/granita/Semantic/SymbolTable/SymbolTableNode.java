/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.SymbolTable;

import java.util.HashMap;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class SymbolTableNode {

    private SymbolTableNode parent;
    private HashMap<String, SymbolTableValue> table;
    private HashMap<String, Function> functions;

    public SymbolTableNode(SymbolTableNode parent) {
        this.parent = parent;
        this.table = new HashMap<String, SymbolTableValue>();
        this.functions = new HashMap<String, Function>();
    }

    public void addEntry(String id, SymbolTableValue value) {
        this.table.put(id, value);
    }

    public SymbolTableValue getEntry(String id) {
        if (this.table.containsKey(id)) {
            return this.table.get(id);
        }
        if (parent == null) {
            return null;
        } else {
            return parent.getEntry(id);
        }
    }

    public SymbolTableValue findInThisTable(String id) {
        if (this.table.containsKey(id)) {
            return this.table.get(id);
        }
        return null;
    }

    public SymbolTableValue findInParent(String id) {
        if (this.parent == null) {
            return null;
        }
        return this.parent.findInThisTable(id);
    }

    public void deleteEntry(String id) {
        this.table.remove(id);
    }

    public SymbolTableNode getParent() {
        return parent;
    }

    public void setParent(SymbolTableNode parent) {
        this.parent = parent;
    }

    public HashMap<String, SymbolTableValue> getTable() {
        return table;
    }

    public void setTable(HashMap<String, SymbolTableValue> table) {
        this.table = table;
    }

    public HashMap<String, Function> getFunctions() {
        return functions;
    }

    public void setFunctions(HashMap<String, Function> functions) {
        this.functions = functions;
    }
    
    public void addFunction(String methodName, Function t) {
        this.functions.put(methodName, t);
    }
    
    public Function getFunction(String methodName) {
        if (this.functions.containsKey(methodName)) {
            return this.functions.get(methodName);
        }
        if (parent == null) {
            return null;
        } else {
            return parent.getFunction(methodName);
        }
    }
    
    public void deleteFunction(String methodName) {
        this.functions.remove(methodName);
    }
}
