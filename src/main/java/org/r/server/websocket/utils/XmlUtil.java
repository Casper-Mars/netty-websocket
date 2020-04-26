package org.r.server.websocket.utils;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * date 20-4-10 下午6:12
 *
 * @author casper
 **/
public class XmlUtil {

    private static final SAXBuilder builder = new SAXBuilder();

    public static Document parseXml(String xml) throws JDOMException, IOException {
        if (StringUtils.isEmpty(xml)) {
            return null;
        }
        Document result;
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        try {
            result = builder.build(inputStream);
        } catch (JDOMException | IOException e) {
            throw e;
        } finally {
            inputStream.close();
        }
        return result;
    }

    public static String encode(String value) {
        if (value == null) {
            return "";
        } else {
            return (value.replaceAll("&", "&amp;").
                    replaceAll("/", "&#47;").
                    replaceAll("<", "&lt;").
                    replaceAll(">", "&gt;").
                    replaceAll("\"", "&quot;").
                    replaceAll("!", "&#33;").
                    replaceAll("\\?", "&#63;").
                    replaceAll("=", "&#61;").
                    replaceAll("%", "&#37;").
                    replaceAll("'", "&apos;"));
        }
    }

}
