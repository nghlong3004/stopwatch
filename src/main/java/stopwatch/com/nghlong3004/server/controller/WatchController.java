package stopwatch.com.nghlong3004.server.controller;

import stopwatch.com.nghlong3004.server.service.WatchService;

public class WatchController {

  private WatchService watchService;

  public WatchController(int hour, int minute, int second, int alarmMinute, int alarmSecond) {
    watchService = new WatchService(hour, minute, second, alarmMinute, alarmSecond);
  }

  public boolean isRun() {
    return watchService.isRun();
  }

  public String getTime() {
    return watchService.getTime();
  }

  public boolean isAlarmRang() {
    return watchService.isAlarmRang();
  }

  public String getAlarmTime() {
    return watchService.getAlarmTime();
  }

  public void increaseTime() {
    watchService.increaseTime();
  }

  public int getHour() {
    return watchService.getHour();
  }

  public int getMinute() {
    return watchService.getMinute();
  }

  public int getSecond() {
    return watchService.getSecond();
  }

  public void setHour(int hour) {
    watchService.setHour(hour);
  }

  public void setMinute(int minute) {
    watchService.setMinute(minute);
  }

  public void setSecond(int second) {
    watchService.setSecond(second);
  }

  public void setAlarmMinute(int minute) {
    watchService.setAlarmMinute(minute);
  }

  public void setAlarmSecond(int second) {
    watchService.setAlarmSecond(second);
  }

}
