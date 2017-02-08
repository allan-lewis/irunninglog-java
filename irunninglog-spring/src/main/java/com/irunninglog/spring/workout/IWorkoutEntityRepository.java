package com.irunninglog.spring.workout;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
public interface IWorkoutEntityRepository extends CrudRepository<WorkoutEntity, Long> {

    List<WorkoutEntity> findByProfileId(long profileId);

    @Query(value = "select w from WorkoutEntity w where w.date >= :startDate and w.date <= :endDate and w.profile.id = :profileId")
    List<WorkoutEntity> findByDateRange(@Param("profileId") long profileId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query(value = "select sum(w.distance) as sum from WorkoutEntity w where w.profile.id = :profileId and  w.route.id = :routeId")
    BigDecimal routeMileage(@Param("profileId") long profileId, @Param("routeId") long routeId);

    @Query(value = "select sum(w.distance) as sum from WorkoutEntity w where w.profile.id = :profileId and w.run.id = :runId")
    BigDecimal runMileage(@Param("profileId") long profileId, @Param("runId") long runId);

    @Query(value = "select sum(w.distance) as sum from WorkoutEntity w where w.profile.id = :profileId and w.shoe.id = :shoeId")
    BigDecimal shoeMileage(@Param("profileId") long profileId, @Param("shoeId") long shoeId);

    @Query(value = "select sum(w.distance) as sum from WorkoutEntity w where w.profile.id = :profileId")
    BigDecimal sumAll(@Param("profileId") long profileId);

    @Query(value = "select sum(w.distance) as sum from WorkoutEntity w where w.date >= :startDate and w.profile.id = :profileId")
    BigDecimal sumFrom(@Param("startDate") LocalDate startDate, @Param("profileId") long profileId);

    @Query(value = "select sum(w.distance) as sum from WorkoutEntity w where w.date <= :endDate and w.profile.id = :profileId")
    BigDecimal sumTo(@Param("endDate") LocalDate endDate, @Param("profileId") long profileId);

    @Query(value = "select sum(w.distance) as sum from WorkoutEntity w where w.date >= :startDate and w.date <= :endDate and w.profile.id = :profileId")
    BigDecimal sumBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("profileId") long profileId);

    @Query(value = "select max(w.date) as ddate from WorkoutEntity w where w.profile.id = :id and w.date < :date")
    LocalDate getFirstWorkoutDateBefore(@Param("id") long id, @Param("date") LocalDate date);

    @Query(value = "select min(w.date) as ddate from WorkoutEntity w where w.profile.id = :id and w.date > :date")
    LocalDate getFirstWorkoutDateAfter(@Param("id") long id, @Param("date") LocalDate date);

}