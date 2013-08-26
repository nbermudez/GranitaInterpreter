/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Semantic.SymbolTable.SymbolTableNode;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.Utils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class BlockStatement extends Statement {

    ArrayList<Statement> statements;
    int localScope; //para el scope de las variables

    public BlockStatement(int localScope, int line) {
        super(line);
        this.localScope = localScope;
        this.statements = new ArrayList<Statement>();
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<Statement> statements) {
        this.statements = statements;
    }

    public void addStatement(Statement stmt) {
        this.statements.add(stmt);
    }

    @Override
    public String toString() {
        String b = "{\n";
        for (Statement s : statements) {
            if (s instanceof BlockStatement
                    || s instanceof ForStatement
                    || s instanceof WhileStatement
                    || s instanceof IfStatement) {
                b = b + "\t" + s.toString() + "\n";
            } else {
                b = b + "\t" + s.toString() + ";\n";
            }
        }
        b = b + "\t}";
        return b;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        SymbolTableNode parent = SymbolTableTree.getInstance().getParentNode();
        SymbolTableTree.getInstance().setCurrentNode(new SymbolTableNode(parent));
        
        boolean is = Utils.getInstance().isFirstBlockInMethod();
        
        for (Statement st : statements) {
            if (st instanceof BlockStatement) {
                Utils.getInstance().setFirstBlockInMethod(false);
            }
            st.validateSemantics();
            Utils.getInstance().setFirstBlockInMethod(is);
        }
        SymbolTableTree.getInstance().setCurrentNode(parent);
        
        boolean returnFound = false, continueFound = false, breakFound = false;
        for (Statement statement : statements) {
            if (returnFound || continueFound || breakFound) {
                ErrorHandler.handle("unreachable statement: line " + statement.getLine());
                break;
            } else if (statement instanceof ReturnStatement) {
                returnFound = true;
            } else if (statement instanceof ContinueStatement) {
                continueFound = true;
            } else if (statement instanceof BreakStatement) {
                breakFound = true;
            }
        }
    }

    @Override
    public Type hasReturn(Type methodType) throws GranitaException {
        for (Statement statement : statements) {
            if (statement instanceof ReturnStatement) {
                return ((ReturnStatement) statement).returnExpression.validateSemantics();
            }
        }
        for (Statement statement : statements) {
            Type t = statement.hasReturn(methodType);
            if (t != null){
                return t;
            }
        }
        return null;
    }
}
