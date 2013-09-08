/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.DataLayout.Context;
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
    
    @Override
    public void execute() {
        for (D_Statement d_Statement : statements) {
            d_Statement.execute();
        }
    }
    
}
