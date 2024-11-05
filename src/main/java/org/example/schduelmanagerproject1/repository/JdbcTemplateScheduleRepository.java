package org.example.schduelmanagerproject1.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;
import org.example.schduelmanagerproject1.exception.DeletedSchedule;
import org.example.schduelmanagerproject1.exception.NotFoundSchedule;
import org.example.schduelmanagerproject1.exception.NotFoundUser;
import org.example.schduelmanagerproject1.exception.WorngPasswordException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

  @Override
  public ScheduleResponseDto saveSchedule(Schedule schedule) throws NotFoundUser {
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("schedule_id");

    Map<String, Object> parameters = new HashMap<>();
    parameters.put("user_id", schedule.getUserId());
    parameters.put("schedule_title", schedule.getScheduleTitle());
    parameters.put("name", schedule.getName());
    parameters.put("password", schedule.getPassword());
    parameters.put("created_date", schedule.getCreatedDate());
    parameters.put("updated_date", schedule.getUpdatedDate());

    Number key = checkUser(jdbcInsert,parameters);

    return new ScheduleResponseDto(key.longValue(), schedule.getUserId(), schedule.getScheduleTitle(), schedule.getName(), schedule.getPassword(), schedule.getCreatedDate(), schedule.getUpdatedDate());
  }

  @Override
  public List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate, Pageable pageable) {
    String sql;
    if(name != null && updatedDate != null) {
      sql = "select * "
          + "from schedule "
          + "where user_id = ? and name = ? and updated_date = ?";
      return jdbcTemplate.query(sql, schedulesRowMapper(), userId, name, updatedDate);
    }else if(name == null && updatedDate != null) {
      sql = "select * "
          + "from schedule "
          + "where user_id = ? and updated_date = ?";
      return jdbcTemplate.query(sql, schedulesRowMapper(), userId, updatedDate);
    }else if(updatedDate == null && name != null) {
      sql = "select * "
          + "from schedule "
          + "where user_id = ? and name = ? "
          + "order by updated_date desc";
      return jdbcTemplate.query(sql, schedulesRowMapper(), userId, name);
    }else{
      sql = "select * "
          + "from schedule "
          + "where user_id = ? "
          + "order by updated_date desc";
      return jdbcTemplate.query(sql, schedulesRowMapper(), userId);
    }

  }


  @Override
  public Schedule getScheduleByIdOrElseThrow(long id) throws DeletedSchedule, NotFoundSchedule {
    List<Schedule> result = jdbcTemplate.query("select * from schedule where schedule_id = ?", schedulesRowMapperV2(), id);

    return checkSchedule(id, result.stream().findAny().orElse(null));
  }

  @Override
  public int updateSchedule(long id, String scheduleTitle, String name, String password)
      throws WorngPasswordException, DeletedSchedule {
    if(checkPassword(id, password))
      return jdbcTemplate.update("update schedule set schedule_title = ?, name = ?, updated_date = ? where schedule_id = ? and password = ?", scheduleTitle, name, LocalDate.now(), id, password);
    return 0;
  }

  @Override
  public int deleteSchedule(long id, String password) throws WorngPasswordException, DeletedSchedule {
    if(checkPassword(id, password))
      return jdbcTemplate.update("delete from schedule where schedule_id = ? and password = ?", id, password);
    return 0;
  }


  private RowMapper<ScheduleResponseDto> schedulesRowMapper() {
    return new RowMapper<ScheduleResponseDto>() {

      @Override
      public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ScheduleResponseDto(
            rs.getLong("schedule_id"),
            rs.getLong("user_id"),
            rs.getString("schedule_title"),
            rs.getString("name"),
            rs.getString("password"),
            rs.getDate("created_date").toLocalDate(),
            rs.getDate("updated_date").toLocalDate()
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
            rs.getString("schedule_title"),
            rs.getString("name"),
            rs.getString("password"),
            rs.getDate("created_date").toLocalDate(),
            rs.getDate("updated_date").toLocalDate()
        );
      }
    };
  }

  private boolean checkPassword(long id, String password)
      throws WorngPasswordException, DeletedSchedule {

    checkScheduleId(id);

    List<Schedule> result = jdbcTemplate.query("select * from schedule where schedule_id = ? and password = ?", schedulesRowMapperV2(), id, password);
    if(result.isEmpty())
      throw new WorngPasswordException(HttpStatus.FORBIDDEN, "비밀번호를 확인하세요.");
    else
      return true;
  }

  private Number checkUser(SimpleJdbcInsert jdbcInsert, Map<String, Object> parameters) throws NotFoundUser {
    Number key;
    try {
      key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
    }catch (Exception e) {
     throw new NotFoundUser(HttpStatus.NOT_FOUND, "등록되지않은 유저입니다.");
    }

    return key;
  }

  private Schedule checkSchedule(long id, Schedule schedule) throws DeletedSchedule, NotFoundSchedule {
    checkScheduleId(id);

    if(schedule == null) throw new NotFoundSchedule(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다");

    return schedule;
  }

  private void checkScheduleId(long id) throws DeletedSchedule {
    Schedule scheduleMaxId = jdbcTemplate.query("select * from schedule order by schedule_id desc", schedulesRowMapperV2()).getFirst();
    Schedule schedule = jdbcTemplate.query("select * from schedule where schedule_id = ?", schedulesRowMapperV2(), id).stream().findAny().orElse(null);
    if((id < scheduleMaxId.getScheduleId()) && schedule == null) throw new DeletedSchedule(HttpStatus.NOT_FOUND, "삭제된 일정입니다.");
  }

}
