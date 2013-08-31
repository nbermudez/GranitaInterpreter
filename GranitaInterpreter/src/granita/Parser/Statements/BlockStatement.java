/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Semantic.SymbolTable.SymbolTableNode;
import granita.Semantic.SymbolTable.SymbolTableTree;
import granita.Semantic.SymbolTable.Variable;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.Interpreter;
import granitainterpreter.SemanticUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class BlockStatement extends Statement {

    ArrayList<Statement> statements;
    int localScope; //para el scope de las variables
    BlockStatement parentBlock = null;
    private HashMap<String, Variable> localSymbolTable;

    public BlockStatement(int localScope, int line) {
        super(line);
        this.localScope = localScope;
        this.statements = new ArrayList<Statement>();
        this.localSymbolTable = new HashMap<String, Variable>();
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

    public BlockStatement getParentBlock() {
        return parentBlock;
    }

    public void setParentBlock(BlockStatement parentBlock) {
        this.parentBlock = parentBlock;
    }
    
    public Variable getVariable(String id) {
        Variable v = this.localSymbolTable.get(id);
        if (v == null) {
            if (this.parentBlock != null){
                return this.parentBlock.getVariable(id);
            } else {
                return (Variable) SymbolTableTree.getInstance().getRoot().getEntry(id);
            }
        }return v;
    }
    
    public boolean alreadyRegistered(String id) {
        return localSymbolTable.containsKey(id);
    }
    
    public void registerVariable(String id, Variable var) {
        var.setVarName(id);
        this.localSymbolTable.put(id, var);
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
        if (SemanticUtils.getInstance().isUnreachableStatement() == 1) {
            int line2 = this.firstStatement().getLine();
            if (line2 != this.getLine()){
                ErrorHandler.handle("unreachable statement: line " 
                    + line2);
            }
            
            SemanticUtils.getInstance().setUnreachableStatement();
        }
        for (Statement st : statements) {
            SemanticUtils.getInstance().setCurrentBlock(this);
            if (st instanceof BlockStatement) {
                BlockStatement bl = (BlockStatement) st;
                bl.setParentBlock(this);
            } else if (st instanceof WhileStatement) {
                WhileStatement w = (WhileStatement) st;
                w.block.setParentBlock(this);
            } else if (st instanceof ForStatement) {
                ForStatement f = (ForStatement) st;
                f.block.setParentBlock(this);
            }
            st.validateSemantics();
        }
        SemanticUtils.getInstance().resetUnreachableStatement();
        
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
    
    public BlockStatement getCopy() {
        BlockStatement block = new BlockStatement(localScope, line);
        block.setStatements(statements);
        Set<String> keys = localSymbolTable.keySet();
        for (String name : keys) {
            block.registerVariable(name, localSymbolTable.get(name).getCopy());
        }
        return block;
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
                    block.setParentBlock(this);
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

    @Override
    public void execute() throws GranitaException {
        Interpreter.getInstance().pushBlockToFunction(this);
        for (Statement statement : statements) {  
            if (statement instanceof ForStatement) {
                System.out.println("");
            }
            statement.execute();
            if (Interpreter.getInstance().returnReached()) {
                break;
            }
        }
        Interpreter.getInstance().popBlockToFunction();
        
    }
    
}
