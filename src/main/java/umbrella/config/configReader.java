package umbrella.config;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

public class configReader {
    public static void main(String[] args) throws Exception {
        String filepath = "D:\\CodeProject\\jubilant-umbrella\\src\\main\\resources\\Config.xml";
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new File(filepath));
        Element root = doc.getRootElement();
        configReader configReader = new configReader();
        for (Element classElement : (List<Element>) root.elements()) {
            Class c = Class.forName(classElement.getQualifiedName());
            for (Element fieldElement : (List<Element>) classElement.elements()) {
                configReader.setValueToStaticField(c, c.getField(fieldElement.getQualifiedName()), fieldElement.getStringValue());
            }
        }
    }

    public void initConfig(String ConfigFilePath) throws ClassNotFoundException,
            NoSuchFieldException, IllegalAccessException, DocumentException {
        SAXReader saxReader = new SAXReader();
        Document doc = saxReader.read(new File(ConfigFilePath));
        Element root = doc.getRootElement();
        configReader configReader = new configReader();
        for (Element classElement : (List<Element>) root.elements()) {
            Class c = Class.forName(classElement.getQualifiedName());
            for (Element fieldElement : (List<Element>) classElement.elements()) {
                configReader.setValueToStaticField(c, c.getField(fieldElement.getQualifiedName()), fieldElement.getStringValue());
            }
        }
    }

    private void setValueToStaticField(Class c, Field field, String value) throws IllegalAccessException {
        if (StringUtils.isEmpty(value)) {
            return;
        }
        if (!Modifier.isStatic(field.getModifiers())) {
            return;
        }
        if (field.getType().equals(Void.TYPE) || field.getType().equals(Void.class)) {
            return;
        }
        if (field.getType().equals(String.class)) {
            field.set(c, value);
            return;
        }
        if (field.getType().equals(Boolean.TYPE) || field.getType().equals(Boolean.class)) {
            field.set(c, Boolean.valueOf(value));
            return;
        }
        if (field.getType().equals(Character.TYPE) || field.getType().equals(Character.class)) {
            field.set(c, value.charAt(0));
            return;
        }
        if (field.getType().equals(Byte.TYPE) || field.getType().equals(Byte.class)) {
            field.set(c, Byte.valueOf(value));
            return;
        }
        if (field.getType().equals(Short.TYPE) || field.getType().equals(Short.class)) {
            field.set(c, Short.valueOf(value));
            return;
        }
        if (field.getType().equals(Integer.TYPE) || field.getType().equals(Integer.class)) {
            field.set(c, Integer.valueOf(value));
            return;
        }
        if (field.getType().equals(Long.TYPE) || field.getType().equals(Long.class)) {
            field.set(c, Long.valueOf(value));
            return;
        }
        if (field.getType().equals(Float.TYPE) || field.getType().equals(Float.class)) {
            field.set(c, Float.valueOf(value));
            return;
        }
        if (field.getType().equals(Double.TYPE) || field.getType().equals(Double.class)) {
            field.set(c, Double.valueOf(value));
            return;
        }
    }
}
