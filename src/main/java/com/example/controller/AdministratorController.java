package com.example.controller;

import com.example.domain.Administrator;
import com.example.form.AdministratorCreateForm;
import com.example.form.AdministratorLoginForm;
import com.example.service.AdministratorService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理者の Controller.
 *
 * @author shunsei
 */
@Controller
@RequestMapping("/admin")
public class AdministratorController {
    /** 管理者の Service */
    private final AdministratorService service;

    /**
     * コンストラクタ
     *
     * @param service AdministratorService
     */
    public AdministratorController(AdministratorService service) {
        this.service = service;
    }

    /**
     * 管理者登録フォームを表示する.
     *
     * @param form 管理者登録フォームの入力値
     * @return 管理者登録フォームのパス
     */
    @GetMapping("/new")
    public String showCreateForm(AdministratorCreateForm form) {
        return "administrator/insert";
    }

    /**
     * 管理者を登録する.
     *
     * @param form          管理者登録フォームの入力値
     * @param bindingResult バリデーションエラーを保持したオブジェクト
     * @return バリデーションエラー時は管理者登録フォームを表示する。正常時はログイン画面にリダイレクトする。
     */
    @PostMapping("")
    public String create(
            @Valid AdministratorCreateForm form,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return this.showCreateForm(form);
        }

        Administrator administrator = new Administrator();
        BeanUtils.copyProperties(form, administrator);

        this.service.create(administrator);

        return "redirect:/admin/login";
    }

    /**
     * ログインフォームを表示する.
     *
     * @return ログインフォームのパス
     */
    @GetMapping("/login")
    public String showLoginForm(AdministratorLoginForm form) {
        return "administrator/login";
    }

    /**
     * ユーザーを管理者であるか認証する.
     *
     * @param form          AdministratorLoginForm
     * @param bindingResult バリデーション結果を保持したオブジェクト
     * @return バリデーションエラー時と認証失敗はログインフォームを表示する。認証成功時は従業員一覧を表示する。
     */
    @PostMapping("/login")
    public String login(
            @Validated AdministratorLoginForm form,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return this.showLoginForm(form);
        }

        if (!this.service.authenticate(form)) {
            bindingResult.reject("login.error", "メールアドレスまたはパスワードが間違っています");
            return this.showLoginForm(form);
        }

        return "redirect:/employees/list";
    }
}
