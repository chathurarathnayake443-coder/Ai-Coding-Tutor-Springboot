package com.ijse.AI_Coding_Tutor_Springboot.controller;

import com.ijse.AI_Coding_Tutor_Springboot.constants.CommonResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.AdminDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Admin;
import com.ijse.AI_Coding_Tutor_Springboot.service.AdminService;
import org.springframework.web.bind.annotation.*;

import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseCode.OPERATION_SUCCESS;
import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseMessage.SUCCESS_MESSAGE;

@RestController
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/getAdminName/{userId}")
    public CommonResponse getAdminName(@PathVariable long userId) {
        String adminName = adminService.getAdminNameByUserId(userId).getAdminFullName();
        return new CommonResponse(OPERATION_SUCCESS,adminName,SUCCESS_MESSAGE);
    }

    @PostMapping("/saveAdmin")
    public CommonResponse saveAdmin(@RequestBody AdminDTO adminDTO) {
        System.out.println("ADMIN name - " + adminDTO.getAdminFullName());
        adminService.saveAdmin(adminDTO);
        return new CommonResponse(OPERATION_SUCCESS,SUCCESS_MESSAGE);
    }
}
