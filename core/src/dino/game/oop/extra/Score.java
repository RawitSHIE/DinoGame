package dino.game.oop.extra;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Score {
    private static final String LOC = "score.txt";
    private static FileReader fileReader;
    private static BufferedReader bufferedReader;
    private static FileWriter fileWriter;
    private static PrintWriter printWriter;
    private static ArrayList<Integer> rank;

    public Score(){


    }

    public static ArrayList<Integer> getScore(){
        try {
            fileReader = new FileReader(LOC);
            bufferedReader = new BufferedReader(fileReader);
            rank = new ArrayList(3);
            String line = null;
            while ((line = bufferedReader.readLine()) != null){
                rank.add(Integer.parseInt(line));
            }
            Collections.sort(rank);
            Collections.reverse(rank);
            return rank;

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            try {
                if (fileReader != null){
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bufferedReader != null){
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static boolean setScore(Integer score){
        boolean highscore = false;
        try {
            rank = getScore();
            Collections.sort(rank);
            Collections.reverse(rank);
            fileWriter = new FileWriter(LOC);
            printWriter = new PrintWriter(fileWriter);

            for (int i = 0; i < rank.size(); i++){

                if (rank.get(i) == score){
                    break;
                }
                if (rank.get(i) < score){
                    if (i == 0){
                        highscore  = true;
                    }
                    rank.add(score);
                    Collections.sort(rank);
                    Collections.reverse(rank);
                    rank.remove(3);
                    break;
                }
            }
            String line = null;
            for (Integer i : rank){
                printWriter.write(i + "\n");
            }

            System.out.println(rank);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return highscore;
    }

    public static void ResetScore(){
        for (int i = 0 ; i < rank.size() ; i ++){
            rank.set(i, 0);
        }

        try {
            fileWriter = new FileWriter(LOC);
            printWriter = new PrintWriter(fileWriter);

            for (Integer i : rank){
                printWriter.write(i + "\n");
            }

            System.out.println(rank);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

    }

}
