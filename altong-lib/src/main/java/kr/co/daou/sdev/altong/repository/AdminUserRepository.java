package kr.co.daou.sdev.altong.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import kr.co.daou.sdev.altong.domain.admin.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, String> {

}
