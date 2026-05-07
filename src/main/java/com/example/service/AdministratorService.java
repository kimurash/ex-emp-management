package com.example.service;

import com.example.domain.Administrator;
import com.example.repository.AdministratorRepository;
import org.springframework.stereotype.Service;

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
     * コンストラクタ
     *
     * @param repository AdministratorRepository
     */
    public AdministratorService(AdministratorRepository repository) {
        this.repository = repository;
    }

    public Integer create(Administrator administrator) {
        return this.repository.save(administrator);
    }
}
