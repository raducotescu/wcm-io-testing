/*
 * #%L
 * wcm.io
 * %%
 * Copyright (C) 2018 wcm.io
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package io.wcm.testing.mock.aem.junit5;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.resourceresolver.impl.ResourceResolverImpl;
import org.apache.sling.spi.resource.provider.ResourceProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test with {@link NoResourceResolverTypeAemContext}.
 */
@ExtendWith({ AemContextExtension.class, MockitoExtension.class })
class NoResourceResolverTypeAemContextTest {

  @Mock
  private ResourceProvider<?> resourceProvider;

  @BeforeEach
  void setUp(NoResourceResolverTypeAemContext context) {

    // register dummy resource provider because otherwise ResourceResolverFactory get's not activated
    // with latest sling resource resolver implementation
    context.registerService(ResourceProvider.class, resourceProvider,
        ResourceProvider.PROPERTY_ROOT, "/");

    assertTrue(context.resourceResolver() instanceof ResourceResolverImpl);
    assertNull(context.resourceResolver().adaptTo(Session.class));
  }

  @Test
  void testResource(AemContext context) {
    Resource resource = context.resourceResolver().getResource("/content/test");
    assertNull(resource);
  }

}
