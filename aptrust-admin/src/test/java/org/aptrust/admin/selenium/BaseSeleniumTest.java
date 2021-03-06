/*
 * Copyright (c) 2009-2012 AP Trust. All rights reserved.
 */
package org.aptrust.admin.selenium;

import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**
 * @author "Daniel Bernstein (dbernstein@duraspace.org)"
 *
 */
public abstract class BaseSeleniumTest {

    // initialize dfr-sync with test config
    protected Properties props = null;
    
    protected static Logger log =
        LoggerFactory.getLogger(BaseSeleniumTest.class);

    protected Selenium sc;

    public static String DEFAULT_PAGE_LOAD_WAIT_IN_MS = "60000";

    private static String DEFAULT_PORT = "8888";

    protected String getAppRoot() {
        return "/aptrust-admin";
    }

    private String getPort() throws Exception {
        String port = System.getProperty("jetty.port");
        return port != null ? port : DEFAULT_PORT;
    }



    protected String getBaseUrl() throws Exception {
        return "http://localhost:" + getPort() + getAppRoot();
    }
    @Before
    public void before() throws Exception {
        String url = getBaseUrl() + "/";
        sc = createSeleniumClient(url);
        sc.start();
        log.info("started selenium client on " + url);
        props = getProperties();
    }
    
    protected static Properties getProperties() throws Exception{
        Properties p = new Properties();
        p.load(BaseSeleniumTest.class.getClassLoader()
                   .getResourceAsStream("test.properties"));

        return p;
    }


    @BeforeClass
    public static void beforeClass() throws Exception {

    }


    @After
    public void after() {
        sc.stop();
        sc = null;
        log.info("stopped selenium client");
    }

    protected boolean isTextPresent(Selenium sc, String pattern) {
        return sc.isTextPresent(pattern);
    }

    protected boolean isTextPresent(String pattern) {
      return isTextPresent(sc,pattern);
    }

    protected boolean isElementPresent(String locator) {
        return isElementPresent(sc, locator);
    }

    protected boolean isElementPresent(Selenium sc, String locator) {
        return sc.isElementPresent(locator);
    }

    protected Selenium createSeleniumClient(String url) {

        return new DefaultSelenium("localhost", 4444, "*firefox", url);
    }


    /**
     * @param sc
     */
    protected void waitForPage() {
        log.debug("waiting for page to load...");
        sc.waitForPageToLoad(DEFAULT_PAGE_LOAD_WAIT_IN_MS);
        log.debug("body=" + sc.getBodyText());
    }

    protected void clickAndWait(String locator) {
        sc.click(locator);
        log.debug("clicked " + locator);
        waitForPage(sc);
    }

    
    protected void login(String username, String password){
        sc.open(getAppRoot() + "/login");
        sc.type("id=username", username);
        sc.type("id=password", password);
        clickAndWait("css=button");
    }

    protected void loginAsUva() {
        login("uva-admin", "password");
    }

    protected String getInstitutionRoot(String institutionId) {
        return getAppRoot()+"/html/"+ institutionId;
    }

    protected void loginAsRoot() {
        login("aptrust-admin","password");
    }

    /**
     * @param sc
     */
    public static void waitForPage(Selenium sc) {
        log.debug("waiting for page to load...");
        sc.waitForPageToLoad(DEFAULT_PAGE_LOAD_WAIT_IN_MS);
        log.debug("body=" + sc.getBodyText());
    }

    public static void clickAndWait(Selenium sc, String locator) {
        sc.click(locator);
        log.debug("clicked " + locator);
        waitForPage(sc);
    }

}
