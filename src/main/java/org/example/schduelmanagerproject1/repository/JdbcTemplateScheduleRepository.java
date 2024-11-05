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
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;


/**
 * 쿼리문에 비밀번호를 입력하여 수정하는 것으로 구현해보자
 * where에 비밀번호 조건도 넣자..
 * */
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
    parameters.put("updated_date", schedule.getUpdatedDate());

    Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

    return new ScheduleResponseDto(key.longValue(), schedule.getUserId(), schedule.getScheduleTitle(), schedule.getName(), schedule.getPassword(), schedule.getCreatedDate(), schedule.getUpdatedDate());
  }

  /**
   * 스케쥴 dto에 있는정보를 조회할 때 user의 의미가 없음
   * 스케줄에있는 userId로 조회가 다 되는 데 궂이 join을 할 이율르 모르겠다.
   * 이유를 만드려면 ScheduleResponseDto에서 email을 반환하게 만드는게 제일일듯?
   * @param userId
   * @param name
   * @param updatedDate
   * @return
   */

  @Override
  public List<ScheduleResponseDto> getAllSchedules(long userId, String name, LocalDate updatedDate) {
    String sql;
    if(name != null && updatedDate != null) {
      sql = "select * "
          + "from schedule s "
          + "join users u on s.user_id = u.user_id  "
          + "where u.user_id = ? and s.name = ? and s.updated_date = ?";
      return jdbcTemplate.query(sql, schedulesRowMapper(), userId, name, updatedDate);
    }else if(name == null && updatedDate != null) {
      sql = "select * "
          + "from schedule "
          + "join users u on s.user_id = u.user_id  "
          + "where u.user_id = ? and updated_date = ?";
      return jdbcTemplate.query(sql, schedulesRowMapper(), updatedDate);
    }else if(updatedDate == null && name != null) {
      sql = "select * "
          + "from schedule "
          + "join users u on s.user_id = u.user_id  "
          + "where u.user_id = ? and name = ? "
          + "order by updated_date desc";
      return jdbcTemplate.query(sql, schedulesRowMapper(), name);
    }else{
      sql = "select * "
          + "from schedule "
          + "join users u on s.user_id = u.user_id  "
          + "where u.user_id = ? "
          + "order by updated_date desc";
      return jdbcTemplate.query(sql, schedulesRowMapper(), userId);
    }

  }

  @Override
  public Schedule getScheduleByIdOrElseThrow(long id) {
    List<Schedule> result = jdbcTemplate.query("select * from schedule where schedule_id = ?", schedulesRowMapperV2(), id);
    return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Dose not exisit id = " + id));
  }

  @Override
  public int updateSchedule(long id, String scheduleTitle, String name, String password) {
    if(checkPassword(id, password))
      return jdbcTemplate.update("update schedule set schedule_title = ?, name = ?, updated_date = ? where schedule_id = ? and password = ?", scheduleTitle, name, LocalDate.now(), id, password);
    else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id 혹은 비밀번호를 확인해주세요");
  }

  @Override
  public int deleteSchedule(long id, String password) {
    if(checkPassword(id, password))
      return jdbcTemplate.update("delete from schedule where schedule_id = ? and password = ?", id, password);
    else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "id 혹은 비밀번호를 확인해주세요");
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

  private boolean checkPassword(long id, String password){
    List<Schedule> result = jdbcTemplate.query("select * from schedule where schedule_id = ? and password = ?", schedulesRowMapperV2(), id, password);
    if(result.isEmpty())
      return false;
    else
      return true;
  }

}
