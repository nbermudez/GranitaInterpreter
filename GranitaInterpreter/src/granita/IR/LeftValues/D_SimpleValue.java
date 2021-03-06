/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.LeftValues;

import granita.DataLayout.ArrayVariable;
import granita.DataLayout.SimpleVariable;
import granita.DataLayout.Variable;
import granita.Interpreter.Interpreter;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.SemanticUtils;


/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_SimpleValue extends D_LeftValue {

    public D_SimpleValue(String identifier) {
        super(identifier);        
        SimpleVariable val = (SimpleVariable) SemanticUtils.getInstance().currentContext().find(identifier);
        this.setExpressionType(val.getType());
    }

    @Override
    public Object evaluate() {
        
        SimpleVariable val = (SimpleVariable) Interpreter.currentContext().find(identifier);
        Type t = val.getType();
        return t.getValue();
         
        //return this.getExpressionType().getValue();
    }
    
}
