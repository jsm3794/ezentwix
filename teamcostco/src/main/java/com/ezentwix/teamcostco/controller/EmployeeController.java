package com.ezentwix.teamcostco.controller;

import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezentwix.teamcostco.config.BCryptUtils;
import com.ezentwix.teamcostco.dto.employee.EmployeeDTO;
import com.ezentwix.teamcostco.dto.filter.EmployeeFilterDTO;
import com.ezentwix.teamcostco.pagination.PaginationResult;
import com.ezentwix.teamcostco.service.EmailService;
import com.ezentwix.teamcostco.service.EmployeeDetailService;
import com.ezentwix.teamcostco.service.EmployeeFixService;
import com.ezentwix.teamcostco.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeDetailService employeeDetailService;
    private final EmployeeFixService employeeFixService;
    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @GetMapping("")
    public String showEmployee(Model model,
            @RequestParam(defaultValue = "") String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int size,
            @ModelAttribute EmployeeFilterDTO empFilterDTO) {

        employeeService.configureModel(model);

        System.out.println(empFilterDTO);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.convertValue(empFilterDTO, Map.class);
        PaginationResult<EmployeeDTO> result = employeeService.getPage(query, page, size, map);

        model.addAttribute("empList", result.getData());
        model.addAttribute("pageDetail", result.getPageDetails());
        return "index";
    }

    @GetMapping("/detail/{emp_id}")
    public String getMethodName(
            @PathVariable(name = "emp_id") Integer emp_id,
            HttpSession session, Model model) {
        if (emp_id == null) {
            emp_id = 0;
        }
        employeeDetailService.configureModel(model);
        model.addAttribute("empDetail", employeeDetailService.getOne(emp_id));
        return "index";
    }

    @GetMapping("/modify/{emp_id}")
    public String empFix(@PathVariable Integer emp_id, Model model) {
        employeeFixService.configureModel(model);

        model.addAttribute("getOne", employeeFixService.getOne(emp_id));

        return "index";
    }

    @PostMapping("/modify")
    public String empFix(@ModelAttribute EmployeeDTO empDTO, @RequestParam Integer emp_id) {
        // 기존 직원 정보를 가져옵니다.
        EmployeeDTO existingEmployee = employeeFixService.getOne(emp_id);

        // 이메일이 변경되었는지 확인합니다.
        boolean emailChanged = !existingEmployee.getEmp_email().equals(empDTO.getEmp_email());

        if (emailChanged) {
            // 새로운 토큰 생성
            String newToken = UUID.randomUUID().toString();

            // 새로운 이메일 인증 토큰을 데이터베이스에 저장합니다.
            employeeFixService.updateEmailVerificationToken(existingEmployee.getLogin_id(), newToken);

            // 새로운 토큰으로 이메일 전송
            emailService.sendnewToken(empDTO.getEmp_email(), newToken);
        }

        // 직원 정보 업데이트
        empDTO.setEmp_id(emp_id);
        empDTO.setLogin_pw(BCryptUtils.hashPassword(empDTO.getLogin_pw())); // 비밀번호 해시화
        employeeFixService.fix(empDTO); // 데이터베이스에 업데이트

        return "redirect:/employee/detail/" + emp_id; // 직원 상세 페이지로 리다이렉트
    }

    @GetMapping("/newtoken")
    public String verifyEmail(@RequestParam("token") String token, Model model) {

        if (employeeFixService.verifyEmail(token)) {
            model.addAttribute("message", "이메일 인증이 완료되었습니다.");
        } else {
            model.addAttribute("message", "인증 실패. 유효하지 않은 토큰입니다.");
        }
        return "login";
    }
}
