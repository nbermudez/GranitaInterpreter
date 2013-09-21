/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Interpreter.DataLayout;

import granita.Semantic.DataLayout.Context;
import granita.IR.Statements.D_Block;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class Procedure {
    D_Block body;
    String name;

    public Procedure(String name) {
        this.name = name;
    }
    
    public Context createEnvironment() {
        return body.getContext().getCopy();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    } 

    public D_Block getBody() {
        return body;
    }

    public void setBody(D_Block block) {
        this.body = block;
    }
}
