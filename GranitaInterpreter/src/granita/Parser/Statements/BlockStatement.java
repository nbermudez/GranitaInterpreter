/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granitainterpreter.GranitaException;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class BlockStatement extends Statement{
    ArrayList<Statement> statements;
    int localScope; //para el scope de las variables
    
    public BlockStatement(int localScope, int line){
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
    
    public void addStatement(Statement stmt){
        this.statements.add(stmt);
    }
    
    @Override
    public String toString() {
        String b = "{\n";
        for(Statement s : statements){
            if (s instanceof BlockStatement ||
                    s instanceof ForStatement ||
                    s instanceof WhileStatement ||
                    s instanceof IfStatement){
                b = b + "\t" +s.toString() + "\n";
            }else{
                b = b + "\t" +s.toString() + ";\n";
            }
        }
        b = b + "\t}";
        return b;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        for (Statement st : statements){
            st.validateSemantics();
        }
    }
}
