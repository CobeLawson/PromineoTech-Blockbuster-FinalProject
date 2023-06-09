package com.promineotech.blockbuster.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.blockbuster.entity.Administrator;
import com.promineotech.blockbuster.service.AdministrationSystemService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultAdministrationSystemController implements AdministrationSystemController {
	
	@Autowired
	AdministrationSystemService administrationSystemService;

	@Override
	public List<Administrator> fetchAdministrators(String administratorRole) {
		log.info("role={}", administratorRole);
		return administrationSystemService.fetchAdministrators(administratorRole);
	}

}
