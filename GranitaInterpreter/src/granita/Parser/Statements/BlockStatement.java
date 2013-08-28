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
        if (Utils.getInstance().isUnreachableStatement() == 1) {
            int line2 = this.firstStatement().getLine();
            if (line2 != this.getLine()){
                ErrorHandler.handle("unreachable statement: line " 
                    + line2);
            }
            
            Utils.getInstance().setUnreachableStatement();
        }
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

    private Statement firstStatement() {
        Statement st;
        for (Statement statement : statements) {
            if (statement instanceof BlockStatement) {
                st = ((BlockStatement) statement).firstStatement();
                if (st != statement) {
                    return st;
                }
            } else {
                return statement;
            }
        }
        return this;
    }

    @Override
    public Type hasReturn(Type methodType) throws GranitaException {
        int unreachable = 0;
        Type tt = null;
        boolean simple = false;
        for (Statement statement : statements) {
            if (unreachable == 1 && !simple) {
                int lineN = statement.getLine();
                if (statement instanceof BlockStatement) {
                    BlockStatement block = (BlockStatement) statement;
                    lineN = block.firstStatement().getLine();
                    if (lineN != statement.getLine()) {
                        ErrorHandler.handle("unreachable statement: line " + lineN);
                    }
                } else {
                    ErrorHandler.handle("unreachable statement: line " + lineN);
                }
            }
            Type t = statement.hasReturn(methodType);
            if (t != null) {
                tt = t;
                unreachable += 1;
            }
            if (statement instanceof ReturnStatement
                    || statement instanceof ContinueStatement
                    || statement instanceof BreakStatement) {
                simple = true;
            } else {
                simple = false;
            }
        }
        return tt;
    }
}
