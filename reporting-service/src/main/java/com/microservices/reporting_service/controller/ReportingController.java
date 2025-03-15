package com.microservices.reporting_service.controller;

import com.microservices.reporting_service.commons.exceptions.CustomException;
import com.microservices.reporting_service.commons.exceptions.GlobalExceptionHandling;
import com.microservices.reporting_service.commons.response.BaseResponse;
import com.microservices.reporting_service.service.ReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping(path = "/v1/report")
@RequiredArgsConstructor
@Tag(name = "User Service APIs")
public class ReportingController {

    private final GlobalExceptionHandling globalExceptionHandling;
    private final ReportService reportService;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<BaseResponse> createCustomException(CustomException ex) {
        return globalExceptionHandling.createBaseResponse(INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @GetMapping("/daily")
    public Map<String, Object> generateDailyReport(@RequestParam String stockCode) {
        // Sample logic for daily report
        return reportService.generateDailyReport(stockCode);
    }

    @GetMapping("/weekly")
    public Map<String, Object> generateWeeklyReport(@RequestParam String stockCode) {
        // Sample logic for weekly report
        return reportService.generateWeeklyReport(stockCode);

    }
}
