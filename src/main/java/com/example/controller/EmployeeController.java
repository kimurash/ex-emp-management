package com.example.controller;

import com.example.domain.Employee;
import com.example.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/detail")
    public String detail(Integer id, Model model) {
        Optional<Employee> optional = this.service.findById(id);
        if (optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "従業員が見つかりません");
        }
        model.addAttribute("employee", optional.get());

        return "employee/detail";
    }
}
