package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable;

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> sessionsOfTheDay;
        ArrayList<TrainingSession> sessionsOfTheTime;
        if (timetable.containsKey(day)) {
            sessionsOfTheDay = timetable.get(day);
        } else {
            sessionsOfTheDay = new TreeMap<>();
            timetable.put(day, sessionsOfTheDay);
        }
        if (sessionsOfTheDay.containsKey(time)) {
            sessionsOfTheTime = sessionsOfTheDay.get(time);
        } else {
            sessionsOfTheTime = new ArrayList<>();
            sessionsOfTheDay.put(time, sessionsOfTheTime);
        }
        sessionsOfTheTime.add(trainingSession);
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingSessionsForDay = timetable.get(dayOfWeek);
        return trainingSessionsForDay.get(timeOfDay);
    }
}
