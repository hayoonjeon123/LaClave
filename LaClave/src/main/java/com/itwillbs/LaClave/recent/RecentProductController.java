package com.itwillbs.LaClave.recent;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api/RecentProduct")
@RequiredArgsConstructor
@Log4j2
public class RecentProductController {
	
	private final RecentProductService recentProductService;
	private final RecentProductRepository recentProductRepository;
	
	@GetMapping("/{memberIdx}")
	public ResponseEntity<List<RecentProduct>> getRecentProductsBymember(
			@PathVariable("memberIdx") Integer memberIdx) {
		return ResponseEntity.ok(
				recentProductService.getRecentProductsBymember(memberIdx));
	}
	
	@GetMapping("/recent/{memberIdx}")
	public List<RecentProductDto> getRecentProducts(@PathVariable Integer memberIdx) {
	    return recentProductRepository
	             .findTop5ByMemberIdxOrderByViewedAtDesc(memberIdx)
	             .stream()
	             .map(r -> new RecentProductDto(r.getProductIdx(), r.getViewedAt()))
	             .collect(Collectors.toList());
	}
	

}	
