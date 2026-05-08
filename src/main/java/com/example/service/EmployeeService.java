package com.example.service;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    /**
     * 全ての従業員を取得する.
     *
     * @return List<Employee>
     */
    public List<Employee> findAll() {
        return this.repository.findAll();
    }
}
