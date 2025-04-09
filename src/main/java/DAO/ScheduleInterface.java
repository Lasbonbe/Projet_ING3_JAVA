package DAO;

import Modele.Schedule;

import java.util.ArrayList;

public interface ScheduleInterface {
    public ArrayList<Schedule> getSchedule();
    public void addSchedule(Schedule schedule);
    public void deleteSchedule(Schedule schedule);
}
