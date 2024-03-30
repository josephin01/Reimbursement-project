package com.reimbursement.project.service;

import com.reimbursement.project.dto.FilterDto;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.text.ParseException;

public interface ReportService {
    void exportReport(HttpServletResponse response, FilterDto filterDto) throws IOException, JRException, ParseException;

}
