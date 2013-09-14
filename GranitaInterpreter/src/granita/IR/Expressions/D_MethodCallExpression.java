/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.DataLayout.Context;
import granita.Parser.Statements.BlockStatement;
import granita.DataLayout.Function;
import granita.SymbolTable.SymbolTableTree;
import granita.DataLayout.Variable;
import granita.IR.Statements.D_Block;
import granita.Interpreter.DataLayout.Procedure;
import granita.Interpreter.Interpreter;
import granita.Interpreter.Results.Result;
import granita.Types.Type;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_MethodCallExpression extends D_Expression {
    private String methodName;
    private ArrayList<D_Expression> arguments;
    private int procedureIndex;

    public D_MethodCallExpression(Type type, String methodName, ArrayList<D_Expression> arguments) {
        this.methodName = methodName;
        this.arguments = arguments;
        this.expressionType = type;
    }    

    public D_MethodCallExpression(Type type, String methodName, ArrayList<D_Expression> arguments, int procedureIndex) {
        this.methodName = methodName;
        this.arguments = arguments;
        this.procedureIndex = procedureIndex;
        this.expressionType = type;
    }

    @Override
    public Object evaluate() {
        Function f = (Function) SymbolTableTree.getInstance().lookupFunction(methodName);
        Function AR = f.getCopy();
        
        int i = 0;
        for (D_Expression arg : arguments) {
            Type t = AR.getParameters().get(i).getType();
            Object param = arg.evaluate();
            t.setValue(param);
            String varName = AR.getParameters().get(i).getVarName();
            Variable v = AR.getBody().getContext().find(varName);
            v.getType().setValue(param);
            
            i = i + 1;
        }
        D_Block toRun = AR.getBody().getCopy();
        toRun.execute();
        Object res = Interpreter.returnedValue();
        Interpreter.returnedValue(null);
        return res;
    }

    @Override
    public Result eval() {
        Procedure proc = Interpreter.getProcedure(procedureIndex);
        D_Block toRun = proc.getBody().getCopy();
        //Interpreter.registerContext(toRun.getContext());
        
        int i = 1;
        for (D_Expression arg : arguments) {
            Result param = arg.eval();
            int contextId = proc.getBody().getContext().getContextId();
            toRun.getContext().setVariableInRE(contextId, i, param);            
            i = i + 1;
        }
        
        toRun.getContext().hasReturnValue(true);
        toRun.exec();
        
        Result r = Interpreter.getReturnValue();
        //Interpreter.unregisterContext();
        
        return r;
    }
}
