package me.ac.ucg.kompajleri.statements_and_expressions;

public class Expr2 extends Statement{

    private String operator;
    private Expr2 left, right;
    private String type;
    private Object value;

    public Expr2(String operator, Expr2 left, Expr2 right, String kind) {
        this.operator = operator;
        this.left = left;
        this.right = right;
        this.kind = kind;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Expr2 getLeft() {
        return left;
    }

    public void setLeft(Expr2 left) {
        this.left = left;
    }

    public Expr2 getRight() {
        return right;
    }

    public void setRight(Expr2 right) {
        this.right = right;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
