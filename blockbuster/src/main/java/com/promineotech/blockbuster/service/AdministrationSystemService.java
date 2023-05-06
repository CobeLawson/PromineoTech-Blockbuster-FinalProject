package com.promineotech.blockbuster.service;

import java.util.List;
import com.promineotech.blockbuster.entity.Administrator;

public interface AdministrationSystemService {

	List<Administrator> fetchAdministrators(String administratorRole);

}
