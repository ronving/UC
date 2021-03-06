package com.olkhovyi.mykhailo.ast;

import com.olkhovyi.mykhailo.lib.Value;
import com.olkhovyi.mykhailo.lib.Variables;

public class VariableExpression implements Expression {
    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public Value eval() {
        if (!Variables.isExists(name)) throw new RuntimeException("Такої константи не існує");
        return Variables.get(name);
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}
