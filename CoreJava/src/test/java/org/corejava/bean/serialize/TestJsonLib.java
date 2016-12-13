package org.corejava.bean.serialize;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class TestJsonLib {

    /**
     * 测试表明，在jdk 7 -8上测试，fastjson在反序列化的时候，有接近50%的性能优势，而jackson在序列化的时候有40-50%左右的性能优势。
     */
    @Test
    public void test() {
        PerformanceTest.main(null);
    }

}

class PerformanceTest {

    static ObjectMapper mapper = new ObjectMapper();
    static int cnt = 10000;
    static List<String> l1 = new ArrayList<>(cnt * 2);
    static List<String> l2 = new ArrayList<>(cnt * 2);
    static List<Corp> ll1 = new ArrayList<>(cnt * 2);
    static List<Corp> ll2 = new ArrayList<>(cnt * 2);

    public static void main(String[] args) {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        List<Corp> list = new ArrayList<>(cnt * 2);

        Monitoring.begin();
        for (int i = 0; i < cnt; i++) {
            list.add(fullObject(Corp.class));
        }
        long t = Monitoring.end();
        System.out.println("生成数据：" + t / 1000000.0);
        jackson(list);

        fastjson(list);
        long jsum = 0, fsum = 0;
        for (int i = 0; i < 10; i++) {
            l1.clear();
            l2.clear();
            System.gc();
            Monitoring.begin();
            fastjson(list);
            long fast = Monitoring.end();
            fsum += fast;
            System.gc();
            Monitoring.begin();
            jackson(list);
            long jack = Monitoring.end();
            jsum += jack;

            System.out.println(String.format("Serialize jackson:%10f, fastjson %10f", jack / 1000000.0, fast / 1000000.0));
            System.gc();
        }
        System.out.println(String.format("Serialize jackson sum :%10f, fastjson sum  %10f , jack faster :%10.2f %%", jsum / 1000000.0,
            fsum / 1000000.0, (jsum - fsum) * -100.0 / fsum));
        jsum = 0;
        fsum = 0;
        deFastjson(l2);

        deJackson(l1);
        for (int i = 0; i < 10; i++) {
            ll1.clear();
            ll2.clear();
            System.gc();
            Monitoring.begin();
            deJackson(l1);
            long jack = Monitoring.end();
            jsum += jack;
            System.gc();
            Monitoring.begin();
            deFastjson(l2);
            long fast = Monitoring.end();
            fsum += fast;
            System.out.println(String.format("deSerialize jackson:%10f, fastjson %10f", jack / 1000000.0, fast / 1000000.0));
            System.gc();
        }
        System.out.println(String.format("deSerialize jackson sum :%10f, fastjson sum  %10f , jack faster :%10.2f %%", jsum / 1000000.0,
            fsum / 1000000.0, (jsum - fsum) * -100.0 / fsum));

    }

    public static void fastjson(List<Corp> list) {
        for (Corp corp : list) {

            String string = JSON.toJSONString(corp);
            l2.add(string);
        }
    }

    public static void deFastjson(List<String> list) {

        for (String str : list) {

            Corp corp = JSON.parseObject(str, Corp.class);
            ll2.add(corp);
        }
    }

    public static void jackson(List<Corp> list) {
        for (Corp corp : list) {
            try {
                String string = mapper.writeValueAsString(corp);
                l1.add(string);
            }
            catch (JsonGenerationException e) {
                e.printStackTrace();
            }
            catch (JsonMappingException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deJackson(List<String> list) {
        for (String str : list) {
            try {

                Corp co = mapper.readValue(str, Corp.class);
                ll1.add(co);
            }
            catch (JsonGenerationException e) {
                e.printStackTrace();
            }
            catch (JsonMappingException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 填充一个对象（一般用于测试）
     */
    public static <T> T fullObject(Class<T> cl) {
        T t = null;
        try {
            t = cl.newInstance();
            Method methods[] = cl.getMethods();
            for (Method method : methods) {
                // 如果是set方法,进行随机数据的填充
                if (method.getName().indexOf("set") == 0) {
                    Class param = method.getParameterTypes()[0];
                    if (param.equals(String.class)) {
                        method.invoke(t, RandomString.getRandomString(103));
                    }
                    else if (param.equals(Short.class)) {
                        method.invoke(t, (short) new Random().nextInt(1998223));
                    }
                    else if (param.equals(Float.class)) {
                        method.invoke(t, new Random().nextFloat());
                    }
                    else if (param.equals(Double.class)) {
                        method.invoke(t, new Random().nextDouble());
                    }
                    else if (param.equals(Integer.class)) {
                        method.invoke(t, new Random().nextInt(109992));
                    }
                    else if (param.equals(Long.class)) {
                        method.invoke(t, new Random().nextLong());
                    }
                    else if (param.equals(Date.class)) {
                        method.invoke(t, new Date());
                    }
                    else if (param.equals(Timestamp.class)) {
                        method.invoke(t, new Timestamp(System.currentTimeMillis()));
                    }
                    else if (param.equals(java.sql.Date.class)) {
                        method.invoke(t, new java.sql.Date(System.currentTimeMillis()));
                    }
                }
            }
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return t;
    }

}

class RandomString {
    public static final String base = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static final Random random = new Random();

    public static String getRandomString(int length) { // length表示生成字符串的长度
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}

class Monitoring {
    private static ThreadLocal<Long> begin = new ThreadLocal<>();

    public static void begin() {
        begin.set(System.nanoTime());
    }

    public static long end() {
        long time = (System.nanoTime() - begin.get());
        return time;
    }
}

class Corp implements Serializable {
    private static final long serialVersionUID = 8445344022641582230L;
    private Long uid;
    private Integer corpGrade;
    private Integer cityId;
    private String name;
    private String EName;
    private String description;
    private String zipCode;
    private String tel;
    private String fax;
    private String EMail;
    private Integer isEmailOpen;
    private Integer EMailChecked;
    private Timestamp regDateOnGov;
    private String backroll;
    private String address;
    private String webStoreUrl;
    private Integer isNew;
    private Integer credit;
    private Integer activeDegrees;
    private Integer hits;
    private Integer isHitsRecord;
    private Timestamp regTimeOnZfa;
    private Integer corpType;
    private Integer corpMajorcategoryId;
    private Integer businessRoleId;
    private String keyword;
    private Integer developState;
    private String isAlert;
    private Integer advMemState;
    private Integer advStockState;
    private Integer allianceState;
    private Timestamp lastUpdateTime;
    private Integer corpMajorcategoryId1;
    private String keyword1;
    private Long certificatePic;
    private Integer isUpdateCharter;
    private Integer currcount;
    private Integer curronsale;
    private Integer curronhot;
    private Integer currniccount;
    private Integer currniconsale;
    private Integer currniconhot;
    private String buyProducts;
    private Integer isOpenShop;
    private Integer state;
    private String mainProduct;
    private String advBrandIds;
    private String feature;
    private Integer category;
    private Integer contactFlag;
    private String fastPassage;

    public Long getUid() {
        return uid;
    }

    public Integer getCorpGrade() {
        return corpGrade;
    }

    public Integer getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    public String getEName() {
        return EName;
    }

    public String getDescription() {
        return description;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getTel() {
        return tel;
    }

    public String getFax() {
        return fax;
    }

    public String getEMail() {
        return EMail;
    }

    public Integer getIsEmailOpen() {
        return isEmailOpen;
    }

    public Integer getEMailChecked() {
        return EMailChecked;
    }

    public Timestamp getRegDateOnGov() {
        return regDateOnGov;
    }

    public String getBackroll() {
        return backroll;
    }

    public String getAddress() {
        return address;
    }

    public String getWebStoreUrl() {
        return webStoreUrl;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public Integer getCredit() {
        return credit;
    }

    public Integer getActiveDegrees() {
        return activeDegrees;
    }

    public Integer getHits() {
        return hits;
    }

    public Integer getIsHitsRecord() {
        return isHitsRecord;
    }

    public Timestamp getRegTimeOnZfa() {
        return regTimeOnZfa;
    }

    public Integer getCorpType() {
        return corpType;
    }

    public Integer getCorpMajorcategoryId() {
        return corpMajorcategoryId;
    }

    public Integer getBusinessRoleId() {
        return businessRoleId;
    }

    public String getKeyword() {
        return keyword;
    }

    public Integer getDevelopState() {
        return developState;
    }

    public String getIsAlert() {
        return isAlert;
    }

    public Integer getAdvMemState() {
        return advMemState;
    }

    public Integer getAdvStockState() {
        return advStockState;
    }

    public Integer getAllianceState() {
        return allianceState;
    }

    public Timestamp getLastUpdateTime() {
        return lastUpdateTime;
    }

    public Integer getCorpMajorcategoryId1() {
        return corpMajorcategoryId1;
    }

    public String getKeyword1() {
        return keyword1;
    }

    public Long getCertificatePic() {
        return certificatePic;
    }

    public Integer getIsUpdateCharter() {
        return isUpdateCharter;
    }

    public Integer getCurrcount() {
        return currcount;
    }

    public Integer getCurronsale() {
        return curronsale;
    }

    public Integer getCurronhot() {
        return curronhot;
    }

    public Integer getCurrniccount() {
        return currniccount;
    }

    public Integer getCurrniconsale() {
        return currniconsale;
    }

    public Integer getCurrniconhot() {
        return currniconhot;
    }

    public String getBuyProducts() {
        return buyProducts;
    }

    public Integer getIsOpenShop() {
        return isOpenShop;
    }

    public Integer getState() {
        return state;
    }

    public String getMainProduct() {
        return mainProduct;
    }

    public String getAdvBrandIds() {
        return advBrandIds;
    }

    public String getFeature() {
        return feature;
    }

    public Integer getCategory() {
        return category;
    }

    public Integer getContactFlag() {
        return contactFlag;
    }

    public String getFastPassage() {
        return fastPassage;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public void setCorpGrade(Integer corpGrade) {
        this.corpGrade = corpGrade;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEName(String eName) {
        EName = eName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public void setEMail(String eMail) {
        EMail = eMail;
    }

    public void setIsEmailOpen(Integer isEmailOpen) {
        this.isEmailOpen = isEmailOpen;
    }

    public void setEMailChecked(Integer eMailChecked) {
        EMailChecked = eMailChecked;
    }

    public void setRegDateOnGov(Timestamp regDateOnGov) {
        this.regDateOnGov = regDateOnGov;
    }

    public void setBackroll(String backroll) {
        this.backroll = backroll;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setWebStoreUrl(String webStoreUrl) {
        this.webStoreUrl = webStoreUrl;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public void setActiveDegrees(Integer activeDegrees) {
        this.activeDegrees = activeDegrees;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public void setIsHitsRecord(Integer isHitsRecord) {
        this.isHitsRecord = isHitsRecord;
    }

    public void setRegTimeOnZfa(Timestamp regTimeOnZfa) {
        this.regTimeOnZfa = regTimeOnZfa;
    }

    public void setCorpType(Integer corpType) {
        this.corpType = corpType;
    }

    public void setCorpMajorcategoryId(Integer corpMajorcategoryId) {
        this.corpMajorcategoryId = corpMajorcategoryId;
    }

    public void setBusinessRoleId(Integer businessRoleId) {
        this.businessRoleId = businessRoleId;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setDevelopState(Integer developState) {
        this.developState = developState;
    }

    public void setIsAlert(String isAlert) {
        this.isAlert = isAlert;
    }

    public void setAdvMemState(Integer advMemState) {
        this.advMemState = advMemState;
    }

    public void setAdvStockState(Integer advStockState) {
        this.advStockState = advStockState;
    }

    public void setAllianceState(Integer allianceState) {
        this.allianceState = allianceState;
    }

    public void setLastUpdateTime(Timestamp lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public void setCorpMajorcategoryId1(Integer corpMajorcategoryId1) {
        this.corpMajorcategoryId1 = corpMajorcategoryId1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public void setCertificatePic(Long certificatePic) {
        this.certificatePic = certificatePic;
    }

    public void setIsUpdateCharter(Integer isUpdateCharter) {
        this.isUpdateCharter = isUpdateCharter;
    }

    public void setCurrcount(Integer currcount) {
        this.currcount = currcount;
    }

    public void setCurronsale(Integer curronsale) {
        this.curronsale = curronsale;
    }

    public void setCurronhot(Integer curronhot) {
        this.curronhot = curronhot;
    }

    public void setCurrniccount(Integer currniccount) {
        this.currniccount = currniccount;
    }

    public void setCurrniconsale(Integer currniconsale) {
        this.currniconsale = currniconsale;
    }

    public void setCurrniconhot(Integer currniconhot) {
        this.currniconhot = currniconhot;
    }

    public void setBuyProducts(String buyProducts) {
        this.buyProducts = buyProducts;
    }

    public void setIsOpenShop(Integer isOpenShop) {
        this.isOpenShop = isOpenShop;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setMainProduct(String mainProduct) {
        this.mainProduct = mainProduct;
    }

    public void setAdvBrandIds(String advBrandIds) {
        this.advBrandIds = advBrandIds;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public void setContactFlag(Integer contactFlag) {
        this.contactFlag = contactFlag;
    }

    public void setFastPassage(String fastPassage) {
        this.fastPassage = fastPassage;
    }
}
