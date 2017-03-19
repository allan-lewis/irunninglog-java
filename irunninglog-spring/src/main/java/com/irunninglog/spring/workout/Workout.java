package com.irunninglog.spring.workout;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.irunninglog.api.Privacy;
import com.irunninglog.api.workout.IWorkout;
import com.irunninglog.api.workout.IWorkoutData;
import com.irunninglog.spring.json.PrivacyDeserializer;
import com.irunninglog.spring.json.PrivacySerializer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
final class Workout implements IWorkout {

    private long id;
    private String date;
    private String distance;
    private String duration;
    private String pace;
    private String title;
    @JsonSerialize(using = PrivacySerializer.class)
    @JsonDeserialize(using = PrivacyDeserializer.class)
    private Privacy privacy;

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = WorkoutData.class)
    private IWorkoutData route;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = WorkoutData.class)
    private IWorkoutData run;
    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, defaultImpl = WorkoutData.class)
    private IWorkoutData shoe;

    @Override
    public IWorkout setId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public IWorkout setPrivacy(Privacy privacy) {
        this.privacy = privacy;
        return this;
    }

    @Override
    public IWorkout setDate(String date) {
        this.date = date;
        return this;
    }

    @Override
    public IWorkout setDistance(String distance) {
        this.distance = distance;
        return this;
    }

    @Override
    public IWorkout setDuration(String duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public IWorkout setPace(String pace) {
        this.pace = pace;
        return this;
    }

    @Override
    public IWorkout setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public IWorkout setRoute(IWorkoutData data) {
        this.route = data;
        return this;
    }

    @Override
    public IWorkout setRun(IWorkoutData data) {
        this.run = data;
        return this;
    }

    @Override
    public IWorkout setShoe(IWorkoutData data) {
        this.shoe = data;
        return this;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getDistance() {
        return distance;
    }

    @Override
    public String getDuration() {
        return duration;
    }

    @Override
    public String getPace() {
        return pace;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Privacy getPrivacy() {
        return privacy;
    }

    @Override
    public IWorkoutData getRoute() {
        return route;
    }

    @Override
    public IWorkoutData getRun() {
        return run;
    }

    @Override
    public IWorkoutData getShoe() {
        return shoe;
    }

}
