package kr.co.daou.sdev.altong.service;

import java.util.List;

import kr.co.daou.sdev.altong.exception.AltongException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.daou.sdev.altong.domain.project.Member;
import kr.co.daou.sdev.altong.domain.project.SmartPhone;
import kr.co.daou.sdev.altong.dto.project.MemberDto;
import kr.co.daou.sdev.altong.mapper.CommonModelMapper;
import kr.co.daou.sdev.altong.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("memberService")
public class MemberServiceImpl implements MemberService {

	@Autowired
	private CommonModelMapper modelMapper;

	@Autowired
	private MemberRepository memberRepository;

	@Override
	@Transactional(readOnly = true)
	public Page<MemberDto> getMembers(Pageable pageable) {
		Page<Member> page = memberRepository.findAll(pageable);

		return modelMapper.mapPage(page, pageable, MemberDto.class);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Member getMember(Integer memberNo) {
		return memberRepository.findOne(memberNo);
	}

	@Override
	@Transactional
	public void saveMember(MemberDto memberDto) {
		// TODO 사번, 이메일, 전화번호, 푸시번호 중복 체크

		log.debug("memberDto={}", memberDto);
		Member member = modelMapper.map(memberDto, Member.class);
		log.debug("member={}", member);

		memberRepository.save(member);
	}

	@Override
	@Transactional
	public void modifyMember(MemberDto memberDto) {
		Member member = memberRepository.findOne(memberDto.getMemberNo());
		if (member == null) {
			throw new AltongException.MemberNotFoundException();
		}
		log.debug("기존 member={}", member);
		log.debug("신규 memberDto={}", memberDto);

		member.changeMemberName(memberDto.getMemberName());
		member.changeStaffNo(memberDto.getStaffNo());
		member.changeEmail(memberDto.getEmail());
		member.changeMobileNo(memberDto.getMobileNo());
		member.changeRemarks(memberDto.getRemarks());
//		member.changeMemberStatus(memberDto.getMemberStatus());

		if (memberDto.getSmartPhone() != null && memberDto.getSmartPhone().getSmartPhoneType() != null) {
			member.changeSmartPhone(new SmartPhone(memberDto.getSmartPhone().getSmartPhoneType(), memberDto.getSmartPhone().getPushNo()));
		} else {
			member.changeSmartPhone(null);
		}
	}

	@Override
	@Transactional
	public void removeMember(Integer memberNo) {
		Member member = memberRepository.findOne(memberNo);
		member.removeAllProject();
		
		memberRepository.delete(memberNo);
	}
}
