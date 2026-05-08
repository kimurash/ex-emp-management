package com.example.service;

import com.example.domain.Administrator;
import com.example.form.AdministratorLoginForm;
import com.example.repository.AdministratorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 管理者の Service.
 *
 * @author shunsei
 */
@Service
public class AdministratorService {
    /** 管理者の Repository */
    private final AdministratorRepository repository;

    /**
     * コンストラクタ.
     *
     * @param repository AdministratorRepository
     */
    public AdministratorService(AdministratorRepository repository) {
        this.repository = repository;
    }

    /**
     * メールアドレスで管理者を取得する.
     *
     * @param mailAddress メールアドレス
     * @return 管理者情報
     */
    public Optional<Administrator> findByMailAddress(String mailAddress) {
        return this.repository.findByMailAddress(mailAddress);
    }

    /**
     * 管理者を作成する.
     *
     * @param administrator 作成する管理者
     * @return 作成された管理者のID
     */
    public Integer create(Administrator administrator) {
        return this.repository.save(administrator);
    }

    /**
     * ユーザーを管理者であるか認証する.
     *
     * @param form AdministratorLoginForm
     * @return 認証が成功したか
     */
    public boolean authenticate(AdministratorLoginForm form) {
        Optional<Administrator> optional = this.repository.findByMailAddress(form.getMailAddress());
        if (optional.isEmpty()) {
            return false;
        }

        Administrator administrator = optional.get();
        return administrator.authenticate(form.getPassword());
    }
}
