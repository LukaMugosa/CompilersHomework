package me.ac.ucg.kompajleri.statements_and_expressions;

public class Couple {

    private String type;
    private Expr2 expr2;


    public Couple(String type, Expr2 expr2) {
        this.type = type;
        this.expr2 = expr2;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Expr2 getExpr2() {
        return expr2;
    }

    public void setExpr2(Expr2 expr2) {
        this.expr2 = expr2;
    }
}
