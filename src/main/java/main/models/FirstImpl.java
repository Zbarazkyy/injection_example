package main.models;

import main.injeection.Inject;

public class FirstImpl implements First {
    @Inject
    private LogService log;


    @Override
    public void getFirst() {
        log.log("In FirstImpl");
    }
}
