/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.SymbolTable;

import granita.Parser.Statements.BlockStatement;
import granita.Semantic.Types.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Function extends SymbolTableValue {
    private Type type;
    //private HashMap<String, SimpleVariable> localSymbolTable;
    private ArrayList<SimpleVariable> parameters;
    private BlockStatement block;
    
    public Function(Type type){
        this.type = type;
        //this.localSymbolTable = new HashMap<String, SimpleVariable>();
        this.parameters = new ArrayList<SimpleVariable>();
    }
    
    public Function getCopy(){
        Function copy = new Function(type.getCopy());
        for (SimpleVariable simpleVariable : parameters) {
            copy.parameters.add(simpleVariable.getCopy());
        }
        copy.setBlock(block.getCopy());
        return copy;
    }
    
    public Function(Type type, BlockStatement block){
        this.type = type;
        //this.localSymbolTable = new HashMap<String, SimpleVariable>();
        this.parameters = new ArrayList<SimpleVariable>();
        this.block = block;
    }

    public Function(Type type, ArrayList<SimpleVariable> parameters) {
        this.type = type;
        this.parameters = parameters;
    }
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public ArrayList<SimpleVariable> getParameters() {
        return parameters;
    }

    public void setParameters(ArrayList<SimpleVariable> parameters) {
        this.parameters = parameters;
    }

    public BlockStatement getBlock() {
        return this.block;
    }

    public void setBlock(BlockStatement block) {
        this.block = block;
    }
    
    public Variable getVariable(String id) {
        return this.block.getVariable(id);
    }
}
