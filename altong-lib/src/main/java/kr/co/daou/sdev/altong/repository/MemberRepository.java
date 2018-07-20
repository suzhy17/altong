package kr.co.daou.sdev.altong.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.daou.sdev.altong.domain.project.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
