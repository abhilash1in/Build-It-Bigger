package in.goflo.jokeapp.jokelibrary;

import java.util.ArrayList;
import java.util.Random;

public class JokeProvider {

    private ArrayList<String> jokesArrayList;

    public JokeProvider(){
        jokesArrayList = new ArrayList<>();
        jokesArrayList.add("What is a programmer's favourite hangout place? Foo Bar");
        jokesArrayList.add("What do computers and air conditioners have in common? They both become useless when they open Windows!");
        jokesArrayList.add("What do Java programmers wear glasses? Because they don't C#");
        jokesArrayList.add("A programmer had a problem. He decided to use Java. Now he has a ProblemFactory!");
    }

    public ArrayList<String> getJokes(){
        return jokesArrayList;
    }

    public String getRandomJoke(){
        return jokesArrayList.get(new Random().nextInt(jokesArrayList.size() -1));
    }
}
