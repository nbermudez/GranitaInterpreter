/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Expressions.Expression;
import granita.Semantic.SymbolTable.Function;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
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
        Type t = findInSymbolTable(this.id);
        if (t == null) {
            ErrorHandler.handle("undefined method " + id + ": line " + line);
        } else {            
            Function f = (Function) SymbolTableTree.getInstance().lookupFromCurrent(id);
            if (params.size() != f.getParameters().size()) {
                ErrorHandler.handle("actual and formal argument list differ in length "
                        + ": line " + this.getLine());
            }
            int min = params.size()<f.getParameters().size()? params.size():f.getParameters().size();
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

    private Type findInSymbolTable(String name) {
        Function f = (Function) SymbolTableTree.getInstance().lookupFromCurrent(name);
        if (f != null) {
            return f.getType();
        }
        return null;
    }
}
