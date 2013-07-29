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
public class Parser {

    private Token currentToken;
    private Lexer lexer;

    public Parser(String path) throws IOException {
        lexer = new Lexer(path);
        currentToken = lexer.nextToken();
    }

    public void parse() {
        Token t;
        do {
            t = lexer.nextToken();
            if (t.type == Token.TokenType.ERROR) {
                break;
            } else {
                System.out.println("TOKEN: " + t.type.name() + "(" + t.lexeme + ")");
            }
        } while (t.type != Token.TokenType.EOF);
    }

    private void program() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void class_name() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void field_decl() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void method_decl() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void type() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void statement() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void var_decl() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void constant() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void block() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void assign() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void method_call() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void expr() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void lvalue() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void method_name() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void argument() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void bin_op() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void arith_op() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void rel_op() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void eq_op() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void cond_op() throws Exception {
        throw new Exception("Parser error debug");
    }

    private void bool_constant() throws Exception {
        throw new Exception("Parser error debug");
    }
}
