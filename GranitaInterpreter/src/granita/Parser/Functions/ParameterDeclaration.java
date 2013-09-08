/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Functions;

import granita.Parser.Statements.Statement;
import granita.DataLayout.Function;
import granita.DataLayout.SimpleVariable;
import granita.SymbolTable.SymbolTableTree;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.SemanticUtils;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ParameterDeclaration extends Statement {

    Type type;
    String name, methodName;
    int scopeId;

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

    public int getScopeId() {
        return scopeId;
    }

    public void setScopeId(int scopeId) {
        this.scopeId = scopeId;
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

    @Override
    public void validateSemantics() throws GranitaException {
        //SymbolTableNode current = SymbolTableTree.getInstance().getCurrentNode();
        
        Function f = (Function)SymbolTableTree.getInstance().lookupFunction(methodName);
        SimpleVariable v = new SimpleVariable(type, null);
        v.setInitialized(true);
        f.getParameters().add(v);
        if (SemanticUtils.getInstance().getCurrentBlock().alreadyRegistered(name)) {
            ErrorHandler.handle("duplicated parameter '" + name + "': line " + this.getLine());
        } else {
            SemanticUtils.getInstance().getCurrentBlock().registerVariable(name, v);
        }
        /*if (current.findInThisTable(name) != null) {
            ErrorHandler.handle("duplicated parameter '" + name + "': line " + this.getLine());
        } else {            
            current.addEntry(name, v);
        }*/
    }
    
    public void register() {        
        Function f = (Function)SymbolTableTree.getInstance().lookupFunction(methodName);
        SimpleVariable v = new SimpleVariable(type, null);
        v.setInitialized(true);
        f.getParameters().add(v);
        if (SemanticUtils.getInstance().getTmpContext().findLocally(name) != null) {
            ErrorHandler.handle("duplicated parameter '" + name + "': line " + this.getLine());
        } else {
            SemanticUtils.getInstance().getTmpContext().add(name, v);
        }
    }
}
