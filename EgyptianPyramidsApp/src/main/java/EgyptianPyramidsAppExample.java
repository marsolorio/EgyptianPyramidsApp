package com.egyptianExample;

import java.util.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.FileReader;
import java.io.IOException;

public class EgyptianPyramidsAppExample {

  // Arrays to store pharaohs and pyramids
  protected Pharaoh[] pharaohArray;
  protected Pyramid[] pyramidArray;
  protected Set<Integer> requestedPyramids = new HashSet<>();

  public static void main(String[] args) {
    EgyptianPyramidsAppExample app = new EgyptianPyramidsAppExample();
    app.start();
  }

  public EgyptianPyramidsAppExample() {
    // File paths to JSON data
    String pharaohFile = "path/to/pharaoh.json"; // Replace with actual path
    String pyramidFile = "path/to/pyramid.json"; // Replace with actual path

    // Load JSON arrays from files
    JSONArray pharaohJSONArray = readJSONArray(pharaohFile);
    JSONArray pyramidJSONArray = readJSONArray(pyramidFile);

    // Initialize arrays
    initializePharaoh(pharaohJSONArray);
    initializePyramid(pyramidJSONArray);
  }

  public void start() {
    Scanner scan = new Scanner(System.in);
    Character command = '_';

    // Main loop until user enters 'q' to quit
    while (command != 'q') {
      printMenu(); // Display menu
      System.out.print("Enter a command: ");
      command = menuGetCommand(scan);

      // Execute command and exit if 'q' is entered
      if (!executeCommand(scan, command)) {
        break; // Exit loop if executeCommand returns false
      }
    }
    scan.close(); // Close scanner when done
    System.out.println("Thank you for using Nassef's Egyptian Pyramid App!");
  }

  // Read JSON file and return as JSONArray
  private JSONArray readJSONArray(String filePath) {
    try (FileReader reader = new FileReader(filePath)) {
      JSONParser parser = new JSONParser();
      return (JSONArray) parser.parse(reader);
    } catch (Exception e) {
      System.out.println("Error reading JSON file: " + filePath);
      return new JSONArray();
    }
  }

  // Initialize pharaohArray from JSON data
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

  // Initialize pyramidArray from JSON data
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

  // Convert JSON field to Integer
  private Integer toInteger(JSONObject o, String key) {
    return Integer.parseInt(o.get(key).toString());
  }

  // Get first character of user input for menu command
  private static Character menuGetCommand(Scanner scan) {
    String rawInput = scan.nextLine();
    return rawInput.isEmpty() ? '_' : rawInput.toLowerCase().charAt(0);
  }

  // Execute menu command
  private Boolean executeCommand(Scanner scan, Character command) {
    switch (command) {
      case '1':
        printAllPharaohs();
        break;
      case 'q':
        System.out.println("Exiting the application...");
        return false; // Set to false to exit main loop
      default:
        System.out.println("ERROR: Unknown command");
    }
    return true;
  }

  // Print all pharaohs
  private void printAllPharaohs() {
    for (Pharaoh pharaoh : pharaohArray) {
      pharaoh.print();
    }
  }

  // Print menu commands
  private static void printMenu() {
    System.out.println("\n--- Egyptian Pyramids App Menu ---");
    System.out.printf("1\t\tList all pharaohs\n");
    System.out.printf("q\t\tQuit\n");
  }
}

// Pharaoh class to represent individual pharaoh data
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
    System.out.printf("Pharaoh ID: %d\nName: %s\nReign: %d - %d B.C.\nContribution: %d\nHieroglyphic: %s\n",
        id, name, begin, end, contribution, hieroglyphic);
  }
}

// Pyramid class to represent individual pyramid data
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
