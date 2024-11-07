package org.example.schedulemanagerproject1.schedule.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.example.schedulemanagerproject1.schedule.dto.ScheduleResponseDto;
import org.example.schedulemanagerproject1.schedule.entity.Schedule;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;


@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

  private final JdbcTemplate jdbcTemplate;

  public JdbcTemplateScheduleRepository(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
  }

  //일정 생성
  @Override
  public ScheduleResponseDto saveSchedule(Schedule schedule) {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("schedule_id");
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("user_id", schedule.getUserId());
    parameters.put("todo_list", schedule.getTodoList());
    parameters.put("name", schedule.getName());
    parameters.put("password", schedule.getPassword());
    parameters.put("created_date", schedule.getCreatedDate());
    parameters.put("updated_date", schedule.getUpdatedDate());

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

    return new ScheduleResponseDto(key.longValue(), schedule.getUserId(), schedule.getTodoList(), schedule.getName(), schedule.getPassword(), schedule.getCreatedDate(), schedule.getUpdatedDate());
  }

  //전체 일정 조회
  @Override
  public List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate, Pageable pageable){
    StringBuilder sql = new StringBuilder("select * from schedule where user_id = ? ");
    List<Object> params = new ArrayList<>();
    params.add(userId);
    if(name != null && !name.isEmpty()) {
      sql.append(" and name = ?");
      params.add(name);
    }
    if(updatedDate != null) {
      sql.append(" and updated_date = ?");
      params.add(updatedDate);
    }
    sql.append(" order by updated_date desc limit ? offset ?");
    params.add(pageable.getPageSize());
    params.add(pageable.getOffset());
    return jdbcTemplate.query(sql.toString(), schedulesRowMapper(), params.toArray());
  }

  //선택 일정 조회
  @Override
  public Schedule getScheduleById(long id){
    List<Schedule> result = jdbcTemplate.query("select * from schedule where schedule_id = ?", schedulesRowMapperV2(), id);
    return result.stream().findAny().orElse(null);
  }

  //선택 일정 수정
  @Override
  public int updateSchedule(long id, String todoList, String name, String password) {
    return jdbcTemplate.update("update schedule set todo_list = ?, name = ?, updated_date = ? where schedule_id = ? and password = ?", todoList, name,
        LocalDateTime.now(), id, password);
  }

  //선택 일정 삭제
  @Override
  public int deleteSchedule(long id, String password) {
    return jdbcTemplate.update("delete from schedule where schedule_id = ? and password = ?", id,
        password);
  }

  private RowMapper<ScheduleResponseDto> schedulesRowMapper() {
    return new RowMapper<ScheduleResponseDto>() {

      @Override
      public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ScheduleResponseDto(
            rs.getLong("schedule_id"),
            rs.getLong("user_id"),
            rs.getString("todo_list"),
            rs.getString("name"),
            rs.getString("password"),
            rs.getDate("created_date").toLocalDate().atTime(LocalTime.now()),
            rs.getDate("updated_date").toLocalDate().atTime(LocalTime.now())
        );
      }
    };
  }

  private RowMapper<Schedule> schedulesRowMapperV2() {
    return new RowMapper<Schedule>() {

      @Override
      public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Schedule(
            rs.getLong("schedule_id"),
            rs.getLong("user_id"),
            rs.getString("todo_list"),
            rs.getString("name"),
            rs.getString("password"),
            rs.getTimestamp("created_date").toLocalDateTime(),
            rs.getTimestamp("updated_date").toLocalDateTime()
        );
      }
    };
  }

  @Override
  public String getMaxScheduleId() {
    return jdbcTemplate.queryForObject("select max(schedule_id) from schedule", String.class);
  }
}
