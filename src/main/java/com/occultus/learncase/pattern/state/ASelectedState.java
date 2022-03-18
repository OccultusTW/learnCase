package com.occultus.learncase.pattern.state;

public class ASelectedState implements State {
    private final Module module;

    public ASelectedState(Module module) {
        this.module = module;
    }

    @Override
    public String print() {
        return "Rectangle side B length?";
    }

    @Override
    public void input(String answer) {
        try {
            Integer answerInt = Integer.valueOf(answer);
            module.setB(answerInt);
            module.setState(new BSelectedState(this.module));
        } catch (NumberFormatException e) {
            return;
        }
    }
}