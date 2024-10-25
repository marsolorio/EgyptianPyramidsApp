package com.egyptianExample;

import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.IOException;

public class EgyptianPyramidsAppExample {

  protected Pharaoh[] pharaohArray;
  protected Pyramid[] pyramidArray;
  protected Set<Integer> requestedPyramids = new HashSet<>(); // To track unique pyramid requests

  public static void main(String[] args) {
    EgyptianPyramidsAppExample app = new EgyptianPyramidsAppExample();
    app.start();
  }

  public EgyptianPyramidsAppExample() {
    String pharaohFile = "path/to/pharaoh.json"; // Update with actual file path
    String pyramidFile = "path/to/pyramid.json"; // Update with actual file path

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
    System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
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
        System.out.println("Exiting the application...");
        return false;
      default:
        System.out.println("ERROR: Unknown command");
    }
    return true;
  }

  // List all pharaohs
  private void printAllPharaohs() {
    System.out.println("\n--- List of All Pharaohs ---");
    for (Pharaoh pharaoh : pharaohArray) {
      pharaoh.print();
      System.out.println(); // Blank line between each pharaoh for readability
    }
  }

  // Display information for a specific pharaoh
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

  // Command 3: List all pyramids and contributors
  private void printAllPyramids() {
    System.out.println("\n--- List of All Pyramids and Their Contributors ---");
    for (Pyramid pyramid : pyramidArray) {
      pyramid.print();
      System.out.println(); // Blank line between each pyramid for readability
    }
  }

  // Command 4: Display information for a specific pyramid
  private void displaySpecificPyramid(Scanner scan) {
    System.out.print("Enter Pyramid ID: ");
    int id = Integer.parseInt(scan.nextLine().trim());
    requestedPyramids.add(id); // Add to unique requests set

    for (Pyramid pyramid : pyramidArray) {
      if (pyramid.getId() == id) {
        pyramid.print();
        return;
      }
    }
    System.out.println("ERROR: Pyramid not found");
  }

  // Command 5: Report unique requested pyramids
  private void reportUniquePyramids() {
    System.out.println("\n--- Unique Requested Pyramids ---");
    for (Integer id : requestedPyramids) {
      System.out.println("Requested Pyramid ID: " + id);
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

// Pharaoh class
class Pharaoh {
  private Integer id;
  private String name;
  private Integer begin;
  private Integer end;
  private Integer contribution;
  private String hieroglyphic;

  public Pharaoh(Integer id, String name, Integer begin, Integer end, Integer contribution, String hieroglyphic) {
    this.id = id;
    this.name = name;
    this.begin = begin;
    this.end = end;
    this.contribution = contribution;
    this.hieroglyphic = hieroglyphic;
  }

  public Integer getId() {
    return id;
  }

  public void print() {
    System.out.printf("Pharaoh ID: %d\nName: %s\nReign: %d - %d B.C.\nContribution: %d gold\nHieroglyphic: %s\n",
        id, name, begin, end, contribution, hieroglyphic);
  }
}

// Pyramid class
class Pyramid {
  private Integer id;
  private String name;
  private String[] contributors;

  public Pyramid(Integer id, String name, String[] contributors) {
    this.id = id;
    this.name = name;
    this.contributors = contributors;
  }

  public Integer getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String[] getContributors() {
    return contributors;
  }

  public void print() {
    System.out.println("Pyramid ID: " + id);
    System.out.println("Name: " + name);
    System.out.println("Contributors:");
    for (String contributor : contributors) {
      System.out.println(" - " + contributor);
    }
  }
}
