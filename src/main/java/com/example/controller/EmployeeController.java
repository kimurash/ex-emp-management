package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 従業員の Controller.
 *
 * @author shunsei
 */
@Controller
@RequestMapping("/employees")
public class EmployeeController {
    /**
     * 従業員の一覧画面を表示する.
     *
     * @return 従業員一覧画面のパス
     */
    @GetMapping("/list")
    public String showList() {
        return "employee/list";
    }
}
