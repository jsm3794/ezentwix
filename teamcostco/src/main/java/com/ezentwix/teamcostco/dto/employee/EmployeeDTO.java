package com.ezentwix.teamcostco.dto.employee;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;

@Data
public class EmployeeDTO {
    private Integer emp_id;
    private String emp_name;
    private String emp_email;
    private String phone_number;
    private String login_id;
    private String login_pw;
    private String job_title;
    private LocalDate birthday;
    private String gender;
    private String address;
    private String detail_address;
    private Integer post_number;
    private Date hire_date;
    private Date join_date;
}