/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.IR.Statements.D_Block;
import granita.Interpreter.DataLayout.Procedure;
import granita.Interpreter.Interpreter;
import granita.Interpreter.Results.Result;
import granita.Semantic.Types.Type;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class D_MethodCallExpression extends D_Expression {
    private ArrayList<D_Expression> arguments;
    private int procedureIndex;   

    public D_MethodCallExpression(Type type, ArrayList<D_Expression> arguments, int procedureIndex) {
        this.arguments = arguments;
        this.procedureIndex = procedureIndex;
        this.expressionType = type;
    }

    @Override
    public Result eval() {
        Procedure proc = Interpreter.getProcedure(procedureIndex);
        
        
        int i = 1;
        for (D_Expression arg : arguments) {
            Result param = arg.eval();
            int contextId = proc.getBody().getContext().getContextId();
            //toRun.getContext().setVariableInRE(contextId, i, param);            
            //i = i + 1;
            proc.getBody().getContext().setVariableInRE(contextId, i, param);
            i = i + 1;
        }
        D_Block toRun = proc.getBody().getCopy();
        toRun.getContext().hasReturnValue(true);
        toRun.getContext().setParent(Interpreter.globalContext());
        toRun.exec();
        
        Result r = Interpreter.getReturnValue();
        
        return r;
    }
}
