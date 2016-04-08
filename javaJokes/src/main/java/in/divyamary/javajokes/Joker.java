package in.divyamary.javajokes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Joker {

    private static final List<String> jokes = new ArrayList<>();

    static {
        jokes.add("Why is there no gambling in Africa? Too Many Cheetahs!");
        jokes.add("What do you call a fish with no eye? Fssshh");
        jokes.add("Why did the cookie cry? Because his mother was a wafer so long!");
        jokes.add("What does a ghost wear when it's raining outside? Boooooooooooooooooooooooooooots!");
        jokes.add("What did 0 say to 8? Nice belt!");
        jokes.add("Why did the elephants get kicked out of the public pool? THEY KEPT DROPPING THEIR TRUNKS!");
        jokes.add("What did one shark say to the other while eating a clownfish? This tastes funny.");
        jokes.add("What do calendars eat? Dates!");
        jokes.add("What's it called when you lend money to a bison? A BUFFA-LOAN!");
        jokes.add("Why wouldn't the shrimp share his treasure? Because he was a little shellfish.");
    }

    public Joker() {

    }

    public String getRandomJoke() {
        Random random = new Random();
        return jokes.get(random.nextInt(jokes.size()));
    }
}
