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
public class SymbolTable {
    private static SymbolTable instance = null;
    private HashMap<String, SymbolTableValue> table;
    
    private SymbolTable(){
        table = new HashMap<String, SymbolTableValue>();
    }
    
    public static SymbolTable getInstance(){
        if (instance == null){
            instance = new SymbolTable();
        }
        return instance;
    }
    
    public void addEntry(String id, SymbolTableValue value){
        this.table.put(id, value);
    }
    
    public SymbolTableValue getEntry(String id){
        if (this.table.containsKey(id)){
            return this.table.get(id);
        }
        return null;
    }
    
    public void deleteEntry(String id){
        this.table.remove(id);
    }
}
