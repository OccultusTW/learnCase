package com.occultus.learncase.pattern.enumstate;

import org.springframework.stereotype.Service;

@Service
public class LeaveRequestService {
    // 有限狀態機(Finite State Machine, FSM)
    //可以在下一階段(nextState)方法中再設計邏輯
    public void test() {
        LeaveRequestState state = LeaveRequestState.Submitted;
        state.nextState(); // LeaveRequestState.Escalated

        state.nextState(); // LeaveRequestState.Approved;

        state.nextState(); // LeaveRequestState.Approved;
    }
}
