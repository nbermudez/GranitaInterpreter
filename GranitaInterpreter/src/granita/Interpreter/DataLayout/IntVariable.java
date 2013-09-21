/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Interpreter.DataLayout;

import granita.Interpreter.Results.IntResult;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class IntVariable extends RE_Variable {

    public IntVariable(String name, int value) {
        super(name);
        super.setValue(new IntResult(value));
    }

    @Override
    public IntResult getValue() {
        return (IntResult)super.getValue();
    }

    @Override
    public RE_Variable getCopy() {
        return new IntVariable(name, this.getValue().getValue());
    }
    
}
