package stopwatch.com.nghlong3004.server.service;

import stopwatch.com.nghlong3004.server.model.Watch;

public class WatchService {
  
  private Watch watch;
  public WatchService(int hour, int minute, int second) {
    watch = new Watch(hour, minute, second);
  }
  
  public void solveTime() {
    int second = getSecond() - 1;
    if(second < 0) {
      int minute = getMinute() - 1;
      if(minute < 0) {
        int hour = getHour() - 1;
        if(hour >= 0) {
          minute = 59;
          second = 59;
        }
        else {
          minute = 0;
          second = 0;
          hour = 0;
        }
        setHour(hour);
      }
      else {
        second = 59;
      }
      setMinute(minute);
    }
    setSecond(second);
  }
  
  public boolean isRun() {
    solveTime();
    if(getHour() <= 0 && getMinute() == 0 && getSecond() == 0) {
      return false;
    }
    return true;
  }
  
  public String timeString() {
    return String.format("%02d:%02d:%02d", getHour(), getMinute(), getSecond());
  }
  
  public int getHour() {
    return watch.getHour();
  }
  
  public int getMinute() {
    return watch.getMinute();
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
  
  public void setSecond(int second) {
    watch.setSecond(second);
  }
  
}
