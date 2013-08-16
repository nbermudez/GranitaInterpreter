/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class BlockStatement extends Statement{
    ArrayList<Statement> statements;
    
    public BlockStatement(int line){
        super(line);
        this.statements = new ArrayList<Statement>();
    }

    public ArrayList<Statement> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<Statement> statements) {
        this.statements = statements;
    }      
    
    public void addStatement(Statement stmt){
        this.statements.add(stmt);
    }
    
    @Override
    public String toString() {
        String b = "{\n";
        for(Statement s : statements){
            b = b + "\t" +s.toString() + "\n";
        }
        b = b + "\t}";
        return b;
    }
}
