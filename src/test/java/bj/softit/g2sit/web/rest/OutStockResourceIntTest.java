package bj.softit.g2sit.web.rest;

import bj.softit.g2sit.G2SitApp;

import bj.softit.g2sit.domain.OutStock;
import bj.softit.g2sit.domain.Produits;
import bj.softit.g2sit.repository.OutStockRepository;
import bj.softit.g2sit.service.HistoriquesService;
import bj.softit.g2sit.service.OutStockService;
import bj.softit.g2sit.repository.search.OutStockSearchRepository;
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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static bj.softit.g2sit.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OutStockResource REST controller.
 *
 * @see OutStockResource
 */
@RunWith(SpringRunner.class)
//@RunWith(Parameterized.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = G2SitApp.class)
public class OutStockResourceIntTest {

    private static final BigDecimal DEFAULT_QUANTITE = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITE = new BigDecimal(2);

    private static final ZonedDateTime DEFAULT_DATESORTIR = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATESORTIR = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PROJET = "AAAAAAAAAA";
    private static final String UPDATED_PROJET = "BBBBBBBBBB";

    private static final String DEFAULT_CLIENT = "AAAAAAAAAA";
    private static final String UPDATED_CLIENT = "BBBBBBBBBB";

    private static final String DEFAULT_CAUSE = "AAAAAAAAAA";
    private static final String UPDATED_CAUSE = "BBBBBBBBBB";

    @Autowired
    private OutStockRepository outStockRepository;

    @Autowired
    private OutStockService outStockService;

    @Autowired
    private OutStockSearchRepository outStockSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private final HistoriquesService historiquesService;

    @Autowired
    private EntityManager em;

    private MockMvc restOutStockMockMvc;

    private OutStock outStock;

    public OutStockResourceIntTest(HistoriquesService historiquesService, OutStockService outStockService) {
        this.historiquesService = historiquesService;
        this.outStockService = outStockService;
    }

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OutStockResource outStockResource = new OutStockResource(outStockService, historiquesService);
        this.restOutStockMockMvc = MockMvcBuilders.standaloneSetup(outStockResource)
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
    public static OutStock createEntity(EntityManager em) {
        OutStock outStock = new OutStock()
            .quantite(DEFAULT_QUANTITE)
            .datesortir(DEFAULT_DATESORTIR)
            .projet(DEFAULT_PROJET)
            .client(DEFAULT_CLIENT)
            .cause(DEFAULT_CAUSE);
        // Add required entity
        Produits produit = ProduitsResourceIntTest.createEntity(em);
        em.persist(produit);
        em.flush();
        outStock.setProduit(produit);
        return outStock;
    }

    @Before
    public void initTest() {
        outStockSearchRepository.deleteAll();
        outStock = createEntity(em);
    }

    @Test
    @Transactional
    public void createOutStock() throws Exception {
        int databaseSizeBeforeCreate = outStockRepository.findAll().size();

        // Create the OutStock
        restOutStockMockMvc.perform(post("/api/out-stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outStock)))
            .andExpect(status().isCreated());

        // Validate the OutStock in the database
        List<OutStock> outStockList = outStockRepository.findAll();
        assertThat(outStockList).hasSize(databaseSizeBeforeCreate + 1);
        OutStock testOutStock = outStockList.get(outStockList.size() - 1);
        assertThat(testOutStock.getQuantite()).isEqualTo(DEFAULT_QUANTITE);
        assertThat(testOutStock.getDatesortir()).isEqualTo(DEFAULT_DATESORTIR);
        assertThat(testOutStock.getProjet()).isEqualTo(DEFAULT_PROJET);
        assertThat(testOutStock.getClient()).isEqualTo(DEFAULT_CLIENT);
        assertThat(testOutStock.getCause()).isEqualTo(DEFAULT_CAUSE);

        // Validate the OutStock in Elasticsearch
        OutStock outStockEs = outStockSearchRepository.findOne(testOutStock.getId());
        assertThat(outStockEs).isEqualToComparingFieldByField(testOutStock);
    }

    @Test
    @Transactional
    public void createOutStockWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = outStockRepository.findAll().size();

        // Create the OutStock with an existing ID
        outStock.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOutStockMockMvc.perform(post("/api/out-stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outStock)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<OutStock> outStockList = outStockRepository.findAll();
        assertThat(outStockList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkQuantiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = outStockRepository.findAll().size();
        // set the field null
        outStock.setQuantite(null);

        // Create the OutStock, which fails.

        restOutStockMockMvc.perform(post("/api/out-stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outStock)))
            .andExpect(status().isBadRequest());

        List<OutStock> outStockList = outStockRepository.findAll();
        assertThat(outStockList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOutStocks() throws Exception {
        // Initialize the database
        outStockRepository.saveAndFlush(outStock);

        // Get all the outStockList
        restOutStockMockMvc.perform(get("/api/out-stocks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].datesortir").value(hasItem(sameInstant(DEFAULT_DATESORTIR))))
            .andExpect(jsonPath("$.[*].projet").value(hasItem(DEFAULT_PROJET.toString())))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE.toString())));
    }

    @Test
    @Transactional
    public void getOutStock() throws Exception {
        // Initialize the database
        outStockRepository.saveAndFlush(outStock);

        // Get the outStock
        restOutStockMockMvc.perform(get("/api/out-stocks/{id}", outStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(outStock.getId().intValue()))
            .andExpect(jsonPath("$.quantite").value(DEFAULT_QUANTITE.intValue()))
            .andExpect(jsonPath("$.datesortir").value(sameInstant(DEFAULT_DATESORTIR)))
            .andExpect(jsonPath("$.projet").value(DEFAULT_PROJET.toString()))
            .andExpect(jsonPath("$.client").value(DEFAULT_CLIENT.toString()))
            .andExpect(jsonPath("$.cause").value(DEFAULT_CAUSE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOutStock() throws Exception {
        // Get the outStock
        restOutStockMockMvc.perform(get("/api/out-stocks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOutStock() throws Exception {
        // Initialize the database
        outStockService.save(outStock);

        int databaseSizeBeforeUpdate = outStockRepository.findAll().size();

        // Update the outStock
        OutStock updatedOutStock = outStockRepository.findOne(outStock.getId());
        updatedOutStock
            .quantite(UPDATED_QUANTITE)
            .datesortir(UPDATED_DATESORTIR)
            .projet(UPDATED_PROJET)
            .client(UPDATED_CLIENT)
            .cause(UPDATED_CAUSE);

        restOutStockMockMvc.perform(put("/api/out-stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOutStock)))
            .andExpect(status().isOk());

        // Validate the OutStock in the database
        List<OutStock> outStockList = outStockRepository.findAll();
        assertThat(outStockList).hasSize(databaseSizeBeforeUpdate);
        OutStock testOutStock = outStockList.get(outStockList.size() - 1);
        assertThat(testOutStock.getQuantite()).isEqualTo(UPDATED_QUANTITE);
        assertThat(testOutStock.getDatesortir()).isEqualTo(UPDATED_DATESORTIR);
        assertThat(testOutStock.getProjet()).isEqualTo(UPDATED_PROJET);
        assertThat(testOutStock.getClient()).isEqualTo(UPDATED_CLIENT);
        assertThat(testOutStock.getCause()).isEqualTo(UPDATED_CAUSE);

        // Validate the OutStock in Elasticsearch
        OutStock outStockEs = outStockSearchRepository.findOne(testOutStock.getId());
        assertThat(outStockEs).isEqualToComparingFieldByField(testOutStock);
    }

    @Test
    @Transactional
    public void updateNonExistingOutStock() throws Exception {
        int databaseSizeBeforeUpdate = outStockRepository.findAll().size();

        // Create the OutStock

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOutStockMockMvc.perform(put("/api/out-stocks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(outStock)))
            .andExpect(status().isCreated());

        // Validate the OutStock in the database
        List<OutStock> outStockList = outStockRepository.findAll();
        assertThat(outStockList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOutStock() throws Exception {
        // Initialize the database
        outStockService.save(outStock);

        int databaseSizeBeforeDelete = outStockRepository.findAll().size();

        // Get the outStock
        restOutStockMockMvc.perform(delete("/api/out-stocks/{id}", outStock.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean outStockExistsInEs = outStockSearchRepository.exists(outStock.getId());
        assertThat(outStockExistsInEs).isFalse();

        // Validate the database is empty
        List<OutStock> outStockList = outStockRepository.findAll();
        assertThat(outStockList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchOutStock() throws Exception {
        // Initialize the database
        outStockService.save(outStock);

        // Search the outStock
        restOutStockMockMvc.perform(get("/api/_search/out-stocks?query=id:" + outStock.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(outStock.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantite").value(hasItem(DEFAULT_QUANTITE.intValue())))
            .andExpect(jsonPath("$.[*].datesortir").value(hasItem(sameInstant(DEFAULT_DATESORTIR))))
            .andExpect(jsonPath("$.[*].projet").value(hasItem(DEFAULT_PROJET.toString())))
            .andExpect(jsonPath("$.[*].client").value(hasItem(DEFAULT_CLIENT.toString())))
            .andExpect(jsonPath("$.[*].cause").value(hasItem(DEFAULT_CAUSE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OutStock.class);
        OutStock outStock1 = new OutStock();
        outStock1.setId(1L);
        OutStock outStock2 = new OutStock();
        outStock2.setId(outStock1.getId());
        assertThat(outStock1).isEqualTo(outStock2);
        outStock2.setId(2L);
        assertThat(outStock1).isNotEqualTo(outStock2);
        outStock1.setId(null);
        assertThat(outStock1).isNotEqualTo(outStock2);
    }
}
