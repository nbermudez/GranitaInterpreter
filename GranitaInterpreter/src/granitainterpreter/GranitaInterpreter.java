/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

import granita.Statements.Statement;
import java.util.ArrayList;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class GranitaInterpreter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String path = "C:/Users/Alejandro/Documents/GitHub/GranitaInterpreter/test_programs/";
            Lexer lexer = new Lexer(path + "program9.txt");
            //System.out.println(lexer.toString());
            
            ParserTree parser = new ParserTree(lexer);
            ArrayList<Statement> trees = parser.parse();
            for (Statement tree : trees){
                System.out.println(tree.toString());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            //System.out.println(ex.getMessage());
        }
    }
}

