package com.data.data.hmly.service.nctripticket;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Created by caiys on 2016/1/25.
 */
class Verifier implements HostnameVerifier {

    public boolean verify(String arg0, SSLSession arg1) {
        return true;   // mark everything as verified
    }
}
