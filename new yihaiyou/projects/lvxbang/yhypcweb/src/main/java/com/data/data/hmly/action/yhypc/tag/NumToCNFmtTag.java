package com.data.data.hmly.action.yhypc.tag;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by zzl on 2017/2/23.
 */
public class NumToCNFmtTag extends TagSupport {

    private String numStr;

    private String ch[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
    private String chBIG[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private String chTenBIG[] = {"拾", "佰", "仟", "万", "拾万", "百万", "仟万", "亿", "拾亿", "百亿", "仟亿"};
    private String chTen[] = {"十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿"};

    @Override
    public int doEndTag() {
        String resultNum = "";
        int num = Integer.parseInt(numStr);
        String je[] = new String[30];
        Integer bit = 0;
        Integer j = -1;
        Integer k = -1;
        Integer i = 0;
        while (num != 0) {
            bit = (int) (num % 10L);
            for (j = 0; j < ch.length; j++) {
                if (j == bit) break;
            }
            if (k != -1) {
                je[i] = chBIG[k];
                i++;
            }
            je[i] = ch[j];
            i++;
            num = num / 10;
            k++;
            if (num == 0) {
                i--;
                for (; i >= 0; i--) {
                    resultNum = resultNum + je[i];
                }
                try {
                    JspWriter jspWriter = pageContext.getOut();
                    jspWriter.print(resultNum);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return SKIP_BODY;
    }

    public String getNumStr() {
        return numStr;
    }

    public void setNumStr(String numStr) {
        this.numStr = numStr;
    }
}
