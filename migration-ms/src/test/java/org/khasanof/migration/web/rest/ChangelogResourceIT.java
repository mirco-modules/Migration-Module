package org.khasanof.migration.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.khasanof.migration.domain.ChangelogAsserts.*;
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
import org.khasanof.migration.domain.common.Changelog;
import org.khasanof.migration.domain.common.Microservice;
import org.khasanof.migration.domain.common.enumeration.ChangelogStatus;
import org.khasanof.migration.repository.common.ChangelogRepository;
import org.khasanof.migration.service.dto.ChangelogDTO;
import org.khasanof.migration.service.mapper.ChangelogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ChangelogResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ChangelogResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PATH = "AAAAAAAAAA";
    private static final String UPDATED_PATH = "BBBBBBBBBB";

    private static final Long DEFAULT_FILESIZE = 1L;
    private static final Long UPDATED_FILESIZE = 2L;
    private static final Long SMALLER_FILESIZE = 1L - 1L;

    private static final ChangelogStatus DEFAULT_STATUS = ChangelogStatus.ACTIVE;
    private static final ChangelogStatus UPDATED_STATUS = ChangelogStatus.INACTIVE;

    private static final String ENTITY_API_URL = "/api/changelogs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ChangelogRepository changelogRepository;

    @Autowired
    private ChangelogMapper changelogMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restChangelogMockMvc;

    private Changelog changelog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Changelog createEntity(EntityManager em) {
        Changelog changelog = new Changelog()
            .name(DEFAULT_NAME)
            .originalName(DEFAULT_ORIGINAL_NAME)
            .path(DEFAULT_PATH)
            .filesize(DEFAULT_FILESIZE)
            .status(DEFAULT_STATUS);
        return changelog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Changelog createUpdatedEntity(EntityManager em) {
        Changelog changelog = new Changelog()
            .name(UPDATED_NAME)
            .originalName(UPDATED_ORIGINAL_NAME)
            .path(UPDATED_PATH)
            .filesize(UPDATED_FILESIZE)
            .status(UPDATED_STATUS);
        return changelog;
    }

    @BeforeEach
    public void initTest() {
        changelog = createEntity(em);
    }

    @Test
    @Transactional
    void createChangelog() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Changelog
        ChangelogDTO changelogDTO = changelogMapper.toDto(changelog);
        var returnedChangelogDTO = om.readValue(
            restChangelogMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(changelogDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ChangelogDTO.class
        );

        // Validate the Changelog in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedChangelog = changelogMapper.toEntity(returnedChangelogDTO);
        assertChangelogUpdatableFieldsEquals(returnedChangelog, getPersistedChangelog(returnedChangelog));
    }

    @Test
    @Transactional
    void createChangelogWithExistingId() throws Exception {
        // Create the Changelog with an existing ID
        changelog.setId(1L);
        ChangelogDTO changelogDTO = changelogMapper.toDto(changelog);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restChangelogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(changelogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Changelog in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        changelog.setName(null);

        // Create the Changelog, which fails.
        ChangelogDTO changelogDTO = changelogMapper.toDto(changelog);

        restChangelogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(changelogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkOriginalNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        changelog.setOriginalName(null);

        // Create the Changelog, which fails.
        ChangelogDTO changelogDTO = changelogMapper.toDto(changelog);

        restChangelogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(changelogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPathIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        changelog.setPath(null);

        // Create the Changelog, which fails.
        ChangelogDTO changelogDTO = changelogMapper.toDto(changelog);

        restChangelogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(changelogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFilesizeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        changelog.setFilesize(null);

        // Create the Changelog, which fails.
        ChangelogDTO changelogDTO = changelogMapper.toDto(changelog);

        restChangelogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(changelogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        changelog.setStatus(null);

        // Create the Changelog, which fails.
        ChangelogDTO changelogDTO = changelogMapper.toDto(changelog);

        restChangelogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(changelogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllChangelogs() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList
        restChangelogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(changelog.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].originalName").value(hasItem(DEFAULT_ORIGINAL_NAME)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].filesize").value(hasItem(DEFAULT_FILESIZE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getChangelog() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get the changelog
        restChangelogMockMvc
            .perform(get(ENTITY_API_URL_ID, changelog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(changelog.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.originalName").value(DEFAULT_ORIGINAL_NAME))
            .andExpect(jsonPath("$.path").value(DEFAULT_PATH))
            .andExpect(jsonPath("$.filesize").value(DEFAULT_FILESIZE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getChangelogsByIdFiltering() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        Long id = changelog.getId();

        defaultChangelogFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultChangelogFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultChangelogFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllChangelogsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where name equals to
        defaultChangelogFiltering("name.equals=" + DEFAULT_NAME, "name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllChangelogsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where name in
        defaultChangelogFiltering("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME, "name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllChangelogsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where name is not null
        defaultChangelogFiltering("name.specified=true", "name.specified=false");
    }

    @Test
    @Transactional
    void getAllChangelogsByNameContainsSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where name contains
        defaultChangelogFiltering("name.contains=" + DEFAULT_NAME, "name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllChangelogsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where name does not contain
        defaultChangelogFiltering("name.doesNotContain=" + UPDATED_NAME, "name.doesNotContain=" + DEFAULT_NAME);
    }

    @Test
    @Transactional
    void getAllChangelogsByOriginalNameIsEqualToSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where originalName equals to
        defaultChangelogFiltering("originalName.equals=" + DEFAULT_ORIGINAL_NAME, "originalName.equals=" + UPDATED_ORIGINAL_NAME);
    }

    @Test
    @Transactional
    void getAllChangelogsByOriginalNameIsInShouldWork() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where originalName in
        defaultChangelogFiltering(
            "originalName.in=" + DEFAULT_ORIGINAL_NAME + "," + UPDATED_ORIGINAL_NAME,
            "originalName.in=" + UPDATED_ORIGINAL_NAME
        );
    }

    @Test
    @Transactional
    void getAllChangelogsByOriginalNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where originalName is not null
        defaultChangelogFiltering("originalName.specified=true", "originalName.specified=false");
    }

    @Test
    @Transactional
    void getAllChangelogsByOriginalNameContainsSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where originalName contains
        defaultChangelogFiltering("originalName.contains=" + DEFAULT_ORIGINAL_NAME, "originalName.contains=" + UPDATED_ORIGINAL_NAME);
    }

    @Test
    @Transactional
    void getAllChangelogsByOriginalNameNotContainsSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where originalName does not contain
        defaultChangelogFiltering(
            "originalName.doesNotContain=" + UPDATED_ORIGINAL_NAME,
            "originalName.doesNotContain=" + DEFAULT_ORIGINAL_NAME
        );
    }

    @Test
    @Transactional
    void getAllChangelogsByPathIsEqualToSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where path equals to
        defaultChangelogFiltering("path.equals=" + DEFAULT_PATH, "path.equals=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    void getAllChangelogsByPathIsInShouldWork() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where path in
        defaultChangelogFiltering("path.in=" + DEFAULT_PATH + "," + UPDATED_PATH, "path.in=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    void getAllChangelogsByPathIsNullOrNotNull() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where path is not null
        defaultChangelogFiltering("path.specified=true", "path.specified=false");
    }

    @Test
    @Transactional
    void getAllChangelogsByPathContainsSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where path contains
        defaultChangelogFiltering("path.contains=" + DEFAULT_PATH, "path.contains=" + UPDATED_PATH);
    }

    @Test
    @Transactional
    void getAllChangelogsByPathNotContainsSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where path does not contain
        defaultChangelogFiltering("path.doesNotContain=" + UPDATED_PATH, "path.doesNotContain=" + DEFAULT_PATH);
    }

    @Test
    @Transactional
    void getAllChangelogsByFilesizeIsEqualToSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where filesize equals to
        defaultChangelogFiltering("filesize.equals=" + DEFAULT_FILESIZE, "filesize.equals=" + UPDATED_FILESIZE);
    }

    @Test
    @Transactional
    void getAllChangelogsByFilesizeIsInShouldWork() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where filesize in
        defaultChangelogFiltering("filesize.in=" + DEFAULT_FILESIZE + "," + UPDATED_FILESIZE, "filesize.in=" + UPDATED_FILESIZE);
    }

    @Test
    @Transactional
    void getAllChangelogsByFilesizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where filesize is not null
        defaultChangelogFiltering("filesize.specified=true", "filesize.specified=false");
    }

    @Test
    @Transactional
    void getAllChangelogsByFilesizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where filesize is greater than or equal to
        defaultChangelogFiltering("filesize.greaterThanOrEqual=" + DEFAULT_FILESIZE, "filesize.greaterThanOrEqual=" + UPDATED_FILESIZE);
    }

    @Test
    @Transactional
    void getAllChangelogsByFilesizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where filesize is less than or equal to
        defaultChangelogFiltering("filesize.lessThanOrEqual=" + DEFAULT_FILESIZE, "filesize.lessThanOrEqual=" + SMALLER_FILESIZE);
    }

    @Test
    @Transactional
    void getAllChangelogsByFilesizeIsLessThanSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where filesize is less than
        defaultChangelogFiltering("filesize.lessThan=" + UPDATED_FILESIZE, "filesize.lessThan=" + DEFAULT_FILESIZE);
    }

    @Test
    @Transactional
    void getAllChangelogsByFilesizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where filesize is greater than
        defaultChangelogFiltering("filesize.greaterThan=" + SMALLER_FILESIZE, "filesize.greaterThan=" + DEFAULT_FILESIZE);
    }

    @Test
    @Transactional
    void getAllChangelogsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where status equals to
        defaultChangelogFiltering("status.equals=" + DEFAULT_STATUS, "status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllChangelogsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where status in
        defaultChangelogFiltering("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS, "status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllChangelogsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        // Get all the changelogList where status is not null
        defaultChangelogFiltering("status.specified=true", "status.specified=false");
    }

    @Test
    @Transactional
    void getAllChangelogsByMicroserviceIsEqualToSomething() throws Exception {
        Microservice microservice;
        if (TestUtil.findAll(em, Microservice.class).isEmpty()) {
            changelogRepository.saveAndFlush(changelog);
            microservice = MicroserviceResourceIT.createEntity(em);
        } else {
            microservice = TestUtil.findAll(em, Microservice.class).get(0);
        }
        em.persist(microservice);
        em.flush();
        changelog.setMicroservice(microservice);
        changelogRepository.saveAndFlush(changelog);
        Long microserviceId = microservice.getId();
        // Get all the changelogList where microservice equals to microserviceId
        defaultChangelogShouldBeFound("microserviceId.equals=" + microserviceId);

        // Get all the changelogList where microservice equals to (microserviceId + 1)
        defaultChangelogShouldNotBeFound("microserviceId.equals=" + (microserviceId + 1));
    }

    private void defaultChangelogFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultChangelogShouldBeFound(shouldBeFound);
        defaultChangelogShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultChangelogShouldBeFound(String filter) throws Exception {
        restChangelogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(changelog.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].originalName").value(hasItem(DEFAULT_ORIGINAL_NAME)))
            .andExpect(jsonPath("$.[*].path").value(hasItem(DEFAULT_PATH)))
            .andExpect(jsonPath("$.[*].filesize").value(hasItem(DEFAULT_FILESIZE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restChangelogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultChangelogShouldNotBeFound(String filter) throws Exception {
        restChangelogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restChangelogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingChangelog() throws Exception {
        // Get the changelog
        restChangelogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingChangelog() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the changelog
        Changelog updatedChangelog = changelogRepository.findById(changelog.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedChangelog are not directly saved in db
        em.detach(updatedChangelog);
        updatedChangelog
            .name(UPDATED_NAME)
            .originalName(UPDATED_ORIGINAL_NAME)
            .path(UPDATED_PATH)
            .filesize(UPDATED_FILESIZE)
            .status(UPDATED_STATUS);
        ChangelogDTO changelogDTO = changelogMapper.toDto(updatedChangelog);

        restChangelogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, changelogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(changelogDTO))
            )
            .andExpect(status().isOk());

        // Validate the Changelog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedChangelogToMatchAllProperties(updatedChangelog);
    }

    @Test
    @Transactional
    void putNonExistingChangelog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        changelog.setId(longCount.incrementAndGet());

        // Create the Changelog
        ChangelogDTO changelogDTO = changelogMapper.toDto(changelog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChangelogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, changelogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(changelogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Changelog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchChangelog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        changelog.setId(longCount.incrementAndGet());

        // Create the Changelog
        ChangelogDTO changelogDTO = changelogMapper.toDto(changelog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangelogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(changelogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Changelog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamChangelog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        changelog.setId(longCount.incrementAndGet());

        // Create the Changelog
        ChangelogDTO changelogDTO = changelogMapper.toDto(changelog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangelogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(changelogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Changelog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateChangelogWithPatch() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the changelog using partial update
        Changelog partialUpdatedChangelog = new Changelog();
        partialUpdatedChangelog.setId(changelog.getId());

        partialUpdatedChangelog.name(UPDATED_NAME).originalName(UPDATED_ORIGINAL_NAME).status(UPDATED_STATUS);

        restChangelogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChangelog.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChangelog))
            )
            .andExpect(status().isOk());

        // Validate the Changelog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChangelogUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedChangelog, changelog),
            getPersistedChangelog(changelog)
        );
    }

    @Test
    @Transactional
    void fullUpdateChangelogWithPatch() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the changelog using partial update
        Changelog partialUpdatedChangelog = new Changelog();
        partialUpdatedChangelog.setId(changelog.getId());

        partialUpdatedChangelog
            .name(UPDATED_NAME)
            .originalName(UPDATED_ORIGINAL_NAME)
            .path(UPDATED_PATH)
            .filesize(UPDATED_FILESIZE)
            .status(UPDATED_STATUS);

        restChangelogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedChangelog.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedChangelog))
            )
            .andExpect(status().isOk());

        // Validate the Changelog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertChangelogUpdatableFieldsEquals(partialUpdatedChangelog, getPersistedChangelog(partialUpdatedChangelog));
    }

    @Test
    @Transactional
    void patchNonExistingChangelog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        changelog.setId(longCount.incrementAndGet());

        // Create the Changelog
        ChangelogDTO changelogDTO = changelogMapper.toDto(changelog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restChangelogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, changelogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(changelogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Changelog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchChangelog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        changelog.setId(longCount.incrementAndGet());

        // Create the Changelog
        ChangelogDTO changelogDTO = changelogMapper.toDto(changelog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangelogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(changelogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Changelog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamChangelog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        changelog.setId(longCount.incrementAndGet());

        // Create the Changelog
        ChangelogDTO changelogDTO = changelogMapper.toDto(changelog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restChangelogMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(changelogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Changelog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteChangelog() throws Exception {
        // Initialize the database
        changelogRepository.saveAndFlush(changelog);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the changelog
        restChangelogMockMvc
            .perform(delete(ENTITY_API_URL_ID, changelog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return changelogRepository.count();
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

    protected Changelog getPersistedChangelog(Changelog changelog) {
        return changelogRepository.findById(changelog.getId()).orElseThrow();
    }

    protected void assertPersistedChangelogToMatchAllProperties(Changelog expectedChangelog) {
        assertChangelogAllPropertiesEquals(expectedChangelog, getPersistedChangelog(expectedChangelog));
    }

    protected void assertPersistedChangelogToMatchUpdatableProperties(Changelog expectedChangelog) {
        assertChangelogAllUpdatablePropertiesEquals(expectedChangelog, getPersistedChangelog(expectedChangelog));
    }
}
