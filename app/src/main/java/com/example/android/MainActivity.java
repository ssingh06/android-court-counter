package com.example.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.actions.CourtCounterAction;
import com.example.android.actions.CourtCounterActionsManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method handles all the button click logic for all buttons in the app.
     * @param view view that invoked this process.
     */
    public void onPointsButtonClick(View view) {
        final int scoreValue;
        final TextView scoreTextView;
        final CourtCounterAction.TEAM team;
        final CourtCounterAction.ACTION_TYPE actionType;

        switch (view.getId()) {
            case R.id.team_a_three_points_button:
                scoreValue = getTeamAScore() + 3;
                scoreTextView = findViewById(R.id.team_a_score);
                team = CourtCounterAction.TEAM.TEAM_A;
                actionType = CourtCounterAction.ACTION_TYPE.THREE_POINTER;
                break;
            case R.id.team_a_two_points_button:
                scoreValue = getTeamAScore() + 2;
                scoreTextView = findViewById(R.id.team_a_score);
                team = CourtCounterAction.TEAM.TEAM_A;
                actionType = CourtCounterAction.ACTION_TYPE.TWO_POINTER;
                break;
            case R.id.team_a_free_throw_button:
                scoreValue = getTeamAScore() + 1;
                scoreTextView = findViewById(R.id.team_a_score);
                team = CourtCounterAction.TEAM.TEAM_A;
                actionType = CourtCounterAction.ACTION_TYPE.FREE_THROW;
                break;
            case R.id.team_b_three_points_button:
                scoreValue = getTeamBScore() + 3;
                scoreTextView = findViewById(R.id.team_b_score);
                team = CourtCounterAction.TEAM.TEAM_B;
                actionType = CourtCounterAction.ACTION_TYPE.THREE_POINTER;
                break;
            case R.id.team_b_two_points_button:
                scoreValue = getTeamBScore() + 2;
                scoreTextView = findViewById(R.id.team_b_score);
                team = CourtCounterAction.TEAM.TEAM_B;
                actionType = CourtCounterAction.ACTION_TYPE.TWO_POINTER;
                break;
            case R.id.team_b_free_throw_button:
                scoreValue = getTeamBScore() + 1;
                scoreTextView = findViewById(R.id.team_b_score);
                team = CourtCounterAction.TEAM.TEAM_B;
                actionType = CourtCounterAction.ACTION_TYPE.FREE_THROW;
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        "Invalid button id in onclick method",
                        Toast.LENGTH_SHORT).show();
                return;
        }

        displayScore(scoreTextView, scoreValue);

        /* handle new action through actions manager */
        CourtCounterActionsManager.getInstance()
                .handleNewAction(new CourtCounterAction(team, actionType));
    }

    /**
     * This method increments the team A and team B fouls.
     * @param view the view that invokes the method.
     */
    public void onFoulButtonClick(View view) {
        final int foulsValue;
        final TextView foulTextView;
        final CourtCounterAction.TEAM team;
        final CourtCounterAction.ACTION_TYPE actionType = CourtCounterAction.ACTION_TYPE.FOUL;

        switch(view.getId()) {
            case R.id.team_a_foul_button:
                foulTextView = findViewById(R.id.team_a_fouls);
                foulsValue = getTeamAFouls() + 1;
                team = CourtCounterAction.TEAM.TEAM_A;
                break;
            case R.id.team_b_foul_button:
                foulTextView = findViewById(R.id.team_b_fouls);
                foulsValue = getTeamBFouls() + 1;
                team = CourtCounterAction.TEAM.TEAM_B;
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        "Invalid button id in onclick method",
                        Toast.LENGTH_SHORT).show();
                return;
        }

        displayFouls(foulTextView, foulsValue);

        /* handle new action through actions manager */
        CourtCounterActionsManager.getInstance()
                .handleNewAction(new CourtCounterAction(team, actionType));
    }

    /**
     * This method increments the team A and team B subs.
     * @param view the view that invokes the method.
     */
    public void onSubButtonClick(View view) {
        final int subsValue;
        final TextView subsTextView;
        final CourtCounterAction.TEAM team;
        final CourtCounterAction.ACTION_TYPE actionType = CourtCounterAction.ACTION_TYPE.SUBSTITUTION;

        switch (view.getId()) {
            case R.id.team_a_subs_button:
                subsTextView = findViewById(R.id.team_a_subs);
                subsValue = getTeamASubs() + 1;
                team = CourtCounterAction.TEAM.TEAM_A;
                break;
            case R.id.team_b_subs_button:
                subsTextView = findViewById(R.id.team_b_subs);
                subsValue = getTeamBSubs() + 1;
                team = CourtCounterAction.TEAM.TEAM_B;
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        "Invalid button id in onclick method",
                        Toast.LENGTH_SHORT).show();
                return;
        }

        displaySubs(subsTextView, subsValue);

        /* handle new action through actions manager */
        CourtCounterActionsManager.getInstance()
                .handleNewAction(new CourtCounterAction(team, actionType));
    }

    /**
     * This method is used to undo the last action on the app. The CourtCounterActionsManager
     * defined a max limit on the number of previous actions that can be undone.
     * @param view the view that invokes the method.
     */
    public void onUndo(View view) {
        final CourtCounterAction prevAction = CourtCounterActionsManager.getInstance().getAndRemoveLastAction();

        /* if no prev actions available, show a message and return */
        if (prevAction.isInvalidAction()) {
            Toast.makeText(getApplicationContext(),
                    "No more actions to undo", Toast.LENGTH_SHORT).show();
            return;
        }

        final CourtCounterAction.TEAM team = prevAction.getTeam();
        final CourtCounterAction.ACTION_TYPE actionType = prevAction.getActionType();

        switch (actionType) {
            case THREE_POINTER:
                if (team == CourtCounterAction.TEAM.TEAM_A) {
                    displayScore((TextView) findViewById(R.id.team_a_score), getTeamAScore() - 3);
                } else {
                    displayScore((TextView) findViewById(R.id.team_b_score), getTeamBScore() - 3);
                }
                break;
            case TWO_POINTER:
                if (team == CourtCounterAction.TEAM.TEAM_A) {
                    displayScore((TextView) findViewById(R.id.team_a_score), getTeamAScore() - 2);
                } else {
                    displayScore((TextView) findViewById(R.id.team_b_score), getTeamBScore() - 2);
                }
                break;
            case FREE_THROW:
                if (team == CourtCounterAction.TEAM.TEAM_A) {
                    displayScore((TextView) findViewById(R.id.team_a_score), getTeamAScore() - 1);
                } else {
                    displayScore((TextView) findViewById(R.id.team_b_score), getTeamBScore() - 1);
                }
                break;
            case FOUL:
                if (team == CourtCounterAction.TEAM.TEAM_A) {
                    displayFouls((TextView) findViewById(R.id.team_a_fouls), getTeamAFouls() - 1);
                } else {
                    displayFouls((TextView) findViewById(R.id.team_b_fouls), getTeamBFouls() - 1);
                }
                break;
            case SUBSTITUTION:
                if (team == CourtCounterAction.TEAM.TEAM_A) {
                    displaySubs((TextView) findViewById(R.id.team_a_subs), getTeamASubs() - 1);
                } else {
                    displaySubs((TextView) findViewById(R.id.team_b_subs), getTeamBSubs() - 1);
                }
                break;
            default:
                Toast.makeText(getApplicationContext(),
                        "Invalid action type", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method resets the team A and team B scores.
     * @param view the view that invokes the method.
     */
    public void onReset(View view) {
        /* reset scores */
        displayScore((TextView) findViewById(R.id.team_a_score), 0);
        displayScore((TextView) findViewById(R.id.team_b_score), 0);

        /* reset fouls */
        displayFouls((TextView) findViewById(R.id.team_a_fouls), 0);
        displayFouls((TextView) findViewById(R.id.team_b_fouls), 0);

        /* reset subs */
        displaySubs((TextView) findViewById(R.id.team_a_subs), 0);
        displaySubs((TextView) findViewById(R.id.team_b_subs), 0);

        /* reset the actions manager */
        CourtCounterActionsManager.getInstance().reset();
    }

    /**
     * This method returns the team A score.
     * @return int team A score
     */
    private int getTeamAScore() {
        return Integer.parseInt(((TextView) findViewById(R.id.team_a_score))
                .getText().toString());
    }

    /**
     * This method returns the team B score.
     * @return int team B score
     */
    private int getTeamBScore() {
        return Integer.parseInt(((TextView) findViewById(R.id.team_b_score))
                .getText().toString());
    }

    /**
     * This method return the total fouls by team A.
     * @return int total fouls by team A.
     */
    private int getTeamAFouls() {
        return Integer.parseInt(((TextView)findViewById(R.id.team_a_fouls))
                .getText().toString());
    }

    /**
     * This method return the total fouls by team A.
     * @return int total fouls by team A.
     */
    private int getTeamBFouls() {
        return Integer.parseInt(((TextView)findViewById(R.id.team_b_fouls))
                .getText().toString());
    }

    /**
     * This method return the total subs by team A.
     * @return int total subs by team A.
     */
    private int getTeamASubs() {
        return Integer.parseInt(((TextView)findViewById(R.id.team_a_subs))
                .getText().toString());
    }

    /**
     * This method return the total subs by team A.
     * @return int total subs by team A.
     */
    private int getTeamBSubs() {
        return Integer.parseInt(((TextView)findViewById(R.id.team_b_subs))
                .getText().toString());
    }

    /**
     * This method displays the team updated score for either team A or teamB,
     * depending on the text view object passed.
     * @param textView text view corresponding to either team A or team B.
     * @param score score to be displayed
     */
    private void displayScore(TextView textView, int score) {
        if (score < 0) {
            Toast.makeText(getApplicationContext(),
                    "Invalid Team Score value.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        textView.setText(String.valueOf(score));
    }

    /**
     * This method displays the updated fouls for either team A or teamB,
     * depending on the text view object passed.
     * @param textView text view corresponding to either team A or team B.
     * @param fouls fouls value to be displayed.
     */
    private void displayFouls(TextView textView, int fouls) {
        if (fouls < 0) {
            Toast.makeText(getApplicationContext(),
                    "Invalid fouls value.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        textView.setText(String.valueOf(fouls));
    }

    /**
     * This method displays the updated subs for either team A or teamB,
     * depending on the text view object passed.
     * @param textView text view corresponding to either team A or team B.
     * @param subs subs value to be displayed.
     */
    private void displaySubs(TextView textView, int subs) {
        if (subs < 0) {
            Toast.makeText(getApplicationContext(),
                    "Invalid substitutions value.",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        textView.setText(String.valueOf(subs));
    }
}
