package com.eigenbaumarkt.test.domain;

import com.eigenbaumarkt.test.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChooserTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Chooser.class);
        Chooser chooser1 = new Chooser();
        chooser1.setId(1L);
        Chooser chooser2 = new Chooser();
        chooser2.setId(chooser1.getId());
        assertThat(chooser1).isEqualTo(chooser2);
        chooser2.setId(2L);
        assertThat(chooser1).isNotEqualTo(chooser2);
        chooser1.setId(null);
        assertThat(chooser1).isNotEqualTo(chooser2);
    }
}
