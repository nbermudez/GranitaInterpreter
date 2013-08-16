/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Statements;

import granita.FieldItems.Field;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class FieldDeclarationStatement extends Statement{

    private ArrayList<Field> declarations;
    String type;

    public FieldDeclarationStatement(String type, int line) {
        super(line);
        this.type = type;
        this.declarations = new ArrayList<Field>();
    }
    
    public void addDeclaration(Field st){
        this.declarations.add(st);
    }
    
    @Override
    public String toString() {
        String fd = type + " ";
        for (int i = 0; i< declarations.size() - 1 ; i++){
            fd += declarations.get(i).toString() + ",";
        }
        fd += declarations.get(declarations.size() - 1).toString();
        return fd;
    }
}
