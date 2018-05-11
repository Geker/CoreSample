package org.corejava.bean.utils.copytools;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.corejava.bean.utils.CorePart;
import org.corejava.bean.utils.CorePartAnother;
import org.springframework.beans.FatalBeanException;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.trimou.Mustache;
import org.trimou.engine.MustacheEngineBuilder;

import com.alibaba.fastjson.JSON;

public class PropertiesCopyUtils {
    static final String template = "{{genPackage}};\r\n" + "\r\n" + "{{#packs}}\r\n" + "import {{.}};\r\n" + "{{/packs}}\r\n" + "\r\n"
            + "public class  {{CopyClassName}}{\r\n" + "\r\n" + "    public  void copyProperties({{source}} src,{{target}} target) {\r\n"
            + "    {{#copyDatas.props}}\r\n" + "         target.set{{.}}(src.get{{.}}());\r\n" + "    {{/copyDatas.props}}\r\n" + "\r\n"
            + "        /***the source do not have the value**/\r\n" + "    {{#copyDatas.commentProps}}\r\n"
            + "        // target.set{{.}}(src.get{{.}}());\r\n" + "    {{/copyDatas.commentProps}}\r\n" + "\r\n" + "\r\n"
            + "        /***the target do not have the field**/\r\n" + "\r\n" + "    {{#copyDatas.notExistsProps}}\r\n"
            + "        // target.set{{.}}(src.get{{.}}());\r\n" + "    {{/copyDatas.notExistsProps}}\r\n" + "    }\r\n" + "\r\n" + "}\r\n"
            + "";

    public static void main(String[] args) {
        Class<?> source = CorePart.class;
        Class<?> target = CorePartAnother.class;
        genCopySrc(source, target);
    }

    private static void genCopySrc(Class<?> source, Class<?> target) {
        String genPackage = PropertiesCopyUtils.class.getPackage().toString();
        // MustacheEngine engine =
        // MustacheEngineBuilder.newBuilder()
        // .addTemplateLocator(new FileSystemTemplateLocator(1,
        // "D:\\dev source\\git\\个人项目\\personJava\\CoreJava\\src\\main\\java\\org\\corejava\\bean\\utils\\copytools", "txt"))
        // .build();
        Mustache mustache = MustacheEngineBuilder.newBuilder().build().compileMustache(template);
        String[] packages =

                new String[] { source.getTypeName(), target.getTypeName() };

        Map<String, Object> data = new HashMap<>();
        data.put("genPackage", genPackage);
        data.put("packs", packages);
        data.put("CopyClassName", "Copy" + source.getSimpleName() + "2" + target.getSimpleName());
        data.put("source", source.getSimpleName());
        data.put("target", target.getSimpleName());

        Map<String, String[]> copyDatas = genSetter(source, target);
        data.put("copyDatas", copyDatas);
        System.err.println(JSON.toJSON(copyDatas));

        // String output = engine.getMustache("copytmp").render(data);
        FileWriter appendable = null;
        PropertiesCopyUtils.class.getPackage();
        try {
            String fileName= "src/main/java/"+PropertiesCopyUtils.class.getPackage().getName().replace('.', File.separatorChar)+File.separatorChar
                    + data.get("CopyClassName") + ".java";
            appendable = new FileWriter(fileName
               );
        }
        catch (IOException e) {
             e.printStackTrace();
        }
        mustache.render(appendable, data);
        try {
            appendable.close();
        }
        catch (IOException e) {
             e.printStackTrace();
        }
    }

    private static Map<String, String[]> genSetter(Class<?> source, Class<?> target) {
        Map<String, String[]> datas = new HashMap<>();
        ArrayList<String> props = new ArrayList<>();
        ArrayList<String> commentProps = new ArrayList<>();
        ArrayList<String> notExistsProps = new ArrayList<>();

        PropertyDescriptor[] targetPds = getPropertyDescriptors(target);
        PropertyDescriptor[] sourcesPds = getPropertyDescriptors(source);

        for (PropertyDescriptor targetPd : targetPds) {
            if (targetPd.getName().equals("class"))
                continue;
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null) {
                PropertyDescriptor sourcePd = getPropertyDescriptor(source, targetPd.getName());
                if (sourcePd != null) {
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }

                            props.add(StringUtils.capitalize(targetPd.getName()));
                            continue;
                            // writeMethod.invoke(target, value);
                        }
                        catch (Throwable ex) {
                            throw new FatalBeanException("Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }

                }

            }
            commentProps.add(StringUtils.capitalize(targetPd.getName()));

        }

        for (PropertyDescriptor pd : sourcesPds) {
            if (pd.getName().equals("class"))
                continue;
            Method readMethod = pd.getReadMethod();

            if (readMethod != null) {

                PropertyDescriptor targetPd = getPropertyDescriptor(target, pd.getName());
                if (targetPd != null) {
                    Method writeMethod = targetPd.getWriteMethod();
                    if (writeMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {

                        continue;
                    }
                }
                notExistsProps.add(StringUtils.capitalize(pd.getName()));
            }

        }
        datas.put("props", props.toArray(new String[0]));
        datas.put("commentProps", commentProps.toArray(new String[0]));
        datas.put("notExistsProps", notExistsProps.toArray(new String[0]));
        return datas;

    }

    private static PropertyDescriptor getPropertyDescriptor(Class<?> tarType, String name) {
        PropertyDescriptor[] pds = null;

        try {
            pds = Introspector.getBeanInfo(tarType, Introspector.IGNORE_ALL_BEANINFO).getPropertyDescriptors();
        }
        catch (IntrospectionException e) {

            e.printStackTrace();
        }
        for (PropertyDescriptor pd : pds) {
            if (pd.getName().equals(name))
                return pd;
        }
        return null;
    }

    private static PropertyDescriptor[] getPropertyDescriptors(Class<?> target) {
        PropertyDescriptor[] pds = null;

        try {
            pds = Introspector.getBeanInfo(target, Introspector.IGNORE_ALL_BEANINFO).getPropertyDescriptors();
        }
        catch (IntrospectionException e) {

            e.printStackTrace();
        }
        return pds;

    }

}
