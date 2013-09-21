/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.DataLayout;

import granita.IR.Statements.D_Block;
import granita.Semantic.SymbolTable.SymbolTableEntry;
import granita.Semantic.Types.Type;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class Function extends SymbolTableEntry {
    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    private Type type;
    private ArrayList<SimpleVariable> parameters;
    private D_Block body;
    //</editor-fold>    
    
    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Function(Type type){
        this.type = type;
        this.parameters = new ArrayList<SimpleVariable>();
    }
    
    public Function(Type type, ArrayList<SimpleVariable> parameters) {
        this.type = type;
        this.parameters = parameters;
    }
    //</editor-fold>    
    
    public Function getCopy(){
        Function copy = new Function(type.getCopy());
        for (SimpleVariable simpleVariable : parameters) {
            copy.parameters.add(simpleVariable.getCopy());
        }
        copy.setBody(body);
        return copy;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
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
    //</editor-fold>
    
}
