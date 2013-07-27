/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

import java.io.IOException;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class GranitaInterpreter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {
            Lexer l = new Lexer("C:/Users/Alejandro/Documents/GitHub/GranitaInterpreter/test_programs/program1.txt");
            Token t;
            do {
                t = l.nextToken();
                System.out.println("Lexeme: " + t.lexeme);
                if (t.type == Token.TokenType.ERROR) {
                    break;
                }
            } while (t.type != Token.TokenType.EOF);
        } catch (IOException ex) {
            System.out.println("IO Error");
        }
    }
}
