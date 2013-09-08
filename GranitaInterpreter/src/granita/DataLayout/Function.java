/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.DataLayout;

import granita.IR.Statements.D_Block;
import granita.SymbolTable.SymbolTableEntry;
import granita.Types.Type;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Function extends SymbolTableEntry {
    private Type type;
    private ArrayList<SimpleVariable> parameters;
    private D_Block body;
    
    public Function(Type type){
        this.type = type;
        this.parameters = new ArrayList<SimpleVariable>();
    }
    
    public Function getCopy(){
        Function copy = new Function(type.getCopy());
        for (SimpleVariable simpleVariable : parameters) {
            copy.parameters.add(simpleVariable.getCopy());
        }
        //copy.setBlock(body.getCopy());
        return copy;
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

    public D_Block getBody() {
        return body;
    }

    public void setBody(D_Block body) {
        this.body = body;
    }
}
