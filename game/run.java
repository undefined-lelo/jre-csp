package game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class run {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            clearConsole.clear();
            // init
            score sc = new score();
            sc.loadScore();

            int guessCount = 0;
            Random rand = new Random();

            // read words
            File inputFile = new File("words.txt");
            ArrayList<String> words = new ArrayList<>();
            try (Scanner in = new Scanner(inputFile)) {
                while (in.hasNextLine()) {
                    String line = in.nextLine().trim();
                    if (!line.isEmpty()) words.add(line);
                }
            }

            if (words.isEmpty()) {
                System.out.println(colors.ansi_red + "No words found in the file." + colors.ansi_reset);
                return;
            }

            String randomWord = words.get(rand.nextInt(words.size())).trim().toLowerCase();
            System.out.println("What's your first guess?");
            String guess = input.nextLine().trim().toLowerCase();

            while (!guess.equals(randomWord)) {
                wordCheck.check(guess, randomWord);
                guessCount++;
                System.out.println(colors.ansi_red + "Wrong! Try again." + colors.ansi_reset);
                guess = input.nextLine().trim().toLowerCase();
            }

            System.out.println(colors.ansi_green + "Correct! The word was " + colors.ansi_cyan_underline + randomWord + colors.ansi_reset);
            sc.incrementAndSave();
            System.out.println("Your new score is: " + colors.ansi_cyan_underline + sc.getScore() + colors.ansi_reset);
            System.out.println("You took " + colors.ansi_cyan_underline + (guessCount + 1) + colors.ansi_reset + " guesses.");
            System.out.println();
            System.out.print("Do you want to play again? (yes/no): ");
            String newRound = input.nextLine();

            if (newRound.equalsIgnoreCase("yes")) {
                main(args);
            } else {
                System.exit(0);
            }
        } catch (IOException e) {
            System.out.println(colors.ansi_red + "Error reading file: " + colors.ansi_reset + e.getMessage());
        }
    }   
}