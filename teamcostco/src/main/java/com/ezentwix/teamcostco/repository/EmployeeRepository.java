package com.ezentwix.teamcostco.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezentwix.teamcostco.dto.employee.EmployeeDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class EmployeeRepository {
    private final SqlSessionTemplate sql;

    public EmployeeDTO getByIdAndPw(String id, String pw) {
        Map<String, String> params = Map.of("id", id, "pw", pw);
        return sql.selectOne("Employees.getByIdAndPw", params);
    }

    public EmployeeDTO getById(String id){
        return sql.selectOne("Employees.getById", id);
    }

    public String getPwById(String id) {
        return sql.selectOne("Employees.getPwById", id);
    }

    public List<EmployeeDTO> getEmpList() {
        return sql.selectList("Employees.getAll");
    }

    public EmployeeDTO getEmp(Integer emp_id) {
        return sql.selectOne("Employees.getByEmpId", emp_id);
    }

    public String getToken(String id) {
        return sql.selectOne("Employees.getToken", id);
    }
}
