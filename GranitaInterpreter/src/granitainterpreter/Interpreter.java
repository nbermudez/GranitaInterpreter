/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

import granita.Parser.Statements.BlockStatement;
import granita.DataLayout.Function;
import granita.DataLayout.Variable;
import granita.Types.Type;
import java.util.Stack;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Interpreter {
    Stack<Function> stackFrames;
    private boolean breakFound = false;
    private boolean returnReached = false;
    
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
        Function f = this.stackFrames.peek();
        Type ret = f.getType();
        ret.setValue(value);
        this.returnReached = true;
    }
    
    public void register(Function function) {
        this.stackFrames.push(function);
    }
    
    public void popFunction() {
        this.stackFrames.pop();
    }
    
    public void setBreakFound(boolean val) {
        this.breakFound = val;
    }
    
    public boolean breakFound() {
        return this.breakFound;
    }
    /*
    public Variable getVariable(String id) {
        Function f = this.stackFrames.peek();
        return f.getVariable(id);
    }
    
    public void pushBlockToFunction(BlockStatement block) {
        this.stackFrames.peek().setBlock(block);
    }
    
    public void popBlockToFunction() {
        BlockStatement block = this.stackFrames.peek().getBlock();
        if (block != null){
            this.stackFrames.peek().setBlock(block.getParentBlock());
        }
    }*/

    public boolean returnReached() {
        return returnReached;
    }

    public void setReturnReached(boolean returnReached) {
        this.returnReached = returnReached;
    }
    
    
}
