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
    /** クエリに名前で値を埋め込むためのオブジェクト */
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
     * ID で従業員を取得する.
     *
     * @param id 従業員ID
     * @return 従業員
     */
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
     * 従業員数を取得する
     *
     * @return 従業員の総数
     */
    public int count() {
        String sql = "select count(*) from employees;";
        // NullPointerException は非検査例外なので捕捉しない
        return this.template.getJdbcOperations().queryForObject(sql, Integer.class);
    }

    /**
     * 全ての従業員を取得する.
     *
     * @return 従業員一覧
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
                    employees
                order by
                    name;
                """;

        return this.template.query(sql, EMPLOYEE_ROW_MAPPER);
    }

    /**
     * 特定の範囲の従業員を取得する
     *
     * @param offset 何件飛ばすか
     * @param limit  何件取得するか
     * @return 従業員一覧
     */
    public List<Employee> findAll(int offset, int limit) {
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
                order by
                    name
                limit
                    :limit
                offset
                    :offset;
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("limit", limit)
                .addValue("offset", offset);
        return this.template.query(sql, param, EMPLOYEE_ROW_MAPPER);
    }

    /**
     * 従業員の扶養人数を更新する
     *
     * @param id              従業員ID
     * @param dependentsCount 扶養人数
     */
    public void updateDependentsCount(Integer id, Integer dependentsCount) {
        String sql = """
                update
                    employees
                set
                    dependents_count = :dependentsCount
                where
                    id = :id;
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("dependentsCount", dependentsCount);
        this.template.update(sql, param);
    }
}
