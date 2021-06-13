package me.ac.ucg.kompajleri.statements_and_expressions;

public class Statement {

    protected String kind;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Statement{" +
                "kind='" + kind + '\'' +
                '}';
    }
}
