package ir.SamadiPour.SnakeFX.Scoreboard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;

public class HighScoreManager {
    private static final String HIGH_SCORE_FILE = "scores.dat";
    private ObjectOutputStream outputStream = null;
    private ObjectInputStream inputStream = null;
    private ArrayList<Score> scores;
    File scoreFile;

    public HighScoreManager() {
        scoreFile = new File(HIGH_SCORE_FILE);
        //create file if not found
        try {
            scoreFile.createNewFile();
        } catch (IOException ignored) {
        }
        scores = new ArrayList<>();
        loadScoreFile();
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public boolean isScore(int score) {
        if (scores.size() == 0)
            return true;
        return scores.size() < 10 | score > scores.get(scores.size() - 1).getScore();
    }

    public void addScore(int score, String name) {
        if (scores.size() < 10) {
            scores.add(new Score(score, name));
            scores.sort(new ScoreComparator());
            updateScoreFile();
        } else {
            if (score > scores.get(scores.size() - 1).getScore()) {
                scores.remove(0);
                scores.add(new Score(score, name));
                scores.sort(new ScoreComparator());
                updateScoreFile();
            }
        }
    }

    public boolean isHigherThanHighest(int score) {
        return scores.size() > 0 && score > scores.get(0).getScore();
    }

    private void loadScoreFile() {
        try {
            inputStream = new ObjectInputStream(new FileInputStream(HIGH_SCORE_FILE));
            scores = (ArrayList<Score>) inputStream.readObject();
        } catch (Exception e) {
//            System.err.println(e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println("IO: " + e.getMessage());
            }
        }
    }

    private void updateScoreFile() {
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(HIGH_SCORE_FILE, false));
            outputStream.writeObject(scores);
        } catch (Exception e) {
//            System.err.println("IO: " + e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ObservableList<Score> getHighScoreList() {
        return FXCollections.observableArrayList(scores);
    }

}
