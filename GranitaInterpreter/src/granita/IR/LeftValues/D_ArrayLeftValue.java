/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.LeftValues;

import granita.DataLayout.ArrayVariable;
import granita.IR.Expressions.D_Expression;
import granita.Interpreter.DataLayout.RE_Variable;
import granita.Interpreter.Interpreter;
import granita.Interpreter.Results.IntResult;
import granita.Interpreter.Results.Result;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_ArrayLeftValue extends D_LeftValue {
    D_Expression arrayIndex;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public D_ArrayLeftValue(D_Expression arrayIndex, String identifier) {
        super(identifier);
        this.arrayIndex = arrayIndex;
        ArrayVariable value = (ArrayVariable) SemanticUtils.getInstance().currentContext().find(identifier);
        this.setExpressionType(value.getType());
    }

    public D_ArrayLeftValue(D_Expression arrayIndex, String identifier, int contextPosition) {
        super(identifier, contextPosition);
        this.arrayIndex = arrayIndex;
        ArrayVariable value = (ArrayVariable) SemanticUtils.getInstance().currentContext().find(identifier);
        this.setExpressionType(value.getType());
    }
    
    public D_ArrayLeftValue(D_Expression arrayIndex, String identifier, int contextPosition, int contextId) {
        super(identifier, contextPosition, contextId);
        this.arrayIndex = arrayIndex;
        ArrayVariable value = (ArrayVariable) SemanticUtils.getInstance().currentContext().find(identifier);
        this.setExpressionType(value.getType());
    }
    //</editor-fold>
    
    @Override
    public Object evaluate() {
        ArrayVariable value = (ArrayVariable) Interpreter.currentContext().find(identifier);
        RE_Variable var = Interpreter.getVariableRE(contextPosition);
        Integer calculatedIndex = (Integer) arrayIndex.evaluate();
        if (calculatedIndex >= value.getSize().getValue()) {
            try {
                ErrorHandler.handleFatalError("index out of bound in variable '"
                        + identifier + "'");
            } catch (GranitaException ex) {
                return null;
            }
        }
        Type[] items = value.getItems();
        Type item = items[calculatedIndex];
        return item.getValue();
    }
    
    public int getIndex() {
        return (Integer)this.arrayIndex.evaluate();
    }
    
    public int getArrayIndex() {
        IntResult r = (IntResult)this.arrayIndex.eval();
        
        return r.getValue();
    }

    @Override
    public Result eval() {
        granita.Interpreter.DataLayout.ArrayVariable var = 
                (granita.Interpreter.DataLayout.ArrayVariable)Interpreter.currentContext().findVariableInRE(contextId, contextPosition);
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
