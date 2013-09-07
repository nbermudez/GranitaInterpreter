/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.LeftValues;

import granita.DataLayout.SimpleVariable;
import granita.Types.Type;
import granitainterpreter.Interpreter;


/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_SimpleValue extends D_LeftValue {

    public D_SimpleValue(String identifier) {
        super(identifier);
        //SimpleVariable val = (SimpleVariable) Interpreter.getInstance().getVariable(identifier);
        //this.setExpressionType(val.getType());
    }

    @Override
    public Object evaluate() {
        /*
        SimpleVariable val = (SimpleVariable) Interpreter.getInstance().getVariable(id);
        Type t = val.getType();
        return t.getValue();
         */
        return this.getExpressionType().getValue();
    }
    
}
