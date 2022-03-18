package com.occultus.learncase.pattern.state;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Module {
    private int a;
    private int b;

    private State state = new InitState(this);

    public String print() {
        return this.state.print();
    }

    public void input(String answer) {
        this.state.input(answer);
    }
}
