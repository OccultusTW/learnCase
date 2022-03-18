package com.occultus.learncase.pattern.state;

public class BSelectedState implements State {
    private final Module module;

    public BSelectedState(Module module) {
        this.module = module;
    }

    @Override
    public String print() {
        return "Area=" + (module.getA() * module.getB()) + ", Circumference=" + (2 * (module.getA() + module.getB()));
    }

    @Override
    public void input(String answer) {
        //do nothing
    }
}