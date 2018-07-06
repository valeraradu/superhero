package superhero.validators;

import org.springframework.beans.factory.annotation.Autowired;
import superhero.enums.Skill;
import superhero.models.Superhero;
import superhero.models.SuperheroRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Collectors;

public class SuperheroValidator implements ConstraintValidator<IsTrueSuperhero, Superhero> {

    @Autowired
    private SuperheroRepository repository;

    @Override
    public void initialize(IsTrueSuperhero constraintAnnotation) {

    }

    @Override
    public boolean isValid(Superhero superhero, ConstraintValidatorContext context) {

        if (repository.findByName(superhero.getName()) != null) {
            setExceptionMessage(
                    String.format("superhero with name %s already exists", superhero.getName()), context);
            return false;
        }

        if (repository.findByPseudonym(superhero.getPseudonym()) != null) {
            setExceptionMessage(
                    String.format("superhero with pseudonym %s already exists", superhero.getPseudonym()), context);
            return false;
        }

        if (superhero.getSkills() == null || superhero.getSkills().size() == 0) {
            setExceptionMessage("a true superhero must have superskills", context);
            return false;
        }

        if (superhero.getSkills().stream()
                .map(skill -> {
                    if (Skill.lookup(skill) == null) {
                        setExceptionMessage(String.format("%s is not a superskill", skill), context);
                    }
                    return Skill.lookup(skill);
                }).collect(Collectors.toList())
                .contains(null)) {

            return false;
        }

        if (superhero.getAllies() == null || superhero.getAllies().size() == 0) {
            return true;
        }

        if (superhero.getAllies().stream().map(ally -> {
            if (repository.findByName(ally) == null)
                setExceptionMessage(String.format("ally %s does not exists", ally), context);

            return repository.findByName(ally);
        }).collect(Collectors.toList()).contains(null)) {
            return false;
        }

        return true;
    }

    private void setExceptionMessage(String message, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
