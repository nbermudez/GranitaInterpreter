/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.SymbolTable;

import granita.Parser.Expressions.Expression;
import granita.Parser.Statements.BlockStatement;
import granita.Semantic.Types.Type;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Function extends SymbolTableValue {
    private Type type;
    private HashMap<String, Variable> localSymbolTable;
    private ArrayList<Variable> parameters;
    private BlockStatement block;
    
    public Function(Type type){
        this.type = type;
        this.localSymbolTable = new HashMap<String, Variable>();
        this.parameters = new ArrayList<Variable>();
    }
    
    public Function(Type type, BlockStatement block){
        this.type = type;
        this.localSymbolTable = new HashMap<String, Variable>();
        this.parameters = new ArrayList<Variable>();
        this.block = block;
    }

    public Function(Type type, HashMap<String, Variable> localSymbolTable, 
            ArrayList<Variable> parameters) {
        this.type = type;
        this.localSymbolTable = localSymbolTable;
        this.parameters = parameters;
    }
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public HashMap<String, Variable> getLocalSymbolTable() {
        return localSymbolTable;
    }

    public void setLocalSymbolTable(HashMap<String, Variable> localSymbolTable) {
        this.localSymbolTable = localSymbolTable;
    }

    public ArrayList<Variable> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<Variable> parameters) {
        this.parameters = parameters;
    }
    
    public void addSymbolTableEntry(String id, Variable value){
        this.localSymbolTable.put(id, value);
    }
    
    public Variable getSymbolTableEntry(String id){
        if (this.localSymbolTable.containsKey(id)){
            return this.localSymbolTable.get(id);
        }
        return null;
    }
    
    public void deleteSymbolTableEntry(String id){
        this.localSymbolTable.remove(id);
    }

    public BlockStatement getBlock() {
        return block;
    }

    public void setBlock(BlockStatement block) {
        this.block = block;
    }
    
}
