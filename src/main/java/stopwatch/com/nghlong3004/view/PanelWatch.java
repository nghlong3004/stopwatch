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
  private JLabel timeSettingLabel;

  private JPanel spinnerPanel;

  private JButton btnStart;
  private JButton btnStop;

  private JSpinner timeSettingSpinner;
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

    JPanel labelPanel = new JPanel(new FlowLayout());

    labelPanel.add(timeLabel);

    JPanel btnPanel = new JPanel(new FlowLayout());
    btnPanel.add(btnStart);
    btnPanel.add(btnStop);

    spinnerPanel = new JPanel(new FlowLayout());
    spinnerPanel.add(hourSpinner);
    spinnerPanel.add(new JLabel(":"));
    spinnerPanel.add(minuteSpinner);
    spinnerPanel.add(new JLabel(":"));
    spinnerPanel.add(secondSpinner);
    spinnerPanel.add(new JLabel("  "));
    spinnerPanel.add(timeSettingSpinner);
    JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    topLeftPanel.add(timeSettingLabel);
    JPanel centerPanel = new JPanel(new BorderLayout());
    centerPanel.add(labelPanel, BorderLayout.CENTER);
    centerPanel.add(spinnerPanel, BorderLayout.SOUTH);
    add(topLeftPanel, BorderLayout.NORTH);
    add(centerPanel, BorderLayout.CENTER);
    add(btnPanel, BorderLayout.SOUTH);
  }

  private void setActions() {
    btnStart.addActionListener(e -> startTimer());
    btnStop.addActionListener(e -> stopTimer());

    timeSettingSpinner.addChangeListener(e -> updateTimeLabel());
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

  private String setTextTimeSetting(String time) {
    return "<html><div style='text-align: center;'>Báo thức sau<br><span style='font-size: 12px;'>"
        + time + " </span></div></html>";
  }

  private void initialize() {
    controller = new WatchController(00, 00, 00, 30, 00);
    timeLabel = new JLabel("00:00:00", JLabel.CENTER);
    timeSettingLabel = new JLabel(setTextTimeSetting("30:00"), JLabel.CENTER);
    btnStart = new JButton("Start");
    btnStop = new JButton("Stop");
    isRunning = false;
    time = new Timer(1000, e -> updateTime());

    SpinnerNumberModel timeSettingModel = new SpinnerNumberModel(30, 0, 240, 1);

    timeSettingSpinner = new JSpinner(timeSettingModel);
    timeSettingSpinner.setFont(new Font("Serif", Font.PLAIN, 24));
    timeSettingSpinner.setEditor(new JSpinner.NumberEditor(timeSettingSpinner, "00"));
    timeSettingSpinner.setBorder(null);
    timeSettingSpinner.setFocusable(false);

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
      int minuteAlarm = (int) timeSettingSpinner.getValue();
      controller.setHour(hour);
      controller.setMinute(minute);
      controller.setSecond(second);
      controller.setAlarmMinute(minuteAlarm);
      controller.setAlarmSecond(0);
      updateLabel();
    }
  }

  private void updateLabel() {
    timeSettingLabel.setText(setTextTimeSetting(controller.getAlarmTime()));
    timeLabel.setText(controller.getTime());
    controller.increaseTime();
  }

  private void updateTime() {
    if (isRunning) {
      if (controller.isRun()) {
        if (controller.isAlarmRang()) {
          playAlarmSound();
        }
        updateLabel();
      } else {
        stopTimer();
        timeLabel.setText(controller.getTime());
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

