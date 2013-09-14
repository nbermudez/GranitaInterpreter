/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Semantic.DataLayout.Function;
import granita.IR.Expressions.D_Expression;
import granita.IR.Statements.D_MethodCall;
import granita.IR.Statements.D_Statement;
import granita.Parser.Expressions.Expression;
import granita.Semantic.SymbolTable.SymbolTableEntry;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granita.Semantics.SemanticUtils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class MethodCallStatement extends Statement {

    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    ArrayList<Expression> params;
    String id;
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public MethodCallStatement(int line) {
        super(line);
    }

    public MethodCallStatement(String id, ArrayList<Expression> params, int line) {
        super(line);
        this.params = params;
        this.id = id;
    }
    //</editor-fold>    

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
    public D_Statement getIR() {
        SymbolTableEntry val = SymbolTableTree.getInstance().lookupFunction(id);
        int proc = SemanticUtils.getInstance().findProcedureIndex(id);
        Function f;
        if (val == null) {
            ErrorHandler.handle("no such method '" + id + "': line " 
                    + this.getLine());
            return null;
        } else {
            f = (Function) val;
        }
        Type t = f.getType();
        if (t == null) {
            ErrorHandler.handle("undefined method " + id + ": line " + line);
            return null;
        } else {
            if (params.size() != f.getParameters().size()) {
                ErrorHandler.handle("actual and formal argument list differ in length "
                        + ": line " + this.getLine());
            }
            int min = params.size()<f.getParameters().size()?
                    params.size():f.getParameters().size();
            ArrayList<D_Expression> dParams = new ArrayList<D_Expression>();
            for (int i = 0; i < min; i++) {
                D_Expression dExp = params.get(i).getIR();
                Type ret = dExp.getExpressionType();
                Type o = f.getParameters().get(i).getType();
                if (!o.equivalent(ret)) {
                    ErrorHandler.handle("incompatible types in method call's arg " + i
                            + ": line " + params.get(i).getLine());
                }
                dParams.add(dExp);
            }
            return new D_MethodCall(dParams, proc);
        }
    }
}
