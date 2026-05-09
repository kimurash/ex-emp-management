package com.example.controller;

import com.example.domain.Employee;
import com.example.form.EmployeeDependentsCountUpdateForm;
import com.example.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    /** 1ページあたりの人数 */
    private static final int PAGE_SIZE = 10;

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
     * @param page 何番目のページか
     * @return 従業員一覧画面のパス
     */
    @GetMapping("")
    public String showList(@RequestParam(defaultValue = "1") Integer page, Model model) {
        List<Employee> employees = this.service.findByPage(page, PAGE_SIZE);
        model.addAttribute("employees", employees);

        int totalCount = this.service.getTotalCount();
        int totalPages = (int) Math.ceil(totalCount * 1.0 / PAGE_SIZE);
        model.addAttribute("totalPages", totalPages);

        model.addAttribute("currentPage", page);

        return "employee/list";
    }

    /**
     * 従業員の詳細画面を表示する.
     *
     * @param id    従業員ID
     * @param model request スコープを保持するオブジェクト
     * @return 詳細画面のパス
     */
    @GetMapping("/detail")
    public String showDetail(Integer id, Model model) {
        Optional<Employee> optional = this.service.findById(id);
        if (optional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "従業員が見つかりません");
        }
        model.addAttribute("employee", optional.get());

        return "employee/detail";
    }

    /**
     * 従業員の扶養人数を更新する.
     *
     * @param form          扶養人数更新フォーム
     * @param bindingResult BindingResult
     * @param model         request スコープを保持するオブジェクト
     * @return バリデーションエラー時は詳細画面を表示する。正常時は従業員一覧画面へリダイレクトする。
     */
    @PostMapping("/detail")
    public String updateDependentsCount(
            @Validated EmployeeDependentsCountUpdateForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            Optional<Employee> optional = this.service.findById(form.getId());
            if (optional.isPresent()) {
                model.addAttribute("employee", optional.get());
            }
            return "employee/detail";
        }

        this.service.updateDependentsCount(
                form.getId(),
                form.getDependentsCount()
        );

        return "redirect:/employees";
    }
}
