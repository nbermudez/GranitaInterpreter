/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Expressions.Expression;
import granita.Semantic.SymbolTable.Function;
import granita.Semantic.SymbolTable.SymbolTableEntry;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.Variable;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.Interpreter;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class MethodCallStatement extends Statement {

    ArrayList<Expression> params;
    String id;

    public MethodCallStatement(int line) {
        super(line);
    }

    public MethodCallStatement(String id, ArrayList<Expression> params, int line) {
        super(line);
        this.params = params;
        this.id = id;
    }

    @Override
    public String toString() {
        String t = id + "(";

        for (int i = 0; i < params.size() - 1; i++) {
            t = t + params.get(i).toString() + ",";
        }
        if (params.size() > 0) {
            t = t + params.get(params.size() - 1).toString();
        }

        t = t + ")";
        return t;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        super.validateSemantics();        
        SymbolTableEntry val = SymbolTableTree.getInstance().lookupFunction(id);
        Function f;
        if (val == null) {
            ErrorHandler.handle("no such method '" + id + "': line " 
                    + this.getLine());
            return;
        } else {
            f = (Function) val;
        }
        Type t = f.getType();
        if (t == null) {
            ErrorHandler.handle("undefined method " + id + ": line " + line);
        } else {
            if (params.size() != f.getParameters().size()) {
                ErrorHandler.handle("actual and formal argument list differ in length "
                        + ": line " + this.getLine());
            }
            int min = params.size()<f.getParameters().size()?
                    params.size():f.getParameters().size();
            for (int i = 0; i < min; i++) {
                Expression ex = params.get(i);
                Type ret = ex.validateSemantics();
                Type o = f.getParameters().get(i).getType();
                if (!o.equivalent(ret)) {
                    ErrorHandler.handle("incompatible types in method call's arg " + i
                            + ": line " + ex.getLine());
                }
            }
        }
    }

    @Override
    public void execute() throws GranitaException {        
        Function f = (Function) SymbolTableTree.getInstance().lookupFunction(this.id);
        Function AR = f.getCopy();
        
        int i = 0;
        for (Expression arg : params) {
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
        Interpreter.getInstance().popFunction();
        Interpreter.getInstance().setReturnReached(false);
    }
}
