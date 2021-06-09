package me.ac.ucg.kompajleri.statements_and_expressions;

public class Declaration {

    private String type;
    private String identifierName ;

    public Declaration(String  code,String identifierName){
        this.type = code;
        this.identifierName = identifierName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifierName() {
        return identifierName;
    }

    public void setIdentifierName(String identifierName) {
        this.identifierName = identifierName;
    }
}
