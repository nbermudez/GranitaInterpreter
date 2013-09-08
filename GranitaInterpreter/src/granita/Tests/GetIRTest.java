/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Tests;

import granita.IR.General.D_Program;
import granita.IR.General.IntermediateRepresentation;
import granita.Interpreter.Interpreter;
import granita.Lexer.Lexer;
import granita.Parser.ParserTree;
import granita.Parser.Statements.ClassStatement;
import granitainterpreter.ErrorHandler;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class GetIRTest {
    public static void main(String[] args){
        try {
            String path = "C:/Users/Alejandro/Documents/GitHub/GranitaInterpreter/test_programs/";
            Lexer lexer = new Lexer(path + "program5.txt");

            ParserTree parser = new ParserTree(lexer);
            ArrayList<ClassStatement> trees = parser.parse();
            for (ClassStatement tree : trees) {
                //System.out.println(tree.toString());
                D_Program program = IntermediateRepresentation.validateAndGenerate(tree);
                if (ErrorHandler.isEmpty()) {
                    Interpreter.interpret(program);
                } else {
                    ErrorHandler.printAll();
                    ErrorHandler.cleanup();
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
