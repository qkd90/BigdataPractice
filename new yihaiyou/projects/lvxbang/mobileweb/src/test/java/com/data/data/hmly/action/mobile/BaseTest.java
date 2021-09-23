package com.data.data.hmly.action.mobile;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.struts2.StrutsSpringTestCase;
import org.junit.Ignore;

/**
 * Created by huangpeijie on 2016-04-27,0027.
 */
@Ignore
public class BaseTest extends StrutsSpringTestCase {
    protected final ObjectMapper mapper = new ObjectMapper();

    protected String[] getContextLocations() {
        return new String[]{"classpath:applicationContext*.xml"};
    }
}
