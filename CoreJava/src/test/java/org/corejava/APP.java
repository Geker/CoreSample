package org.corejava;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

public class APP {
  public static void main(String[] args) throws IOException {
    // BigDecimal sum = new BigDecimal("0.00");
    // // BigDecimal bd = new BigDecimal(0.01);
    // // System.out.println(bd);
    // double yid = Math.pow(sum.divide(new BigDecimal("10000")).add(new
    // BigDecimal("1")).doubleValue(), 365.0 / 5);
    // BigDecimal yidBigDecimal = new BigDecimal(0.0);
    // System.err.println(yidBigDecimal);
    ArrayList<Integer> l = null;
    for (Integer i : l) {
      System.err.println(i);
    }

    System.out.println(System.getProperty("os.name"));
    System.out.println(System.getProperty("os.version"));
    System.out.println(System.getProperty("os.arch"));
    FileType localFile = new FileType("filename.txt");
    System.out.println(FilenameUtils.getBaseName(localFile.getName()));
  }

  static class FileType {
    String name;

    public FileType(String string) {
      name = string;
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

  }
}
