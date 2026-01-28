package com.itwillbs.LaClave.point;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.itwillbs.LaClave.config.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService {
	
	private final PointRepository pointRepository;
	
    @Override
    public List<PointDto> getMyPointList(CustomUserDetails user) {
        List<Point> points = pointRepository.findByMemberOrderByCreatedAtDesc(user.getMember());
        return points.stream()
                     .map(p -> new PointDto(
                         p.getPointIdx(),
                         p.getPointAmount(),
                         p.getOrderIdx(),
                         p.getDescription(),
                         p.getCreatedAt()
                     ))
                     .collect(Collectors.toList());
    }

}
