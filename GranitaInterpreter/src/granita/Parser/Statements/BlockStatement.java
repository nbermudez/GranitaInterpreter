/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.DataLayout.Context;
import granita.IR.Statements.D_Block;
import granita.IR.Statements.D_Statement;
import granita.Parser.Functions.VarDeclaration;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.SemanticUtils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class BlockStatement extends Statement {
    
    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    ArrayList<Statement> statements;
    BlockStatement parentBlock = null;
    //</editor-fold>
    
    public BlockStatement(int line) {
        super(line);
        this.statements = new ArrayList<Statement>();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
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
    //</editor-fold>    

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
    public void checkForUnreachableStatement() {
        if (SemanticUtils.getInstance().isUnreachableStatement() == 1) {
            int line2 = this.firstStatement().getLine();
            if (line2 != this.getLine()){
                ErrorHandler.handle("unreachable statement: line " 
                    + line2);
            }
            
            SemanticUtils.getInstance().setUnreachableStatement();
        }
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
        /*BlockStatement back_up = this.getCopy();
        Interpreter.getInstance().pushBlockToFunction(back_up);
        
        for (Statement st : statements) {
            if (st instanceof BlockStatement) {
                BlockStatement bl = (BlockStatement) st;
                bl.setParentBlock(back_up);
                //back_up = this.getCopy();
            } else if (st instanceof WhileStatement) {
                WhileStatement w = (WhileStatement) st;
                w.block.setParentBlock(back_up);
            } else if (st instanceof ForStatement) {
                ForStatement f = (ForStatement) st;
                f.block.setParentBlock(back_up);
            } else if (st instanceof IfStatement) {
                IfStatement ifS = (IfStatement) st;
                ifS.trueBlock.setParentBlock(back_up);
                if (ifS.falseBlock != null) {
                    ifS.falseBlock.setParentBlock(back_up);
                }
            }
            st.execute();
            if (st instanceof BlockStatement 
                    || st instanceof IfStatement
                    || st instanceof WhileStatement
                    || st instanceof ForStatement) {
                this.localSymbolTable = back_up.localSymbolTable;
            }
            if (Interpreter.getInstance().returnReached()) {
                break;
            }
        }
        Interpreter.getInstance().popBlockToFunction();
        */
        
    }

    @Override
    public D_Block getIR() {
        checkForUnreachableStatement();
        
        Context thisContext = new Context();
        if (SemanticUtils.getInstance().mustMergeWithTempContext()) {
            thisContext.merge(SemanticUtils.getInstance().getTmpContext());
            SemanticUtils.getInstance().mustMergeWithTempContext(false);
            SemanticUtils.getInstance().setTmpContext(null);
        }
        
        SemanticUtils.getInstance().registerContext(thisContext);
        ArrayList<D_Statement> dStatements = new ArrayList<D_Statement>();
        for (Statement statement : statements) {
            //switch dependiendo del tipo de statement.
            if (statement instanceof VarDeclaration) {
                statement.getIR();
            } else {
                dStatements.add(statement.getIR());
            }
        }
        
        SemanticUtils.getInstance().resetUnreachableStatement();
        D_Block dBlock = new D_Block(dStatements, thisContext);
        SemanticUtils.getInstance().unregisterContext();
        return dBlock;
    }
    
}
