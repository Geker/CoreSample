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
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${sql_query}")
    String sqlQuery;
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

        ResultSetWrappingSqlRowSet rs = (ResultSetWrappingSqlRowSet) template.queryForRowSet(sqlQuery,
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
