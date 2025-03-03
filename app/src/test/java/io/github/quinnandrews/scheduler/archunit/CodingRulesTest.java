package io.github.quinnandrews.scheduler.archunit;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import io.github.quinnandrews.scheduler.Application;
import org.slf4j.Logger;
import org.springframework.boot.test.context.SpringBootTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noFields;
import static com.tngtech.archunit.library.GeneralCodingRules.*;

@AnalyzeClasses(packagesOf = Application.class)
public class CodingRulesTest {

    @ArchTest
    private final ArchRule test_classes_should_reside_in_the_same_package_as_its_implementation
            = testClassesShouldResideInTheSamePackageAsImplementation()
            .because("");

    @ArchTest
    private final ArchRule no_access_to_standard_streams = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS
            .because("""
                     while using System.out during local development is OK, such code should \
                     not be pushed to the repository nor deployed to any Environment. Such \
                     code should be removed or refactored to log data with SLF4J, so that \
                     we are consistent with our logging practices and avoid potential \
                     performance issues.
                     """);

    @ArchTest
    private final ArchRule no_generic_exceptions = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS
            .because("""
                     Throwing specific Exceptions provides clarity when troubleshooting and \
                     analyzing log files.
                      """);

    @ArchTest
    private final ArchRule no_java_util_logging = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING
            .because("""
                    SLF4J provides string formatting and utilizes what is already provided \
                    by the Spring Framework.
                     """);

    @ArchTest
    private final ArchRule loggers_should_be_private_static_final =
            fields().that().haveRawType(Logger.class)
                    .should().bePrivate()
                    .andShould().beStatic()
                    .andShould().beFinal()
                    .because("We agreed on this convention.");

    @ArchTest
    private final ArchRule no_jodatime = NO_CLASSES_SHOULD_USE_JODATIME
            .because("""
                    Java's modern Date/Time APIs are more robust and require no additional \
                    dependencies.
                     """);

    @ArchTest
    private final ArchRule no_field_injection = noFields().that()
            .areDeclaredInClassesThat()
            .areNotAnnotatedWith(SpringBootTest.class)
            .should(BE_ANNOTATED_WITH_AN_INJECTION_ANNOTATION)
            .because("""
                    Constructor and Setter Injection is more convenient for testing and \
                    often makes the code less cluttered and more legible.
                    """);
}
