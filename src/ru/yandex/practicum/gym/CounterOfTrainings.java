package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {
    private final Coach coach;
    private final int trainingsCount;

    public CounterOfTrainings(Coach coach, int trainingsCount) {
        this.coach = coach;
        this.trainingsCount = trainingsCount;
    }

    public Coach getCoach() {
        return this.coach;
    }

    public int getTrainingsCount() {
        return this.trainingsCount;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        return o.trainingsCount - this.trainingsCount;
    }
}
