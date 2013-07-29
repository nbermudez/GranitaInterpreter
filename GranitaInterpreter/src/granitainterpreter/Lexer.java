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
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Néstor A. Bermúdez <nestor.bermudez@unitec.edu>
 */
public class Lexer {

    private final int EOF = -1;
    private int lineNumber = 0;
    private RandomAccessFile memoryMappedFile = null;
    private MappedByteBuffer out = null;
    Hashtable<String, Token.TokenType> keywords;

    public Lexer(String path)
            throws IOException {
        try {
            FileChannel fc = new FileInputStream(new File(path)).getChannel();
            this.memoryMappedFile = new RandomAccessFile(path, "r");
            this.out = fc.map(FileChannel.MapMode.READ_ONLY, 0L, fc.size());

            this.keywords = new Hashtable();
            this.keywords.put("bool", Token.TokenType.KW_BOOL);
            this.keywords.put("break", Token.TokenType.KW_BREAK);
            this.keywords.put("class", Token.TokenType.KW_CLASS);
            this.keywords.put("continue", Token.TokenType.KW_CONTINUE);
            this.keywords.put("else", Token.TokenType.KW_ELSE);
            this.keywords.put("extends", Token.TokenType.KW_EXTENDS);
            this.keywords.put("false", Token.TokenType.KW_FALSE);
            this.keywords.put("for", Token.TokenType.KW_FOR);
            this.keywords.put("if", Token.TokenType.KW_IF);
            this.keywords.put("int", Token.TokenType.KW_INT);
            this.keywords.put("new", Token.TokenType.KW_NEW);
            this.keywords.put("null", Token.TokenType.KW_NULL);
            this.keywords.put("print", Token.TokenType.KW_PRINT);
            this.keywords.put("read", Token.TokenType.KW_READ);
            this.keywords.put("return", Token.TokenType.KW_RETURN);
            this.keywords.put("rot", Token.TokenType.KW_ROT);
            this.keywords.put("true", Token.TokenType.KW_TRUE);
            this.keywords.put("void", Token.TokenType.KW_VOID);
            this.keywords.put("while", Token.TokenType.KW_WHILE);
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

    int getSymbol() {
        if (this.out.hasRemaining()) {
            this.out.mark();
            return this.out.get();
        }
        return -1;
    }

    void ungetSymbol(int cs) {
        if (this.out.hasRemaining()) {
            this.out.reset();
        }
    }

    private boolean isEscapedValid(int cs) {
        if ((cs == 92) || (cs == 110) || (cs == 116) || (cs == 114) || (cs == 97)) {
            return true;
        }
        return false;
    }

    private boolean isHexDigit(int cs) {
        if (((cs >= 48) && (cs <= 57)) || ((cs >= 97) && (cs <= 102)) || ((cs >= 65) && (cs <= 70))) {
            return true;
        }
        return false;
    }

    public Token nextToken() {
        Token result = new Token(Token.TokenType.EOF, "");
        int cs = getSymbol();
        while ((cs != EOF) && (Character.isWhitespace((char) cs))) {
            if (cs == 10) {
                this.lineNumber += 1;
            }
            cs = getSymbol();
        }
        switch (cs) {
            case '-':
                result.lexeme += (char) cs;
                result.type = Token.TokenType.OP_MINUS;
                return result;
            case '+':
                result.lexeme += (char) cs;
                result.type = Token.TokenType.OP_SUM;
                return result;
            case '*':
                result.lexeme += (char) cs;
                result.type = Token.TokenType.OP_TIMES;
                return result;
            case '/':
                result.lexeme += (char) cs;

                cs = getSymbol();
                if (cs == '/') {
                    result.lexeme = "";
                    cs = getSymbol();
                    while ((cs != '\n') && (cs != EOF)) {
                        cs = getSymbol();
                    }
                    if (cs == 10) {
                        this.lineNumber += 1;
                    }
                    return nextToken();
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
                    return nextToken();
                }
                result.type = Token.TokenType.OP_DIV;
                return result;
            case '%':
                result.lexeme += (char) cs;
                result.type = Token.TokenType.OP_MOD;
                return result;
            case '.':
                result.lexeme += (char) cs;
                result.type = Token.TokenType.DOT;
                return result;
            case ',':
                result.lexeme += (char) cs;
                result.type = Token.TokenType.COMA;
                return result;
            case ';':
                result.lexeme += (char) cs;
                result.type = Token.TokenType.DOT_COMA;
                return result;
            case '>':
                result.lexeme += (char) cs;
                cs = getSymbol();
                if (cs == '>') {
                    result.lexeme += (char) cs;
                    result.type = Token.TokenType.OP_RIGHT_SHIFT;
                    return result;
                }
                if (cs == '=') {
                    result.lexeme += (char) cs;
                    result.type = Token.TokenType.REL_GREATER_EQ;
                    return result;
                }
                ungetSymbol(cs);
                result.type = Token.TokenType.REL_GREATER;
                return result;
            case '<':
                result.lexeme += (char) cs;
                cs = getSymbol();
                if (cs == '<') {
                    result.lexeme += (char) cs;
                    result.type = Token.TokenType.OP_LEFT_SHIFT;
                    return result;
                }
                if (cs == '=') {
                    result.lexeme += (char) cs;
                    result.type = Token.TokenType.REL_LESS_EQ;
                    return result;
                }
                ungetSymbol(cs);
                result.type = Token.TokenType.REL_LESS;
                return result;
            case '=':
                result.lexeme += (char) cs;
                cs = getSymbol();
                if (cs == '=') {
                    result.lexeme += (char) cs;
                    result.type = Token.TokenType.REL_EQ;
                    return result;
                }
                ungetSymbol(cs);
                result.type = Token.TokenType.OP_EQ;
                return result;
            case '!':
                result.lexeme += (char) cs;
                cs = getSymbol();
                if (cs == '=') {
                    result.lexeme += (char) cs;
                    result.type = Token.TokenType.REL_NOT_EQ;
                    return result;
                }
                ungetSymbol(cs);
                result.type = Token.TokenType.OP_NOT;
                return result;
            case '&':
                result.lexeme += (char) cs;
                cs = getSymbol();
                if (cs == '&') {
                    result.lexeme += (char) cs;
                    result.type = Token.TokenType.OP_AND;
                    return result;
                }
                result.lexeme = ("Lexer error: & expected, found " + (char) cs + " in line " + this.lineNumber);
                result.type = Token.TokenType.ERROR;
                ungetSymbol(cs);
                return result;
            case '|':
                result.lexeme += (char) cs;
                cs = getSymbol();
                if (cs == '|') {
                    result.lexeme += (char) cs;
                    result.type = Token.TokenType.OP_OR;
                    return result;
                }
                result.lexeme = ("Lexer error: | expected, found " + (char) cs + " in line " + this.lineNumber);
                result.type = Token.TokenType.ERROR;
                ungetSymbol(cs);
                return result;
            case '{':
                result.lexeme += (char) cs;
                result.type = Token.TokenType.LEFT_CURLY;
                return result;
            case '}':
                result.lexeme += (char) cs;
                result.type = Token.TokenType.RIGHT_CURLY;
                return result;
            case '[':
                result.lexeme += (char) cs;
                result.type = Token.TokenType.LEFT_BRACKET;
                return result;
            case ']':
                result.lexeme += (char) cs;
                result.type = Token.TokenType.RIGHT_BRACKET;
                return result;
            case '(':
                result.lexeme += (char) cs;
                result.type = Token.TokenType.LEFT_PARENTHESIS;
                return result;
            case ')':
                result.lexeme += (char) cs;
                result.type = Token.TokenType.RIGHT_PARENTHESIS;
                return result;
            case '\"':
                result.lexeme += (char) cs;
                cs = getSymbol();
                while (cs != '"') {
                    if (cs == EOF) {
                        result.lexeme = ("Lexer error: \" expected: got EOF in line " + this.lineNumber);
                        result.type = Token.TokenType.ERROR;
                        return result;
                    }
                    if (cs == '\\') {
                        result.lexeme += (char) cs;
                        cs = getSymbol();
                        if (!isEscapedValid(cs)) {
                            result.lexeme = ("Lexer error: invalid escaped character in line " + this.lineNumber);
                            result.type = Token.TokenType.ERROR;
                            return result;
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
                        result.lexeme = ("Lexer error: invalid escaped character in line " + this.lineNumber);
                        result.type = Token.TokenType.ERROR;
                        return result;
                    }
                    result.lexeme += (char) cs;
                    cs = getSymbol();
                    if (cs == '\'') {
                        result.lexeme += (char) cs;
                        result.type = Token.TokenType.CHAR_CONSTANT;
                        return result;
                    }
                    result.lexeme = ("Lexer error: expected ', got " + (char) cs + " in line " + this.lineNumber);
                    result.type = Token.TokenType.ERROR;
                    return result;
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
                    result.lexeme = ("Lexer error: expected ', got " + (char) cs + " in line " + this.lineNumber);
                    result.type = Token.TokenType.ERROR;
                    return result;
                }

                result.lexeme = ("Lexer error: not a printable character in line " + this.lineNumber);
                result.type = Token.TokenType.ERROR;
                return result;
            case '0':
                result.lexeme += (char) cs;
                cs = getSymbol();
                if ((cs == 'x') || (cs == 'X')) {
                    result.lexeme += (char) cs;
                    cs = getSymbol();
                    if (!isHexDigit(cs)) {
                        result.lexeme = ("Lexer error: not a valid hexadecimal number in line " + this.lineNumber);
                        result.type = Token.TokenType.ERROR;
                        return result;
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
                }
                if ((Character.isLetter((char) cs)) || (cs == '_')) {
                    result.lexeme += (char) cs;
                    cs = getSymbol();
                    while ((Character.isLetterOrDigit((char) cs)) || (cs == '_')) {
                        result.lexeme += (char) cs;
                        cs = getSymbol();
                    }
                    ungetSymbol(cs);
                    if (this.keywords.containsKey(result.lexeme)) {
                        result.type = ((Token.TokenType) this.keywords.get(result.lexeme));
                        return result;
                    }
                    result.type = Token.TokenType.ID;
                    return result;
                }
        }
        return result;
    }
}
