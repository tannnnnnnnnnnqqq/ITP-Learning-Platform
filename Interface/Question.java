package src.Interface;

import javax.swing.JPanel;

public interface Question {

    public abstract JPanel getQuestionPanel();

    public abstract boolean checkAnswer();

    public abstract void loadAnswer();

    public abstract void resetAnswer();

    public abstract void saveAnswer();

}

