package com.olkhovyi.mykhailo.ast;

import com.olkhovyi.mykhailo.lib.Value;

public final class ValueExpression implements Expression {
    private final Value value;

    public ValueExpression(double value) {
        this.value = new NumberValue(value);
    }

    public ValueExpression(String value) {
        this.value = new StringValue(value);
    }

    @Override
    public Value eval() {
        return value;
    }

    @Override
    public String toString() {
        return value.asString();
    }
}
