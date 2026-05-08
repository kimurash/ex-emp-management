package com.example.service;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 従業員の Service.
 */
@Service
public class EmployeeService {
    /** 従業員の Repository */
    private final EmployeeRepository repository;

    /**
     * コンストラクタ.
     *
     * @param repository EmployeeRepository
     */
    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Optional<Employee> findById(Integer id) {
        return this.repository.findById(id);
    }

    /**
     * 全ての従業員を取得する.
     *
     * @return List<Employee>
     */
    public List<Employee> findAll() {
        return this.repository.findAll();
    }

    /**
     * 従業員の扶養人数を更新する
     *
     * @param id              従業員ID
     * @param dependentsCount 扶養人数
     */
    public void updateDependentsCount(Integer id, Integer dependentsCount) {
        this.repository.updateDependentsCount(id, dependentsCount);
    }
}
