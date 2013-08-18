/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Functions;

import granita.Statements.Statement;
import granitainterpreter.GranitaException;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class VarDeclaration extends Statement{
    String type;
    ArrayList<String> varNames;

    public VarDeclaration(String type, ArrayList<String> varNames, int line) {
        super(line);
        this.type = type;
        this.varNames = varNames;
    }

    @Override
    public String toString() {
        String var = type + " ";
        for (int i = 0; i < varNames.size() - 1 ; i++){
            var += varNames.get(i) + ",";
        }
        var += varNames.get(varNames.size() - 1);
        return var;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
