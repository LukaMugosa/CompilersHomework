package me.ac.ucg.kompajleri;

import me.ac.ucg.kompajleri.constants.TokenNames;

public class Token {
    public int kind;		// token kind
    public int line;		// token line
    public int col;			// token column
    public int valForInt;			// token value (for number and charConst)
    public double valForDouble;			// token value (for number and charConst)
    public String string;	// token string

    public String toString() {
        String TYPE = "";
        if(Scanner.keywords.containsValue(kind)){
            TYPE = "Keyword";
        }else if(Scanner.dataTypes.containsValue(kind)){
            TYPE = "DataType";
        }
        else if(Scanner.operators.containsValue(kind)){
            TYPE = "Operator";
        }
        else
            TYPE = TokenNames.tokenNames[kind];
//        System.out.println(string);
//        System.out.println(TYPE);
        return string + " " + TYPE;
    }

}

