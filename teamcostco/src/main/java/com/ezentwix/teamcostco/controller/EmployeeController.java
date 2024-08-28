package com.ezentwix.teamcostco.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezentwix.teamcostco.service.EmployeeDetailService;
import com.ezentwix.teamcostco.service.EmployeeService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeDetailService employeeDetailService;

    @GetMapping("/emp_list")
    public String showEmployee(Model model) {
        employeeService.configureModel(model);
        model.addAttribute("empList", employeeService.getEmpList());
        return "index";
    }

    @GetMapping("/emp_detail")
    public String getMethodName(
            @RequestParam(name = "emp_id", required = false) Integer emp_id,
            HttpSession session, Model model) {
        if (emp_id == null) {
            emp_id = 0;
        }
        employeeDetailService.configureModel(model);
        model.addAttribute("empDetail", employeeDetailService.getEmp(emp_id));
        return "index";
    }
}
