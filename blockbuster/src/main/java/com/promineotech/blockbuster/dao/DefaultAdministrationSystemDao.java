package com.promineotech.blockbuster.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.promineotech.blockbuster.entity.Administrator;
import lombok.extern.slf4j.Slf4j;

@Service
@Component
@Slf4j
public class DefaultAdministrationSystemDao implements AdministrationSystemDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Administrator> fetchAdministrators(String administratorRole) {
		log.info("DAO: role={}", administratorRole);
		
		// @formatter:off
		String sql = ""
				+ "SELECT * "
				+ "FROM administration "
				+ "WHERE administrator_role = :administrator_role";
		// @formatter:on
		
		Map<String, Object> params = new HashMap<>();
		log.info(administratorRole);
		params.put("administrator_role", administratorRole);
		
		return jdbcTemplate.query(sql, params,
				new RowMapper<>() {
					@Override
					public Administrator mapRow(ResultSet rs, int rowNum) throws SQLException {
						
						// @formatter:off
						return Administrator.builder()
								.administratorPK(rs.getInt("administrator_pk"))
								.administratorName(rs.getString("administrator_name"))
								.administratorRole(administratorRole)
								.administratorPhone(rs.getString("administrator_phone"))
								.build();
						// @formatter:on
					}
			}
		);
	}


}
