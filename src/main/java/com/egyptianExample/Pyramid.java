package com.egyptianExample;

public class Pyramid {
  protected Integer id;
  protected String name;
  protected String[] contributors;

  public Pyramid(Integer id, String name, String[] contributors) {
    this.id = id;
    this.name = name;
    this.contributors = contributors;
  }

  public Integer getId() {
    return id;
  }

  public void print() {
    System.out.println("Pyramid Name: " + name);
    System.out.println("\tID: " + id);
    System.out.println("\tContributors:");
    for (String contributor : contributors) {
      System.out.println("\t - " + contributor);
    }
  }
}
