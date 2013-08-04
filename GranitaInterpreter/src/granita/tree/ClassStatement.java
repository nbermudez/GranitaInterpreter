/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.tree;

import granitainterpreter.GranitaException;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ClassStatement extends Statement{
    
    public ArrayList<Statement> field_decls;
    public ArrayList<Statement> method_decls;
    
    public ClassStatement(){
        field_decls = new ArrayList<Statement>();
        method_decls = new ArrayList<Statement>();
    }

    @Override
    public void execute() throws GranitaException {
        for (Statement fieldDeclarationStatement : field_decls) {
            fieldDeclarationStatement.execute();
        }
        for (Statement methodDeclarationStatement : method_decls) {
            methodDeclarationStatement.execute();
        }
    }
    
}
