package com.framework.struts;

import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import org.apache.struts2.StrutsConstants;
import org.apache.struts2.spring.StrutsSpringObjectFactory;

import javax.servlet.ServletContext;

public class CustomSpringObjectFactory extends StrutsSpringObjectFactory {
    private static final long serialVersionUID = 2115579796800885324L;

    @Inject
    public CustomSpringObjectFactory(@Inject(value = StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_AUTOWIRE, required = false) String autoWire, @Inject(value = StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_AUTOWIRE_ALWAYS_RESPECT, required = false) String alwaysAutoWire,
        @Inject(value = StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_USE_CLASS_CACHE, required = false) String useClassCacheStr, @Inject(value = StrutsConstants.STRUTS_OBJECTFACTORY_SPRING_ENABLE_AOP_SUPPORT, required = false) String enableAopSupport, @Inject(value = StrutsConstants.STRUTS_DEVMODE, required = false) String devMode, @Inject ServletContext servletContext, @Inject Container container) {
        super(autoWire, alwaysAutoWire, useClassCacheStr, enableAopSupport, servletContext, devMode, container);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class getClassInstance(String className) throws ClassNotFoundException {
        return super.getClassInstance(convertFirstLetterOfClassNameToUpper(className));
    }

    String convertFirstLetterOfClassNameToUpper(String className) {
        StringBuilder builder = new StringBuilder(className);
        int lastIndexOfSlash = className.lastIndexOf('.');
        builder.replace(lastIndexOfSlash + 1, lastIndexOfSlash + 2, String.valueOf(Character.toUpperCase(className.charAt(lastIndexOfSlash + 1))));
        return builder.toString();
    }
}
