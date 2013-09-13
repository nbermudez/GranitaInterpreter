/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.IR.Expressions.D_Expression;
import granita.DataLayout.Function;
import granita.SymbolTable.SymbolTableTree;
import granita.DataLayout.Variable;
import granita.Interpreter.DataLayout.Procedure;
import granita.Interpreter.Interpreter;
import granita.Interpreter.Results.Result;
import granita.Types.Type;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_MethodCall extends D_Statement {
    String identifier;
    ArrayList<D_Expression> parameters;
    int procedureIndex;

    public D_MethodCall(String identifier, ArrayList<D_Expression> parameters) {
        this.identifier = identifier;
        this.parameters = parameters;
    }

    public D_MethodCall(String identifier, ArrayList<D_Expression> parameters, int procedureIndex) {
        this.identifier = identifier;
        this.parameters = parameters;
        this.procedureIndex = procedureIndex;
    }
    
    @Override
    public void execute() {
        Function f = (Function) SymbolTableTree.getInstance().lookupFunction(identifier);
        Procedure proc = Interpreter.getProcedure(procedureIndex);
        Function AR = f.getCopy();
        
        int i = 0;
        for (D_Expression arg : parameters) {
            Type t = AR.getParameters().get(i).getType();
            Object param = arg.evaluate();
            t.setValue(param);
            String varName = AR.getParameters().get(i).getVarName();
            Variable v = AR.getBody().getContext().find(varName);
            v.getType().setValue(param);
            
            
            
            i = i + 1;
        }
        //Interpreter.getInstance().register(AR);
        D_Block toRun = proc.getBody();
        toRun.getCopy().execute();
        Interpreter.returnWasReached(false);
        //Interpreter.getInstance().popFunction();
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
        toRun.exec();
        Interpreter.returnWasReached(false);
    }
}
