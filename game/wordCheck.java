package game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class wordCheck {
    public static void check(String guess, String target) {
        if (guess == null || target == null) return;
        guess = guess.trim().toLowerCase();
        target = target.trim().toLowerCase();

        Map<Character, Integer> remaining = new HashMap<>();
        char[] correctPositions = new char[target.length()];
        Arrays.fill(correctPositions, '_');

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

        for (int i = 0; i < guess.length(); i++) {
            char gc = guess.charAt(i);
            if (i < target.length() && gc == target.charAt(i)) {
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
        if (correctPositions.length != 0) {
            System.out.print(colors.ansi_green + "Correct letters (by position): ");
            for (char c : correctPositions) System.out.print(c + " ");
            System.out.println(colors.ansi_reset);
        }
        if (wrongPlacement.size() > 0) {
            System.out.print(colors.ansi_red + "Wrong placement letters: ");
            for (char c : wrongPlacement) System.out.print(c + " ");
            System.out.println(colors.ansi_reset);
        }
        if (notValid.size() > 0) {
            System.out.print(colors.ansi_yellow + "Non-used letters: ");
            for (char c : notValid) System.out.print(c + " ");
            System.out.println(colors.ansi_reset);
        }
    }
}