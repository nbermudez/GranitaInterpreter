/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granita.Lexer;

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
    
    public Token(Token toCopy){
        this.type = toCopy.type;
        this.lexeme = toCopy.lexeme;
    }

    public static enum TokenType {

        RIGHT_PARENTHESIS, LEFT_PARENTHESIS,
        RIGHT_CURLY, LEFT_CURLY,
        RIGHT_BRACKET, LEFT_BRACKET,
        REL_GREATER, REL_GREATER_EQ, REL_LESS, REL_LESS_EQ,
        REL_EQ, REL_NOT_EQ, OP_ADD, OP_SUB, OP_MULT,
        OP_DIV, OP_MOD, OP_NOT, OP_AND, OP_OR, DOT, SEMICOLON,
        COLON, OP_EQ, OP_RIGHT_SHIFT, OP_LEFT_SHIFT,
        IDENTIFIER, STRING_CONSTANT, INT_CONSTANT, CHAR_CONSTANT,
        
        KW_BOOL, KW_BREAK, KW_PRINT, KW_READ, KW_CONTINUE,
        KW_CLASS, KW_ELSE, KW_EXTENDS, KW_FALSE, KW_FOR,
        KW_IF, KW_INT, KW_NEW, KW_NULL, KW_RETURN,
        KW_ROT, KW_TRUE, KW_VOID, KW_WHILE,
        ERROR, EOF;
    }

    @Override
    public String toString() {
        return "Token{" + "lexeme: " + lexeme + ", type: " + type.name() + '}';
    }    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.type != null ? this.type.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Token other = (Token) obj;
        if (this.type != other.type) {
            return false;
        }
        return true;
    }
    
    //<editor-fold defaultstate="collapsed" desc="Static Tokens">
    public static final Token TK_RIGHT_PARENTHESIS = new Token(TokenType.RIGHT_PARENTHESIS, ")");
    public static final Token TK_LEFT_PARENTHESIS = new Token(TokenType.LEFT_PARENTHESIS, "(");
    public static final Token TK_RIGHT_CURLY_BRACKET = new Token(TokenType.RIGHT_CURLY, "}");
    public static final Token TK_LEFT_CURLY_BRACKET = new Token(TokenType.LEFT_CURLY, "{");
    public static final Token TK_RIGHT_BRACKET = new Token(TokenType.RIGHT_BRACKET, "]");
    public static final Token TK_LEFT_BRACKET = new Token(TokenType.LEFT_BRACKET, "[");
    public static final Token TK_DOT = new Token(TokenType.DOT, ".");
    public static final Token TK_COLON = new Token(TokenType.COLON, ",");
    public static final Token TK_SEMICOLON = new Token(TokenType.SEMICOLON, ";");
    
    public static final Token TK_REL_LT = new Token(TokenType.REL_LESS, "<"); 
    public static final Token TK_REL_LTE = new Token(TokenType.REL_LESS_EQ, "<="); 
    public static final Token TK_REL_GT = new Token(TokenType.REL_GREATER, ">"); 
    public static final Token TK_REL_GTE = new Token(TokenType.REL_GREATER_EQ, ">="); 
    public static final Token TK_REL_EQ = new Token(TokenType.REL_EQ, "=="); 
    public static final Token TK_REL_NEQ = new Token(TokenType.REL_NOT_EQ, "!="); 
    
    public static final Token TK_OP_ADD = new Token(TokenType.OP_ADD, "+");
    public static final Token TK_OP_SUB = new Token(TokenType.OP_SUB, "-");
    public static final Token TK_OP_MULT = new Token(TokenType.OP_MULT, "*");
    public static final Token TK_OP_DIV = new Token(TokenType.OP_DIV, "/");
    public static final Token TK_OP_MOD = new Token(TokenType.OP_MOD, "%");    
    
    public static final Token TK_OP_RIGHT_SHIFT = new Token(TokenType.OP_RIGHT_SHIFT, ">>"); 
    public static final Token TK_OP_LEFT_SHIFT = new Token(TokenType.OP_LEFT_SHIFT, "<<");
    public static final Token TK_OP_EQ = new Token(TokenType.OP_EQ, "="); 
    public static final Token TK_OP_AND = new Token(TokenType.OP_AND, "&&");
    public static final Token TK_OP_OR = new Token(TokenType.OP_OR, "||"); 
    public static final Token TK_OP_NOT = new Token(TokenType.OP_NOT, "!"); 
    
    public static final Token TK_IDENTIFIER = new Token(TokenType.IDENTIFIER, "");
    public static final Token TK_CHAR_CONSTANT = new Token(TokenType.CHAR_CONSTANT, "");
    public static final Token TK_INT_CONSTANT = new Token(TokenType.INT_CONSTANT, "");
    public static final Token TK_STRING_CONSTANT = new Token(TokenType.STRING_CONSTANT, "");
    
    public static final Token TK_KW_BOOL = new Token(TokenType.KW_BOOL, "bool");
    public static final Token TK_KW_BREAK = new Token(TokenType.KW_BREAK, "break");
    public static final Token TK_KW_CLASS = new Token(TokenType.KW_CLASS, "class");
    public static final Token TK_KW_CONTINUE = new Token(TokenType.KW_CONTINUE, "continue");
    public static final Token TK_KW_ELSE = new Token(TokenType.KW_ELSE, "");
    public static final Token TK_KW_EXTENDS = new Token(TokenType.KW_EXTENDS, "extends");
    public static final Token TK_KW_FALSE = new Token(TokenType.KW_FALSE, "false");
    public static final Token TK_KW_FOR = new Token(TokenType.KW_FOR, "for");
    public static final Token TK_KW_IF = new Token(TokenType.KW_IF, "if");
    public static final Token TK_KW_INT = new Token(TokenType.KW_INT, "int");
    public static final Token TK_KW_NEW = new Token(TokenType.KW_NEW, "new");
    public static final Token TK_KW_NULL = new Token(TokenType.KW_NULL, "null");
    public static final Token TK_KW_PRINT = new Token(TokenType.KW_PRINT, "print");
    public static final Token TK_KW_READ = new Token(TokenType.KW_READ, "read");
    public static final Token TK_KW_RETURN = new Token(TokenType.KW_RETURN, "return");
    public static final Token TK_KW_ROT = new Token(TokenType.KW_ROT, "rot");
    public static final Token TK_KW_TRUE = new Token(TokenType.KW_TRUE, "true");
    public static final Token TK_KW_VOID = new Token(TokenType.KW_VOID, "void");
    public static final Token TK_KW_WHILE = new Token(TokenType.KW_WHILE, "while");
    
    public static final Token TK_EOF = new Token(TokenType.EOF, ""); 
    //</editor-fold>

}
