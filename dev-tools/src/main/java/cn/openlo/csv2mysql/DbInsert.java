package cn.openlo.csv2mysql;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

public class DbInsert {
    public static <T> void insertTable(Class<T> tc, List<T> list)
            throws SQLException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        QueryRunner qr = new QueryRunner(MysqlConnectionFactory.INSTANCE.getDataSource());
        String col_detail[];
        String sql;

        switch (tc.getSimpleName()) {
        case "TaskExecutionDetail": {
            col_detail = new String[] { "runkey", "divide", "divide_value", "step", "step_value", "step_finishMsg", "job", "job_value",
                                        "divide_status",
                                        "step_status", "job_status", "crttime" };
            sql =

                    "INSERT INTO taskexecutiondetail (" + StringUtils.join(col_detail, ",") + ") VALUES (?, ?, ?, ?, ?, ?,?, ?, ?, ?, ?,?)";

            break;
        }
        case "TaskExecutionInfo": {

            col_detail = new String[] { "systemname", "transdate", "taskname", "runkey", "runkey_value", "status", "crttime" };
            sql = "INSERT INTO taskexecutioninfo (" + StringUtils.join(col_detail, ",") + ") VALUES (?, ?, ?, ?, ?, ?,?)";
            break;
        }
        case "ServerInfo": {
            col_detail = new String[] { "transdate", "taskname", "runkey", "status", "subpath", "server", "crttime" };
            sql = "INSERT INTO serverinfo (" + StringUtils.join(col_detail, ",") + ") VALUES (?, ?, ?, ?, ?, ?,?)";
            break;
        }
        default: {
            throw new IllegalArgumentException(tc.getClass().getSimpleName() + "无法识别!");
        }
        }
        // long bg = System.nanoTime();
        List<List<T>> llist = Lists.partition(list, 200);// 200条一次提交commit
        for (List<T> list_item : llist) {
            int rows = list_item.size();
            Object[][] mutiParams = new Object[rows][];

            for (int j = 0; j < list_item.size(); j++) {
                T t = list_item.get(j);
                Map<?, ?> map = BeanUtils.describe(t);
                Object[] params = new Object[col_detail.length];

                for (int i = 0; i < params.length - 1; i++) {
                    String str = (String) map.get(col_detail[i]);
                    switch (col_detail[i]) {
                    case "divide_value":
                        str = StringUtils.substring(str, 0, 399);
                        break;
                    case "step_value":
                    case "job_value":
                        str = StringUtils.substring(str, 0, 1999);
                        break;
                    case "step_finishMsg":
                        str = StringUtils.substring(str, 0, 2999);
                        break;
                    case "runkey_value":
                        str = StringUtils.substring(str, 0, 500);
                        break;
                    }
                    params[i] = str;
                }
                java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
                params[params.length - 1] = sqlDate;

                mutiParams[j] = params;

            }
            qr.batch(sql, mutiParams);
        }
        // System.out.println(System.nanoTime() - bg);
    }
}
