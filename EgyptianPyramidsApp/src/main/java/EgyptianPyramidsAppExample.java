package com.egyptianExample;

import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

public class EgyptianPyramidsAppExample {

  protected Pharaoh[] pharaohArray;
  protected Pyramid[] pyramidArray;
  protected Set<Integer> requestedPyramids = new HashSet<>();

  public static void main(String[] args) {
    EgyptianPyramidsAppExample app = new EgyptianPyramidsAppExample();
    app.start();
  }

  public EgyptianPyramidsAppExample() {
    String pharaohFile = "path/to/pharaoh.json";
    String pyramidFile = "path/to/pyramid.json";

    JSONArray pharaohJSONArray = JSONFile.readArray(pharaohFile);
    JSONArray pyramidJSONArray = JSONFile.readArray(pyramidFile);

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
      executeCommand(scan, command);
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

      Pharaoh p = new Pharaoh(id, name, begin, end, contribution, hieroglyphic);
      pharaohArray[i] = p;
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

      Pyramid p = new Pyramid(id, name, contributors);
      pyramidArray[i] = p;
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
        printAllPharaoh();
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
        System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
        return false;
      default:
        System.out.println("ERROR: Unknown command");
    }
    return true;
  }

  private void printAllPharaoh() {
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
    System.out.println("ERROR: Pharaoh not found");
  }

  private void printAllPyramids() {
    for (Pyramid pyramid : pyramidArray) {
      System.out.println("Pyramid: " + pyramid.getName());
      for (String contributor : pyramid.getContributors()) {
        System.out.println(" - Contributor: " + contributor);
      }
    }
  }

  private void displaySpecificPyramid(Scanner scan) {
    System.out.print("Enter Pyramid ID: ");
    int id = Integer.parseInt(scan.nextLine().trim());
    requestedPyramids.add(id);

    for (Pyramid pyramid : pyramidArray) {
      if (pyramid.getId() == id) {
        System.out.println("Pyramid: " + pyramid.getName());
        for (String contributor : pyramid.getContributors()) {
          System.out.println(" - Contributor: " + contributor);
        }
        return;
      }
    }
    System.out.println("ERROR: Pyramid not found");
  }

  private void reportUniquePyramids() {
    System.out.println("Unique Pyramids Requested:");
    for (Integer id : requestedPyramids) {
      System.out.println(" - Pyramid ID: " + id);
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
