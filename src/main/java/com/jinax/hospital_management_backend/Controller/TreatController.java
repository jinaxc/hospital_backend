package com.jinax.hospital_management_backend.Controller;

import com.jinax.hospital_management_backend.Entity.DailyReport;
import com.jinax.hospital_management_backend.Entity.Test;
import com.jinax.hospital_management_backend.Service.DailyReportService;
import com.jinax.hospital_management_backend.Service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : chara
 */
@Api(tags = "TreatController")
@RestController
@RequestMapping("/treat")
public class TreatController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TreatController.class);
    private final TestService testService;
    private final DailyReportService dailyReportService;

    public TreatController(TestService testService, DailyReportService dailyReportService) {
        this.testService = testService;
        this.dailyReportService = dailyReportService;
    }

    @ApiOperation("查找检测单信息")
    @ResponseBody
    @GetMapping("/naTest/{testId}")
    public Test getTest(@PathVariable("testId") Long testId){
        return testService.getTest(testId);
    }

    @ApiOperation("查找每日记录信息")
    @ResponseBody
    @GetMapping("/daily/{dailyId}")
    public DailyReport getDaily(@PathVariable("dailyId") Long dailyId){
        return dailyReportService.findById(dailyId);
    }

    @ApiOperation("添加检测单信息")
    @ResponseBody
    @PostMapping("/naTest")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public Test addTest(Test test){
        LOGGER.info("addTest");
        return testService.addTest(test);
    }

    @ApiOperation("添加每日状态信息")
    @ResponseBody
    @PostMapping("/daily")
    @PreAuthorize("hasAuthority('WARD_NURSE')")
    public DailyReport addDailyReport(DailyReport dailyReport){
        LOGGER.info("addDailyReport");
        return dailyReportService.addDailyReport(dailyReport);
    }

    @ApiOperation("修改检测单信息")
    @ResponseBody
    @PutMapping("/naTest/{testId}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public Map<String, Long> updateTest(@PathVariable("testId") Long testId, Test.Level level){
        Map<String, Long> result = new HashMap<>();
        Test test = testService.updateTest(testId, level.getCode());
        result.put("data",test.getId());
        return result;
    }

    @ApiOperation("修改每日状态信息")
    @ResponseBody
    @PutMapping("/daily/{dailyId}")
    @PreAuthorize("hasAuthority('DOCTOR')")
    public Map<String, Long> updateDaily(@PathVariable("dailyId") Long dailyId, DailyReport.State state){
        Map<String, Long> result = new HashMap<>();
        DailyReport dailyReport = dailyReportService.updateDaily(dailyId, state.getCode());
        result.put("data",dailyReport.getId());
        return result;
    }
}
