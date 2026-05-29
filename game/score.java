package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class score {
    int score = 0;
    File scoreFile = new File("score.json");

    public int loadScore() {
        // create file if doesn't exist
        if (!scoreFile.exists()) {
            try {
                // try to create parent dirs if any
                File parent = scoreFile.getParentFile();
                if (parent != null) parent.mkdirs();
                try (PrintWriter out = new PrintWriter(scoreFile)) {
                    out.println("{\n    \"score\": 0\n}");
                }
            } catch (IOException ex) {
                System.out.println(colors.ansi_red + "Error creating score file: " + colors.ansi_reset + ex.getMessage());
            }
            score = 0;
            return score;
        }

        // parse json w regex
        try (Scanner scoreScanner = new Scanner(scoreFile)) {
            scoreScanner.useDelimiter("\\A");
            String content = scoreScanner.hasNext() ? scoreScanner.next() : "";
            Pattern p = Pattern.compile("\"score\"\\s*:\\s*(\\d+)");
            Matcher m = p.matcher(content);
            if (m.find()) {
                try {
                    if (Integer.parseInt(m.group(1)) < 0) {
                        score = 0;
                    } else if (Integer.parseInt(m.group(1)) > 9999) {
                        System.out.println(colors.ansi_yellow + "You have exceeded 9999, your score has been reset to 0." + colors.ansi_reset);
                        score = 0;
                    } else {
                        score = Integer.parseInt(m.group(1));
                    }
                } catch (NumberFormatException e) {
                    score = 0;
                }
            } else {
                // reset score to 0 if format invalid
                score = 0;
                saveScore();
            }
        } catch (FileNotFoundException e) {
            // create file if not found
            try (PrintWriter out = new PrintWriter(scoreFile)) {
                out.println("{\n    \"score\": 0\n}");
            } catch (IOException ex) {
                System.out.println(colors.ansi_red + "Error creating score file: " + colors.ansi_reset + ex.getMessage());
            }
            score = 0;
        }
        return score;
    }

    public void saveScore() {
        try (PrintWriter out = new PrintWriter(scoreFile)) {
            out.println("{\n    \"score\": " + score + "\n}");
        } catch (IOException e) {
            System.out.println(colors.ansi_red + "Error saving score: " + colors.ansi_reset + e.getMessage());
        }
    }

    public void incrementAndSave() {
        score++;
        saveScore();
    }

    public int getScore() {
        return score;
    }
}