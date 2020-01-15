package com.eigenbaumarkt.test.web.rest;

import com.eigenbaumarkt.test.RadiobuttonsApp;
import com.eigenbaumarkt.test.domain.Chooser;
import com.eigenbaumarkt.test.domain.enumeration.Enums;
import com.eigenbaumarkt.test.repository.ChooserRepository;
import com.eigenbaumarkt.test.service.ChooserService;
import com.eigenbaumarkt.test.web.rest.errors.ExceptionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.eigenbaumarkt.test.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
/**
 * Integration tests for the {@link ChooserResource} REST controller.
 */
@SpringBootTest(classes = RadiobuttonsApp.class)
public class ChooserResourceIT {

    private static final Enums DEFAULT_ENUMERATION = Enums.A;
    private static final Enums UPDATED_ENUMERATION = Enums.B;

    @Autowired
    private ChooserRepository chooserRepository;

    @Autowired
    private ChooserService chooserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restChooserMockMvc;

    private Chooser chooser;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ChooserResource chooserResource = new ChooserResource(chooserService);
        this.restChooserMockMvc = MockMvcBuilders.standaloneSetup(chooserResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chooser createEntity(EntityManager em) {
        Chooser chooser = new Chooser()
            .enumeration(DEFAULT_ENUMERATION);
        return chooser;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Chooser createUpdatedEntity(EntityManager em) {
        Chooser chooser = new Chooser()
            .enumeration(UPDATED_ENUMERATION);
        return chooser;
    }

    @BeforeEach
    public void initTest() {
        chooser = createEntity(em);
    }

    @Test
    @Transactional
    public void createChooser() throws Exception {
        int databaseSizeBeforeCreate = chooserRepository.findAll().size();

        // Create the Chooser
        restChooserMockMvc.perform(post("/api/choosers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chooser)))
            .andExpect(status().isCreated());

        // Validate the Chooser in the database
        List<Chooser> chooserList = chooserRepository.findAll();
        assertThat(chooserList).hasSize(databaseSizeBeforeCreate + 1);
        Chooser testChooser = chooserList.get(chooserList.size() - 1);
        assertThat(testChooser.getEnumeration()).isEqualTo(DEFAULT_ENUMERATION);
    }

    @Test
    @Transactional
    public void createChooserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = chooserRepository.findAll().size();

        // Create the Chooser with an existing ID
        chooser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restChooserMockMvc.perform(post("/api/choosers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chooser)))
            .andExpect(status().isBadRequest());

        // Validate the Chooser in the database
        List<Chooser> chooserList = chooserRepository.findAll();
        assertThat(chooserList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllChoosers() throws Exception {
        // Initialize the database
        chooserRepository.saveAndFlush(chooser);

        // Get all the chooserList
        restChooserMockMvc.perform(get("/api/choosers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(chooser.getId().intValue())))
            .andExpect(jsonPath("$.[*].enumeration").value(hasItem(DEFAULT_ENUMERATION.toString())));
    }

    @Test
    @Transactional
    public void getChooser() throws Exception {
        // Initialize the database
        chooserRepository.saveAndFlush(chooser);

        // Get the chooser
        restChooserMockMvc.perform(get("/api/choosers/{id}", chooser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(chooser.getId().intValue()))
            .andExpect(jsonPath("$.enumeration").value(DEFAULT_ENUMERATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingChooser() throws Exception {
        // Get the chooser
        restChooserMockMvc.perform(get("/api/choosers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateChooser() throws Exception {
        // Initialize the database
        chooserService.save(chooser);

        int databaseSizeBeforeUpdate = chooserRepository.findAll().size();

        // Update the chooser
        Chooser updatedChooser = chooserRepository.findById(chooser.getId()).get();
        // Disconnect from session so that the updates on updatedChooser are not directly saved in db
        em.detach(updatedChooser);
        updatedChooser
            .enumeration(UPDATED_ENUMERATION);

        restChooserMockMvc.perform(put("/api/choosers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedChooser)))
            .andExpect(status().isOk());

        // Validate the Chooser in the database
        List<Chooser> chooserList = chooserRepository.findAll();
        assertThat(chooserList).hasSize(databaseSizeBeforeUpdate);
        Chooser testChooser = chooserList.get(chooserList.size() - 1);
        assertThat(testChooser.getEnumeration()).isEqualTo(UPDATED_ENUMERATION);
    }

    @Test
    @Transactional
    public void updateNonExistingChooser() throws Exception {
        int databaseSizeBeforeUpdate = chooserRepository.findAll().size();

        // Create the Chooser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChooserMockMvc.perform(put("/api/choosers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(chooser)))
            .andExpect(status().isBadRequest());

        // Validate the Chooser in the database
        List<Chooser> chooserList = chooserRepository.findAll();
        assertThat(chooserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteChooser() throws Exception {
        // Initialize the database
        chooserService.save(chooser);

        int databaseSizeBeforeDelete = chooserRepository.findAll().size();

        // Delete the chooser
        restChooserMockMvc.perform(delete("/api/choosers/{id}", chooser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Chooser> chooserList = chooserRepository.findAll();
        assertThat(chooserList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
