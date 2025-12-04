package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDayNoSessions() {
        Timetable timetable = new Timetable();

        for (DayOfWeek day : DayOfWeek.values()) {
            Assertions.assertNull(timetable.getTrainingSessionsForDay(day));
        }
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingSessionsForMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        Assertions.assertEquals(1, trainingSessionsForMonday.size());
        ArrayList<TrainingSession> trainingSessionsForTime = trainingSessionsForMonday.get(new TimeOfDay(13, 0));
        Assertions.assertEquals(1, trainingSessionsForTime.size());

        //Проверить, что за вторник не вернулось занятий
        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertEquals(2, timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).size());
        TimeOfDay[] timeOfDayArray = new TimeOfDay[2];
        int i = 0;
        for (TimeOfDay timeOfDay : timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).navigableKeySet()) {
            timeOfDayArray[i] = timeOfDay;
            i++;
        }
        Assertions.assertEquals(new TimeOfDay(13, 0), timeOfDayArray[0]);
        Assertions.assertEquals(new TimeOfDay(20, 0), timeOfDayArray[1]);

        // Проверить, что за вторник не вернулось занятий
        Assertions.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessionsAtTheSameTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession trainingSession1 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession2 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(
                1,
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size()
        );

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertNull(
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0))
        );
    }

    @Test
    void testGetTrainingSessionsForDayAndTimeMultipleSessionsAtTheSameTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession trainingSession1 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession2 = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);

        //Проверить, что за понедельник в 13:00 вернулось два занятия
        Assertions.assertEquals(
                2,
                timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0)).size()
        );
    }

    @Test
    void testGetCountByCoachesNoTrainingSessions() {

        Timetable timetable = new Timetable();
        ArrayList<CounterOfTrainings> counterOfTrainingsList = timetable.getCountByCoaches();
        //Проверить, что вернулся пустой список
        Assertions.assertTrue(counterOfTrainingsList.isEmpty());
    }

    @Test
    void testGetCountByCoachesSingleCoach() {

        Timetable timetable = new Timetable();

        Group group1 = new Group("Группа 1", Age.CHILD, 45);
        Group group2 = new Group("Группа 2", Age.ADULT, 30);
        Group group3 = new Group("Группа 3", Age.ADULT, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession trainingSession1 = new TrainingSession(group1, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession2 = new TrainingSession(group2, coach,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession3 = new TrainingSession(group3, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);
        timetable.addNewTrainingSession(trainingSession3);

        ArrayList<CounterOfTrainings> counterOfTrainingsList = timetable.getCountByCoaches();
        //Проверить, что вернулся 1 тренер
        Assertions.assertEquals(1, counterOfTrainingsList.size());
        //Проверить, что вернулся правильный тренер
        Assertions.assertEquals(coach, counterOfTrainingsList.get(0).getCoach());
        //Проверить, что у тренера 3 тренировки
        Assertions.assertEquals(3, counterOfTrainingsList.get(0).getTrainingsCount());
    }

    @Test
    void testGetCountByCoachesMultipleCoaches() {

        Timetable timetable = new Timetable();

        Group group1 = new Group("Группа 1", Age.CHILD, 45);
        Group group2 = new Group("Группа 2", Age.ADULT, 30);
        Group group3 = new Group("Группа 3", Age.ADULT, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Евгений", "Геннадьевич");
        TrainingSession trainingSession1 = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession2 = new TrainingSession(group2, coach1,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSession3 = new TrainingSession(group3, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(trainingSession1);
        timetable.addNewTrainingSession(trainingSession2);
        timetable.addNewTrainingSession(trainingSession3);

        ArrayList<CounterOfTrainings> counterOfTrainingsList = timetable.getCountByCoaches();
        //Проверить, что вернулись 2 тренера
        Assertions.assertEquals(2, counterOfTrainingsList.size());
        //Проверить, что первым вернулся coach1 с 2мя тренировками
        Assertions.assertEquals(coach1, counterOfTrainingsList.get(0).getCoach());
        Assertions.assertEquals(2, counterOfTrainingsList.get(0).getTrainingsCount());
        //Проверить, что вторым вернулся coach2 с 1 тренировкой
        Assertions.assertEquals(coach2, counterOfTrainingsList.get(1).getCoach());
        Assertions.assertEquals(1, counterOfTrainingsList.get(1).getTrainingsCount());
    }
}
