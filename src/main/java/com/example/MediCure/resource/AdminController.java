package com.example.MediCure.resource;

import com.example.MediCure.model.AdminInfo;
import com.example.MediCure.model.DoctorInfo;
import com.example.MediCure.repository.AdminRepo;
import com.example.MediCure.repository.DoctorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    AdminRepo adminRepo;
    @Autowired
    DoctorRepo doctorRepo;

    @PostMapping("/login_admin/{name}/{pass}")
    public ResponseEntity<AdminInfo> loginAdmin(@PathVariable("name")String name, @PathVariable("pass")String pass)
    {
        AdminInfo adminInfo = adminRepo.findByAdminNameAndAdminPass(name, pass);
        return new ResponseEntity<>(adminInfo, HttpStatus.OK);
    }

    @PostMapping("/update_admin_pass/{id}/{pass}")
    public ResponseEntity<?> updateAdminPass(@PathVariable("id")int id,@PathVariable("pass")String pass){
        System.out.print("in passsssssssss...........");
        adminRepo.updateAdminPass(id, pass);
        System.out.print("Admin Pass updated successfully");
        return new ResponseEntity<>("Admin Pass Updated...",HttpStatus.OK);
    }

    @PostMapping("/update_doctor_pass/{id}/{pass}")
    public ResponseEntity<?> updateDoctor(@PathVariable("id")int id,@PathVariable("pass")String pass) {
        doctorRepo.updateDoctorPass(id, pass);
        return new ResponseEntity<>("Doctor pass changed.....", HttpStatus.OK);
    }



}
