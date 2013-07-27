/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Token {

    public String lexeme;
    public TokenType type;

    public Token(TokenType type, String lex) {
        this.type = type;
        this.lexeme = lex;
    }

    public static enum TokenType {

        RIGHT_PARENTHESIS, LEFT_PARENTHESIS,
        RIGHT_CURLY, LEFT_CURLY,
        RIGHT_BRACKET, LEFT_BRACKET,
        REL_GREATER, REL_GREATER_EQ, REL_LESS, REL_LESS_EQ,
        REL_EQ, REL_NOT_EQ, OP_SUM, OP_MINUS, OP_TIMES,
        OP_DIV, OP_MOD, OP_NOT, OP_AND, OP_OR, DOT, DOT_COMA,
        COMA, OP_EQ, OP_RIGHT_SHIFT, OP_LEFT_SHIFT,
        ID, STRING_CONSTANT, INT_CONSTANT, CHAR_CONSTANT,
        KW_BOOL, KW_BREAK, KW_PRINT, KW_READ, KW_CONTINUE,
        KW_CLASS, KW_ELSE, KW_EXTENDS, KW_FALSE, KW_FOR,
        KW_IF, KW_INT, KW_NEW, KW_NULL, KW_RETURN,
        KW_ROT, KW_TRUE, KW_VOID, KW_WHILE,
        ERROR, EOF;
    }
}
