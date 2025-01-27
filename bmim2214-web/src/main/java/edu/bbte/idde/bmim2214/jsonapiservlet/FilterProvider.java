package edu.bbte.idde.bmim2214.jsonapiservlet;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class FilterProvider {
    private static final Properties properties = new Properties();
    private static final String envVar = System.getenv("profile");
    private static final String fileName = "/application.json";
    private static final Logger log = LoggerFactory.getLogger(FilterProvider.class);

    static {
        log.info(fileName);
        try {
            InputStream inputStream = FilterProvider.class.getResourceAsStream(fileName);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(inputStream);
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                properties.setProperty(entry.getKey(), entry.getValue().asText());
            }
        } catch (IOException e) {
            log.info("Cannot read the file");
        }
    }

    public static String getFilterFlag(String str) {
        return properties.getProperty(str);
    }

}

