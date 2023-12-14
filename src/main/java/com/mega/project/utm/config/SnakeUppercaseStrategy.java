package com.mega.project.utm.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class SnakeUppercaseStrategy extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalTableName(Identifier logicalName, JdbcEnvironment context) {
        return convertToSnakeCase(logicalName);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier logicalName, JdbcEnvironment context) {
        return convertToSnakeCase(logicalName);
    }

    private Identifier convertToSnakeCase(final Identifier identifier) {
        // System.out.println("old name: " + identifier.getText());
        // final String regex = "([a-z])([A-Z])";
        // final String replacement = "$1_$2";
        final String newName = identifier.getText();
        // .replaceAll(regex, replacement)
        // .toUpperCase();
        // System.out.println("naming: " + newName);
        return Identifier.toIdentifier(newName, true);
    }

}
