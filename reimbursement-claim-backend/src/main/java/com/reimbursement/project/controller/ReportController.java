package com.reimbursement.project.controller;

import com.reimbursement.project.api.ReportApi;
import com.reimbursement.project.dto.FilterDto;
import com.reimbursement.project.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class ReportController implements ReportApi {


    private final ReportService reportService;

    @Override
    public void createPDF(HttpServletResponse response,FilterDto filterDto ) throws IOException, JRException, ParseException {
        reportService.exportReport(response,filterDto);
    }

}


