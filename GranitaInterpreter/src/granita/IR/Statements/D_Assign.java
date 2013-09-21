/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.IR.Expressions.D_Expression;
import granita.IR.LeftValues.D_ArrayLeftValue;
import granita.IR.LeftValues.D_LeftValue;
import granita.Interpreter.Interpreter;
import granita.Interpreter.Results.Result;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class D_Assign extends D_Statement {
    D_LeftValue lSide;
    D_Expression value;

    public D_Assign(D_LeftValue lSide, D_Expression value) {
        this.lSide = lSide;
        this.value = value;
    }

    @Override
    public void exec() {
        Result result = value.eval();
        if (lSide instanceof D_ArrayLeftValue) {
            D_ArrayLeftValue array = (D_ArrayLeftValue) lSide;
            int contextId = lSide.getContextId();
            int index = lSide.getContextPosition();
            int arrayIndex = array.getArrayIndex();
            Interpreter.currentContext().setArrayItemInRE(contextId, index, arrayIndex, result);
        } else {
            Interpreter.currentContext().setVariableInRE(lSide.getContextId(), lSide.getContextPosition(), result);
        }
    }
    
}
