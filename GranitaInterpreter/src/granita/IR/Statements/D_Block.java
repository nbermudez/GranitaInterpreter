/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_Block extends D_Statement {
    ArrayList<D_Statement> statements;

    public D_Block(ArrayList<D_Statement> statements) {
        this.statements = statements;
    }

    public ArrayList<D_Statement> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<D_Statement> statements) {
        this.statements = statements;
    }
    
    @Override
    public void execute() {
        for (D_Statement d_Statement : statements) {
            d_Statement.execute();
        }
    }
    
}
