package superhero;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import superhero.controllers.SuperheroController;
import superhero.models.Superhero;
import superhero.models.SuperheroRepository;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EnableAutoConfiguration
@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
public class SuperheroControllerTests {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Autowired
    SuperheroController superheroController;

    @Autowired
    private SuperheroRepository repository;

    private Authentication authentication;

    @Before
    public void init() {
        this.authentication =
                new UsernamePasswordAuthenticationToken("superhero", "password");
    }

    @After
    public void close() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @Transactional
    public void canCreateSuperheroTest() {

        List<String> skillList = new ArrayList<>();
        skillList.add("fly");
        skillList.add("throw flame");

        Superhero superhero = new Superhero("name", "pseudonym", "MARVEL", skillList, null, new Date());
        superhero = superheroController.addSuperhero(superhero).getBody();
        Assert.notNull(repository.findOne(superhero.getId()));
    }

    @Test
    @Transactional
    public void canGetSuperheroTest() {

        List<String> skillList = new ArrayList<>();
        skillList.add("fly");
        skillList.add("throw flame");

        Superhero superhero = new Superhero("name", "pseudonym", "MARVEL", skillList, null, new Date());
        Superhero superheroSaved = superheroController.addSuperhero(superhero).getBody();
        assert superheroController.getSuperhero(superheroSaved.getId())
                .getBody().getName()
                .equals(superhero.getName());
    }

    @Test
    @Transactional
    public void canFindAllSuperheroTest() {

        List<String> skillList = new ArrayList<>();
        skillList.add("fly");
        skillList.add("throw flame");

        Superhero superhero = new Superhero("name", "pseudonym", "MARVEL", skillList, null, new Date());
        superheroController.addSuperhero(superhero).getBody();

        Superhero superhero1 = new Superhero("name1", "pseudonym1", "MARVEL", skillList, null, new Date());
        superheroController.addSuperhero(superhero1).getBody();

        Superhero superhero2 = new Superhero("name2", "pseudonym2", "MARVEL", skillList, null, new Date());
        superheroController.addSuperhero(superhero2).getBody();

        Superhero superhero3 = new Superhero("name3", "pseudonym3", "MARVEL", skillList, null, new Date());
        superheroController.addSuperhero(superhero3).getBody();

        List<Superhero> list = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(list::add);

        assert list.size() == 4;
    }

    @Test
    @Transactional
    public void canDeleteSuperheroTest() {

        List<String> skillList = new ArrayList<>();
        skillList.add("fly");
        skillList.add("throw flame");

        Superhero superhero = new Superhero("name", "pseudonym", "MARVEL", skillList, null, new Date());
        superhero = superheroController.addSuperhero(superhero).getBody();

        Superhero superhero1 = new Superhero("name1", "pseudonym1", "MARVEL", skillList, null, new Date());
        superhero1 = superheroController.addSuperhero(superhero1).getBody();

        Superhero superhero2 = new Superhero("name2", "pseudonym2", "MARVEL", skillList, null, new Date());
        superhero2 = superheroController.addSuperhero(superhero2).getBody();

        superheroController.delete(superhero1.getId());

        List<Superhero> list = new ArrayList<>();
        repository.findAll().iterator().forEachRemaining(list::add);

        assert list.size() == 2;

        assert superheroController.getSuperhero(superhero
                .getId()).getBody()
                .getName().equals(superhero.getName());

        assert superheroController.getSuperhero(superhero2
                .getId()).getBody()
                .getName().equals(superhero2.getName());

    }

    @Test
    @Transactional
    public void canFindSuperheroByNameTest() {

        List<String> skillList = new ArrayList<>();
        skillList.add("fly");
        skillList.add("throw flame");

        Superhero superhero = new Superhero("name", "pseudonym", "MARVEL", skillList, null, new Date());
        Superhero saved = superheroController.addSuperhero(superhero).getBody();

        Assert.isTrue(repository.findByName(saved.getName()).getName().equals(superhero.getName()));
    }

    @Test
    @Transactional
    public void canNotCreateSuperheroDuplicateNameTest() throws Exception {

        thrown.expect(ConstraintViolationException.class);
        thrown.expect(
                new ConstraintViolationMatcher(
                        "superhero with name name already exists"));

        List<String> skillList = new ArrayList<>();
        skillList.add("fly");
        skillList.add("throw flame");

        Superhero superhero = new Superhero("name", "pseudonym", "MARVEL", skillList, null, new Date());
        Superhero saved = superheroController.addSuperhero(superhero).getBody();

        Assert.notNull(repository.findOne(saved.getId()));

        Superhero superhero1 = new Superhero("name", "pseudonym1", "MARVEL", skillList, null, new Date());
        superheroController.addSuperhero(superhero1).getBody();
    }

    @Test
    @Transactional
    public void canNotCreateSuperheroDuplicatePseudonymTest() throws Exception {

        thrown.expect(ConstraintViolationException.class);
        thrown.expect(
                new ConstraintViolationMatcher("superhero with pseudonym pseudonym already exists"));

        List<String> skillList = new ArrayList<>();
        skillList.add("fly");
        skillList.add("throw flame");

        Superhero superhero = new Superhero("name", "pseudonym", "MARVEL", skillList, null, new Date());
        Superhero saved = superheroController.addSuperhero(superhero).getBody();

        Assert.notNull(repository.findOne(saved.getId()));

        Superhero superhero1 = new Superhero("name1", "pseudonym", "MARVEL", skillList, null, new Date());
        superheroController.addSuperhero(superhero1).getBody();
    }

    @Test
    @Transactional
    public void canCreateSuperheroExistingAlly() {

        List<String> skillList = new ArrayList<>();
        skillList.add("fly");
        skillList.add("throw flame");

        Superhero superhero = new Superhero("name", "pseudonym", "MARVEL", skillList, null, new Date());
        Superhero superheroSaved = superheroController.addSuperhero(superhero).getBody();

        Assert.notNull(repository.findOne(superheroSaved.getId()));

        List<String> superheroList = new ArrayList<>();
        superheroList.add(superheroSaved.getName());

        Superhero superhero1 = new Superhero("name1", "pseudonym1", "MARVEL", skillList, superheroList, new Date());
        superheroController.addSuperhero(superhero1).getBody();
    }

    @Test
    @Transactional
    public void canNotCreateSuperheroNonExistingAlly() {

        thrown.expect(ConstraintViolationException.class);
        thrown.expect(
                new ConstraintViolationMatcher("ally randomname does not exists"));

        List<String> skillList = new ArrayList<>();
        skillList.add("fly");
        skillList.add("throw flame");

        List<String> superheroList = new ArrayList<>();
        superheroList.add("randomname");

        Superhero superhero1 = new Superhero("name2", "pseudonym2", "MARVEL", skillList, superheroList, new Date());
        superheroController.addSuperhero(superhero1).getBody();
    }

    @Test
    @Transactional
    public void canNotCreateSuperheroNonExistingSkill() {

        thrown.expect(ConstraintViolationException.class);
        thrown.expect(new ConstraintViolationMatcher("throw flamez is not a superskill"));

        List<String> skillList = new ArrayList<>();
        skillList.add("fly");
        skillList.add("throw flamez");

        Superhero superhero1 =
                new Superhero("name2", "pseudonym2", "MARVEL", skillList, null, new Date());
        superheroController.addSuperhero(superhero1).getBody();
        superheroController.addSuperhero(superhero1).getBody();
    }

    @Test
    @Transactional
    public void canNotCreateSuperheroWithoutSkill() {

        thrown.expect(ConstraintViolationException.class);
        thrown.expect(
                new ConstraintViolationMatcher("a true superhero must have superskills"));

        List<String> skillList = new ArrayList<>();

        Superhero superhero1 =
                new Superhero("name2", "pseudonym2", "MARVEL", skillList, null, new Date());
        superheroController.addSuperhero(superhero1).getBody();
    }
}
