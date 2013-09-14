/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.Semantic.DataLayout.Context;
import granita.Interpreter.Interpreter;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_Block extends D_Statement {
    
    private ArrayList<D_Statement> statements;
    private Context context;

    public D_Block(ArrayList<D_Statement> statements, Context context) {
        this.statements = statements;
        this.context = context;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public ArrayList<D_Statement> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<D_Statement> statements) {
        this.statements = statements;
    }
    
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    //</editor-fold>
    
    public D_Block getCopy() {
        Context c = new Context(context.getParent());
        c.copyFrom(context);
        ArrayList<D_Statement> stats = new ArrayList<D_Statement>();
        for (D_Statement d_Statement : statements) {
            if (d_Statement instanceof D_Block) {
                stats.add(((D_Block)d_Statement).getCopy());
            } else if (d_Statement instanceof D_If){
                D_If b = (D_If) d_Statement;
                D_If dIf;
                if (b.falseBlock == null) {
                    dIf = new D_If(b.conditional, b.trueBlock.getCopy());
                } else {
                    dIf = new D_If(b.conditional, b.trueBlock.getCopy(), b.falseBlock.getCopy());
                }
                stats.add(dIf);
            } else {
                stats.add(d_Statement);
            }
        }
        D_Block copy = new D_Block(stats, c);
        
        return copy;
    }

    @Override
    public void exec() {
        Interpreter.saveContext();
        Interpreter.registerContext(context);
        for (D_Statement d_Statement : statements) {
            if (Interpreter.returnReached()) {
                break;
            }
            d_Statement.exec();
        }
        Interpreter.unregisterContext();
        Interpreter.loadContext();
    }
    
}
