package cn.openlo.csv2mysql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import cn.openlo.batchCheck.ServerInfo;
import cn.openlo.batchCheck.TaskExecutionDetail;
import cn.openlo.batchCheck.TaskExecutionInfo;

public class batchCsvImport {
    public static void main(String[] args) throws ZipException, IOException {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("batch.conf"));
        }
        catch (IOException e) {
            System.err.println("加载配置文件batch.conf 失败 " + e);
        }
        String expPath = (String) properties.get("expPath");
        String bakPath = (expPath != null ? expPath : "./bak") + File.separator;
        File bakpathFile = new File(bakPath);
        FileFilter ffilter = DirectoryFileFilter.INSTANCE;
        File[] files = bakpathFile.listFiles(ffilter);
        String url = (String) properties.get("dburl");
        String user = (String) properties.get("dbuser");
        String passwd = (String) properties.get("dbpassword");
        if (StringUtils.isEmpty(passwd)) {
            passwd = (String) properties.get("dbpasswordEncrtpty");
        }
        MysqlDataSource mysqlDataSource = MysqlConnectionFactory.INSTANCE.getDataSource();

        mysqlDataSource.setUrl(url);
        mysqlDataSource.setUser(user);
        mysqlDataSource.setPassword(passwd);
        Function<Field, String> getFiledName = new Function<Field, String>() {
            @Override
            public String apply(Field f) {
                return f.getName();
            }
        };

        for (File file : files) {
            File[] subfiles = file.listFiles(new FilenameFilter() {

                @Override
                public boolean accept(File dir, String name) {

                    return name.contains("imported");
                }
            });
            if (subfiles == null || subfiles.length > 0) {
                System.out.println(file.getCanonicalPath() + " is empty or imported! skip.");
                continue;
            }
            FileFilter ft = FileFileFilter.FILE;
            File[] csvFiles = file.listFiles(ft);
            for (File csv : csvFiles) {
                @SuppressWarnings("resource")
                ZipFile zf = new ZipFile(csv);
                System.out.println("start import:" + csv.getCanonicalFile());
                Enumeration<?> ZipEntryS = zf.entries();
                while (ZipEntryS.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) ZipEntryS.nextElement();
                    BufferedReader br = new BufferedReader(new InputStreamReader(zf.getInputStream(entry)));
                    CsvBeanReader csvBeanReader = new CsvBeanReader(br, CsvPreference.STANDARD_PREFERENCE);
                    try {
                        if (entry.getName().contains("ServerInfo")) {

                            dealFiles(getFiledName, csvBeanReader, ServerInfo.class);
                        }
                        else if (entry.getName().contains("TaskExecutionInfo")) {
                            dealFiles(getFiledName, csvBeanReader, TaskExecutionInfo.class);

                        }
                        else if (entry.getName().contains("TaskExecutionDetail")) {
                            dealFiles(getFiledName, csvBeanReader, TaskExecutionDetail.class);
                        }
                    }
                    finally {
                        csvBeanReader.close();
                    }

                }
                System.out.println("import finished!:" + csv.getName());

            }
            
            File importFile = new File(file.getAbsolutePath(), "imported");
            importFile.createNewFile();


        }

    }

    private static <T> void dealFiles(Function<Field, String> getFiledName, CsvBeanReader csvBeanReader, Class<T> tc) throws IOException {
        List<T> list = new ArrayList<>();
        List<String> headers = Lists.transform(Arrays.asList(tc.getDeclaredFields()), getFiledName);
        String[] header = csvBeanReader.getHeader(true);
        if (header == null) {
            System.out.println("reading header meet EOF!");
            return;
        }
        else {
            System.out.println("Header is skiped!");
        }
        T row = null;
        do {
            row = csvBeanReader.read(tc, headers.toArray(new String[0]));

            if (row != null) {
                list.add(row);
            }
        } while (row != null);

        try {
            DbInsert.insertTable(tc, list);
        }
        catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("数据库插入失败!");
        }



    }
}
