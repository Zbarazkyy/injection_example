package main;

import main.models.First;
import main.models.LogService;
import main.models.Second;
import main.injeection.Inject;

public class ThirdHaveFieldFirstSecond {
    @Inject
    private First first;
    @Inject
    private Second second;
    @Inject
    private LogService log;

    void get(){
        first.getFirst();
        second.getSecond();
    }
}
