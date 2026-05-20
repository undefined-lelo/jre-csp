import java.util.Scanner;

public class Main {
  static Scanner input = new Scanner(System.in);
  public static void main(String[] args) {
    System.out.println("What's your initial guess?");
    String guess = input.nextLine();
    if (guess.equals("me")) {
      System.out.println("Correct!");
    } else {
      System.out.println("Wrong!");
    }
  }
}