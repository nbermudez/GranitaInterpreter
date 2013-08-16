/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Values;

import granita.Semantic.Types.Type;
import java.util.Hashtable;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Function extends Value{
    Type type;
    Hashtable<String, Var> localTable;
    Hashtable<String, Var> params;    
    
    public Function(){
        this.localTable = new Hashtable<String, Var>();
        this.params = new Hashtable<String, Var>();
    }
    
    public Function(Type type, Hashtable<String, Var> localTable, Hashtable<String, Var> params) {
        this.type = type;
        this.localTable = localTable;
        this.params = params;        
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Hashtable<String, Var> getLocalTable() {
        return localTable;
    }

    public void setLocalTable(Hashtable<String, Var> localTable) {
        this.localTable = localTable;
    }

    public Hashtable<String, Var> getParams() {
        return params;
    }

    public void setParams(Hashtable<String, Var> params) {
        this.params = params;
    }    
    
}
