package com.itwillbs.LaClave.recent;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/RecentProduct")
@RequiredArgsConstructor
@Log4j2
public class RecentProductController {
	
	private final RecentProductService recentProductService;
	
	@GetMapping("/{memberIdx}")
	public ResponseEntity<List<RecentProduct>> getRecentProductsBymember(
			@PathVariable("memberIdx") Integer memberIdx) {
		return ResponseEntity.ok(
				recentProductService.getRecentProductsBymember(memberIdx));
	}
	

}	
