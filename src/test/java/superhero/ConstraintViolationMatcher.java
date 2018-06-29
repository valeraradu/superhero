package superhero;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

public class ConstraintViolationMatcher extends TypeSafeMatcher<ConstraintViolationException> {

    private String expectedErrorMessage;

    public ConstraintViolationMatcher(String expectedErrorMessage) {
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Error doesn't match");
    }

    @Override
    protected boolean matchesSafely(ConstraintViolationException exceptionToTest) {
        return exceptionToTest
                .getConstraintViolations().stream()
                .map(violation -> violation.getMessageTemplate()
                        .contains(expectedErrorMessage))
                .collect(Collectors.toList()).contains(true);
    }
}