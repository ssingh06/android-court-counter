package com.example.android.actions;

import java.util.Stack;

/**
 * This class contains the logic to perform any management tasks related to
 * actions available in the app. It is defined as a singleton class to only
 * allow one instance in the app.
 */
public class CourtCounterActionsManager {
    /* max number of previous actions that the CourtCounterActionsManager stores */
    private static int MAX_PREV_ACTIONS = 3;

    private static CourtCounterActionsManager instance;
    private final Stack<CourtCounterAction> prevActions;

    /**
     * constructor
     */
    private CourtCounterActionsManager() {
        prevActions = new Stack<>();
    }

    /**
     * static method to return singleton instance.
     * @return singleton instance
     */
    public static CourtCounterActionsManager getInstance() {
        if (instance == null) {
            instance = new CourtCounterActionsManager();
        }

        return instance;
    }

    /**
     * This method stores the previous action in its stack of previous actions.
     * @param action action to be handled.
     */
    public void handleNewAction(CourtCounterAction action) {
        if (prevActions.size() == MAX_PREV_ACTIONS) {
            prevActions.remove(0);
        }

        prevActions.push(action);
    }

    /**
     * This method returns the last action taken by the user
     * @return last action
     */
    public CourtCounterAction getLastAction() {
        if (!hasMoreActions()) {
            return new CourtCounterAction(CourtCounterAction.TEAM.INVALID_TEAM,
                    CourtCounterAction.ACTION_TYPE.INVALID_ACTION_TYPE);
        }
        return prevActions.peek();
    }

    /**
     * This method removes the last action from its stack of previous actions
     * and return it to the user.
     * @return last action
     */
    public CourtCounterAction getAndRemoveLastAction() {
        if (!hasMoreActions()) {
            return new CourtCounterAction(CourtCounterAction.TEAM.INVALID_TEAM,
                    CourtCounterAction.ACTION_TYPE.INVALID_ACTION_TYPE);
        }
        return prevActions.pop();
    }

    /**
     * This method resets the CourtCounterActionsManager by cleaning up the
     * working instance of CourtCounterActionsManager.
     */
    public void reset() {
        instance = null;
    }

    /**
     * This method returns if the prevActions stack has any more actions.
     * @return boolean whether there are any more previous actions.
     */
    private boolean hasMoreActions() {
        return !prevActions.isEmpty();
    }
}
