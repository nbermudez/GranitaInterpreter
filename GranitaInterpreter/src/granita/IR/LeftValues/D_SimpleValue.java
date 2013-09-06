/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.LeftValues;

import granita.Semantic.SymbolTable.SimpleVariable;
import granitainterpreter.Interpreter;


/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_SimpleValue extends D_LeftValue {

    public D_SimpleValue(String identifier) {
        super(identifier);
        SimpleVariable val = (SimpleVariable) Interpreter.getInstance().getVariable(identifier);
        this.setExpressionType(val.getType());
    }

    @Override
    public Object evaluate() {
        return this.getExpressionType().getValue();
    }
    
}
