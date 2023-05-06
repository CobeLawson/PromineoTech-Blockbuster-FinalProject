package com.promineotech.blockbuster.service;

import java.util.List;
import com.promineotech.blockbuster.entity.Review;

public interface ReviewSystemService {

	List<Review> fetchReviews(int productPK);

}
