package stopwatch.com.nghlong3004.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import stopwatch.com.nghlong3004.server.controller.WatchController;

public class PanelWatch extends JPanel {

  private static final long serialVersionUID = 891062491271419814L;

  private WatchController controller;
  private JLabel timeLabel;
  private JPanel spinnerPanel;

  private JButton btnStart;
  private JButton btnStop;

  private JSpinner hourSpinner;
  private JSpinner minuteSpinner;
  private JSpinner secondSpinner;

  private Timer time;

  private boolean isRunning;

  public PanelWatch() {
    initialize();
    setActions();
    setUpLayout();
  }

  private void setUpLayout() {
    timeLabel.setFont(new Font("Serif", Font.PLAIN, 48));
    setLayout(new BorderLayout());

    JPanel btnPanel = new JPanel(new FlowLayout());
    btnPanel.add(btnStart);
    btnPanel.add(btnStop);

    spinnerPanel = new JPanel(new FlowLayout());
    spinnerPanel.add(hourSpinner);
    spinnerPanel.add(new JLabel(":"));
    spinnerPanel.add(minuteSpinner);
    spinnerPanel.add(new JLabel(":"));
    spinnerPanel.add(secondSpinner);

    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.add(timeLabel, BorderLayout.CENTER);
    centerPanel.add(spinnerPanel, BorderLayout.SOUTH);
    add(centerPanel, BorderLayout.CENTER);
    add(btnPanel, BorderLayout.SOUTH);
  }

  private void setActions() {
    btnStart.addActionListener(e -> startTimer());
    btnStop.addActionListener(e -> stopTimer());
    hourSpinner.addChangeListener(e -> updateTimeLabel());
    minuteSpinner.addChangeListener(e -> updateTimeLabel());
    secondSpinner.addChangeListener(e -> updateTimeLabel());
  }

  private void startTimer() {
    if (!isRunning) {
      controller.setHour((int) hourSpinner.getValue());
      controller.setMinute((int) minuteSpinner.getValue());
      controller.setSecond((int) secondSpinner.getValue());
      spinnerSetVisible();
      isRunning = true;
      time.start();
    }
  }

  private void stopTimer() {
    if (isRunning) {
      spinnerSetVisible();
      hourSpinner.setValue(controller.getHour());
      minuteSpinner.setValue(controller.getMinute());
      secondSpinner.setValue(controller.getSecond());
      isRunning = false;
      time.stop();
    }
  }

  private void spinnerSetVisible() {
    spinnerPanel.setVisible(isRunning);
  }

  private void initialize() {
    controller = new WatchController(00, 00, 00);
    timeLabel = new JLabel("00:00:00", JLabel.CENTER);
    btnStart = new JButton("Start");
    btnStop = new JButton("Stop");
    isRunning = false;
    time = new Timer(1000, e -> updateTime());
    SpinnerNumberModel hourModel = new SpinnerNumberModel(0, 0, 24, 1);

    hourSpinner = new JSpinner(hourModel);
    hourSpinner.setFont(new Font("Serif", Font.PLAIN, 24));
    hourSpinner.setEditor(new JSpinner.NumberEditor(hourSpinner, "00"));
    hourSpinner.setBorder(null);
    hourSpinner.setFocusable(false);

    SpinnerNumberModel minuteModel = new SpinnerNumberModel(0, 0, 59, 1);

    minuteSpinner = new JSpinner(minuteModel);
    minuteSpinner.setFont(new Font("Serif", Font.PLAIN, 24));
    minuteSpinner.setEditor(new JSpinner.NumberEditor(minuteSpinner, "00"));
    minuteSpinner.setBorder(null);
    minuteSpinner.setFocusable(false);

    SpinnerNumberModel secondModel = new SpinnerNumberModel(0, 0, 59, 1);

    secondSpinner = new JSpinner(secondModel);
    secondSpinner.setFont(new Font("Serif", Font.PLAIN, 24));
    secondSpinner.setEditor(new JSpinner.NumberEditor(secondSpinner, "00"));
    secondSpinner.setBorder(null);
    secondSpinner.setFocusable(false);
  }

  private void updateTimeLabel() {
    if (!isRunning) {
      int hour = (int) hourSpinner.getValue();
      int minute = (int) minuteSpinner.getValue();
      int second = (int) secondSpinner.getValue();
      controller.setHour(hour);
      controller.setMinute(minute);
      controller.setSecond(second);
      timeLabel.setText(controller.timeString());
    }
  }

  private void updateTime() {
    if (isRunning) {
      if (controller.isRun()) {
        timeLabel.setText(controller.timeString());
      } else {
        stopTimer();
        timeLabel.setText(controller.timeString());
        playAlarmSound();
      }
    }
  }

  private void playAlarmSound() {
    try {
      InputStream audioSrc =
          getClass().getResource("/com/nghlong3004/sound/funny-minion.wav").openStream();
      if (audioSrc == null) {
        System.err.println("Không tìm thấy file beep.wav trong resources!");
        return;
      }

      AudioInputStream audioStream =
          AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc));
      Clip clip = AudioSystem.getClip();
      clip.open(audioStream);
      clip.start();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
      e.printStackTrace();
    }
  }
}

