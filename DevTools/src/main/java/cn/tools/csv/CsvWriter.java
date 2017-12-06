package cn.tools.csv;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

public class CsvWriter {
    public static void writeWithCsvWriter(String filename, List<?> objs) throws Exception {
        if (objs == null || objs.size() == 0)
            return;
        File file = new File(filename);
        file.getParentFile().mkdirs();
        boolean first = !file.exists();
        FileWriter fileWriter = new FileWriter(file, true);

        CsvBeanWriter beanWriter =
                new CsvBeanWriter(fileWriter, CsvPreference.STANDARD_PREFERENCE);
        try {

            final CellProcessor[] processors = getProcessors(objs.get(0));
            Object _obj = objs.get(0);
            Field[] fields = _obj.getClass().getDeclaredFields();

            Function<Field, String> getFiledName = new Function<Field, String>() {
                @Override
                public String apply(Field f) {
                    return f.getName();
                }
            };

            List<String> headers = Lists.transform(Arrays.asList(fields), getFiledName);
            final String[] header = headers.toArray(new String[0]);
            if (first)
                beanWriter.writeHeader(header);
            for (Object obj : objs) {
                beanWriter.write(obj, header, processors);

            }

        }
        finally {
            if (beanWriter != null) {
                beanWriter.close();
            }
        }
    }

    /**
     * Sets up the processors used for the examples. There are 10 CSV columns, so 10 processors are defined. All values
     * are converted to Strings before writing (there's no need to convert them), and null values will be written as
     * empty columns (no need to convert them to "").
     *
     * @return the cell processors
     */
    private static CellProcessor[] getProcessors(Object obj) {
        int i = obj.getClass().getDeclaredFields().length;
        final CellProcessor[] processors = new CellProcessor[i];
        Arrays.fill(processors, null);
        return processors;
    }
}
