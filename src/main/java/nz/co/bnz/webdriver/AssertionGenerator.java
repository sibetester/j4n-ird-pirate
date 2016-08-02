package nz.co.bnz.webdriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import org.assertj.assertions.generator.AssertionsEntryPointType;
import org.assertj.assertions.generator.BaseAssertionGenerator;
import org.assertj.assertions.generator.description.ClassDescription;
import org.assertj.assertions.generator.description.GetterDescription;
import org.assertj.assertions.generator.description.converter.ClassToClassDescriptionConverter;
import org.assertj.assertions.generator.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

public class AssertionGenerator {

    private static final Logger logger = LoggerFactory.getLogger(AssertionGenerator.class);

    private static ClassToClassDescriptionConverter classDescriptionConverter = new GroovyClassToClassDescriptionConverter();

    public static void main(String[] args) throws IOException {

        Set<Class<?>> setOfPages = ClassUtil.collectClasses("nz.govt.ird.payekiwisaverdeductionscalculator");
        Set<Class<?>> setOfAssertions = Sets.newHashSet();

        // The setOfPages contains all of the classes in the pages* packages from src/main/java
        // ................... and all of the classes in the pages* packages from src/assertions/java
        // This loop copies the assertion classes in to a separate setOfAssertions
        for (Class clazz : setOfPages) {
            if (clazz.getName().endsWith("Assert") || clazz.getName().endsWith("Assertions")) {
                setOfAssertions.add(clazz);
            }
        }

        // Then all the of the classes in the pages* packages from src/assertions/java
        // are removed from the setOfPages, leaving only those classes in src/main/java
        setOfPages.removeAll(setOfAssertions);

        // Initialise the assertion generator provided by assertj
        BaseAssertionGenerator customAssertionGenerator = new BaseAssertionGenerator();
        customAssertionGenerator.setDirectoryWhereAssertionFilesAreGenerated("src/assertions/java");

        Set<ClassDescription> descriptionsNewAssertions = Sets.newHashSet();
        Set<ClassDescription> descriptionsAllAssertions = Sets.newHashSet();

        // Track whether we've found a matching assertion for a page class
        boolean assertionClassAlreadyExists;

        // Loop through all of the page classes in src/main/java
        for (Class<?> pageClass : setOfPages) {

            assertionClassAlreadyExists = false;
            descriptionsAllAssertions.add(toClassDescription(pageClass));

            // Loop through all of the existing assertions in src/assertions/java
            for (Class assertionClass : setOfAssertions) {

                // Check whether the page already has a corresponding assertion file
                if (toClassDescription(assertionClass).getClassName()
                    .equals(toClassDescription(pageClass).getClassName() + "Assert")) {

                    // Log a message that the assertion file exists
                    assertionClassAlreadyExists = true;

                    // A string of source code that will be generated for the page
                    String assertionThatWillBeGenerated = customAssertionGenerator
                        .generateCustomAssertionContentFor(toClassDescription(pageClass));

                    // The existing assertion source code that has previously been generated for the page
                    String filePath = "src/assertions/java/"
                            + toClassDescription(assertionClass).getPackageName().replaceAll("\\.", "/") + "/"
                            + toClassDescription(assertionClass).getClassName() + ".java";

                    String assertionThatAlreadyExists = new String(Files.readAllBytes(Paths.get(filePath)));

                    // If the assertion file that will be generated exactly matches the assertion file
                    // that already exists, then log a message to indicate that the file will not be re-generated
                    if (assertionThatWillBeGenerated.replaceAll("\\s", "").compareTo(
                        assertionThatAlreadyExists.replaceAll("\\s", "")) == 0) {

                        logger.info("Assertion class already exists for : {}", toClassDescription(pageClass).getClassName());
                    }

                    // Otherwise there have been changes to the page file that mean it will generate a
                    // different set of assertions to what was generated previously, so add this class
                    // to the set of descriptions that will have assertions generated
                    else {
                        logger.info("Assertion class will be re-generated for : {}", toClassDescription(pageClass).getClassName());
                        descriptionsNewAssertions.add(toClassDescription(pageClass));
                    }
                }
            }

            // If no assertion class exists for this page yet, add this class
            // to the set of descriptions that will have assertions generated
            if (!assertionClassAlreadyExists) {
                logger.info("Assertion class does not exist for : {}", toClassDescription(pageClass).getClassName());
                descriptionsNewAssertions.add(toClassDescription(pageClass));
            }
        }

        // For all of the descriptions that require assertions to be re-generated
        // Use the assertj generateCustomAssertionFor method to create or re-create the assertions
        logger.info("***** Assertion Generator *****");
        for (ClassDescription description : descriptionsNewAssertions) {
            logger.info("Generating assertions for class : {}", description.getClassName());
            File customAssertionFile = customAssertionGenerator.generateCustomAssertionFor(description);
            logger.info("Generated {} assertions file -> {}", description.getClassName(), customAssertionFile.getAbsolutePath());
        }

        // Create the index class as an entry point to the assertions
        customAssertionGenerator.generateAssertionsEntryPointClassFor
            (descriptionsAllAssertions, AssertionsEntryPointType.STANDARD, "nz.govt.ird.payekiwisaverdeductionscalculator");
    }

    private static ClassDescription toClassDescription(Class<?> clazz) {
        ClassDescription classDescription = classDescriptionConverter.convertToClassDescription(clazz);
        return classDescription;
    }

}

class GroovyClassToClassDescriptionConverter extends ClassToClassDescriptionConverter {

    @Override
    protected Set<GetterDescription> getterDescriptionsOf(Class<?> clazz) {
        Set<GetterDescription> result = super.getterDescriptionsOf(clazz);

        for (GetterDescription description : result) {
            if (description.getPropertyName().equals("metaClass")) {
                result.remove(description);
                break;
            }
        }
        return result;
    }
}
