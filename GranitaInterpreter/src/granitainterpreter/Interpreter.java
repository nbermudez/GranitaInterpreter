/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

import granita.Semantic.SymbolTable.Function;
import granita.Semantic.Types.Type;
import java.util.Stack;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Interpreter {
    Stack<Function> stackFrames;
    private boolean breakFound = false;
    
    private Interpreter() {
        stackFrames = new Stack<Function>();
    }
    
    public static Interpreter getInstance() {
        return InterpreterHolder.INSTANCE;
    }
    
    private static class InterpreterHolder {

        private static final Interpreter INSTANCE = new Interpreter();
    }
    
    public void setReturnValue(Object value) {
        Function f = this.stackFrames.pop();
        Type ret = f.getType();
        ret.setValue(value);
    }
    
    public void register(Function function) {
        this.stackFrames.push(function);
    }
    
    public void setBreakFound(boolean val) {
        this.breakFound = val;
    }
    
    public boolean breakFound() {
        return this.breakFound;
    }
}
