package stopwatch.com.nghlong3004.server.model;

public class Watch {

  private int hour;
  private int minute;
  private int second;
  private int alarmMinute;
  private int alarmSecond;

  public Watch(int hour, int minute, int second, int alarmMinute, int alarmSecond) {
    this.hour = hour;
    this.minute = minute;
    this.second = second;
    this.alarmMinute = alarmMinute;
    this.alarmSecond = alarmSecond;
  }


  public int getAlarmMinute() {
    return alarmMinute;
  }


  public void setAlarmMinute(int alarmMinute) {
    this.alarmMinute = alarmMinute;
  }


  public int getAlarmSecond() {
    return alarmSecond;
  }


  public void setAlarmSecond(int alarmSecond) {
    this.alarmSecond = alarmSecond;
  }


  public int getHour() {
    return hour;
  }

  public void setHour(int hour) {
    this.hour = hour;
  }

  public int getMinute() {
    return minute;
  }

  public void setMinute(int minute) {
    this.minute = minute;
  }

  public int getSecond() {
    return second;
  }

  public void setSecond(int second) {
    this.second = second;
  }

}
