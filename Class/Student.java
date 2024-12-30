package src.Class;

import src.Interface.Scoreable;

public class Student implements Scoreable{
    private String name;
    private int percentageScore;

    public Student(String name, int percentageScore) {
        this.name = name;
        this.percentageScore = percentageScore;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getScore() {
        return percentageScore;
    }

    @Override
    public void increaseScore() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'increaseScore'");
    }

    @Override
    public void resetScore() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resetScore'");
    }
}
