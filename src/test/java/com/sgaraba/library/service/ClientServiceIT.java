package com.sgaraba.library.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.sgaraba.library.IntegrationTest;
import com.sgaraba.library.config.Constants;
import com.sgaraba.library.domain.Client;
import com.sgaraba.library.repository.ClientRepository;
import com.sgaraba.library.service.dto.AdminUserDTO;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.auditing.AuditingHandler;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.security.RandomUtil;

/**
 * Integration tests for {@link ClientService}.
 */
@IntegrationTest
@Transactional
class CLientServiceIT {

    private static final String DEFAULT_FIRSTNAME = "john";

    private static final String DEFAULT_LASTNAME = "doe";

    private static final String DEFAULT_EMAIL = "johndoe@localhost";

    private static final String DEFAULT_ADDRESS = "123 street";

    private static final String DEFAULT_PHONE = "123456789";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AuditingHandler auditingHandler;

    @MockBean
    private DateTimeProvider dateTimeProvider;

    private Client client;

    @BeforeEach
    public void init() {
        client = new Client();
        client.setFirstName(DEFAULT_FIRSTNAME);
        client.setLastName(DEFAULT_LASTNAME);
        client.setEmail(DEFAULT_EMAIL);
        client.setAddress(DEFAULT_ADDRESS);
        client.setPhone(DEFAULT_PHONE);

        when(dateTimeProvider.getNow()).thenReturn(Optional.of(LocalDateTime.now()));
        auditingHandler.setDateTimeProvider(dateTimeProvider);
    }

    @Test
    @Transactional
    void assertThatClientGetsSaved() {
        clientService.save(client);
        Optional<Client> maybeClient = clientService.findOne(client.getId());
        assertThat(maybeClient).isPresent();
    }

    @Test
    @Transactional
    void assertThatClientGetsDeleted() {
        clientService.save(client);
        clientService.delete(client.getId());

        Optional<Client> maybeClient = clientService.findOne(client.getId());
        assertThat(maybeClient).isNotPresent();
    }
}
