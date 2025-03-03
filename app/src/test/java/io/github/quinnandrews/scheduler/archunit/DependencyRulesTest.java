package io.github.quinnandrews.scheduler.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.github.quinnandrews.scheduler.Application;

import static com.tngtech.archunit.core.domain.JavaClass.Predicates.*;
import static com.tngtech.archunit.lang.conditions.ArchConditions.dependOnClassesThat;
import static com.tngtech.archunit.lang.conditions.ArchConditions.not;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packagesOf = Application.class)
public class DependencyRulesTest {

    @ArchTest
    private final ArchRule no_dependencies_on_jax_rs_in_packages_that_should_not_have_them = classes()
            .that(resideOutsideOfPackages("..rest..", "..restassured.."))
            .should(not(dependOnClassesThat(resideInAPackage("jakarta.ws.rs.."))))
            .because("""
                    lower level components should be agnostic of REST API components.
                    """);

    @ArchTest
    private final ArchRule no_dependencies_on_jax_rs_extensions_in_packages_that_should_not_have_them  = classes()
            .that(resideOutsideOfPackages("..rest..", "..restassured.."))
            .should(not(dependOnClassesThat(are(assignableTo(resideInAPackage("jakarta.ws.rs.."))))))
            .because("""
                    lower level components should be agnostic of REST API components.
                    """);
}
