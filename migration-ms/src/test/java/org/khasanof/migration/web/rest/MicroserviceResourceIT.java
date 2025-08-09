package org.khasanof.migration.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.khasanof.migration.domain.MicroserviceAsserts.*;
import static org.khasanof.migration.web.rest.TestUtil.createUpdateProxyForBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.khasanof.migration.IntegrationTest;
import org.khasanof.migration.domain.common.Microservice;
import org.khasanof.migration.repository.common.MicroserviceRepository;
import org.khasanof.migration.service.dto.MicroserviceDTO;
import org.khasanof.migration.service.mapper.MicroserviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link MicroserviceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class MicroserviceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final Boolean DEFAULT_UPDATED = false;
    private static final Boolean UPDATED_UPDATED = true;

    private static final String ENTITY_API_URL = "/api/microservices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MicroserviceRepository microserviceRepository;

    @Autowired
    private MicroserviceMapper microserviceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMicroserviceMockMvc;

    private Microservice microservice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Microservice createEntity(EntityManager em) {
        Microservice microservice = new Microservice().name(DEFAULT_NAME).version(DEFAULT_VERSION).updated(DEFAULT_UPDATED);
        return microservice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Microservice createUpdatedEntity(EntityManager em) {
        Microservice microservice = new Microservice().name(UPDATED_NAME).version(UPDATED_VERSION).updated(UPDATED_UPDATED);
        return microservice;
    }

    @BeforeEach
    public void initTest() {
        microservice = createEntity(em);
    }

    @Test
    @Transactional
    void createMicroservice() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Microservice
        MicroserviceDTO microserviceDTO = microserviceMapper.toDto(microservice);
        var returnedMicroserviceDTO = om.readValue(
            restMicroserviceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(microserviceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            MicroserviceDTO.class
        );

        // Validate the Microservice in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedMicroservice = microserviceMapper.toEntity(returnedMicroserviceDTO);
        assertMicroserviceUpdatableFieldsEquals(returnedMicroservice, getPersistedMicroservice(returnedMicroservice));
    }

    @Test
    @Transactional
    void createMicroserviceWithExistingId() throws Exception {
        // Create the Microservice with an existing ID
        microservice.setId(1L);
        MicroserviceDTO microserviceDTO = microserviceMapper.toDto(microservice);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMicroserviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(microserviceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Microservice in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        microservice.setName(null);

        // Create the Microservice, which fails.
        MicroserviceDTO microserviceDTO = microserviceMapper.toDto(microservice);

        restMicroserviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(microserviceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        microservice.setVersion(null);

        // Create the Microservice, which fails.
        MicroserviceDTO microserviceDTO = microserviceMapper.toDto(microservice);

        restMicroserviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(microserviceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        microservice.setUpdated(null);

        // Create the Microservice, which fails.
        MicroserviceDTO microserviceDTO = microserviceMapper.toDto(microservice);

        restMicroserviceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(microserviceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllMicroservices() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList
        restMicroserviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(microservice.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.booleanValue())));
    }

    @Test
    @Transactional
    void getMicroservice() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get the microservice
        restMicroserviceMockMvc
            .perform(get(ENTITY_API_URL_ID, microservice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(microservice.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.booleanValue()));
    }

    @Test
    @Transactional
    void getMicroservicesByIdFiltering() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        Long id = microservice.getId();

        defaultMicroserviceFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultMicroserviceFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultMicroserviceFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllMicroservicesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where name equals to
        defaultMicroserviceFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMicroservicesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where name in
        defaultMicroserviceFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMicroservicesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where name is not null
        defaultMicroserviceFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllMicroservicesByNameContainsSomething() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where name contains
        defaultMicroserviceFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllMicroservicesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where name does not contain
        defaultMicroserviceFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllMicroservicesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where version equals to
        defaultMicroserviceFiltering("version.equals=" + DEFAULT_VERSION, "version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllMicroservicesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where version in
        defaultMicroserviceFiltering("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION, "version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllMicroservicesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where version is not null
        defaultMicroserviceFiltering("version.specified=true", "version.specified=false");
    }

    @Test
    @Transactional
    void getAllMicroservicesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where version is greater than or equal to
        defaultMicroserviceFiltering("version.greaterThanOrEqual=" + DEFAULT_VERSION, "version.greaterThanOrEqual=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllMicroservicesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where version is less than or equal to
        defaultMicroserviceFiltering("version.lessThanOrEqual=" + DEFAULT_VERSION, "version.lessThanOrEqual=" + SMALLER_VERSION);
    }

    @Test
    @Transactional
    void getAllMicroservicesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where version is less than
        defaultMicroserviceFiltering("version.lessThan=" + UPDATED_VERSION, "version.lessThan=" + DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void getAllMicroservicesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where version is greater than
        defaultMicroserviceFiltering("version.greaterThan=" + SMALLER_VERSION, "version.greaterThan=" + DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void getAllMicroservicesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where updated equals to
        defaultMicroserviceFiltering("updated.equals=" + DEFAULT_UPDATED, "updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllMicroservicesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where updated in
        defaultMicroserviceFiltering("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED, "updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllMicroservicesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        // Get all the microserviceList where updated is not null
        defaultMicroserviceFiltering("updated.specified=true", "updated.specified=false");
    }

    private void defaultMicroserviceFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultMicroserviceShouldBeFound(shouldBeFound);
        defaultMicroserviceShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMicroserviceShouldBeFound(String filter) throws Exception {
        restMicroserviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(microservice.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.booleanValue())));

        // Check, that the count call also returns 1
        restMicroserviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMicroserviceShouldNotBeFound(String filter) throws Exception {
        restMicroserviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMicroserviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingMicroservice() throws Exception {
        // Get the microservice
        restMicroserviceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingMicroservice() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the microservice
        Microservice updatedMicroservice = microserviceRepository.findById(microservice.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedMicroservice are not directly saved in db
        em.detach(updatedMicroservice);
        updatedMicroservice.name(UPDATED_NAME).version(UPDATED_VERSION).updated(UPDATED_UPDATED);
        MicroserviceDTO microserviceDTO = microserviceMapper.toDto(updatedMicroservice);

        restMicroserviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, microserviceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(microserviceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Microservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedMicroserviceToMatchAllProperties(updatedMicroservice);
    }

    @Test
    @Transactional
    void putNonExistingMicroservice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        microservice.setId(longCount.incrementAndGet());

        // Create the Microservice
        MicroserviceDTO microserviceDTO = microserviceMapper.toDto(microservice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMicroserviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, microserviceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(microserviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Microservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchMicroservice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        microservice.setId(longCount.incrementAndGet());

        // Create the Microservice
        MicroserviceDTO microserviceDTO = microserviceMapper.toDto(microservice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMicroserviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(microserviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Microservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamMicroservice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        microservice.setId(longCount.incrementAndGet());

        // Create the Microservice
        MicroserviceDTO microserviceDTO = microserviceMapper.toDto(microservice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMicroserviceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(microserviceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Microservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateMicroserviceWithPatch() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the microservice using partial update
        Microservice partialUpdatedMicroservice = new Microservice();
        partialUpdatedMicroservice.setId(microservice.getId());

        partialUpdatedMicroservice.version(UPDATED_VERSION);

        restMicroserviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMicroservice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMicroservice))
            )
            .andExpect(status().isOk());

        // Validate the Microservice in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMicroserviceUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedMicroservice, microservice),
            getPersistedMicroservice(microservice)
        );
    }

    @Test
    @Transactional
    void fullUpdateMicroserviceWithPatch() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the microservice using partial update
        Microservice partialUpdatedMicroservice = new Microservice();
        partialUpdatedMicroservice.setId(microservice.getId());

        partialUpdatedMicroservice.name(UPDATED_NAME).version(UPDATED_VERSION).updated(UPDATED_UPDATED);

        restMicroserviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedMicroservice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedMicroservice))
            )
            .andExpect(status().isOk());

        // Validate the Microservice in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertMicroserviceUpdatableFieldsEquals(partialUpdatedMicroservice, getPersistedMicroservice(partialUpdatedMicroservice));
    }

    @Test
    @Transactional
    void patchNonExistingMicroservice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        microservice.setId(longCount.incrementAndGet());

        // Create the Microservice
        MicroserviceDTO microserviceDTO = microserviceMapper.toDto(microservice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMicroserviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, microserviceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(microserviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Microservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchMicroservice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        microservice.setId(longCount.incrementAndGet());

        // Create the Microservice
        MicroserviceDTO microserviceDTO = microserviceMapper.toDto(microservice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMicroserviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(microserviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Microservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamMicroservice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        microservice.setId(longCount.incrementAndGet());

        // Create the Microservice
        MicroserviceDTO microserviceDTO = microserviceMapper.toDto(microservice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restMicroserviceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(microserviceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Microservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteMicroservice() throws Exception {
        // Initialize the database
        microserviceRepository.saveAndFlush(microservice);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the microservice
        restMicroserviceMockMvc
            .perform(delete(ENTITY_API_URL_ID, microservice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return microserviceRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Microservice getPersistedMicroservice(Microservice microservice) {
        return microserviceRepository.findById(microservice.getId()).orElseThrow();
    }

    protected void assertPersistedMicroserviceToMatchAllProperties(Microservice expectedMicroservice) {
        assertMicroserviceAllPropertiesEquals(expectedMicroservice, getPersistedMicroservice(expectedMicroservice));
    }

    protected void assertPersistedMicroserviceToMatchUpdatableProperties(Microservice expectedMicroservice) {
        assertMicroserviceAllUpdatablePropertiesEquals(expectedMicroservice, getPersistedMicroservice(expectedMicroservice));
    }
}
