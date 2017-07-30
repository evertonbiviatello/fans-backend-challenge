package com.fans.challenge.service;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fans.challenge.domain.MonitoringReport;
import com.fans.challenge.domain.Report;
import com.fans.challenge.repository.MonitoringRepository;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MonitoringRepositoryTest {

	@Autowired
	public MonitoringRepository monitoringRepository;

	@Test
	public void testMonitoringReportSave() {
		Report newReport = new Report("READY", new Date(), 55.0, "http://www.example.com", 2000L);
		monitoringRepository.save(newReport);

		MonitoringReport report = monitoringRepository.find();
		assertNotNull(report);
		assertThat(report.getReportList().size(), greaterThan(0));
	}

	@After
	public void tearDown() {
		monitoringRepository.clearData();
	}

}
