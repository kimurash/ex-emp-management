package com.example.controller;

import com.example.domain.Employee;
import com.example.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 従業員の Controller.
 *
 * @author shunsei
 */
@Controller
@RequestMapping("/employees")
public class EmployeeController {
    /** 従業員の Service */
    private final EmployeeService service;

    /**
     * コンストラクタ.
     *
     * @param service EmployeeService
     */
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    /**
     * 従業員の一覧画面を表示する.
     *
     * @return 従業員一覧画面のパス
     */
    @GetMapping("/list")
    public String showList(Model model) {
        List<Employee> employees = this.service.findAll();
        model.addAttribute("employees", employees);

        return "employee/list";
    }
}
