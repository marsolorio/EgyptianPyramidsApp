package com.egyptianExample;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class JSONFile {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/pharaoh.json"));
            JSONArray pharaohs = (JSONArray) obj;
            for (Object pharaohObj : pharaohs) {
                JSONObject pharaoh = (JSONObject) pharaohObj;
                System.out.println("Pharaoh: " + pharaoh.get("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
