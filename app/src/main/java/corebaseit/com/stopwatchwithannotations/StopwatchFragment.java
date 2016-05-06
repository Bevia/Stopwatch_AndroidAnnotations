package corebaseit.com.stopwatchwithannotations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

@EFragment
public class StopwatchFragment extends Fragment {

    @Bind(R.id.stopwatch_view)
    TextView stopwatchView;
    @Bind(R.id.start_button)
    Button startButton;
    @Bind(R.id.stop_button)
    Button stopButton;
    @Bind(R.id.reset_button)
    Button resetButton;

    private int seconds = 0;
    private boolean running;
    private boolean notRunning;
    private boolean onReset;
    private boolean onStop;
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stopwatch, container, false);

        if (savedInstanceState == null) {
        } else {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            notRunning = savedInstanceState.getBoolean("notRunning");
            onStop = savedInstanceState.getBoolean("onStop", onStop);
            onReset = savedInstanceState.getBoolean("onReset", onReset);
        }
        runStopWatch();
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        logicStates();
        uiLogic();
    }

    private void uiLogic() {

        startButton.setOnClickListener((View view) -> {
            running = true;
            onStop = false;
            onReset = false;
            stopButton.setEnabled(true);
            resetButton.setEnabled(true);
            startButton.setEnabled(false);
            stopwatchView.setTextColor(ContextCompat.getColor(getActivity(), R.color.indigo_900));
            startButton.setText("Running");
        });

        stopButton.setOnClickListener((View view) -> {

            running = false;
            onStop = true;
            startButton.setEnabled(true);
            stopwatchView.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey_600));
            startButton.setText("Continue");
        });

        resetButton.setOnClickListener((View view) -> {
            running = false;
            onReset = true;
            seconds = 0;
            startButton.setEnabled(true);
            stopButton.setEnabled(false);
            resetButton.setEnabled(false);
            stopwatchView.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey_300));
            startButton.setText("Start");
        });
    }

    private void logicStates() {
        if (seconds > 0) {
            stopwatchView.setTextColor(ContextCompat.getColor(getActivity(), R.color.indigo_900));
            startButton.setText("Running");
            stopButton.setEnabled(true);
            resetButton.setEnabled(true);
        }
        if (seconds < 1) {
            stopwatchView.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey_300));
            stopButton.setEnabled(false);
            resetButton.setEnabled(false);
        }
        if (onStop) {
            stopwatchView.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey_600));
            startButton.setText("Continue");
        }
        if (onReset) {
            stopwatchView.setTextColor(ContextCompat.getColor(getActivity(), R.color.grey_300));
            startButton.setText("Start");
            stopButton.setEnabled(false);
            resetButton.setEnabled(false);
        }
    }

    //Sets the number of seconds on the timer.
    private void runStopWatch() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                stopwatchView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("notRunning", notRunning);
        savedInstanceState.putBoolean("onStop", onStop);
        savedInstanceState.putBoolean("onReset", onReset);
    }

    @Override
    public void onPause() {
        super.onPause();
        notRunning = running;
        running = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (notRunning) {
            running = true;
        }
    }
}