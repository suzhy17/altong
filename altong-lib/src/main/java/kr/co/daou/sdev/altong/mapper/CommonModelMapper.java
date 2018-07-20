package kr.co.daou.sdev.altong.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component("modelMapper")
public class CommonModelMapper {

	private ModelMapper modelMapper;

	public CommonModelMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration()
				.setFieldMatchingEnabled(true)
				.setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
				.setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * 일반 객체 변환 (Domain - DTO)
	 * <p>
	 * - 객체 안에 또다른 객체나 리스트가 있을 경우에도 매핑 됨<br>
	 * - 필드명은 반드시 같아야 함
	 *
	 * @param obj  원본 객체
	 * @param type 변환될 객체 타입
	 * @param <T>  결과 타입
	 * @return 변환된 객체
	 */
	public <T> T map(Object obj, Class<T> type) {
		return modelMapper.map(obj, type);
	}

	/**
	 * Page<T> 객체 변환
	 * <p>
	 * - Page의 content를 변환<br>
	 * - Page 입출력 말고는 일반 객체 변환과 동일한 기능을 함
	 *
	 * @param page     page 데이터 원본
	 * @param pageable 페이징 정보
	 * @param type     변환될 객체 타입
	 * @param <I>      입력 타입
	 * @param <O>      결과 타입
	 * @return 변환된 Page 객체
	 */
	public <I, O> Page<O> mapPage(Page<I> page, Pageable pageable, Class<O> type) {
		List<O> content = new ArrayList<>();
		for (I obj : page.getContent()) {
			content.add(modelMapper.map(obj, type));
		}

		return new PageImpl<>(content, pageable, page.getTotalElements());
	}
}
