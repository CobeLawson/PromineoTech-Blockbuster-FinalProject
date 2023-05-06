package com.promineotech.blockbuster.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotech.blockbuster.dao.AdministrationSystemDao;
import com.promineotech.blockbuster.entity.Administrator;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultAdministrationSystemService implements AdministrationSystemService {
	
	@Autowired
	AdministrationSystemDao administrationSystemDao;

	@Transactional(readOnly = true)
	@Override
	public List<Administrator> fetchAdministrators(String administratorRole) {
		log.info("The fetchAdministrators method was called with role={}", administratorRole);
		
		List<Administrator> administrators = administrationSystemDao.fetchAdministrators(administratorRole);
		
		if(administrators.isEmpty()) {
			String msg = String.format("No Administrators were found with role=%s", administratorRole);
			throw new NoSuchElementException(msg);
		}
		
		return administrators;
	}

}
