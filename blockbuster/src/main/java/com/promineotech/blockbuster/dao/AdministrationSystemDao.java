package com.promineotech.blockbuster.dao;

import java.util.List;
import com.promineotech.blockbuster.entity.Administrator;

public interface AdministrationSystemDao {

	List<Administrator> fetchAdministrators(String administratorRole);

}
