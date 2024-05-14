package com.valenscalco.library.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.valenscalco.library.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

public class AuthorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuthorDTO.class);
        AuthorDTO authorDTO1 = new AuthorDTO();
        authorDTO1.setId(1L);
        AuthorDTO authorDTO2 = new AuthorDTO();
        assertThat(authorDTO1).isNotEqualTo(authorDTO2);
        authorDTO2.setId(authorDTO1.getId());
        assertThat(authorDTO1).isEqualTo(authorDTO2);
        authorDTO2.setId(2L);
        assertThat(authorDTO1).isNotEqualTo(authorDTO2);
        authorDTO1.setId(null);
        assertThat(authorDTO1).isNotEqualTo(authorDTO2);
    }
}
