/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.LeftValues;

import granita.Interpreter.DataLayout.RE_Variable;
import granita.Interpreter.Interpreter;
import granita.Interpreter.Results.Result;
import granita.Semantic.DataLayout.SimpleVariable;
import granita.Semantics.SemanticUtils;


/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class D_SimpleValue extends D_LeftValue {

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public D_SimpleValue(String identifier, int contextPosition, int contextId) {
        super(identifier, contextPosition, contextId);
        SimpleVariable val = (SimpleVariable) SemanticUtils.getInstance().currentContext().find(identifier);
        this.setExpressionType(val.getType());
    }
    //</editor-fold> 

    @Override
    public Result eval() {
        RE_Variable var = Interpreter.currentContext().findVariableInRE(contextId, contextPosition);
        if (var == null) {
            var = Interpreter.currentContext().findVariableInRE(contextId, contextPosition);
        }
        return var.getValue();
    }
    
}
