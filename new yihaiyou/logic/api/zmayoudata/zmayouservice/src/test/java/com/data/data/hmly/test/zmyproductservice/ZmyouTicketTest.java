package com.data.data.hmly.test.zmyproductservice;

import com.data.data.hmly.service.zmyproduct.ZmyTicketService;
import junit.framework.TestCase;
import org.junit.Ignore;
import org.springframework.util.Base64Utils;

import java.io.UnsupportedEncodingException;

/**
 * Created by dy on 2016/5/9.
 */
@Ignore
public class ZmyouTicketTest extends TestCase {

    final ZmyTicketService zmyTicketService = new ZmyTicketService();

    public void testSynProuduct() {


        String resultData = "W3siZGlzdHJpYnV0b3JJZCI6NjEsImlzVGVhbSI6MSwibWFya2V0UHJpY2UiOjAuMDEsIm1heE51bSI6NCwibWluTnVtIjoyLCJuYW1lIjoi5rWL6K+VLS3lkIzlvKAiLCJwcm9kdWN0SWQiOjQ1MywicHVyY2hhc2VQcmljZSI6MC4wMSwicmVhbG5hbWUiOjAsInJlZnVuZEZhY3RvcmFnZSI6MSwicmVmdW5kVGltZUxhZyI6MCwicmVmdW5kVHlwZSI6MSwic3RhdGUiOjIsInR5cGUiOjEsInZhbGlkVHlwZSI6MSwidmFsaWRpdHkiOjd9LHsiZGlzdHJpYnV0b3JJZCI6NjEsImlzVGVhbSI6MSwibWFya2V0UHJpY2UiOjAuMDEsIm1heE51bSI6NCwibWluTnVtIjoyLCJuYW1lIjoiU1hMIiwicHJvZHVjdElkIjo0NDYsInB1cmNoYXNlUHJpY2UiOjAuMDEsInJlYWxuYW1lIjowLCJyZWZ1bmRGYWN0b3JhZ2UiOjEsInJlZnVuZFRpbWVMYWciOjAsInJlZnVuZFR5cGUiOjEsInN0YXRlIjoyLCJ0eXBlIjoxLCJ2YWxpZFR5cGUiOjEsInZhbGlkaXR5Ijo3fSx7ImRpc3RyaWJ1dG9ySWQiOjYxLCJpc1RlYW0iOjAsIm1hcmtldFByaWNlIjowLjAxLCJtYXhOdW0iOjEsIm1pbk51bSI6MSwibmFtZSI6IuiDoemHjOWxseelqC3miJDkurrnpagiLCJwcm9kdWN0SWQiOjQ1NCwicHVyY2hhc2VQcmljZSI6MC4wMSwicmVhbG5hbWUiOjAsInJlZnVuZEZhY3RvcmFnZSI6MSwicmVmdW5kVGltZUxhZyI6MCwicmVmdW5kVHlwZSI6MSwic3RhdGUiOjIsInR5cGUiOjEsInZhbGlkVHlwZSI6MSwidmFsaWRpdHkiOjd9XQ==";

        byte[] bt = null;

        bt = Base64Utils.decodeFromString(resultData);
        try {
            resultData = new String(bt, "UTF-8");
            zmyTicketService.doSynProduct(resultData);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public void testCreateOrder() {

//        zmyTicketService.findZmyProduct();

    }

}
