package com.example.repository;

import com.example.domain.Employee;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    public Optional<Employee> findById(Integer id) {
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
                    employees
                where
                    id = :id;
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            Employee employee = this.template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
            if (employee == null) {
                return Optional.empty();
            }
            return Optional.of(employee);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
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
