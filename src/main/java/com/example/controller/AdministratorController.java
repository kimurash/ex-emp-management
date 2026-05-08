package com.example.controller;

import com.example.domain.Administrator;
import com.example.form.AdministratorCreateForm;
import com.example.form.AdministratorLoginForm;
import com.example.service.AdministratorService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

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
    /** session スコープを保持するオブジェクト */
    private final HttpSession session;

    /**
     * コンストラクタ
     *
     * @param service AdministratorService
     */
    public AdministratorController(
            HttpSession session,
            AdministratorService service
    ) {
        this.session = session;
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

        Optional<Administrator> optional = this.service.findByMailAddress(form.getMailAddress());

        if (optional.isEmpty()) {
            bindingResult.reject("login.error");
            return this.showLoginForm(form);
        }

        Administrator administrator = optional.get();
        if (!administrator.authenticate(form.getPassword())) {
            bindingResult.reject("login.error");
            return this.showLoginForm(form);
        }

        this.session.setAttribute("administratorName", administrator.getName());
        return "redirect:/employees";
    }

    @PostMapping("/logout")
    public String logout() {
        // removeAttribute() で削除するとセッションID自体は残る
        // administratorName 以外の値は残るのでセッションハイジャックの危険性がある
        this.session.invalidate();
        return "redirect:/admin/login";
    }
}
