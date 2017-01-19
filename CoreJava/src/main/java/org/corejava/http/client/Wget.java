package org.corejava.http.client;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

public class Wget {
    public static void main(String[] args) throws UnsupportedOperationException, IOException {

        HttpClient httpclient = HttpClientBuilder.create().build();

        HttpPost httpget = new HttpPost("http://sina.com/");

        HttpResponse response = httpclient.execute(httpget);

        HttpEntity entity = response.getEntity();

        if (entity != null) {

            InputStream instream = entity.getContent();


            byte[] tmp = new byte[2048];

            while ((instream.read(tmp)) != -1) {
                System.out.println(new String(tmp));
            }

        }
    }
}
