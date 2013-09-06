/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.General;

import granita.Parser.Statements.ClassStatement;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class IntermediateRepresentation {
    public static D_Program validateAndGenerate(ClassStatement st) {
        D_Program program = (D_Program) st.getIR();
        return program;
    }
}
