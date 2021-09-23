package com.data.data.hmly.action.mobile.tag;

import com.zuipin.util.StringUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.File;
import java.io.IOException;

public class IncludeTag extends TagSupport {

	/**
	 * .
	 */
	private static final long serialVersionUID = -2165476717536407223L;

	private final Log log = LogFactory.getLog(IncludeTag.class);

	private Object fileAttr;
	private String encode = "utf-8";

	public int doStartTag() throws JspException {
		if (fileAttr == null || StringUtils.isBlank(fileAttr.toString())) {
			throw new JspException("Tag attr 'fileAttr' is blank");
		}
		try {
			String filePath = new StringBuilder(pageContext.getServletContext().getRealPath("")).append(fileAttr).toString();
			String html = FileUtils.readFileToString(new File(filePath), encode);
			pageContext.getOut().write(html);
			return Tag.SKIP_BODY;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			throw new JspException(e.getMessage(), e);
		}
	}

	public Object getFileAttr() {
		return fileAttr;
	}

	public void setFileAttr(Object fileAttr) throws JspException {
		if (fileAttr != null) {
			this.fileAttr = ExpressionEvaluatorManager.evaluate("fileAttr", fileAttr.toString(), Object.class, this, pageContext);
		}
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

}
