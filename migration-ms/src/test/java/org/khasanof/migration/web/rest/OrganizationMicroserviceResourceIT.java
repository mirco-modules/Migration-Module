package org.khasanof.migration.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.khasanof.migration.domain.OrganizationMicroserviceAsserts.*;
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
import org.khasanof.migration.domain.common.Organization;
import org.khasanof.migration.domain.common.OrganizationMicroservice;
import org.khasanof.migration.repository.common.OrganizationMicroserviceRepository;
import org.khasanof.migration.service.dto.OrganizationMicroserviceDTO;
import org.khasanof.migration.service.mapper.OrganizationMicroserviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link OrganizationMicroserviceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class OrganizationMicroserviceResourceIT {

    private static final Integer DEFAULT_VERSION = 1;
    private static final Integer UPDATED_VERSION = 2;
    private static final Integer SMALLER_VERSION = 1 - 1;

    private static final Boolean DEFAULT_UPDATED = false;
    private static final Boolean UPDATED_UPDATED = true;

    private static final String ENTITY_API_URL = "/api/organization-microservices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private OrganizationMicroserviceRepository organizationMicroserviceRepository;

    @Autowired
    private OrganizationMicroserviceMapper organizationMicroserviceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrganizationMicroserviceMockMvc;

    private OrganizationMicroservice organizationMicroservice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationMicroservice createEntity(EntityManager em) {
        OrganizationMicroservice organizationMicroservice = new OrganizationMicroservice()
            .version(DEFAULT_VERSION)
            .updated(DEFAULT_UPDATED);
        return organizationMicroservice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrganizationMicroservice createUpdatedEntity(EntityManager em) {
        OrganizationMicroservice organizationMicroservice = new OrganizationMicroservice()
            .version(UPDATED_VERSION)
            .updated(UPDATED_UPDATED);
        return organizationMicroservice;
    }

    @BeforeEach
    public void initTest() {
        organizationMicroservice = createEntity(em);
    }

    @Test
    @Transactional
    void createOrganizationMicroservice() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the OrganizationMicroservice
        OrganizationMicroserviceDTO organizationMicroserviceDTO = organizationMicroserviceMapper.toDto(organizationMicroservice);
        var returnedOrganizationMicroserviceDTO = om.readValue(
            restOrganizationMicroserviceMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationMicroserviceDTO))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            OrganizationMicroserviceDTO.class
        );

        // Validate the OrganizationMicroservice in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedOrganizationMicroservice = organizationMicroserviceMapper.toEntity(returnedOrganizationMicroserviceDTO);
        assertOrganizationMicroserviceUpdatableFieldsEquals(
            returnedOrganizationMicroservice,
            getPersistedOrganizationMicroservice(returnedOrganizationMicroservice)
        );
    }

    @Test
    @Transactional
    void createOrganizationMicroserviceWithExistingId() throws Exception {
        // Create the OrganizationMicroservice with an existing ID
        organizationMicroservice.setId(1L);
        OrganizationMicroserviceDTO organizationMicroserviceDTO = organizationMicroserviceMapper.toDto(organizationMicroservice);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMicroserviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationMicroserviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMicroservice in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkVersionIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organizationMicroservice.setVersion(null);

        // Create the OrganizationMicroservice, which fails.
        OrganizationMicroserviceDTO organizationMicroserviceDTO = organizationMicroserviceMapper.toDto(organizationMicroservice);

        restOrganizationMicroserviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationMicroserviceDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUpdatedIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        organizationMicroservice.setUpdated(null);

        // Create the OrganizationMicroservice, which fails.
        OrganizationMicroserviceDTO organizationMicroserviceDTO = organizationMicroserviceMapper.toDto(organizationMicroservice);

        restOrganizationMicroserviceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationMicroserviceDTO))
            )
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllOrganizationMicroservices() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        // Get all the organizationMicroserviceList
        restOrganizationMicroserviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationMicroservice.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.booleanValue())));
    }

    @Test
    @Transactional
    void getOrganizationMicroservice() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        // Get the organizationMicroservice
        restOrganizationMicroserviceMockMvc
            .perform(get(ENTITY_API_URL_ID, organizationMicroservice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(organizationMicroservice.getId().intValue()))
            .andExpect(jsonPath("$.version").value(DEFAULT_VERSION))
            .andExpect(jsonPath("$.updated").value(DEFAULT_UPDATED.booleanValue()));
    }

    @Test
    @Transactional
    void getOrganizationMicroservicesByIdFiltering() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        Long id = organizationMicroservice.getId();

        defaultOrganizationMicroserviceFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultOrganizationMicroserviceFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultOrganizationMicroserviceFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOrganizationMicroservicesByVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        // Get all the organizationMicroserviceList where version equals to
        defaultOrganizationMicroserviceFiltering("version.equals=" + DEFAULT_VERSION, "version.equals=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllOrganizationMicroservicesByVersionIsInShouldWork() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        // Get all the organizationMicroserviceList where version in
        defaultOrganizationMicroserviceFiltering("version.in=" + DEFAULT_VERSION + "," + UPDATED_VERSION, "version.in=" + UPDATED_VERSION);
    }

    @Test
    @Transactional
    void getAllOrganizationMicroservicesByVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        // Get all the organizationMicroserviceList where version is not null
        defaultOrganizationMicroserviceFiltering("version.specified=true", "version.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationMicroservicesByVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        // Get all the organizationMicroserviceList where version is greater than or equal to
        defaultOrganizationMicroserviceFiltering(
            "version.greaterThanOrEqual=" + DEFAULT_VERSION,
            "version.greaterThanOrEqual=" + UPDATED_VERSION
        );
    }

    @Test
    @Transactional
    void getAllOrganizationMicroservicesByVersionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        // Get all the organizationMicroserviceList where version is less than or equal to
        defaultOrganizationMicroserviceFiltering(
            "version.lessThanOrEqual=" + DEFAULT_VERSION,
            "version.lessThanOrEqual=" + SMALLER_VERSION
        );
    }

    @Test
    @Transactional
    void getAllOrganizationMicroservicesByVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        // Get all the organizationMicroserviceList where version is less than
        defaultOrganizationMicroserviceFiltering("version.lessThan=" + UPDATED_VERSION, "version.lessThan=" + DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void getAllOrganizationMicroservicesByVersionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        // Get all the organizationMicroserviceList where version is greater than
        defaultOrganizationMicroserviceFiltering("version.greaterThan=" + SMALLER_VERSION, "version.greaterThan=" + DEFAULT_VERSION);
    }

    @Test
    @Transactional
    void getAllOrganizationMicroservicesByUpdatedIsEqualToSomething() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        // Get all the organizationMicroserviceList where updated equals to
        defaultOrganizationMicroserviceFiltering("updated.equals=" + DEFAULT_UPDATED, "updated.equals=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllOrganizationMicroservicesByUpdatedIsInShouldWork() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        // Get all the organizationMicroserviceList where updated in
        defaultOrganizationMicroserviceFiltering("updated.in=" + DEFAULT_UPDATED + "," + UPDATED_UPDATED, "updated.in=" + UPDATED_UPDATED);
    }

    @Test
    @Transactional
    void getAllOrganizationMicroservicesByUpdatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        // Get all the organizationMicroserviceList where updated is not null
        defaultOrganizationMicroserviceFiltering("updated.specified=true", "updated.specified=false");
    }

    @Test
    @Transactional
    void getAllOrganizationMicroservicesByMicroserviceIsEqualToSomething() throws Exception {
        Microservice microservice;
        if (TestUtil.findAll(em, Microservice.class).isEmpty()) {
            organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);
            microservice = MicroserviceResourceIT.createEntity(em);
        } else {
            microservice = TestUtil.findAll(em, Microservice.class).get(0);
        }
        em.persist(microservice);
        em.flush();
        organizationMicroservice.setMicroservice(microservice);
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);
        Long microserviceId = microservice.getId();
        // Get all the organizationMicroserviceList where microservice equals to microserviceId
        defaultOrganizationMicroserviceShouldBeFound("microserviceId.equals=" + microserviceId);

        // Get all the organizationMicroserviceList where microservice equals to (microserviceId + 1)
        defaultOrganizationMicroserviceShouldNotBeFound("microserviceId.equals=" + (microserviceId + 1));
    }

    @Test
    @Transactional
    void getAllOrganizationMicroservicesByOrganizationIsEqualToSomething() throws Exception {
        Organization organization;
        if (TestUtil.findAll(em, Organization.class).isEmpty()) {
            organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);
            organization = OrganizationResourceIT.createEntity(em);
        } else {
            organization = TestUtil.findAll(em, Organization.class).get(0);
        }
        em.persist(organization);
        em.flush();
        organizationMicroservice.setOrganization(organization);
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);
        Long organizationId = organization.getId();
        // Get all the organizationMicroserviceList where organization equals to organizationId
        defaultOrganizationMicroserviceShouldBeFound("organizationId.equals=" + organizationId);

        // Get all the organizationMicroserviceList where organization equals to (organizationId + 1)
        defaultOrganizationMicroserviceShouldNotBeFound("organizationId.equals=" + (organizationId + 1));
    }

    private void defaultOrganizationMicroserviceFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultOrganizationMicroserviceShouldBeFound(shouldBeFound);
        defaultOrganizationMicroserviceShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrganizationMicroserviceShouldBeFound(String filter) throws Exception {
        restOrganizationMicroserviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationMicroservice.getId().intValue())))
            .andExpect(jsonPath("$.[*].version").value(hasItem(DEFAULT_VERSION)))
            .andExpect(jsonPath("$.[*].updated").value(hasItem(DEFAULT_UPDATED.booleanValue())));

        // Check, that the count call also returns 1
        restOrganizationMicroserviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrganizationMicroserviceShouldNotBeFound(String filter) throws Exception {
        restOrganizationMicroserviceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrganizationMicroserviceMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOrganizationMicroservice() throws Exception {
        // Get the organizationMicroservice
        restOrganizationMicroserviceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingOrganizationMicroservice() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationMicroservice
        OrganizationMicroservice updatedOrganizationMicroservice = organizationMicroserviceRepository
            .findById(organizationMicroservice.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedOrganizationMicroservice are not directly saved in db
        em.detach(updatedOrganizationMicroservice);
        updatedOrganizationMicroservice.version(UPDATED_VERSION).updated(UPDATED_UPDATED);
        OrganizationMicroserviceDTO organizationMicroserviceDTO = organizationMicroserviceMapper.toDto(updatedOrganizationMicroservice);

        restOrganizationMicroserviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationMicroserviceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMicroserviceDTO))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationMicroservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedOrganizationMicroserviceToMatchAllProperties(updatedOrganizationMicroservice);
    }

    @Test
    @Transactional
    void putNonExistingOrganizationMicroservice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMicroservice.setId(longCount.incrementAndGet());

        // Create the OrganizationMicroservice
        OrganizationMicroserviceDTO organizationMicroserviceDTO = organizationMicroserviceMapper.toDto(organizationMicroservice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMicroserviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, organizationMicroserviceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMicroserviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMicroservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOrganizationMicroservice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMicroservice.setId(longCount.incrementAndGet());

        // Create the OrganizationMicroservice
        OrganizationMicroserviceDTO organizationMicroserviceDTO = organizationMicroserviceMapper.toDto(organizationMicroservice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMicroserviceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(organizationMicroserviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMicroservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOrganizationMicroservice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMicroservice.setId(longCount.incrementAndGet());

        // Create the OrganizationMicroservice
        OrganizationMicroserviceDTO organizationMicroserviceDTO = organizationMicroserviceMapper.toDto(organizationMicroservice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMicroserviceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(organizationMicroserviceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationMicroservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOrganizationMicroserviceWithPatch() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationMicroservice using partial update
        OrganizationMicroservice partialUpdatedOrganizationMicroservice = new OrganizationMicroservice();
        partialUpdatedOrganizationMicroservice.setId(organizationMicroservice.getId());

        restOrganizationMicroserviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationMicroservice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationMicroservice))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationMicroservice in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationMicroserviceUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedOrganizationMicroservice, organizationMicroservice),
            getPersistedOrganizationMicroservice(organizationMicroservice)
        );
    }

    @Test
    @Transactional
    void fullUpdateOrganizationMicroserviceWithPatch() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the organizationMicroservice using partial update
        OrganizationMicroservice partialUpdatedOrganizationMicroservice = new OrganizationMicroservice();
        partialUpdatedOrganizationMicroservice.setId(organizationMicroservice.getId());

        partialUpdatedOrganizationMicroservice.version(UPDATED_VERSION).updated(UPDATED_UPDATED);

        restOrganizationMicroserviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOrganizationMicroservice.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedOrganizationMicroservice))
            )
            .andExpect(status().isOk());

        // Validate the OrganizationMicroservice in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertOrganizationMicroserviceUpdatableFieldsEquals(
            partialUpdatedOrganizationMicroservice,
            getPersistedOrganizationMicroservice(partialUpdatedOrganizationMicroservice)
        );
    }

    @Test
    @Transactional
    void patchNonExistingOrganizationMicroservice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMicroservice.setId(longCount.incrementAndGet());

        // Create the OrganizationMicroservice
        OrganizationMicroserviceDTO organizationMicroserviceDTO = organizationMicroserviceMapper.toDto(organizationMicroservice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrganizationMicroserviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, organizationMicroserviceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationMicroserviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMicroservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOrganizationMicroservice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMicroservice.setId(longCount.incrementAndGet());

        // Create the OrganizationMicroservice
        OrganizationMicroserviceDTO organizationMicroserviceDTO = organizationMicroserviceMapper.toDto(organizationMicroservice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMicroserviceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(organizationMicroserviceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OrganizationMicroservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOrganizationMicroservice() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        organizationMicroservice.setId(longCount.incrementAndGet());

        // Create the OrganizationMicroservice
        OrganizationMicroserviceDTO organizationMicroserviceDTO = organizationMicroserviceMapper.toDto(organizationMicroservice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOrganizationMicroserviceMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(organizationMicroserviceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OrganizationMicroservice in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOrganizationMicroservice() throws Exception {
        // Initialize the database
        organizationMicroserviceRepository.saveAndFlush(organizationMicroservice);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the organizationMicroservice
        restOrganizationMicroserviceMockMvc
            .perform(delete(ENTITY_API_URL_ID, organizationMicroservice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return organizationMicroserviceRepository.count();
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

    protected OrganizationMicroservice getPersistedOrganizationMicroservice(OrganizationMicroservice organizationMicroservice) {
        return organizationMicroserviceRepository.findById(organizationMicroservice.getId()).orElseThrow();
    }

    protected void assertPersistedOrganizationMicroserviceToMatchAllProperties(OrganizationMicroservice expectedOrganizationMicroservice) {
        assertOrganizationMicroserviceAllPropertiesEquals(
            expectedOrganizationMicroservice,
            getPersistedOrganizationMicroservice(expectedOrganizationMicroservice)
        );
    }

    protected void assertPersistedOrganizationMicroserviceToMatchUpdatableProperties(
        OrganizationMicroservice expectedOrganizationMicroservice
    ) {
        assertOrganizationMicroserviceAllUpdatablePropertiesEquals(
            expectedOrganizationMicroservice,
            getPersistedOrganizationMicroservice(expectedOrganizationMicroservice)
        );
    }
}
