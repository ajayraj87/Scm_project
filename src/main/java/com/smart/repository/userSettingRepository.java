package com.smart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smart.entity.Setting;

@Repository
public interface userSettingRepository extends JpaRepository<Setting, Integer> {

}
