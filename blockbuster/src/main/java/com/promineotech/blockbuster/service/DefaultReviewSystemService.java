package com.promineotech.blockbuster.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.promineotech.blockbuster.dao.ReviewSystemDao;
import com.promineotech.blockbuster.entity.Review;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DefaultReviewSystemService implements ReviewSystemService {

	@Autowired
	private ReviewSystemDao reviewSystemDao;
	
	@Transactional(readOnly = true)
	@Override
	public List<Review> fetchReviews(int productPK) {
		log.info("The fetchReviews method was called with productPK={}", productPK);
		
		List<Review> reviews = reviewSystemDao.fetchReviews(productPK);
		
		if(reviews.isEmpty()) {
			String msg = String.format("No reviews were found with productPK=%s", productPK);
			throw new NoSuchElementException(msg);
		}
		
		return reviews;
	}

}
