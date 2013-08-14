/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granitainterpreter.AstNode;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class BlockStatement extends Statement{
    ArrayList<AstNode> statements;
    
    public BlockStatement(int line){
        super(line);
        this.statements = new ArrayList<AstNode>();
    }

    public ArrayList<AstNode> getStatements() {
        return statements;
    }

    public void setStatements(ArrayList<AstNode> statements) {
        this.statements = statements;
    }      
    
    public void addStatement(AstNode stmt){
        this.statements.add(stmt);
    }
}
