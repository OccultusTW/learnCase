package com.occultus.learncase.pattern.state;

public class RectangleSelectedStete implements State {
    private final Module module;

    public RectangleSelectedStete(Module module) {
        this.module = module;
    }

    @Override
    public String print() {
        return "Rectangle side A length?";
    }

    @Override
    public void input(String answer) {
        try {
            module.setA(Integer.parseInt(answer));
            module.setState(new RectangleSelectedStete(this.module));
        } catch (NumberFormatException e) {
            //do nothing
        }
    }
}