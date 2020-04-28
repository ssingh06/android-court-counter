package com.example.android.actions;

/**
 * This class define an action can be taken by a user in the app.
 */
public class CourtCounterAction {

    /* enum to define the teams in the app */
    public enum TEAM {
        INVALID_TEAM,
        TEAM_A,
        TEAM_B
    }

    /* enum to define the action types in the app */
    public enum ACTION_TYPE {
        INVALID_ACTION_TYPE,
        THREE_POINTER,
        TWO_POINTER,
        FREE_THROW,
        FOUL,
        SUBSTITUTION
    }

    private final TEAM team;
    private final ACTION_TYPE actionType;

    /**
     * constructor
     * @param team team
     * @param actionType action type
     */
    public CourtCounterAction(TEAM team, ACTION_TYPE actionType) {
        this.team = team;
        this.actionType = actionType;
    }

    /**
     * This method returns if a CourtCounterAction is invalid
     * @return
     */
    public boolean isInvalidAction() {
        return (team == TEAM.INVALID_TEAM) || (actionType == ACTION_TYPE.INVALID_ACTION_TYPE);
    }

    /**
     * getter method for team
     * @return TEAM team
     */
    public TEAM getTeam() {
        return team;
    }

    /**
     * getter method for actionType
     * @return ACTION_TYPE actionType
     */
    public ACTION_TYPE getActionType() {
        return actionType;
    }
}
