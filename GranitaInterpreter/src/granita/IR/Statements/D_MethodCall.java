/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.IR.Expressions.D_Expression;
import granita.Interpreter.DataLayout.Procedure;
import granita.Interpreter.Interpreter;
import granita.Interpreter.Results.Result;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class D_MethodCall extends D_Statement {
    ArrayList<D_Expression> parameters;
    int procedureIndex;

    public D_MethodCall(ArrayList<D_Expression> parameters, int procedureIndex) {
        this.parameters = parameters;
        this.procedureIndex = procedureIndex;
    }
    
    @Override
    public void exec() {
        Procedure proc = Interpreter.getProcedure(procedureIndex);
        
        int i = 0;
        for (D_Expression arg : parameters) {
            Result param = arg.eval();
            proc.getBody().getContext().setVariableInRE(proc.getBody().getContext().getContextId(), i, param);
            i = i + 1;
        }
        D_Block toRun = proc.getBody().getCopy();
        toRun.getContext().setParent(Interpreter.globalContext());
        toRun.exec();
        Interpreter.returnWasReached(false);
    }
}
