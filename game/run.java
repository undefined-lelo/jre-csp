package game;

import static game.colors.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class run {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.print("\033[H\033[2J");
            System.out.flush();
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
                System.out.println("No words found in the file.");
                return;
            }

            String randomWord = words.get(rand.nextInt(words.size())).trim().toLowerCase();
            System.out.println("What's your first guess?");
            String guess = input.nextLine().trim().toLowerCase();

            while (!guess.equals(randomWord)) {
                wordCheck.check(guess, randomWord);
                guessCount++;
                System.out.println(ansi_red + "Wrong! Try again." + ansi_reset);
                guess = input.nextLine().trim().toLowerCase();
            }

            System.out.println(ansi_green + "Correct! The word was: " + randomWord + ansi_reset);
            sc.incrementAndSave();
            System.out.println("Your new score is: " + sc.getScore());
            System.out.println("You took " + guessCount + " guesses.");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}