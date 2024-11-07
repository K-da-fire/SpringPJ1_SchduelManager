package org.example.schduelmanagerproject1.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.example.schduelmanagerproject1.dto.UserResponseDto;
import org.example.schduelmanagerproject1.entity.Users;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
    parameters.put("name", users.getName());
    parameters.put("password", users.getPassword());
    parameters.put("created_date", users.getCreatedDate());
    parameters.put("updated_date", LocalDate.EPOCH.atTime(LocalTime.MIN));

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
    return new UserResponseDto(key.longValue(), users.getEmail(), users.getName(),
        users.getPassword(), users.getCreatedDate(), users.getUpdatedDate());
  }

  @Override
  public List<UserResponseDto> getAllUsers() {
    String sql = "select * from users";
    return jdbcTemplate.query(sql, usersRowMapper());
  }

  @Override
  public int updateUser(long id, String name, String email, String password) {
    String sql = "update users set name = ?, email = ?, updated_date = ? where user_id = ? and password = ?";
    return jdbcTemplate.update(sql, name, email, LocalDateTime.now(), id, password);
  }

  @Override
  public Users getUserById(long id) {
    String sql = "select * from users where user_id = ?";
    List<Users> result = jdbcTemplate.query(sql, usersRowMapperV2(), id);
    return result.stream().findAny().orElse(null);
  }


  private RowMapper<UserResponseDto> usersRowMapper(){
    return new RowMapper<UserResponseDto>() {

      @Override
      public UserResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new UserResponseDto(
            rs.getLong("user_id"),
            rs.getString("email"),
            rs.getString("name"),
            rs.getString("password"),
            rs.getDate("created_date").toLocalDate().atTime(LocalTime.now()),
            rs.getDate("updated_date").toLocalDate().atTime(LocalTime.now())
        );
      }
    };
  }

  private RowMapper<Users> usersRowMapperV2(){
    return new RowMapper<Users>() {

      @Override
      public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Users(
            rs.getLong("user_id"),
            rs.getString("email"),
            rs.getString("name"),
            rs.getString("password"),
            rs.getTimestamp("created_date").toLocalDateTime(),
            rs.getTimestamp("updated_date").toLocalDateTime()
        );
      }
    };
  }

  @Override
  public Long getMaxUserId() {
    return jdbcTemplate.queryForObject("select max(user_id) from users", Long.class);
  }

  @Override
  public String getPassword(long id) {
    return jdbcTemplate.queryForObject("select password from users where user_id = ?", String.class, id);
  }


}
