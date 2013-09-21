/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Interpreter.DataLayout;

import granita.Interpreter.Results.BoolResult;

/**
 *
 * @author Néstor A. Bermúdez < nestor.bermudez@unitec.edu >
 */
public class BoolVariable extends  RE_Variable {

    public BoolVariable(String name, boolean value) {
        super(name);
        super.setValue(new BoolResult(value));
    }
    

    @Override
    public BoolResult getValue() {
        return (BoolResult)super.getValue();
    }   

    @Override
    public RE_Variable getCopy() {
        return new BoolVariable(name, getValue().getValue());
    }
    
}
