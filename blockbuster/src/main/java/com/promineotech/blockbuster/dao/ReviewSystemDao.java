package com.promineotech.blockbuster.dao;

import java.util.List;
import com.promineotech.blockbuster.entity.Review;

public interface ReviewSystemDao {

	List<Review> fetchReviews(int productPK);

}
