package me.ac.ucg.kompajleri.statements_and_expressions;

public class PrintStatement extends Statement{

    private Expr2 expr2;

    public PrintStatement(Expr2 expr2) {
        this.expr2 = expr2;
    }

    public Expr2 getExpr2() {
        return expr2;
    }

    public void setExpr2(Expr2 expr2) {
        this.expr2 = expr2;
    }
}
