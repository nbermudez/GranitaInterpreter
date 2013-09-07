/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.DataLayout;

import granita.Parser.Expressions.LitInt;
import granita.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ArrayVariable extends Variable {
    LitInt size;
    Type items[];

    public ArrayVariable(Type type, LitInt size) {
        super(type);
        this.size = size;
        this.items = new Type[size.getValue()];
        for (int i = 0; i < size.getValue(); i++) {
            this.items[i] = type.getCopy();
        }
    }
    
    public LitInt getSize() {
        return size;
    }

    public void setSize(LitInt size) {
        this.size = size;
    }

    public Type[] getItems() {
        return items;
    }

    public void setItems(Type[] items) {
        this.items = items;
    }

    @Override
    public Variable getCopy() {
        ArrayVariable var = new ArrayVariable(type.getCopy(), size);
        var.setVarName(varName);
        return var;
    }
    
}
