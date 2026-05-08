package com.example.repository;

import com.example.domain.Employee;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 従業員の Repository.
 *
 * @author shunsei
 */
@Repository
public class EmployeeRepository {
    /** 名前でクエリに値を埋め込むためのオブジェクト */
    private final NamedParameterJdbcTemplate template;
    /** employees テーブルの1レコードを Employee クラスに変換するマッパー */
    private static final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = new BeanPropertyRowMapper<>(Employee.class);

    /**
     * コンストラクタ
     *
     * @param template NamedParameterJdbcTemplate
     */
    public EmployeeRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    /**
     * 全ての従業員を取得する.
     *
     * @return List<Employee>
     */
    public List<Employee> findAll() {
        String sql = """
                select
                    id,
                    name,
                    image,
                    gender,
                    hire_date,
                    mail_address,
                    zip_code,
                    address,
                    salary,
                    characteristics,
                    dependents_count
                from
                    employees;
                """;

        return this.template.query(sql, EMPLOYEE_ROW_MAPPER);
    }
}
