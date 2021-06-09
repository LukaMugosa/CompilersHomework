package me.ac.ucg.kompajleri.statements_and_expressions;

public class AssignmentExpression extends Statement{

    private String identifier;
    private Expr2 expr2;

    public AssignmentExpression(String identifier, Expr2 expr2) {
        this.identifier = identifier;
        this.expr2 = expr2;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Expr2 getExpr2() {
        return expr2;
    }

    public void setExpr2(Expr2 expr2) {
        this.expr2 = expr2;
    }
}
