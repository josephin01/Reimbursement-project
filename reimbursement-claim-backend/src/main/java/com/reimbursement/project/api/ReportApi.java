package com.reimbursement.project.api;


import com.reimbursement.project.constant.ApiConstant;
import com.reimbursement.project.dto.FilterDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.text.ParseException;

@CrossOrigin
@RequestMapping(ApiConstant.ADMIN)
public interface ReportApi {
    @PostMapping(ApiConstant.REPORT)
    void createPDF(HttpServletResponse response, @Valid @RequestBody FilterDto filterDto) throws IOException, JRException, ParseException;

}
