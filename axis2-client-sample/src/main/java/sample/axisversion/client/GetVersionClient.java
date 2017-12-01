package sample.axisversion.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import sample.axisversion.VersionStub;
import sample.axisversion.VersionStub.GetVersion;
import sample.axisversion.VersionStub.GetVersionResponse;

public class GetVersionClient {
    public static void main(String[] args) throws Exception {

        final Log logger = LogFactory.getLog(GetVersionClient.class);

        // 通过axis2.xml可以切换httpclient的版本，但是修改axis2.xml时，需要指定repo。maven配置，main运行，执行repo有困难。
        // URL url = GetVersionClient.class.getClassLoader().getResource("axis2.xml");
        // // URL url = new URL("classpath:");
        // ConfigurationContext context = ConfigurationContextFactory.createConfigurationContextFromURIs(url, null);
        // AxisConfiguration axisCon = new AxisConfiguration();
        //
        // ConfigurationContext con = new ConfigurationContext(axisCon);

        // options.setProperty(org.apache.axis2.transport.http.HTTPConstants.HEADER_CONNECTION,
        // org.apache.axis2.transport.http.HTTPConstants.HEADER_CONNECTION_CLOSE);
        VersionStub stub = new VersionStub();

        // 共享httpclient
        MultiThreadedHttpConnectionManager conmgr = new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = conmgr.getParams();

        params.setConnectionTimeout(6000);
        params.setSoTimeout(30000);
        params.setDefaultMaxConnectionsPerHost(64);
        params.setMaxTotalConnections(256);
        params.setStaleCheckingEnabled(true);
        HttpClient client = new HttpClient(conmgr);
        // CRM支持重复调用，设置发送后重发支持
        client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3, true));
        stub._getServiceClient().getServiceContext().getConfigurationContext().setProperty(HTTPConstants.CACHED_HTTP_CLIENT, client);
        Options options = stub._getServiceClient().getOptions();

        options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Boolean.TRUE);

        // 共享httpstate
        HttpState myHttpState = new HttpState();
        options.setProperty(HTTPConstants.CACHED_HTTP_STATE, myHttpState);

        // 使用HTTP1.0防止connection断开
        // options.setProperty(HTTPConstants.HTTP_PROTOCOL_VERSION, HTTPConstants.HEADER_PROTOCOL_10);
        options.setProperty(HTTPConstants.CHUNKED, Boolean.FALSE);
        // 设置自动清理
        options.setCallTransportCleanup(true);

        GetVersion param = new GetVersion();
        int i = 1;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {

            GetVersionResponse s = stub.getVersion(param);
            logger.trace(ReflectionToStringBuilder.toString(s));
            logger.trace("hello:" + i++);
            String input = br.readLine();
            logger.trace("input:" + input);
        }

    }
}
