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
import granita.Interpreter.Interpreter;
import granita.Types.Type;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_MethodCallExpression extends D_Expression {
    private String methodName;
    private ArrayList<D_Expression> arguments;

    public D_MethodCallExpression(Type type, String methodName, ArrayList<D_Expression> arguments) {
        this.methodName = methodName;
        this.arguments = arguments;
        this.expressionType = type;
    }    

    @Override
    public Object evaluate() {
        Function f = (Function) SymbolTableTree.getInstance().lookupFunction(methodName);
        Function AR = f.getCopy();
        
        int i = 0;
        //System.out.println("Llamada a " + methodName);
        //System.out.println("Function " + AR.getBody().getContext());
        for (D_Expression arg : arguments) {
            Type t = AR.getParameters().get(i).getType();
            Object param = arg.evaluate();
            //System.out.println("param: " + param);
            t.setValue(param);
            String varName = AR.getParameters().get(i).getVarName();
            Variable v = AR.getBody().getContext().find(varName);
            v.getType().setValue(param);
            
            i = i + 1;
        }
        D_Block toRun = AR.getBody().getCopy();
        //Context t = new Context();
        //t.copyFrom(toRun.getContext());
        //toRun.setContext(t);
        //Interpreter.registerContext(toRun.getContext());
        System.out.println("MC: " + methodName);
        toRun.execute();
        Object res = Interpreter.returnedValue();
        Interpreter.returnedValue(null);
        //Interpreter.loadContext();
        //Interpreter.unregisterContext();
        return res;
        //Interpreter.getInstance().popFunction();
        //Interpreter.getInstance().setReturnReached(false);
        //return null;
    }
}
