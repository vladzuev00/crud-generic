package by.nhorushko.crudgenerictest.service;

import by.nhorushko.crudgeneric.exception.AppNotFoundException;
import by.nhorushko.crudgenerictest.domain.dto.Tracker;
import by.nhorushko.crudgenerictest.domain.entity.TrackerEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static java.lang.Long.MAX_VALUE;
import static java.lang.Long.MIN_VALUE;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class TrackerServiceIT {

    @Autowired
    private TrackerServiceCRUD service;

    @Autowired
    private EntityManager entityManager;

    @Test
    @Sql("classpath:data/data.sql")
    public void trackerShouldBeFoundById() {
        final Optional<Tracker> optionalActual = this.service.getByIdOptional(1L);
        assertTrue(optionalActual.isPresent());
        final Tracker actual = optionalActual.get();
        final Tracker expected = new Tracker(1L, "355234055650192", "+37257063997");
        assertEquals(expected, actual);
    }

    @Test
    public void trackerShouldNotBeFoundById() {
        final Optional<Tracker> optionalActual = this.service.getByIdOptional(MAX_VALUE);
        assertTrue(optionalActual.isEmpty());
    }

    @Test
    @Sql("classpath:data/data.sql")
    public void trackersShouldBeFoundByIds() {
        final List<Long> givenIds = List.of(1L, 2L, 3L);

        final List<Tracker> foundTrackers = this.service.getById(givenIds);
        final List<Long> actual = foundTrackers.stream()
                .map(Tracker::getId)
                .collect(toList());

        assertEquals(givenIds, actual);
    }

    @Test
    public void trackersShouldNotBeFoundByIds() {
        final List<Long> givenIds = List.of(1L, 2L, 3L);

        final List<Tracker> foundTrackers = this.service.getById(givenIds);
        assertTrue(foundTrackers.isEmpty());
    }

    @Test
    @Sql("classpath:data/data.sql")
    public void trackerShouldBeGotById() {
        final Tracker actual = this.service.getById(1L);
        final Tracker expected = new Tracker(1L, "355234055650192", "+37257063997");
        assertEquals(expected, actual);
    }

    @Test(expected = AppNotFoundException.class)
    public void trackerShouldNotBeGotById() {
        this.service.getById(MAX_VALUE);
    }

    @Test
    @Sql("classpath:data/data.sql")
    public void trackerWithGivenIdShouldExist() {
        final Long givenId = 3L;
        assertTrue(this.service.isExist(givenId));
    }

    @Test
    public void trackerWithGivenIdShouldNotExist() {
        final Long givenId = MIN_VALUE;
        assertFalse(this.service.isExist(givenId));
    }

    @Test
    @Sql("classpath:data/data.sql")
    public void trackerShouldBeUpdated() {
        final Tracker givenTrackerToUpdate = new Tracker(1L, "3550260722834532", "37257591222");
        this.service.update(givenTrackerToUpdate);

        final TrackerEntity updatedTracker = findTrackerFromDB(givenTrackerToUpdate.getId());
        assertEquals("3550260722834532", updatedTracker.getImei());
        assertEquals("37257591222", updatedTracker.getPhoneNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void trackerShouldNotBeUpdatedBecauseOfIdIsNull() {
        final Tracker givenTrackerToUpdate = new Tracker(null, "3550260722834532", "37257591222");
        this.service.update(givenTrackerToUpdate);
    }

    @Test(expected = IllegalArgumentException.class)
    public void trackerShouldNotBeUpdatedBecauseOfIdIsZero() {
        final Tracker givenTrackerToUpdate = new Tracker(0L, "3550260722834532", "37257591222");
        this.service.update(givenTrackerToUpdate);
    }

    @Test
    @Sql("classpath:data/data.sql")
    public void trackerShouldBePartialUpdated() {
        final Long givenTrackerId = 1L;

        final TrackerImei trackerImei = new TrackerImei("12345678912345678912");

        this.service.updatePartial(givenTrackerId, trackerImei);

        final TrackerEntity updatedTracker = findTrackerFromDB(givenTrackerId);
        entityManager.flush();
        entityManager.refresh(updatedTracker);
        assertEquals(trackerImei.getImei(), updatedTracker.getImei());
    }

    @Test
    @Sql("classpath:data/data.sql")
    public void trackerShouldBeDeletedById() {
        final Long givenTrackerId = 1L;

        this.service.delete(givenTrackerId);

        assertNull(findTrackerFromDB(givenTrackerId));
    }

    @Test
    public void trackerShouldBeSaved() {
        final Tracker givenTracker = new Tracker(null, "355234055650192", "+3197011460885");

        final Tracker savedTracker = this.service.save(givenTracker);
        assertNotNull(savedTracker.getId());

        final TrackerEntity savedTrackedFromDB = findTrackerFromDB(savedTracker.getId());
        assertEquals(givenTracker.getImei(), savedTrackedFromDB.getImei());
        assertEquals(givenTracker.getPhoneNumber(), savedTrackedFromDB.getPhoneNumber());
    }

    @Test
    public void trackersShouldBeSaved() {
        final List<Tracker> givenTrackers = List.of(
                new Tracker(null, "355234055650192", "+3197011460885"),
                new Tracker(null, "355026070834532", "+3197011405848"));

        final List<Tracker> savedTrackers = this.service.saveAll(givenTrackers);
        assertTrue(savedTrackers.stream().allMatch(tracker -> tracker.getId() != null));

        final List<TrackerEntity> savedTrackersFromDB = savedTrackers
                .stream()
                .map(savedTracker -> findTrackerFromDB(savedTracker.getId()))
                .collect(toList());

        range(0, givenTrackers.size())
                .forEach(i -> {
                    assertEquals(givenTrackers.get(i).getImei(), savedTrackersFromDB.get(i).getImei());
                    assertEquals(givenTrackers.get(i).getPhoneNumber(), savedTrackersFromDB.get(i).getPhoneNumber());
                });
    }

    private static class TrackerImei {
        private final String imei;

        TrackerImei(String imei) {
            this.imei = imei;
        }

        String getImei() {
            return this.imei;
        }
    }

    protected TrackerEntity findTrackerFromDB(Long id) {
        this.entityManager.flush();
        this.entityManager.clear();
        return this.entityManager.find(TrackerEntity.class, id);
    }
}

