package main;

import main.injeection.Injector;

public class Main {
    public static void main(String[] args) {
        Injector injector = Injector.getInjector();
        ThirdHaveFieldFirstSecond third = (ThirdHaveFieldFirstSecond) injector.getInstance(ThirdHaveFieldFirstSecond.class);
        third.get();
    }
}
