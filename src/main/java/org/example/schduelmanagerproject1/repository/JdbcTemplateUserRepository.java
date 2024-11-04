package org.example.schduelmanagerproject1.repository;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.example.schduelmanagerproject1.dto.UserResponseDto;
import org.example.schduelmanagerproject1.entity.Users;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateUserRepository implements UserRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateUserRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  @Override
  public UserResponseDto saveUsers(Users users) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    jdbcInsert.withTableName("users").usingGeneratedKeyColumns("user_id");

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("email", users.getEmail());
    parameters.put("password", users.getPassword());
    parameters.put("name", users.getName());
    parameters.put("created_date", users.getCreatedDate());
    parameters.put("updated_date", null);

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
    return new UserResponseDto(key.longValue(), users.getEmail(), users.getPassword(), users.getName(), users.getCreatedDate(), users.getUpdatedDate());
  }
}
