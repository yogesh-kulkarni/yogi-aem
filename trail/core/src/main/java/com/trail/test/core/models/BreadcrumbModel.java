package com.trail.test.core.models;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.sling.models.annotations.Model;
import javax.annotation.PostConstruct;
import com.day.cq.wcm.api.Page;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.injectorspecific.ScriptVariable;
import java.util.Arrays;

/**
 * Created by yogesh.kulkarni on 8/22/17.
 */

@Model(adaptables={SlingHttpServletRequest.class})
public class BreadcrumbModel {

    @ScriptVariable
    private Page currentPage;
    @ScriptVariable
    private ValueMap properties;

    protected static final String PROP_HIDE_PAGES = "hidePages";
    private String[] hidePages;

    //set depth of the content tree to 2 e.g /mysite/en. Can be authored.
    private int startContentLevel = 2;
    private List<NavItemBreadcrumb> items;

    /**
     * Constructor to support Sling Models.
     */
    public BreadcrumbModel() {
        // Empty constructor to support Models.
    }

    /**
     * set navItems to the context.
     */
    public Collection<NavItemBreadcrumb> getItems()
    {
        if (this.items == null)
        {
            this.items = new ArrayList();
            createItems();
        }
        return this.items;
    }

    private List<NavItemBreadcrumb> createItems()
    {
        int currentLevel = this.currentPage.getDepth();
        addNavItems(currentLevel);
        return this.items;
    }

    @PostConstruct
    private void initModel()
    {
        this.hidePages = PropertiesUtil.toStringArray(this.properties.get(PROP_HIDE_PAGES));
    }


    private void addNavItems(int currentLevel)
    {
        while (this.startContentLevel < currentLevel)
        {
            Page page = this.currentPage.getAbsoluteParent(this.startContentLevel);
            if (page != null)
            {
                boolean isActivePage = page.equals(this.currentPage);
                // find if page is NOT in hide page list, before adding it into the Navigation list
                if (!checkIfPageHidden(page))
                {
                    NavItemBreadcrumb navigationItem = new NavItemBreadcrumb(page, isActivePage);
                    this.items.add(navigationItem);
                }
            }
            this.startContentLevel += 1;
        }
    }

    private boolean checkIfPageHidden(Page page)
    {
        if (hidePages == null ) {
            return false;
        }
        final Iterator<String> hidePageIterator = Arrays.asList(hidePages).iterator();
        while (hidePageIterator.hasNext()) {
            if (hidePageIterator.next().equals(page.getPath())){
                return true;
            }
        }
        return false;
    }
}
