/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.DataLayout;

import granita.IR.Expressions.D_LitInt;
import granita.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ArrayVariable extends Variable {
    D_LitInt size;
    Type items[];

    public ArrayVariable(Type type, D_LitInt size) {
        super(type);
        this.size = size;
        this.items = new Type[size.getValue()];
        for (int i = 0; i < size.getValue(); i++) {
            this.items[i] = type.getCopy();
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public D_LitInt getSize() {
        return size;
    }

    public void setSize(D_LitInt size) {
        this.size = size;
    }

    public Type[] getItems() {
        return items;
    }

    public void setItems(Type[] items) {
        this.items = items;
    }
    //</editor-fold>    

    @Override
    public String toString() {
        return "Array of Size " + size.getValue();
    }

    @Override
    public Variable getCopy() {
        ArrayVariable var = new ArrayVariable(type.getCopy(), size);
        var.setVarName(varName);
        return var;
    }
    
}
