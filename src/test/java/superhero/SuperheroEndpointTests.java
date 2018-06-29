package superhero;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.context.WebApplicationContext;
import superhero.controllers.SuperheroController;
import superhero.models.Superhero;
import superhero.models.SuperheroRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@ContextConfiguration
@WebAppConfiguration
public class SuperheroEndpointTests extends BaseEndpointTest {


    @Autowired
    private WebApplicationContext wac;

    @Autowired
    EntityManager entityManager;

    @Autowired
    SuperheroController superheroController;

    @Autowired
    private SuperheroRepository repository;

    @Autowired
    private FilterChainProxy filterChainProxy;

    private Authentication authentication;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = webAppContextSetup(wac).dispatchOptions(true).addFilters(filterChainProxy).build();
    }

    @Before
    public void setup() throws Exception {
        super.setup();
    }

    @After
    public void close() {
        SecurityContextHolder.clearContext();
    }

    @Test
    public void createSuperheroTest() throws Exception {
        List<String> skillList = new ArrayList<>();
        skillList.add("fly");
        skillList.add("throw flame");

        Superhero superhero =
                new Superhero("name", "pseudonym", "Marvel", skillList, null, new Date());

        String content = json(superhero);

        mockMvc.perform(
                post("/superhero")
                        .accept(JSON_MEDIA_TYPE)
                        .content(content)
                        .contentType(JSON_MEDIA_TYPE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", isA(Number.class)))
                .andExpect(jsonPath("$.name", is(superhero.getName())))
                .andExpect(jsonPath("$.pseudonym", is(superhero.getPseudonym())))
                .andExpect(jsonPath("$.publisher", is(superhero.getPublisher())));

       assertNotNull(repository.findByName(superhero.getName()));
    }

    @Test
    public void deleteIsProtectedSuperheroTest() throws Exception {

        List<String> skillList = new ArrayList<>();
        skillList.add("fly");
        skillList.add("throw flame");

        Superhero superhero = new Superhero("name", "pseudonym", "MARVEL", skillList, null, new Date());
        superhero = superheroController.addSuperhero(superhero).getBody();
        String content = json(superhero);

        assertNotNull(repository.findOne(superhero.getId()));

        mockMvc.perform(
                delete("/superhero/{id}", 1)
                        .accept(JSON_MEDIA_TYPE)
                        .contentType(JSON_MEDIA_TYPE))
                .andDo(print())
                .andExpect(status().is(403));
    }

    @Test
    public void authTest() throws Exception {
        String contentType = MediaType.APPLICATION_JSON + ";charset=UTF-8";

        String authzToken = mockMvc
                .perform(
                        post("/auth")
                                .contentType(
                                        MediaType.APPLICATION_JSON).
                                content("{\n" +
                                        "\t\"user\":\"superhero\",\n" +
                                        "\t\"password\": \"password\"\n" +
                                        "}")).
                        andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.token", is(notNullValue())))
                .andReturn().getResponse().getContentAsString();

          System.out.print(authzToken);
    }
}