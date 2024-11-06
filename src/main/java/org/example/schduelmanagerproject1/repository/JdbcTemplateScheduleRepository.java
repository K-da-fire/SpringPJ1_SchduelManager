package org.example.schduelmanagerproject1.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.example.schduelmanagerproject1.dto.ScheduleResponseDto;
import org.example.schduelmanagerproject1.entity.Schedule;
import org.example.schduelmanagerproject1.entity.Users;
import org.example.schduelmanagerproject1.exception.NotFoundException;
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
  public ScheduleResponseDto saveSchedule(Schedule schedule)
      throws NotFoundException {
    findUser(schedule.getUserId());
    SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
    jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("schedule_id");
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("user_id", schedule.getUserId());
    parameters.put("schedule_title", schedule.getScheduleTitle());
    parameters.put("name", schedule.getName());
    parameters.put("password", schedule.getPassword());
    parameters.put("created_date", schedule.getCreatedDate());
    parameters.put("updated_date", schedule.getUpdatedDate());

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

    return new ScheduleResponseDto(key.longValue(), schedule.getUserId(), schedule.getScheduleTitle(), schedule.getName(), schedule.getPassword(), schedule.getCreatedDate(), schedule.getUpdatedDate());
  }

  @Override
  public List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate, Pageable pageable)
      throws NotFoundException {
    findUser(userId);
    String sql = "select * from schedule where user_id = ? ";
    if(name != null && updatedDate != null) {
      sql = sql + "and name = ? and updated_date = ? limit ? offset ?";
      return jdbcTemplate.query(sql, schedulesRowMapper(), userId, name, updatedDate, pageable.getPageSize(), pageable.getOffset());
    }else if(name == null && updatedDate != null) {
      sql = sql +"and updated_date = ? limit ? offset ?";
      return jdbcTemplate.query(sql, schedulesRowMapper(), userId, updatedDate, pageable.getPageSize(), pageable.getOffset());
    }else if(updatedDate == null && name != null) {
      sql = sql + "and name = ? order by updated_date desc limit ? offset ?";
      return jdbcTemplate.query(sql, schedulesRowMapper(), userId, name, pageable.getPageSize(), pageable.getOffset());
    }else{
      sql = sql + "order by updated_date desc limit ? offset ?";
      return jdbcTemplate.query(sql, schedulesRowMapper(), userId, pageable.getPageSize(), pageable.getOffset());
    }
  }

  @Override
  public Schedule getScheduleByIdOrElseThrow(long id) throws NotFoundException {
    findSchedule(id);
    List<Schedule> result = jdbcTemplate.query("select * from schedule where schedule_id = ?", schedulesRowMapperV2(), id);
    return result.stream().findAny().orElse(null);
  }

  @Override
  public int updateSchedule(long id, String scheduleTitle, String name, String password)
      throws WorngPasswordException, NotFoundException {
    findSchedule(id);
    checkIdPassword(id, password);
    return jdbcTemplate.update("update schedule set schedule_title = ?, name = ?, updated_date = ? where schedule_id = ? and password = ?", scheduleTitle, name, LocalDate.now(), id, password);
  }

  @Override
  public int deleteSchedule(long id, String password)
      throws WorngPasswordException, NotFoundException {
    findSchedule(id);
    checkIdPassword(id, password);
    return jdbcTemplate.update("delete from schedule where schedule_id = ? and password = ?", id, password);
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
            rs.getString("schedule_title"),
            rs.getString("name"),
            rs.getString("password"),
            rs.getTimestamp("created_date").toLocalDateTime(),
            rs.getTimestamp("updated_date").toLocalDateTime()
        );
      }
    };
  }

  private RowMapper<Users> usersRowMapper() {
    return new RowMapper<Users>() {

      @Override
      public Users mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Users(
            rs.getLong("user_id"),
            rs.getString("email"),
            rs.getString("name")
        );
      }
    };
  }

  private void checkIdPassword(long id, String password)
      throws WorngPasswordException, NotFoundException {
    checkScheduleDeleted(id);

    List<Schedule> result = jdbcTemplate.query("select * from schedule where schedule_id = ? and password = ?", schedulesRowMapperV2(), id, password);
    if(result.isEmpty()) throw new WorngPasswordException(HttpStatus.FORBIDDEN, "잘못된 비밀번호 입니다.");
  }

  private void findUser(long userId) throws NotFoundException {
    Users userMaxId = jdbcTemplate.query("select * from users order by user_id desc",usersRowMapper()).getFirst();
    Users user = jdbcTemplate.query("select * from users where user_id = ?",usersRowMapper(), userId).stream().findAny().orElse(null);
    if((userId > userMaxId.getUserId()) || user == null) throw new NotFoundException(HttpStatus.NOT_FOUND, "등록되지않은 유저입니다.");
  }

  private void findSchedule(long id) throws NotFoundException {
    checkScheduleDeleted(id);
    Schedule schedule = jdbcTemplate.query("select * from schedule where schedule_id = ?", schedulesRowMapperV2(), id).stream().findAny().orElse(null);
    if(schedule == null) throw new NotFoundException(HttpStatus.NOT_FOUND, "일정을 찾을 수 없습니다");
  }

  private void checkScheduleDeleted(long id) throws NotFoundException {
    Schedule scheduleMaxId = jdbcTemplate.query("select * from schedule order by schedule_id desc", schedulesRowMapperV2()).getFirst();
    Schedule schedule = jdbcTemplate.query("select * from schedule where schedule_id = ?", schedulesRowMapperV2(), id).stream().findAny().orElse(null);
    if((id < scheduleMaxId.getScheduleId()) && schedule == null) throw new NotFoundException(HttpStatus.NOT_FOUND, "삭제된 일정입니다.");
  }
}
