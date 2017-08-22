package com.trail.test.core.models;

import org.junit.Before;
import org.apache.sling.api.resource.ValueMap;

import org.junit.Rule;
import org.junit.Test;
import io.wcm.testing.mock.aem.junit.AemContext;
import com.day.cq.wcm.api.Page;
import org.apache.sling.api.resource.Resource;

import org.apache.sling.api.scripting.SlingBindings;
import com.adobe.cq.sightly.WCMBindings;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;

/**
 * Simple JUnit test verifying the BreadcrumbModel
 */
public class TestBreadcrumbModel {
    @Rule
    public AemContext context = new AemContext();
    private SlingBindings slingBindings;

    private static final String TEST_CONTENT_ROOT = "/content/article";
    private static final String TITLE_RESOURCE = TEST_CONTENT_ROOT + "/jcr:content/bodypar/breadcrumb";

    private Resource resource;

    @Before
    public void setup() throws Exception {
        context.addModelsForClasses(BreadcrumbModel.class);
       // resource = context.currentResource(TITLE_RESOURCE);
    }
    
    @Test
    public void testBreadcrumbNav() throws Exception {
        context.load().json("/test-breadcrumb-page1.json", TEST_CONTENT_ROOT);
        Page page = context.currentPage(TEST_CONTENT_ROOT);
        slingBindings = (SlingBindings) context.request().getAttribute(SlingBindings.class.getName());
       //slingBindings.put(WCMBindings.PROPERTIES, new String[]{"page1","page2"});
        resource = context.currentResource(TITLE_RESOURCE);
        if (resource != null) {
            ValueMap properties = resource.adaptTo(ValueMap.class);
            properties.get("hidePage", "[\"/content/trail/en/articles\"]");
            slingBindings.put(SlingBindings.RESOURCE, resource);
            slingBindings.put(WCMBindings.CURRENT_PAGE, page);
            slingBindings.put(WCMBindings.PROPERTIES, properties);
        }
        final  BreadcrumbModel breadcrumb = context.request().adaptTo(BreadcrumbModel.class);
        // some very basic junit tests
       Collection<NavItemBreadcrumb> navItems = breadcrumb.getItems();
        assertNotNull(navItems);
    }

}
