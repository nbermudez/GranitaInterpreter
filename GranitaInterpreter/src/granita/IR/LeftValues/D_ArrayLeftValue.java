/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.LeftValues;

import granita.IR.Expressions.D_Expression;
import granita.Interpreter.DataLayout.ArrayVariable;
import granita.Interpreter.Interpreter;
import granita.Interpreter.Results.IntResult;
import granita.Interpreter.Results.Result;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_ArrayLeftValue extends D_LeftValue {
    D_Expression arrayIndex;

    //<editor-fold defaultstate="collapsed" desc="Constructors">    
    public D_ArrayLeftValue(Type type, D_Expression arrayIndex, String identifier, int contextPosition, int contextId) {
        super(identifier, contextPosition, contextId);
        this.arrayIndex = arrayIndex;
        this.setExpressionType(type);
    }
    //</editor-fold>
    
    public int getArrayIndex() {
        IntResult r = (IntResult)this.arrayIndex.eval();        
        return r.getValue();
    }

    @Override
    public Result eval() {
        ArrayVariable var = 
                (ArrayVariable)Interpreter.currentContext().findVariableInRE(contextId, contextPosition);
        int calculatedIndex = ((IntResult) arrayIndex.eval()).getValue();
        if (calculatedIndex >= var.getItems().length) {
            try {
                ErrorHandler.handleFatalError("index out of bound in variable '"
                        + identifier + "'");
            } catch (GranitaException ex) {                
            }return null;
        } else {
            return var.getItem(calculatedIndex).getValue();
        }
    }
}
