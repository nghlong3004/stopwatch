package stopwatch.com.nghlong3004.server.service;

import stopwatch.com.nghlong3004.server.model.Watch;

public class WatchService {

  private Watch watch;
  private int currentTime;

  public WatchService(int hour, int minute, int second, int alarmMinute, int alarmSecond) {
    watch = new Watch(hour, minute, second, alarmMinute, alarmSecond);
    this.currentTime = 0;
  }

  public void solveTime() {
    int second = getSecond() - 1;
    if (second < 0) {
      int minute = getMinute() - 1;
      if (minute < 0) {
        int hour = getHour() - 1;
        if (hour >= 0) {
          minute = 59;
          second = 59;
        } else {
          minute = 0;
          second = 0;
          hour = 0;
        }
        setHour(hour);
      } else {
        second = 59;
      }
      setMinute(minute);
    }
    setSecond(second);

  }

  public boolean isRun() {
    solveTime();
    if (getHour() <= 0 && getMinute() == 0 && getSecond() == 0) {
      return false;
    }
    return true;
  }

  public boolean isAlarmRang() {
    System.out.println(getAlarmSecond());
    if (getAlarmMinute() < currentTime / 60 && getAlarmSecond() == 0) {
      currentTime = 0;
      return true;
    }
    return false;
  }

  public String getTime() {
    return String.format("%02d:%02d:%02d", getHour(), getMinute(), getSecond());
  }

  public int getHour() {
    return watch.getHour();
  }

  private int getAlarmMinute() {
    return watch.getAlarmMinute() - this.currentTime / 60 - (this.currentTime >= 1 ? 1 : 0);
  }

  public int getMinute() {
    return watch.getMinute();
  }

  private int getAlarmSecond() {
    return (watch.getAlarmSecond() - this.currentTime % 60 + 60) % 60;
  }

  public int getSecond() {
    return watch.getSecond();
  }

  public void setHour(int hour) {
    watch.setHour(hour);
  }

  public void setMinute(int minute) {
    watch.setMinute(minute);
  }

  public void setAlarmMinute(int minute) {
    watch.setAlarmMinute(minute);
    this.currentTime = 0;
  }

  public void setAlarmSecond(int second) {
    watch.setAlarmSecond(second);
  }

  public void setSecond(int second) {
    watch.setSecond(second);
  }

  public void increaseTime() {
    this.currentTime++;
  }

  private String fixTime(int time) {
    return (time < 10 ? "0" : "") + time;
  }

  public String getAlarmTime() {
    return (fixTime(getAlarmMinute()) + ":" + fixTime(getAlarmSecond()));
  }

}
