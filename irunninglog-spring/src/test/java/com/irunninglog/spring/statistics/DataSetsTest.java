package com.irunninglog.spring.statistics;

import com.irunninglog.api.security.IUser;
import com.irunninglog.api.statistics.IDataPoint;
import com.irunninglog.api.statistics.IDataSet;
import com.irunninglog.api.statistics.IStatistics;
import com.irunninglog.api.statistics.IStatisticsService;
import com.irunninglog.spring.AbstractTest;
import com.irunninglog.strava.IStravaRun;
import com.irunninglog.strava.IStravaService;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;

public class DataSetsTest extends AbstractTest {

    private IStatisticsService statisticsService;
    private IStravaService stravaService;

    @Override
    protected void afterBefore(ApplicationContext applicationContext) {
        super.afterBefore(applicationContext);

        statisticsService = applicationContext.getBean(IStatisticsService.class);
        stravaService = applicationContext.getBean(IStravaService.class);
    }

    @Test
    public void getDataSet() {
        int year = LocalDate.now().getYear();

        List<IStravaRun> runs = new ArrayList<>();
        runs.add(run(LocalDateTime.parse((year - 1) + "-09-22T00:00:00"), 16093.4F));
        runs.add(run(LocalDateTime.parse(year + "-09-22T00:00:00"), 16093.4F));
        runs.add(run(LocalDateTime.parse(year + "-09-21T00:00:00"), 16093.4F));
        runs.add(run(LocalDateTime.parse(year + "-08-03T00:00:00"), 16093.4F));
        runs.add(run(LocalDateTime.parse(year + "-08-02T00:00:00"), 16093.4F));
        runs.add(run(LocalDateTime.parse(year + "-08-01T00:00:00"), 16093.4F));
        Mockito.when(stravaService.runs(any(IUser.class))).thenReturn(runs);

        IStatistics statistics = statisticsService.get(null, ZonedDateTime.now().getOffset().getTotalSeconds() / 60 * -1);

        assertNotNull(statistics.getDataSets());
        assertEquals(2, statistics.getDataSets().size());

        IDataSet points = statistics.getDataSets().get("points");
        assertNotNull(points);
        assertEquals(3, points.getPoints().size());

        Iterator<IDataPoint> pointIterator = statistics.getDataSets().get("points").getPoints().iterator();
        IDataPoint point1 = pointIterator.next();
        assertEquals((year-1) + "-09", point1.getDate());
        assertEquals("Sep " + (year - 1), point1.getLabel());
        assertEquals("10", point1.getValue());
        assertEquals("10 mi", point1.getValueFormatted());

        IDataPoint point2 = pointIterator.next();
        assertEquals(year + "-08", point2.getDate());
        assertEquals("Aug " + year, point2.getLabel());
        assertEquals("30", point2.getValue());
        assertEquals("30 mi", point2.getValueFormatted());

        IDataSet totals = statistics.getDataSets().get("totals");
        assertNotNull(totals);
        assertEquals(3, totals.getPoints().size());

        Iterator<IDataPoint> totalsIterator = statistics.getDataSets().get("totals").getPoints().iterator();
        IDataPoint total1 = totalsIterator.next();
        assertEquals((year - 1) + "-09", total1.getDate());
        assertEquals("Sep " + (year - 1), total1.getLabel());
        assertEquals("10", total1.getValue());
        assertEquals("10 mi", total1.getValueFormatted());

        IDataPoint total2 = totalsIterator.next();
        assertEquals(year + "-08", total2.getDate());
        assertEquals("Aug " + year, total2.getLabel());
        assertEquals("40", total2.getValue());
        assertEquals("40 mi", total2.getValueFormatted());
    }

}