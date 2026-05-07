package com.example.repository;

import com.example.domain.Administrator;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * administrator テーブルの Repository.
 *
 * @author shunsei
 */
@Repository
public class AdministratorRepository {
    /** 名前でクエリに値を埋め込むためのオブジェクト */
    private final NamedParameterJdbcTemplate template;

    /**
     * コンストラクタ
     *
     * @param template NamedParameterJdbcTemplate
     */
    public AdministratorRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    /**
     * 管理者を挿入する
     *
     * @param administrator 挿入する管理者
     * @return 挿入された管理者のID
     */
    public Integer save(Administrator administrator) {
        String sql = """
                insert into
                    administrators(name, mail_address, password)
                values
                    (:name, :mailAddress, :password);
                """;
        SqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String[] keyColumnNames = {"id"};
        this.template.update(sql, param, keyHolder, keyColumnNames);

        return keyHolder.getKeyAs(Integer.class);
    }
}
