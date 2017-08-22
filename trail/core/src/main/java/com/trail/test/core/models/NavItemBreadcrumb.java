package com.trail.test.core.models;

/**
 * Created by yogesh.kulkarni on 8/21/17.
 */import com.day.cq.wcm.api.Page;


public class NavItemBreadcrumb {

    private Page page;
    private boolean active;

    public NavItemBreadcrumb(Page page, boolean active)
    {
        this.page = page;
        this.active = active;
    }

    public Page getPage()
    {
        return this.page;
    }

    public boolean isActive()
    {
        return this.active;
    }
}
