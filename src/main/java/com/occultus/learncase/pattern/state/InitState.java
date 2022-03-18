package com.occultus.learncase.pattern.state;

public class InitState implements State {
    private final Module module;

    public InitState(Module module) {
        this.module = module;
    }

    @Override
    public String print() {
        return "Shape: (C)ircle or (R)ectangle?";
    }

    @Override
    public void input(String answer) {
        if (answer.equals("R")) {
            module.setState(new RectangleSelectedStete(this.module));
        }
    }
}