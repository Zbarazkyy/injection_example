package main.models;

import main.injeection.Inject;

public class SecondHaveFieldFirst  implements Second{
    @Inject
    private First first;
    @Inject
    private LogService log;

    @Override
    public void getSecond() {
        log.log("In Second");
    }
}
