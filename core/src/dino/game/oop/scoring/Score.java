package dino.game.oop.scoring;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Score {
    private static final String LOC = "score.txt";
    private static FileReader fileReader;
    private static BufferedReader bufferedReader;
    private static FileWriter fileWriter;

    public static int getScore(){
        try {
            fileReader = new FileReader(LOC);
            bufferedReader = new BufferedReader(fileReader);
            String line = null;
            line = bufferedReader.readLine();
            return Integer.parseInt(line);
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
        return 0;
    }
    public static void setScore(int score){
        try {
            fileWriter = new FileWriter(LOC);
            fileWriter.write(Integer.toString(score));
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
        Score obj = new Score();
        System.out.println(obj.getScore());
    }

}
