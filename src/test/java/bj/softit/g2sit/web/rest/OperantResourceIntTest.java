package bj.softit.g2sit.web.rest;

import bj.softit.g2sit.G2SitApp;

import bj.softit.g2sit.domain.Operant;
import bj.softit.g2sit.repository.OperantRepository;
import bj.softit.g2sit.service.OperantService;
import bj.softit.g2sit.repository.search.OperantSearchRepository;
import bj.softit.g2sit.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OperantResource REST controller.
 *
 * @see OperantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = G2SitApp.class)
public class OperantResourceIntTest {

    private static final String DEFAULT_FISTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FISTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final Long DEFAULT_TELEPHONE = 1L;
    private static final Long UPDATED_TELEPHONE = 2L;

    @Autowired
    private OperantRepository operantRepository;

    @Autowired
    private OperantService operantService;

    @Autowired
    private OperantSearchRepository operantSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOperantMockMvc;

    private Operant operant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OperantResource operantResource = new OperantResource(operantService);
        this.restOperantMockMvc = MockMvcBuilders.standaloneSetup(operantResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Operant createEntity(EntityManager em) {
        Operant operant = new Operant()
            .fistname(DEFAULT_FISTNAME)
            .lastname(DEFAULT_LASTNAME)
            .telephone(DEFAULT_TELEPHONE);
        return operant;
    }

    @Before
    public void initTest() {
        operantSearchRepository.deleteAll();
        operant = createEntity(em);
    }

    @Test
    @Transactional
    public void createOperant() throws Exception {
        int databaseSizeBeforeCreate = operantRepository.findAll().size();

        // Create the Operant
        restOperantMockMvc.perform(post("/api/operants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operant)))
            .andExpect(status().isCreated());

        // Validate the Operant in the database
        List<Operant> operantList = operantRepository.findAll();
        assertThat(operantList).hasSize(databaseSizeBeforeCreate + 1);
        Operant testOperant = operantList.get(operantList.size() - 1);
        assertThat(testOperant.getFistname()).isEqualTo(DEFAULT_FISTNAME);
        assertThat(testOperant.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testOperant.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);

        // Validate the Operant in Elasticsearch
        Operant operantEs = operantSearchRepository.findOne(testOperant.getId());
        assertThat(operantEs).isEqualToComparingFieldByField(testOperant);
    }

    @Test
    @Transactional
    public void createOperantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = operantRepository.findAll().size();

        // Create the Operant with an existing ID
        operant.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOperantMockMvc.perform(post("/api/operants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operant)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Operant> operantList = operantRepository.findAll();
        assertThat(operantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOperants() throws Exception {
        // Initialize the database
        operantRepository.saveAndFlush(operant);

        // Get all the operantList
        restOperantMockMvc.perform(get("/api/operants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operant.getId().intValue())))
            .andExpect(jsonPath("$.[*].fistname").value(hasItem(DEFAULT_FISTNAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.intValue())));
    }

    @Test
    @Transactional
    public void getOperant() throws Exception {
        // Initialize the database
        operantRepository.saveAndFlush(operant);

        // Get the operant
        restOperantMockMvc.perform(get("/api/operants/{id}", operant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(operant.getId().intValue()))
            .andExpect(jsonPath("$.fistname").value(DEFAULT_FISTNAME.toString()))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOperant() throws Exception {
        // Get the operant
        restOperantMockMvc.perform(get("/api/operants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOperant() throws Exception {
        // Initialize the database
        operantService.save(operant);

        int databaseSizeBeforeUpdate = operantRepository.findAll().size();

        // Update the operant
        Operant updatedOperant = operantRepository.findOne(operant.getId());
        updatedOperant
            .fistname(UPDATED_FISTNAME)
            .lastname(UPDATED_LASTNAME)
            .telephone(UPDATED_TELEPHONE);

        restOperantMockMvc.perform(put("/api/operants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOperant)))
            .andExpect(status().isOk());

        // Validate the Operant in the database
        List<Operant> operantList = operantRepository.findAll();
        assertThat(operantList).hasSize(databaseSizeBeforeUpdate);
        Operant testOperant = operantList.get(operantList.size() - 1);
        assertThat(testOperant.getFistname()).isEqualTo(UPDATED_FISTNAME);
        assertThat(testOperant.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testOperant.getTelephone()).isEqualTo(UPDATED_TELEPHONE);

        // Validate the Operant in Elasticsearch
        Operant operantEs = operantSearchRepository.findOne(testOperant.getId());
        assertThat(operantEs).isEqualToComparingFieldByField(testOperant);
    }

    @Test
    @Transactional
    public void updateNonExistingOperant() throws Exception {
        int databaseSizeBeforeUpdate = operantRepository.findAll().size();

        // Create the Operant

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOperantMockMvc.perform(put("/api/operants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(operant)))
            .andExpect(status().isCreated());

        // Validate the Operant in the database
        List<Operant> operantList = operantRepository.findAll();
        assertThat(operantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOperant() throws Exception {
        // Initialize the database
        operantService.save(operant);

        int databaseSizeBeforeDelete = operantRepository.findAll().size();

        // Get the operant
        restOperantMockMvc.perform(delete("/api/operants/{id}", operant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean operantExistsInEs = operantSearchRepository.exists(operant.getId());
        assertThat(operantExistsInEs).isFalse();

        // Validate the database is empty
        List<Operant> operantList = operantRepository.findAll();
        assertThat(operantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOperant() throws Exception {
        // Initialize the database
        operantService.save(operant);

        // Search the operant
        restOperantMockMvc.perform(get("/api/_search/operants?query=id:" + operant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(operant.getId().intValue())))
            .andExpect(jsonPath("$.[*].fistname").value(hasItem(DEFAULT_FISTNAME.toString())))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operant.class);
        Operant operant1 = new Operant();
        operant1.setId(1L);
        Operant operant2 = new Operant();
        operant2.setId(operant1.getId());
        assertThat(operant1).isEqualTo(operant2);
        operant2.setId(2L);
        assertThat(operant1).isNotEqualTo(operant2);
        operant1.setId(null);
        assertThat(operant1).isNotEqualTo(operant2);
    }
}
