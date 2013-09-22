/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Functions;

import granita.Interpreter.DataLayout.BoolVariable;
import granita.Interpreter.DataLayout.IntVariable;
import granita.Misc.ErrorHandler;
import granita.Parser.Statements.Statement;
import granita.Semantic.DataLayout.Context;
import granita.Semantic.DataLayout.Function;
import granita.Semantic.DataLayout.SimpleVariable;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.Types.IntType;
import granita.Semantic.Types.Type;
import granita.Semantics.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class ParameterDeclaration extends Statement {

    Type type;
    String name, methodName;

    public ParameterDeclaration(int line) {
        super(line);
    }

    public ParameterDeclaration(Type type, String name,
            String methodName, int line) {
        super(line);
        this.type = type;
        this.name = name;
        this.methodName = methodName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return type + " " + name;
    }
    
    public void register() {        
        Function f = (Function)SymbolTableTree.getInstance().lookupFunction(methodName);
        SimpleVariable v = new SimpleVariable(type, false);
        v.setInitialized(true);
        v.setVarName(name);
        f.getParameters().add(v);
        if (SemanticUtils.getInstance().getTmpContext().findLocally(name) != null) {
            ErrorHandler.handle("duplicated parameter '" + name + "': line " + this.getLine());
        } else {
            Context tmp = SemanticUtils.getInstance().getTmpContext();
            tmp.add(name, v);
            if (type instanceof IntType) {
                tmp.add(tmp.getVariableIndex(), new IntVariable(name, 0));
            } else {
                tmp.add(tmp.getVariableIndex(), new BoolVariable(name, false));
            }
        }
    }
}
