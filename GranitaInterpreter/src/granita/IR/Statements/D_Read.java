/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.IR.Statements;

import granita.IR.LeftValues.D_ArrayLeftValue;
import granita.IR.LeftValues.D_LeftValue;
import granita.Interpreter.Interpreter;
import granita.Interpreter.Results.BoolResult;
import granita.Interpreter.Results.IntResult;
import granita.Interpreter.Results.Result;
import granita.Types.IntType;
import granita.Types.Type;
import granitainterpreter.ErrorHandler;
import granitainterpreter.GranitaException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class D_Read extends D_Statement {

    ArrayList<D_LeftValue> variables;

    public D_Read(ArrayList<D_LeftValue> variables) {
        this.variables = variables;
    }

    @Override
    public void execute() {
        Scanner scanner = new Scanner(System.in);
        for (D_LeftValue d_LeftValue : variables) {
            Type t = d_LeftValue.getExpressionType();
            Object readData;
            try {
                if (t instanceof IntType) {
                    readData = scanner.nextInt();
                } else {
                    readData = scanner.nextBoolean();
                }
                if (d_LeftValue instanceof D_ArrayLeftValue) {
                    D_ArrayLeftValue dd = (D_ArrayLeftValue) d_LeftValue;
                    Interpreter.currentContext().set(dd.getIdentifier(), dd.getIndex(), readData);
                } else {
                    Interpreter.currentContext().set(d_LeftValue.getIdentifier(), readData);
                }
            } catch (Exception ex) {
                try {
                    ErrorHandler.handleFatalError("incompatible types, expected " + t);
                } catch (GranitaException ex1) {
                    System.err.println("-_-");
                }
            }
        }
    }

    @Override
    public void exec() {
        Scanner scanner = new Scanner(System.in);
        for (D_LeftValue d_LeftValue : variables) {
            Type t = d_LeftValue.getExpressionType();
            Object readData;
            Result r;
            try {
                if (t instanceof IntType) {
                    readData = scanner.nextInt();
                    r = new IntResult((Integer)readData);
                } else {
                    readData = scanner.nextBoolean();
                    r = new BoolResult((Boolean) readData);
                }
                if (d_LeftValue instanceof D_ArrayLeftValue) {
                    D_ArrayLeftValue dd = (D_ArrayLeftValue) d_LeftValue;
                    int x = dd.getArrayIndex();
                    Interpreter.currentContext().setArrayItemInRE(0, d_LeftValue.getContextPosition(), x, r);
                } else {
                    Interpreter.currentContext().setVariableInRE(d_LeftValue.getContextId(), d_LeftValue.getContextPosition(), r);
                }
            } catch (Exception ex) {
                try {
                    ErrorHandler.handleFatalError("incompatible types, expected " + t);
                } catch (GranitaException ex1) {
                    System.err.println("-_-");
                }
            }
        }
    }
}
