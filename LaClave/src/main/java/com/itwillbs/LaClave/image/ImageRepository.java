package com.itwillbs.LaClave.image;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImageRepository extends JpaRepository<Image, Long> {

	Optional<Image> findByTargetTypeAndTargetIdx(String targetType, Long targetIdx);

	Optional<Image> findFirstByTargetCodeAndTargetTypeAndTargetIdx(
			String targetCode,
			String targetType,
			Integer targetIdx);

	@Query(value = "SELECT * FROM IMAGE WHERE TARGET_IDX = :targetIdx AND TARGET_CODE = :targetCode", nativeQuery = true)
	List<Image> findByTargetIdxAndCode(@Param("targetIdx") Integer targetIdx,
			@Param("targetCode") String targetCode);
}
