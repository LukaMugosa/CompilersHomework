package me.ac.ucg.kompajleri;

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
    private static HashMap<String, Integer> keywords;
    /*
    * TODO: PRAVIMO MAPU TOKENA
    * */
    private static HashMap<String, Integer> tokens;
    /*
    * TODO: PRAVIMO MAPU TIPOVA PODATAKA
    * */
    private static HashMap<String, Integer> dataTypes;
    /*
    * TODO: PRAVIMO MAPU OPERATORA
    * */
    private static HashMap<String, Integer> operators;

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

    //---------- Return next input token
    public static Token next() {
        // add your code here
        while (ch <= ' ') nextCh();
        Token t = new Token();
        t.line = line;
        t.col = col;
        switch (ch) {
            case eofCh:
                t.kind = TokenCodes.eof;
                break;
            default:
                nextCh();
                t.kind = TokenCodes.none;
                break;
        }
        return t;
    }
}

