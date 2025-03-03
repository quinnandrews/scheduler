package io.github.quinnandrews.scheduler.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.github.quinnandrews.scheduler.Application;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

@AnalyzeClasses(packagesOf = Application.class)
public class LayersTest {

    @ArchTest
    static final ArchRule layer_dependencies_are_respected = layeredArchitecture()
            .consideringOnlyDependenciesInLayers()
            .layer("Rest").definedBy("..scheduler.modules..rest..")
            .layer("Application").definedBy("..scheduler.modules..core..")
            .layer("Domain").definedBy("..scheduler.modules..core.domain..")
            .layer("Commons").definedBy("..scheduler.commons..")
            .layer("Config").definedBy("..scheduler.config..")
            .whereLayer("Rest").mayOnlyAccessLayers("Application", "Domain", "Commons")
            .whereLayer("Application").mayOnlyAccessLayers("Domain", "Commons")
            .whereLayer("Domain").mayOnlyAccessLayers("Commons")
            .whereLayer("Commons").mayNotAccessAnyLayer()
            .whereLayer("Config").mayNotBeAccessedByAnyLayer()
            .because("""
                    Components within the Application should be organized by a separation of \
                    concerns, distinct roles and responsibilities expressed as a set of layers \
                    that observe dependency rules, which helps manage complexity and scope \
                    without compromising the Application's ability to evolve over time.
                    """);
}
