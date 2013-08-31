/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.Functions.Argument;
import granitainterpreter.GranitaException;
import granitainterpreter.SemanticUtils;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class PrintStatement extends Statement {
    ArrayList<Argument> arguments;
    
    public PrintStatement(int line) {
        super(line);
        arguments = new ArrayList<Argument>();
    }

    public PrintStatement(ArrayList<Argument> arguments, int line) {
        super(line);
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        String print = "print ";
        for (int i = 0; i< arguments.size() - 1 ; i++){
            print += arguments.get(i).toString() + ",";
        }
        print += arguments.get(arguments.size() - 1).toString();
        return print;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        super.validateSemantics();
        
        SemanticUtils.getInstance().setInsidePrint(true);
        for (Argument st : arguments){
            st.validateSemantics();
        }
        SemanticUtils.getInstance().setInsidePrint(false);
    }

    @Override
    public void execute() throws GranitaException {
        for (Argument argument : arguments) {
            System.out.print(argument.evaluate());
        }
    }
    
}
