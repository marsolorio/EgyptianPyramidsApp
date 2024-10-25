package com.egyptianExample;

import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;

public class EgyptianPyramidsAppExample {

  protected Pharaoh[] pharaohArray;
  protected Pyramid[] pyramidArray;
  protected Set<Integer> requestedPyramids = new HashSet<>();

  public static void main(String[] args) {
    EgyptianPyramidsAppExample app = new EgyptianPyramidsAppExample();
    app.start();
  }

  public EgyptianPyramidsAppExample() {
    String pharaohFile = "src/main/resources/pharaoh.json";
    String pyramidFile = "src/main/resources/pyramid.json";

    JSONArray pharaohJSONArray = readJSONArray(pharaohFile);
    JSONArray pyramidJSONArray = readJSONArray(pyramidFile);

    initializePharaoh(pharaohJSONArray);
    initializePyramid(pyramidJSONArray);
  }

  public void start() {
    Scanner scan = new Scanner(System.in);
    Character command = '_';

    while (command != 'q') {
      printMenu();
      System.out.print("Enter a command: ");
      command = menuGetCommand(scan);

      if (!executeCommand(scan, command)) {
        break;
      }
    }
    scan.close();
    System.out.println("Thank you for using Egyptian Pyramid App!");
  }

  private JSONArray readJSONArray(String filePath) {
    try (FileReader reader = new FileReader(filePath)) {
      JSONParser parser = new JSONParser();
      return (JSONArray) parser.parse(reader);
    } catch (Exception e) {
      System.out.println("Error reading JSON file: " + filePath);
      return new JSONArray();
    }
  }

  private void initializePharaoh(JSONArray pharaohJSONArray) {
    pharaohArray = new Pharaoh[pharaohJSONArray.size()];

    for (int i = 0; i < pharaohJSONArray.size(); i++) {
      JSONObject o = (JSONObject) pharaohJSONArray.get(i);
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      Integer begin = toInteger(o, "begin");
      Integer end = toInteger(o, "end");
      Integer contribution = toInteger(o, "contribution");
      String hieroglyphic = o.get("hieroglyphic").toString();
      pharaohArray[i] = new Pharaoh(id, name, begin, end, contribution, hieroglyphic);
    }
  }

  private void initializePyramid(JSONArray pyramidJSONArray) {
    pyramidArray = new Pyramid[pyramidJSONArray.size()];

    for (int i = 0; i < pyramidJSONArray.size(); i++) {
      JSONObject o = (JSONObject) pyramidJSONArray.get(i);
      Integer id = toInteger(o, "id");
      String name = o.get("name").toString();
      JSONArray contributorsJSONArray = (JSONArray) o.get("contributors");
      String[] contributors = new String[contributorsJSONArray.size()];

      for (int j = 0; j < contributorsJSONArray.size(); j++) {
        contributors[j] = contributorsJSONArray.get(j).toString();
      }
      pyramidArray[i] = new Pyramid(id, name, contributors);
    }
  }

  private Integer toInteger(JSONObject o, String key) {
    return Integer.parseInt(o.get(key).toString());
  }

  private static Character menuGetCommand(Scanner scan) {
    String rawInput = scan.nextLine();
    return rawInput.isEmpty() ? '_' : rawInput.toLowerCase().charAt(0);
  }

  private Boolean executeCommand(Scanner scan, Character command) {
    switch (command) {
      case '1':
        printAllPharaohs();
        break;
      case '2':
        displaySpecificPharaoh(scan);
        break;
      case '3':
        printAllPyramids();
        break;
      case '4':
        displaySpecificPyramid(scan);
        break;
      case '5':
        reportUniquePyramids();
        break;
      case 'q':
        return false;
      default:
        System.out.println("ERROR: Unknown command");
    }
    return true;
  }

  private void printAllPharaohs() {
    for (Pharaoh pharaoh : pharaohArray) {
      pharaoh.print();
    }
  }

  private void displaySpecificPharaoh(Scanner scan) {
    System.out.print("Enter Pharaoh ID: ");
    int id = Integer.parseInt(scan.nextLine().trim());
    for (Pharaoh pharaoh : pharaohArray) {
      if (pharaoh.getId() == id) {
        pharaoh.print();
        return;
      }
    }
    System.out.println("Pharaoh not found");
  }

  private void printAllPyramids() {
    for (Pyramid pyramid : pyramidArray) {
      pyramid.print();
    }
  }

  private void displaySpecificPyramid(Scanner scan) {
    System.out.print("Enter Pyramid ID: ");
    int id = Integer.parseInt(scan.nextLine().trim());
    requestedPyramids.add(id);
    for (Pyramid pyramid : pyramidArray) {
      if (pyramid.getId() == id) {
        pyramid.print();
        return;
      }
    }
    System.out.println("Pyramid not found");
  }

  private void reportUniquePyramids() {
    System.out.println("Unique requested pyramid IDs:");
    for (Integer id : requestedPyramids) {
      System.out.println(" - " + id);
    }
  }

  private static void printMenu() {
    System.out.println("\n--- Egyptian Pyramids App Menu ---");
    System.out.printf("1\t\tList all pharaohs\n");
    System.out.printf("2\t\tDisplay specific pharaoh information\n");
    System.out.printf("3\t\tList all pyramids and contributors\n");
    System.out.printf("4\t\tDisplay specific pyramid information\n");
    System.out.printf("5\t\tReport requested pyramids without duplicates\n");
    System.out.printf("q\t\tQuit\n");
  }
}
