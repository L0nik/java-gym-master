package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable;

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TreeMap<TimeOfDay, TrainingSession> sessionsOfTheDay;
        if (timetable.containsKey(day)) {
            sessionsOfTheDay = timetable.get(day);
        } else {
            sessionsOfTheDay = new TreeMap<>();
            timetable.put(day, sessionsOfTheDay);
        }
        sessionsOfTheDay.put(trainingSession.getTimeOfDay(), trainingSession);
    }

    public /* непонятно, что возвращать */ getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }

    public /* непонятно, что возвращать */ getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
    }
}
