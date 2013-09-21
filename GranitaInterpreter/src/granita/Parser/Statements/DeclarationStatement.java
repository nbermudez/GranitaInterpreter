/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Misc.GranitaException;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public abstract class DeclarationStatement extends Statement {

    public DeclarationStatement(int line) {
        super(line);
    }
    
    public abstract void register() throws GranitaException;
}
