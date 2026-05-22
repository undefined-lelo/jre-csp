// TODO: move stuff to other files

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.LinkedHashSet;

public class Main {
  // ansi colors for console outputs
  public static final String ansi_red = "\u001B[31m";
  public static final String ansi_green  = "\u001B[32m";
  public static final String ansi_yellow  = "\u001B[33m";
  public static final String ansi_reset = "\u001B[0m";

  static Scanner input = new Scanner(System.in);

  public static void main(String[] args) {
    try {
      // init stuff
      int score = 0;
      int guessCount = 0;
      Random rand = new Random();

      // import words text
      File inputFile = new File("words.txt");
      try (Scanner in = new Scanner(inputFile)) {
        // word array | add words to array
        ArrayList < String > words = new ArrayList < > ();
        while (in.hasNextLine()) {
          String line = in.nextLine();
          words.add(line);
        }

        int size = words.size();
        if (size == 0) {
          System.out.println("No words found in the file.");
          return;
        }

        int index = rand.nextInt(words.size());
        String randomWord = words.get(index);
        System.out.println("What's your first guess?");
        String guess = input.nextLine();
        // loop until correct
        while (!guess.equals(randomWord)) {
          evaluateAndPrint(guess, randomWord);
          guessCount++;
          System.out.println(ansi_red + "Wrong! Try again." + ansi_reset);
          guess = input.nextLine();
        }
        System.out.println("Correct! The word was: " + randomWord);
        score++;
        System.out.println("Your score is: " + score);
        System.out.println("You took " + guessCount + " guesses.");
      }
    } catch (IOException e) {
      System.out.println("Error reading file: " + e.getMessage());
    }
    // System.out.println("What's your initial guess?");
    // String guess = input.nextLine();
    // if (guess.equals("me")) {
    // System.out.println("Correct!");
    // } else {
    // System.out.println("Wrong!");
    // }
  }

  private static void evaluateAndPrint(String guess, String target) {
    guess = guess.trim();
    target = target.trim();
    // map hashmap
    Map<Character, Integer> remaining = new HashMap<>();
    // check exact matches
    char[] correctPositions = new char[target.length()];
    for (int i = 0; i < target.length(); i++) {
      char tc = target.charAt(i);
      if (i < guess.length() && guess.charAt(i) == tc) {
        correctPositions[i] = tc;
      } else {
        remaining.put(tc, remaining.getOrDefault(tc, 0) + 1);
      }
    }

    Set<Character> wrongPlacement = new LinkedHashSet<>();
    Set<Character> notValid = new LinkedHashSet<>();

    // letters that r in word but not in correct pos
    for (int i = 0; i < guess.length(); i++) {
      char gc = guess.charAt(i);
      if (i < target.length() && guess.charAt(i) == target.charAt(i)) {
        continue;
      }
      Integer cnt = remaining.getOrDefault(gc, 0);
      if (cnt > 0) {
        wrongPlacement.add(gc);
        remaining.put(gc, cnt - 1);
      } else {
        notValid.add(gc);
      }
    }

    // results
    System.out.print("Correct letters (by position): ");
    for (char c : correctPositions) System.out.print(c + " ");
    System.out.println();
    System.out.print("Wrong placement letters: ");
    if (wrongPlacement.isEmpty()) System.out.print("none");
    else for (char c : wrongPlacement) System.out.print(c + " ");
    System.out.println();
    System.out.print("Not valid letters: ");
    if (notValid.isEmpty()) System.out.print("none");
    else for (char c : notValid) System.out.print(c + " ");
    System.out.println();
  }
}