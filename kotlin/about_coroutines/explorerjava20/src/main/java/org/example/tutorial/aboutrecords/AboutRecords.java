package org.example.tutorial.aboutrecords;

public class AboutRecords {

  /*
     https://medium.com/experiencecode/usando-records-em-java-9afecf7495b3
  */

  public record User(String name) {}

  private static void check() {
    System.out.println(new User("Chris"));
  }

  public static void main(String[] args) {
    check();
  }
}
