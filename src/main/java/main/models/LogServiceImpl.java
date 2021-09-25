package main.models;

public class LogServiceImpl implements LogService{
    @Override
    public void log(String message) {
        System.out.println(message);
    }
}
