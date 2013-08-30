/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Semantic.SymbolTable;

import granita.Parser.Expressions.LitInt;
import granita.Semantic.Types.Type;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ArrayVariable extends SymbolTableValue {
    Type type;
    LitInt size;
    Type items[];

    public ArrayVariable(Type type, LitInt size) {
        this.type = type;
        this.size = size;
        this.items = new Type[size.getValue()];
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
    
}
