package org.corejava.net.http;

import java.io.IOException;

import org.junit.Test;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class OkhttpSample {

    @Test
    public void okhttpGet() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Builder reqb = new Builder();
        reqb.url("https://www.baidu.com/");

        Call c = client.newCall(reqb.build());
        Response d = c.execute();
        System.err.println(d.toString());
    }
}
