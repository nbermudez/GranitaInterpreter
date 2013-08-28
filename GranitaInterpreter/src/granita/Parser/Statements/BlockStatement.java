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
        super.validateSemantics();
        SymbolTableNode parent = SymbolTableTree.getInstance().getParentNode();
        
        for (Statement st : statements) {            
            if (st instanceof BlockStatement) {
                SymbolTableTree.getInstance().setCurrentNode(new SymbolTableNode(parent));
            }
            st.validateSemantics();
            SymbolTableTree.getInstance().setCurrentNode(parent);
        }
        Utils.getInstance().resetUnreachableStatement();
    }

    @Override
    public Type hasReturn(Type methodType) throws GranitaException {
        int unreachable = 0;
        Type tt = null;
        for (Statement statement : statements) {
            if (unreachable == 1) {
                ErrorHandler.handle("unreachable statement: line " + statement.getLine());
            }
            Type t = statement.hasReturn(methodType);
            if (t != null) {
                tt = t;
                unreachable += 1;
            }
        }
        return tt;
    }
}
