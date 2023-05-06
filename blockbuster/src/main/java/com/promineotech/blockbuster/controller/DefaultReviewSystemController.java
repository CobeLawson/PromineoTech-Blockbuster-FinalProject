package com.promineotech.blockbuster.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.promineotech.blockbuster.entity.Review;
import com.promineotech.blockbuster.service.ReviewSystemService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class DefaultReviewSystemController implements ReviewSystemController {

	@Autowired
	private	ReviewSystemService reviewSystemService;

	@Override
	public List<Review> fetchReviews(int productPK) {
		log.info("productPK={}", productPK);
		return reviewSystemService.fetchReviews(productPK);
	}

}
