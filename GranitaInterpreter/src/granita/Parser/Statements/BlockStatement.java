/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Semantic.DataLayout.Context;
import granita.IR.Statements.D_Block;
import granita.IR.Statements.D_Statement;
import granita.Parser.Functions.VarDeclaration;
import granita.Semantic.Types.Type;
import granita.Misc.ErrorHandler;
import granita.Misc.GranitaException;
import granita.Semantics.SemanticUtils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
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
        int unreachable = SemanticUtils.getInstance().isUnreachableStatement();
        if (unreachable == 1) {
            Statement first = this.firstStatement();
            if (first != this) {
                ErrorHandler.handleWarning("unreachable statement: line " 
                    + first.getLine());
            }
            
            SemanticUtils.getInstance().setUnreachableStatement();
            SemanticUtils.getInstance().setUnreachable(true);
        } else if (SemanticUtils.getInstance().isUnreachable() &&
                SemanticUtils.getInstance().isUnreachableStatement() == 0) {
            ErrorHandler.handleWarning("unreachable statement: line " + this.getLine());
            SemanticUtils.getInstance().setUnreachable(false);
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
    public D_Block getIR() {
        checkForUnreachableStatement();
        
        Context thisContext = new Context();
        thisContext.setContextId(SemanticUtils.getInstance().generateContextId());
        int count = 0;
        for (Statement statement : statements) {
            if (statement instanceof VarDeclaration) {
                count += ((VarDeclaration) statement).getVarNames().size();
            }
        }
        
        if (SemanticUtils.getInstance().mustMergeWithTempContext()) {
            count += SemanticUtils.getInstance().getTmpContext().getSize();
            thisContext.initialize(count);
            thisContext.setVariableIndexInitValue(SemanticUtils.getInstance().getTmpContext().getSize());
            thisContext.merge(SemanticUtils.getInstance().getTmpContext());
            SemanticUtils.getInstance().mustMergeWithTempContext(false);
            SemanticUtils.getInstance().setTmpContext(null);
        } else {
            thisContext.initialize(count);
        }
        
        
        SemanticUtils.getInstance().registerContext(thisContext);
        ArrayList<D_Statement> dStatements = new ArrayList<D_Statement>();
        for (Statement statement : statements) {
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
