package dubbo.client;

import org.junit.Test;

import com.bn.dubbo.los.LosInvoke;
import com.bn.dubbo.los.request.CheckDHDStatus;

public class ERPClientTest {

    @Test
    public void testName() throws Exception {
        CheckDHDStatus request = new CheckDHDStatus();
        request.setYt(3);
        request.setDhdbh("212312312");

        Object res = LosInvoke.execute_forLosProxy("gw-ec-erp-crm.erp-CheckDHDStatus", request);
        System.err.println("finish");
        System.exit(0);
    }
}
