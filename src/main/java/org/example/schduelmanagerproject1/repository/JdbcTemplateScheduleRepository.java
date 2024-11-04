package org.example.schduelmanagerproject1.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateScheduleRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }


  @Override
  public ScheduleResponseDto saveSchedule(Schedule schedule) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("schedule_id");

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("user_id", schedule.getUserId());
    parameters.put("schedule_title", schedule.getScheduleTitle());
    parameters.put("name", schedule.getName());
    parameters.put("password", schedule.getPassword());
    parameters.put("created_date", schedule.getCreatedDate());
    parameters.put("updated_date", LocalDate.now());

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

    return new ScheduleResponseDto(key.longValue(), schedule.getUserId(), schedule.getScheduleTitle(), schedule.getName(), schedule.getPassword(), schedule.getCreatedDate(), schedule.getUpdatedDate());
  }

  @Override
  public List<ScheduleResponseDto> getAllSchedules() {
    return jdbcTemplate.query("select * from schedule", schedulesRowMapper());
  }

  @Override
  public Schedule getScheduleByIdOrElseThrow(Long scheduleId) {
    List<Schedule> result = jdbcTemplate.query("select * from schedule where schedule_id = ?", schedulesRowMapperV2(), scheduleId);
    return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exisit id = " + scheduleId));
  }


  private RowMapper<ScheduleResponseDto> schedulesRowMapper() {
    return new RowMapper<ScheduleResponseDto>() {

      @Override
      public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        LocalDate updatedDate;
        try {
          updatedDate = rs.getDate("updated_date").toLocalDate();
        }catch (Exception e) {
          updatedDate = null;
        }
        return new ScheduleResponseDto(
            rs.getLong("schedule_id"),
            rs.getLong("user_id"),
            rs.getString("schedule_title"),
            rs.getString("name"),
            rs.getString("password"),
            rs.getDate("created_date").toLocalDate(),
            updatedDate
        );
      }
    };
  }

  private RowMapper<Schedule> schedulesRowMapperV2() {
    return new RowMapper<Schedule>() {

      @Override
      public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        LocalDate updatedDate;
        try {
          updatedDate = rs.getDate("updated_date").toLocalDate();
        }catch (Exception e) {
          updatedDate = null;
        }
        return new Schedule(
            rs.getLong("schedule_id"),
            rs.getLong("user_id"),
            rs.getString("schedule_title"),
            rs.getString("name"),
            rs.getString("password"),
            rs.getDate("created_date").toLocalDate(),
            updatedDate
        );
      }
    };
  }



}
