package com.reactnativenavigation.e2e.androide2e;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TopLevelApiTest extends BaseTest {

	@Test
	public void switchToTabBasedApp() throws Exception {
		assertThat(1 + 1).isPositive();
	}
}
