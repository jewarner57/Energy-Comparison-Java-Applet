//Jonathan Warner 03/12/20
import java.io.*;
import java.util.Scanner;

public class main {
  public static void main(String[] args) throws Exception { 
    
    getFileSequenceNumber("C44No222D2Freq.out");
      
    // get directory location and search target from user
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter file path to target directory. Program will fail if directory is invalid");
    String targetDirectoryPath = scan.nextLine();
    System.out.println("Enter target string. Defaults to: \"E(RB3LYP) =\" with blank response ");
    String targetString = scan.nextLine();

    //Create a list of all files in the directory
    File dir = new File(targetDirectoryPath);
    File[] directoryListing = sortFilesByNumber(dir.listFiles());

    if (directoryListing != null) {
      File[] outputResultList = new File[directoryListing.length];
      int outputResultsCounter = 0;
      for (File child : directoryListing) {
        // Iterate through each file in the directory
        System.out.println(parseFile(child.toString(), targetString));   
        outputResultList[outputResultsCounter] = (child);
        outputResultsCounter++;
      }

      //Create the results file and save it to the directory that the target files are in
      String resultFilePath = targetDirectoryPath + "\\ProgramOutput.txt";
      PrintWriter writer = new PrintWriter(resultFilePath, "UTF-8");
      //Fill the file with the results stored in outputResultList
      for(int i = 0; i < outputResultList.length; i++) {
        writer.println(parseFile(outputResultList[i].toString(), targetString) + "\t" + outputResultList[i].toString());
      }
    
      System.out.println("Results were saved to " + resultFilePath);
      System.out.println("Done");
      writer.close();
    } else {
      // Handle the case where dir is not really a directory.
      System.out.println("Directory does not exist.");
      System.out.println("Please input a valid directory path.");
      System.exit(0);
    }

    
    scan.close();
  } 
  
  private static File[] sortFilesByNumber(File[] directoryListing) {
      for(int i = 0; i < directoryListing.length; i++) {
          for(int j = 0; j < directoryListing[i].length(); j++) {
              if(directoryListing[i].toString().substring(j, j+2).equals("No")) {
                  
              }
          }
      }
      
      return directoryListing;
  }
  
  private static double getFileSequenceNumber(String fileName) {
      //searches for the string "No" then takes the character(s) immediately following "No" and returns it if it is a number
      //otherwise returns 0
      
      double fileSequenceNumber = 0;
      
      for(int i = 0; i < fileName.length(); i++) {
          if(fileName.substring(i, i+2).equals("No")) {
              for(int j = i+2; j < fileName.length(); j++) {
                  try {
                      System.out.println(fileName.substring(i, i+2));
                      System.out.println(fileName.substring(i+2, j+1));
                      fileSequenceNumber = Double.parseDouble(fileName.substring(i+2, j));
                  }
                  catch(NumberFormatException nfe) {
                      System.out.println(fileSequenceNumber);
                      return fileSequenceNumber;
                  }
              }
          }
      }
      
      return fileSequenceNumber;
  }
  
  private static String parseFile(String filePath, String targetString) throws Exception {

    System.out.println(filePath);

    String searchTarget = "";

    // if target input was blank, use default
    if (!targetString.equals("")) {
      searchTarget = targetString;
    } else {
      searchTarget = "E(RB3LYP) =";
    }

    String fileString = "";

    String EValue = "";

    File file = new File(filePath);

    BufferedReader br = new BufferedReader(new FileReader(file));

    String st;
    //add each line of the file into a string
    while ((st = br.readLine()) != null) {
        fileString = fileString + st;
    }
    //find the 16 characters immediately following the search target and return them
    for (int i = 0; i < fileString.length() - searchTarget.length(); i++) {
      if (fileString.substring(i, i + searchTarget.length()).equals(searchTarget)) {
        EValue = fileString.substring(i + searchTarget.length() + 1, i + searchTarget.length() + 16);
      }
    }
    br.close();
    return EValue;
  }
}