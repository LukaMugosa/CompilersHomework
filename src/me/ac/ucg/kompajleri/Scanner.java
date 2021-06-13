package me.ac.ucg.kompajleri;

import me.ac.ucg.kompajleri.constants.RegexConstants;
import me.ac.ucg.kompajleri.constants.TokenCodes;
import me.ac.ucg.kompajleri.services.CreateHashmapsService;

import java.io.*;
import java.util.HashMap;

public class Scanner {
    private static final char eofCh = '\u0080';
    private static final char eol = '\n';


    //    private static final String key[] = { // sorted list of keywords
//            "LET", "IN", "END"
//    };
    /*
     * TODO: NAPRAVICEMO HASH MAPU KOJA CUVA "KEYWORD" => "TOKEN_CODE"
     *  NA INICIJALIZACIJU SKENERA CEMO TU MAPU NAPUNITI KLJUCNIM RIJECIMA I KODOVIMA
     */
    public static HashMap<String, Integer> keywords;
    /*
     * TODO: PRAVIMO MAPU TOKENA
     * */
    public static HashMap<String, Integer> tokens;
    /*
     * TODO: PRAVIMO MAPU TIPOVA PODATAKA
     * */
    public static HashMap<String, Integer> dataTypes;
    /*
     * TODO: PRAVIMO MAPU OPERATORA
     * */
    public static HashMap<String, Integer> operators;

//    private static final int keyVal[] = {
//            TokenCodes.let_, TokenCodes.in_, TokenCodes.end_
//    };

    private static char ch;            // lookahead character
    public static int col;            // current column
    public static int line;        // current line
    private static int pos;            // current position from start of source file
    private static Reader in;        // source file reader
    private static char[] lex;        // current lexeme (token string)

    //----- ch = next input character
    private static void nextCh() {
        try {
            ch = (char) in.read();
            col++;
            pos++;
            if (ch == eol) {
                line++;
                col = 0;
            } else if (ch == '\uffff') ch = eofCh;
        } catch (IOException e) {
            ch = eofCh;
        }
    }

    //--------- Initialize scanner
    public static void init(Reader r) {
        in = new BufferedReader(r);
        lex = new char[64];
        line = 1;
        col = 0;
        keywords = CreateHashmapsService.createKeywordsMap();
        tokens = CreateHashmapsService.createTokensMap();
        dataTypes = CreateHashmapsService.createDataTypesMap();
        operators = CreateHashmapsService.createOperatorsMap();
        nextCh();
    }

    public static void scanString(Token token) {
        nextCh();
        token.string = "";
        while (ch != '\n' && ch != '\"') {
            token.string = token.string.concat(Character.toString(ch));
            nextCh();
        }
        if (ch == '\"')
            token.kind = TokenCodes.stringConstant_;
        else {
            token.kind = TokenCodes.none;
            Parser.errors++;
            System.out.println("An error occurred while trying to scan the file on line " + line + ", col" + col + " .");
            System.out.println("Invalid string format!");
            System.out.println("Either string contains a new line, or is not properly formatted!");
        }
        nextCh();
    }

    private static Boolean isAValidDigit() {
        return Character.isDigit(ch) ||
                ch == '.' ||
                ch == '+' ||
                ch == '-' ||
                Character.toString(ch).equalsIgnoreCase("x") ||
                ('A' <= ch && ch <= 'F') ||
                ('a' <= ch && ch <= 'f');
    }

    private static Integer setIntValue(Token token) {
        return token.string.startsWith("0X") || token.string.startsWith("0x") ?
                Integer.parseInt(token.string.substring(2), 16)
                :
                Integer.parseInt(token.string);
    }

    private static void checkDigitTokenValidity(Token token) {
        if (
                token.string.matches(RegexConstants.intRegex) ||
                token.string.matches(RegexConstants.hexRegex)
        ) {
            token.kind = TokenCodes.integerConstant_;
            token.valForInt = setIntValue(token);
        } else if (token.string.matches(RegexConstants.doubleRegex)) {
            token.kind = TokenCodes.doubleConstant_;
            token.valForDouble = Double.parseDouble(token.string);
        } else {
            token.kind = TokenCodes.none;
            System.out.println("An error occurred while trying to scan the file on line " + line + ", col" + col + " .");
            System.out.println("Number constant is invalid!");
        }
    }

    public static void scanDigit(Token token) {
        token.string = Character.toString(ch);
        nextCh();
        while (isAValidDigit()) {
            token.string = token.string.concat(Character.toString(ch));
            nextCh();
        }
        checkDigitTokenValidity(token);
    }

    public static void recognizeTokenKind(Token token) {
        token.string = Character.toString(ch);
        nextCh();
        while (Character.isLetterOrDigit(ch) || ch == '_') {
            token.string = token.string.concat(Character.toString(ch));
            nextCh();
        }

        if(token.string.compareTo("REPEAT") == 0) {
            token.kind = TokenCodes.repeat_;
            return;
        }
        if(token.string.compareTo("UNTIL") == 0) {
            token.kind = TokenCodes.until_;
            return;
        }

        if (
                token.string.compareTo("true") == 0 ||
                token.string.compareTo("false") == 0
        ) {
            token.kind = TokenCodes.boolConstant_;
        }

        for (String keyword : keywords.keySet()) {
            if (token.string.compareTo(keyword) == 0) {
                if (token.string.startsWith("READ")) {
                    if (ch == '(') {
                        nextCh();
                        if (ch == ')') {
                            nextCh();
                            token.kind = keywords.get(keyword); // keyword = READ[INT/STRING/BOOL/DOUBLE]
                            return;
                        } else {
                            System.out.println("An error occurred while trying to scan the file on line " + line + ", col" + col + " .");
                            System.out.println("Read operation is invalid!");
                            System.out.println("Missing closing bracket!");
                            token.kind = TokenCodes.none;
                            return;
                        }
                    } else {
                        System.out.println("An error occurred while trying to scan the file on line " + line + ", col" + col + " .");
                        System.out.println("Read operation is invalid!");
                        System.out.println("Missing opening bracket!");
                        token.kind = TokenCodes.none;
                        return;
                    }
                } else { // nije READ nesto
                    token.kind = keywords.get(keyword);
                    return;
                }
            }
        }

        for (String type : dataTypes.keySet()) {
            if (token.string.compareTo(type) == 0) {
                token.kind = dataTypes.get(type);
                return;
            }
        }
        token.kind = TokenCodes.identifier_;
    }

    private static Token checkForCommentOrDivision(Token token, String s) {
        nextCh();
        if (ch == '/') {
            nextCh();
            while (ch != eol && ch != eofCh) nextCh();
            nextCh();
            return null;
        } else if (ch == '*') {
            char prev;
            nextCh();
            while (ch != eofCh && ch != '/') {
                prev = ch;
                nextCh();
                if (ch == '/') {
                    if (prev == '*') {
                        break;
                    } else
                        nextCh();
                    while (ch == '/') nextCh();
                }
            }
            if (ch == eofCh) {
                token.kind = TokenCodes.none;
            } else {
                nextCh();
            }
            return null;
        } else {
            token.kind = TokenCodes.div_;
            token.string = s;
            return token;
        }
    }

    public static void checkForLessOrLessEqual(Token token, String s) {
        nextCh();
        if (ch == '=') {
            nextCh();
            s = s.concat("=");
        }
        token.kind = tokens.get(s);
        token.string = s;
    }

    public static void checkForAmpersand(Token token) {
        nextCh();
        if (ch == '&') {
            nextCh();
            token.kind = TokenCodes.and_;
            token.string = "&&";
        } else {
            token.kind = TokenCodes.none;
        }
    }

    public static void checkForPipe(Token token) {
        nextCh();
        if (ch == '|') {
            nextCh();
            token.kind = TokenCodes.or_;
            token.string = "||";
        } else {
            token.kind = TokenCodes.none;
        }
    }

    //---------- Return next input token
    public static Token next() {
        // add your code here
        while (ch <= ' ') nextCh();
        Token t = new Token();
        t.line = line;
        t.col = col;
        if (Character.isLetter(ch)) {
            recognizeTokenKind(t);
        } else if (Character.isDigit(ch)) {
            scanDigit(t);
        } else {
            String stringFromCharacter = Character.toString(ch);
            switch (ch) {
                case '\"' -> scanString(t);
                case '+', '-', '*', ';', ',', '.', '(', ')', '%', '{', '}' -> {
                    nextCh();
                    t.kind = tokens.get(stringFromCharacter);
                    t.string = stringFromCharacter;
                }
                case '/' -> t = checkForCommentOrDivision(t, stringFromCharacter);
                case '<', '>', '=', '!' -> checkForLessOrLessEqual(t, stringFromCharacter);
                case '&' -> checkForAmpersand(t);
                case '|' -> checkForPipe(t);
                case eofCh -> t.kind = TokenCodes.eof;
                default -> {
                    nextCh();
                    t.kind = TokenCodes.none;
                }
            }
        }
        return t;
    }
}

