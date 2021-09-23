package com.framework.struts;

import com.opensymphony.xwork2.ActionInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.dispatcher.StrutsResultSupport;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.Charset;

@SuppressWarnings("serial")
public class TextResult extends StrutsResultSupport {
    public static final int BUFFER_SIZE = 1024;
    private Log             logger      = LogFactory.getLog(TextResult.class);
    private String          charSet     = "UTF-8";
    private String          text;
    
    public TextResult(String text) {
        this.text = text;
    }
    
    public TextResult(String text, String charSet) {
        this.charSet = charSet;
        this.text = text;
    }
    
    protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
        Charset charset = null;
        if (charSet != null) {
            if (Charset.isSupported(charSet)) {
                charset = Charset.forName(charSet);
            } else {
                logger.warn("charset [" + charSet + "] is not recognized ");
                charset = null;
            }
        }
        HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(HTTP_RESPONSE);
        if (charset != null) {
            response.setContentType("text/plain; charset=" + charSet);
        } else {
            response.setContentType("text/plain");
        }
        response.setHeader("Content-Disposition", "inline");
        PrintWriter writer = response.getWriter();
        try {
            writer.write(text);
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }
}
