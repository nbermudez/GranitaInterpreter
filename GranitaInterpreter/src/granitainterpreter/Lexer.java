/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package granitainterpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Lexer {
    
    //<editor-fold defaultstate="collapsed" desc="Instance Attributes">
    private final int EOF = -1;
    private int lineNumber = 0;
    private RandomAccessFile memoryMappedFile = null;
    private MappedByteBuffer in = null;
    private HashMap<String, Token> keywords;
    //</editor-fold>    

    //<editor-fold defaultstate="collapsed" desc="Constructor">
    public Lexer(String path)
            throws IOException {
        try {
            FileChannel fc = new FileInputStream(new File(path)).getChannel();
            this.memoryMappedFile = new RandomAccessFile(path, "r");
            this.in = fc.map(FileChannel.MapMode.READ_ONLY, 0L, fc.size());

            this.keywords = new HashMap<String, Token>();
            this.keywords.put("bool", Token.TK_KW_BOOL);
            this.keywords.put("break", Token.TK_KW_BREAK);
            this.keywords.put("class", Token.TK_KW_CLASS);
            this.keywords.put("continue", Token.TK_KW_CONTINUE);
            this.keywords.put("else", Token.TK_KW_ELSE);
            this.keywords.put("extends", Token.TK_KW_EXTENDS);
            this.keywords.put("false", Token.TK_KW_FALSE);
            this.keywords.put("for", Token.TK_KW_FOR);
            this.keywords.put("if", Token.TK_KW_IF);
            this.keywords.put("int", Token.TK_KW_INT);
            this.keywords.put("new", Token.TK_KW_NEW);
            this.keywords.put("null", Token.TK_KW_NULL);
            this.keywords.put("print", Token.TK_KW_PRINT);
            this.keywords.put("read", Token.TK_KW_READ);
            this.keywords.put("return", Token.TK_KW_RETURN);
            this.keywords.put("rot", Token.TK_KW_ROT);
            this.keywords.put("true", Token.TK_KW_TRUE);
            this.keywords.put("void", Token.TK_KW_VOID);
            this.keywords.put("while", Token.TK_KW_WHILE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Lexer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.memoryMappedFile.close();
            } catch (IOException ex) {
                Logger.getLogger(Lexer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Helper functions">
    private int getSymbol() {
        if (this.in.hasRemaining()) {
            this.in.mark();
            return this.in.get();
        }
        return -1;
    }

    private void ungetSymbol(int cs) {
        if (cs != EOF) {
            this.in.reset();
        }
    }

    private boolean isEscapedValid(int cs) {
        if ((cs == 'n') || (cs == 'r') || (cs == 'a') || (cs == 't') || (cs == '\\')) {
            return true;
        }
        return false;
    }

    private boolean isHexDigit(int cs) {
        if (((cs >= 'a') && (cs <= 'f')) || ((cs >= 'A') && (cs <= 'F')) || ((cs >= '0') && (cs <= '9'))) {
            return true;
        }
        return false;
    }

    private void skipUntilEOL() {
        int cs;
        cs = getSymbol();
        while ((cs != '\n') && (cs != EOF)) {
            cs = getSymbol();
        }
        if (cs == '\n') {
            this.lineNumber += 1;
        }
    }
    
    private Token errorToken(String message, boolean unget){
        if (unget){
            ungetSymbol(' ');
        }
        return new Token(Token.TokenType.ERROR, message);
    }
    //</editor-fold>    

    public Token nextToken() {
        Token result = new Token(Token.TokenType.ERROR, "");

        while (true) {
            int cs = getSymbol();
            while ((cs != EOF) && (Character.isWhitespace((char) cs))) {
                if (cs == '\n') {
                    this.lineNumber += 1;
                }
                cs = getSymbol();
            }
            switch (cs) {
                case '-':
                    return Token.TK_OP_SUB;
                case '+':
                    return Token.TK_OP_ADD;
                case '*':
                    return Token.TK_OP_MULT;
                case '/':
                    result.lexeme += (char) cs;

                    cs = getSymbol();
                    if (cs == '/') {
                        result.lexeme = "";
                        skipUntilEOL();
                        continue;
                    }
                    if (cs == '*') {
                        result.lexeme = "";
                        cs = getSymbol();
                        while (cs != EOF) {
                            if (cs == '\n') {
                                this.lineNumber += 1;
                            }
                            if (cs == '*') {
                                cs = getSymbol();
                                if (cs == '/') {
                                    break;
                                }
                            }
                            cs = getSymbol();
                        }
                        continue;
                    }
                    return Token.TK_OP_DIV;
                case '%':
                    return Token.TK_OP_MOD;
                case '.':
                    return Token.TK_DOT;
                case ',':
                    return Token.TK_COLON;
                case ';':
                    return Token.TK_SEMICOLON;
                case '>':
                    result.lexeme += (char) cs;
                    cs = getSymbol();
                    if (cs == '>') {
                        return Token.TK_OP_RIGHT_SHIFT;
                    }
                    if (cs == '=') {
                        return Token.TK_REL_GTE;
                    }
                    ungetSymbol(cs);
                    return Token.TK_REL_GT;
                case '<':
                    result.lexeme += (char) cs;
                    cs = getSymbol();
                    if (cs == '<') {
                        return Token.TK_OP_LEFT_SHIFT;
                    }
                    if (cs == '=') {
                        return Token.TK_REL_LTE;
                    }
                    ungetSymbol(cs);
                    return Token.TK_REL_LT;
                case '=':
                    result.lexeme += (char) cs;
                    cs = getSymbol();
                    if (cs == '=') {
                        return Token.TK_REL_EQ;
                    }
                    ungetSymbol(cs);
                    return Token.TK_OP_EQ;
                case '!':
                    result.lexeme += (char) cs;
                    cs = getSymbol();
                    if (cs == '=') {
                        return Token.TK_REL_NEQ;
                    }
                    ungetSymbol(cs);
                    return Token.TK_OP_NOT;
                case '&':
                    result.lexeme += (char) cs;
                    cs = getSymbol();
                    if (cs == '&') {
                        return Token.TK_OP_AND;
                    }
                    return errorToken("Lexer error: & expected, found " + (char) cs + " in line " + this.lineNumber, true);
                case '|':
                    result.lexeme += (char) cs;
                    cs = getSymbol();
                    if (cs == '|') {
                        return Token.TK_OP_OR;
                    }
                    return errorToken("Lexer error: | expected, found " + (char) cs + " in line " + this.lineNumber, true);
                case '{':
                    return Token.TK_LEFT_CURLY_BRACKET;
                case '}':
                    return Token.TK_RIGHT_CURLY_BRACKET;
                case '[':
                    return Token.TK_LEFT_BRACKET;
                case ']':
                    return Token.TK_RIGHT_BRACKET;
                case '(':
                    return Token.TK_LEFT_PARENTHESIS;
                case ')':
                    return Token.TK_RIGHT_PARENTHESIS;
                case '\"':
                    result.lexeme += (char) cs;
                    cs = getSymbol();
                    while (cs != '"') {
                        if (cs == EOF) {
                            return errorToken("Lexer error: \" expected: got EOF in line " + this.lineNumber, false);
                        }
                        if (cs == '\\') {
                            result.lexeme += (char) cs;
                            cs = getSymbol();
                            if (!isEscapedValid(cs) && cs == '\"' && cs == '\'') {
                                return errorToken("Lexer error: invalid escaped character in line " + this.lineNumber, true);
                            }
                        }
                        result.lexeme += (char) cs;
                        cs = getSymbol();
                    }
                    result.lexeme += (char) cs;
                    result.type = Token.TokenType.STRING_CONSTANT;
                    return result;
                case '\'':
                    result.lexeme += (char) cs;
                    if (cs == '\\') {
                        cs = getSymbol();
                        if (!isEscapedValid(cs)) {
                            return errorToken("Lexer error: invalid escaped character in line " + this.lineNumber, true);
                        }
                        result.lexeme += (char) cs;
                        cs = getSymbol();
                        if (cs == '\'') {
                            result.lexeme += (char) cs;
                            result.type = Token.TokenType.CHAR_CONSTANT;
                            return result;
                        }
                        return errorToken("Lexer error: expected ', got " + (char) cs + " in line " + this.lineNumber, true);
                    }

                    cs = getSymbol();

                    if ( /*cs >= 32 && */cs <= 126) {
                        result.lexeme += (char) cs;

                        cs = getSymbol();
                        if (cs == '\'') {
                            result.lexeme += (char) cs;
                            result.type = Token.TokenType.CHAR_CONSTANT;
                            return result;
                        }
                        return errorToken("Lexer error: expected ', got " + (char) cs + " in line " + this.lineNumber, true);
                    }
                    
                    return errorToken("Lexer error: not a printable character in line " + this.lineNumber, true);
                case '0':
                    result.lexeme += (char) cs;
                    cs = getSymbol();
                    if ((cs == 'x') || (cs == 'X')) {
                        result.lexeme += (char) cs;
                        cs = getSymbol();
                        if (!isHexDigit(cs)) {
                            return errorToken("Lexer error: not a valid hexadecimal number in line " + this.lineNumber, true);
                        }
                        while (isHexDigit(cs)) {
                            result.lexeme += (char) cs;
                            cs = getSymbol();
                        }
                        ungetSymbol(cs);
                        result.type = Token.TokenType.INT_CONSTANT;
                        return result;
                    }
                    while (Character.isDigit((char) cs)) {
                        result.lexeme += (char) cs;
                        cs = getSymbol();
                    }
                    ungetSymbol(cs);
                    result.type = Token.TokenType.INT_CONSTANT;
                    return result;
                default:
                    if ((cs >= '1') && (cs <= '9')) {
                        result.lexeme += (char) cs;
                        cs = getSymbol();

                        while (Character.isDigit((char) cs)) {
                            result.lexeme += (char) cs;
                            cs = getSymbol();
                        }
                        ungetSymbol(cs);
                        result.type = Token.TokenType.INT_CONSTANT;
                        return result;
                    } else if ((Character.isLetter((char) cs)) || (cs == '_')) {
                        result.lexeme += (char) cs;
                        cs = getSymbol();
                        while ((Character.isLetterOrDigit((char) cs)) || (cs == '_')) {
                            result.lexeme += (char) cs;
                            cs = getSymbol();
                        }
                        ungetSymbol(cs);
                        if (this.keywords.containsKey(result.lexeme)) {
                            return this.keywords.get(result.lexeme);
                        }
                        result.type = Token.TokenType.IDENTIFIER;
                        return result;
                    } else {
                        return Token.TK_EOF;
                    }
            }
        }
    }
    
    @Override
    public String toString() {
        this.in.rewind();
        String all = new String();
        Token tk;
        do {
            tk = this.nextToken();
            if (tk.type == Token.TokenType.ERROR) {
                break;
            } else {
                all += tk.toString() + "\n";
            }
        } while (tk.type != Token.TokenType.EOF);
        return all;
    }
}
