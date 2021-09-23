package test;

import com.zuipin.util.RegExUtil;
import junit.framework.TestCase;

/**
 * Created by dy on 2017/2/8.
 */
public class UtilTest extends TestCase {

    public void testReg() {
        String password = "123456@！";
        System.out.println(RegExUtil.checkPassword(password));
    }
}
