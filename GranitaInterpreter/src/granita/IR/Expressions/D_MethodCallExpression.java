/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Expressions;

import granita.Parser.Statements.BlockStatement;
import granita.Semantic.SymbolTable.Function;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.Variable;
import granita.Semantic.Types.Type;
import granitainterpreter.Interpreter;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_MethodCallExpression extends D_Expression {
    private String methodName;
    private ArrayList<D_Expression> arguments;

    public D_MethodCallExpression(String methodName, ArrayList<D_Expression> arguments) {
        this.methodName = methodName;
        this.arguments = arguments;
    }    

    @Override
    public Object evaluate() {
        /*Function f = (Function) SymbolTableTree.getInstance().lookupFunction(this.methodName);
        Function AR = f.getCopy();
        
        int i = 0;
        for (D_Expression arg : arguments) {
            Type t = AR.getParameters().get(i).getType();
            Object param = arg.evaluate();
            t.setValue(param);
            String varName = AR.getParameters().get(i).getVarName();
            Variable v = AR.getBlock().getVariable(varName);
            v.getType().setValue(param);
            
            i = i + 1;
        }
        Interpreter.getInstance().register(AR);
        BlockStatement toRun = AR.getBlock().getCopy();
        toRun.execute();
        Object o = AR.getType().getValue();
        Interpreter.getInstance().popFunction();
        Interpreter.getInstance().setReturnReached(false);
        return o;*/
        return null;
    }
}
