package org.hibernate.dialect;

import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;

/**
 * Created by caiys on 2017/3/1.
 */
public class ExtendedMySQLDialect extends MySQLDialect {
    /*
    * 使用方式 date_add(now(), 1, WEEK)
    */
    public ExtendedMySQLDialect() {
        super();
        registerFunction("date_add", new SQLFunctionTemplate(StandardBasicTypes.DATE,
                "date_add(?1, interval ?2 ?3)"));
    }
}