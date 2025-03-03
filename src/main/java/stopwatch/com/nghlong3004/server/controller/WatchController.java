package stopwatch.com.nghlong3004.server.controller;

import stopwatch.com.nghlong3004.server.service.WatchService;

public class WatchController {
  
  private WatchService watchService;
  
  public WatchController(int hour, int minute, int second) {
    watchService = new WatchService(hour, minute, second);
  }
  
  public boolean isRun() {
    return watchService.isRun();
  }
  
  public String timeString() {
    return watchService.timeString();
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
  
  
}
