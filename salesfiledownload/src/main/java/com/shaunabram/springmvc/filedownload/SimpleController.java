package com.shaunabram.springmvc.filedownload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BasicDynaBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.RowSetDynaClass;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sargeraswang.util.ExcelUtil.ExcelUtil;

@Controller
public class SimpleController {
    @Autowired
    JdbcTemplate template;

    @RequestMapping(value = "/")
    public String home(Model model) {
        System.out.println("SimpleController: Passing through...");
        // without view resolver
        // return "WEB-INF/views/index.jsp";

        model.addAttribute("formBean", new FormBean());

        return "index";

    }

    @RequestMapping(value = "/Excel", method = RequestMethod.GET)
    public void handleFileDownload(HttpServletRequest req, HttpServletResponse res) {
        try {
            String fn = "/Test.xls";
            URL url = getClass().getResource(fn);
            File f = new File(url.toURI());
            System.out.println("Loading file " + fn + "(" + f.getAbsolutePath() + ")");
            if (f.exists()) {
                res.setContentType("application/xls");
                res.setContentLength(new Long(f.length()).intValue());
                res.setHeader("Content-Disposition", "attachment; filename=Test.xls");
                FileCopyUtils.copy(new FileInputStream(f), res.getOutputStream());
            }
            else {
                System.out.println("File" + fn + "(" + f.getAbsolutePath() + ") does not exist");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @RequestMapping(value = "/down", method = { RequestMethod.GET, RequestMethod.POST })
    public String downFileDownload(HttpServletRequest req, HttpServletResponse res, Model model) throws IOException {
        try {
            model.addAttribute("formBean", new FormBean());
            Map reqs = req.getParameterMap();
            Object[] sDt = (Object[]) reqs.get("startDate");
            Object[] eDt = (Object[]) reqs.get("endDate");
            String startDt = null, endDt = null;
            if (sDt != null && sDt.length > 0)
                startDt = (String) sDt[0];

            if (eDt != null && eDt.length > 0)
                endDt = (String) eDt[0];
            File f = genFile(startDt, endDt);
            // URL url = getClass().getResource(fn);
            // File f = new File(url.toURI());
            System.out.println("Loading file " + f.getCanonicalFile() + "(" + f.getAbsolutePath() + ")");
            if (f.exists()) {
                res.setContentType("application/xls");
                res.setContentLength(new Long(f.length()).intValue());
                res.setHeader("Content-Disposition", "attachment; filename=" + f.getName());
                // res.setCharacterEncoding("UTF-8");
                // PrintWriter writer = res.getWriter();
                // writer.flush();

                ServletOutputStream out = res.getOutputStream();
                // out.flush();
                // res.reset();
                // res.resetBuffer();
                try (FileInputStream fr = new FileInputStream(f)) {
                    FileCopyUtils.copy(fr, res.getOutputStream());

                }
                catch (Exception e) {
                    System.out.println("文件打开失败");
                }
                out.flush();
                out.close();

            }
            else {
                System.out.println("File" + f.getCanonicalPath() + "(" + f.getAbsolutePath() + ") does not exist");
            }

        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
        finally {

        }
        return null;
    }

    File genFile(String startDt, String endDt) throws SQLException, FileNotFoundException, IllegalAccessException {
        DateTime sdt = new DateTime().minusDays(2);
        Date sDt = sdt.toDate();
        Date eDt = new Date();
        try {
            sDt = new SimpleDateFormat("yyyy-MM-dd").parse(startDt);
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());
        }
        try {
            eDt = new SimpleDateFormat("yyyy-MM-dd").parse(endDt);
        }
        catch (ParseException e) {
            System.out.println(e.getMessage());

        }
        eDt = LocalDateTime.fromDateFields(eDt).plusDays(1).minusMillis(1).toDate();
        template.execute("drop table discount;");

        template.execute("CREATE    TABLE discount AS (\n" + "        SELECT\n"
                + "            SUM(discount_amount) discount_amount ,SUM(goods_amount) goods_amount ,SUM(\"YH_discount_amount\") yh_discount,goods_code,order_no\n"
                + "        FROM\n" + "            V_EC_SALES_GOODS_DISCOUNT_DETAIL   \n" + "        GROUP BY\n"
                + "            goods_code,order_no );");

        ResultSetWrappingSqlRowSet rs = (ResultSetWrappingSqlRowSet) template.queryForRowSet("SELECT\n"
                + "    FORMATDATETIME(ord.order_time,'yyyy-MM-dd HH:mm') AS 订单日期,FORMATDATETIME(ord.date_updated,'yyyy-MM-dd HH:mm')\n"
                + "    AS 订单完成时间,ord.order_no AS 订单号,\n" + "    CASE ord.order_status  \n" + "    WHEN 'SUCCESS'  THEN  '成功'\n"
                + "    WHEN 'CANCEL'  THEN  '已取消(未/已付款)'\n" + "    WHEN 'PAYED'  THEN  '已付款'\n" + "    WHEN 'WAIT_RECEIPT'  THEN  '待收货'\n"
                + "    WHEN 'REFUNDING'  THEN  '退款中'\n" + "    WHEN 'RECEIVED' THEN '待确认退货'\n" + "    else  ord.order_status  \n"
                + "    END\n" + "    AS 订单状态,\n" + "    ord.relation_order_no  AS 退货关联的原始订单号,ord.bill_id  AS erp单号,\n"
                + "    goods.goods_code AS  商品代码, goods.goods_num  AS  商品数量,\n" + "    \n" + "    ord.amount  AS 订单支付金额,\n"
                + "    casewhen(ORD.order_op_type='SCAN','扫码购',CASEWHEN(ord.delivery_way='2','自提','配送'))\n"
                + "    AS 订单类型, ord.ORDER_REMARK AS 备注,  ord.consignee_name AS  收货人,ord.consignee_address AS  收货地址, ord.CONSIGNEE_PHONE  AS  电话,\n"
                + "    DELIVERY_TIME_START AS  配送开始时间,DELIVERY_TIME_END AS  配送截止时间,\n" + "    CASE\n"
                + "        WHEN goods.goods_code IN('1571','1570','1572')\n" + "        THEN '预售商品'\n" + "        ELSE '普通商品'\n"
                + "    END  AS 商品类型,IFNULL(goods.goods_price,goods.preferential_price) AS 商品价格, \n"
                + "    dis.discount_amount AS  折扣金额,dis.goods_amount AS  商品折后总价格,dis.yh_discount AS  \"优惠金额分摊[优惠券]\" , goods.goods_bar_code  AS 条码,goods.goods_name AS  商品名称,\n"
                + "    goods.goods_index  AS 订单内商品序号\n" + "FROM\n"
                + "    v_ec_sales_order_detail ord,v_ec_sales_goods_detail goods,discount dis\n" + "WHERE\n"
                + "ord.order_no=goods.order_no\n" + "--AND     ord.order_status  in ('SUCCESS','PAYED')\n"
                + "AND     goods.goods_level_type!='3'"

                + "and ord.order_time>=?  and ord.order_time<? \n" + "and\n"
                + "dis.order_no=ord.order_no and dis.goods_code=goods.goods_code\n" + "--and goods.goods_code in ('1571','1570','1572')\n"
                + "ORDER BY 订单日期,订单号,订单内商品序号 ;",
            sDt, eDt);

        Map map = (Map) FieldUtils.readField(rs, "columnLabelMap", true);
        Map newmap = org.apache.commons.collections.MapUtils.invertMap(map);
        System.out.println(rs.getClass().getSimpleName());
        RowSetDynaClass dyn = new RowSetDynaClass(rs.getResultSet(), false, -1, false);

        DynaProperty[] pros = dyn.getDynaProperties();
        System.out.println(Arrays.asList(pros).toString());
        Map<String, String> header = new LinkedHashMap<>();
        int i = 1;
        for (DynaProperty pro : pros) {

            header.put(pro.getName(), (String) newmap.get(i++));
            // header.put(pro.getName(), pro.getName());
        }

        Collection<DynaBean> DynaBeans = dyn.getRows();
        Iterator<DynaBean> itor = DynaBeans.iterator();
        Collection<Map<String, Object>> datas = new ArrayList<>();
        while (itor.hasNext()) {
            BasicDynaBean item = (BasicDynaBean) itor.next();
            datas.add(item.getMap());
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String fpath = "/tmp/sales_";
        if (SystemUtils.IS_OS_WINDOWS)
            fpath = "sales_";
        File f = new File(fpath + sdf.format(sDt) + "_" + sdf.format(eDt) + ".xls");

        try (FileOutputStream fs = new FileOutputStream(f)) {
            ExcelUtil.exportExcel(header, datas, fs, "yyyy-MM-dd HH:mm");
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return f;

    }

}
