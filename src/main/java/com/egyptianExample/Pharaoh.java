package com.egyptianExample;

public class Pharaoh {
  protected Integer id;
  protected String name;
  protected Integer begin;
  protected Integer end;
  protected Integer contribution;
  protected String hieroglyphic;

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
    System.out.printf("Pharaoh %s\n", name);
    System.out.printf("\tid: %d\n", id);
    System.out.printf("\tbegin: %d B.C.\n", begin);
    System.out.printf("\tend: %d B.C.\n", end);
    System.out.printf("\tcontribution: %d gold coins\n", contribution);
    System.out.printf("\thieroglyphic: %s\n", hieroglyphic);
  }
}
