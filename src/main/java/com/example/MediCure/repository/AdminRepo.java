package com.example.MediCure.repository;

import com.example.MediCure.model.AdminInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepo  extends JpaRepository<AdminInfo,Integer> {

    public AdminInfo findByAdminNameAndAdminPass(String name, String pass);

    @Transactional
    @Modifying
    @Query("update AdminInfo ai set ai.adminPass=:pass where ai.adminId = :id")
    public void updateAdminPass(int id, String pass);



}
