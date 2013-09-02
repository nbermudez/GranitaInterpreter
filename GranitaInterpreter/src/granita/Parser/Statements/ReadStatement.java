/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Parser.Statements;

import granita.Parser.LeftValues.LeftValue;
import granita.Semantic.Types.BoolType;
import granita.Semantic.Types.IntType;
import granita.Semantic.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import granitainterpreter.SemanticUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class ReadStatement extends Statement {
    
    ArrayList<LeftValue> leftValues;

    public ReadStatement(int line) {
        super(line);
        leftValues = new ArrayList<LeftValue>();
    }

    public ReadStatement(ArrayList<LeftValue> leftValues, int line) {
        super(line);
        this.leftValues = leftValues;
    }

    @Override
    public String toString() {
        String read = "read ";
        for (int i = 0; i< leftValues.size() - 1 ; i++){
            read += leftValues.get(i).toString() + ",";
        }
        read += leftValues.get(leftValues.size() - 1).toString();
        return read;
    }

    public ArrayList<LeftValue> getLeftValues() {
        return leftValues;
    }

    public void setLeftValues(ArrayList<LeftValue> leftValues) {
        this.leftValues = leftValues;
    }

    @Override
    public void validateSemantics() throws GranitaException {
        super.validateSemantics();
        SemanticUtils.getInstance().setInsideRead(true);
        for (LeftValue lv : leftValues){
            lv.validateSemantics();
        }
        SemanticUtils.getInstance().setInsideRead(false);
    }

    @Override
    public void execute() throws GranitaException {
        Scanner scanner = new Scanner(System.in);
        for (LeftValue leftValue : leftValues) {
            Type t = leftValue.getLocation();
            try {
                if (t instanceof IntType) {
                    Integer readValue = scanner.nextInt();
                    t.setValue(readValue);
                } else if (t instanceof BoolType) {
                    Boolean boolValue = scanner.nextBoolean();
                    t.setValue(boolValue);
                }
            } catch (Exception ex) {
                ErrorHandler.handleFatalError("incompatible types, expected "+ t
                        + ": line " + line);
            }
        }
    }
    
}
