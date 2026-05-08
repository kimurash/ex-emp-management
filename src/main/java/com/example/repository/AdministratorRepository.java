package com.example.repository;

import com.example.domain.Administrator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * administrator テーブルの Repository.
 *
 * @author shunsei
 */
@Repository
public class AdministratorRepository {
    /** 名前でクエリに値を埋め込むためのオブジェクト */
    private final NamedParameterJdbcTemplate template;
    /** administrators テーブルの1レコードを Administrator クラスに変換するマッパー */
    private static final RowMapper<Administrator> ADMIN_ROW_MAPPER = new BeanPropertyRowMapper<>(Administrator.class);

    /**
     * コンストラクタ.
     *
     * @param template NamedParameterJdbcTemplate
     */
    public AdministratorRepository(NamedParameterJdbcTemplate template) {
        this.template = template;
    }

    /**
     * メールアドレスで管理者を検索する.
     *
     * @param mailAddress メールアドレス
     * @return Optional<Administrator>
     */
    public Optional<Administrator> findByMailAddress(String mailAddress) {
        String sql = """
                select
                    id, name, mail_address, password
                from
                    administrators
                where
                    mail_address = :mailAddress;
                """;
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("mailAddress", mailAddress);

        try {
            Administrator administrator = this.template.queryForObject(sql, param, ADMIN_ROW_MAPPER);
            if (administrator == null) { // QUESTION: なぜか null の可能性がある
                return Optional.empty();
            }
            return Optional.of(administrator);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    /**
     * 管理者を挿入する.
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
