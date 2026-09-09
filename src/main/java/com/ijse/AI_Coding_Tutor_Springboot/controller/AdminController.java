package com.ijse.AI_Coding_Tutor_Springboot.controller;

import com.ijse.AI_Coding_Tutor_Springboot.constants.CommonResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.AdminDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.GetAdminDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.UpdateAdminDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Admin;
import com.ijse.AI_Coding_Tutor_Springboot.service.AdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/loadAdminTable")
    public CommonResponse loadAdminTable() {
        List<GetAdminDetailsDTO> adminList = adminService.getAdminDetails();
        return new CommonResponse(OPERATION_SUCCESS,adminList,SUCCESS_MESSAGE);
    }

    @GetMapping("/getAdmin/{userId}")
    public CommonResponse getAdmin(@PathVariable long userId) {
        GetAdminDetailsDTO adminDTO = adminService.getAdminDetailById(userId);
        return new CommonResponse(OPERATION_SUCCESS,adminDTO,SUCCESS_MESSAGE);
    }

    @PutMapping("/updateAdminDetails")
    public CommonResponse updateAdminDetails(@RequestBody UpdateAdminDetailsDTO updateAdminDetailsDTO) {
        adminService.updateAdmin(updateAdminDetailsDTO);
        return new CommonResponse(OPERATION_SUCCESS,SUCCESS_MESSAGE);
    }

    @DeleteMapping("deleteAdmin/{userId}")
    public CommonResponse deleteAdmin(@PathVariable long userId) {
        adminService.deleteAdmin(userId);
        return new CommonResponse(OPERATION_SUCCESS,SUCCESS_MESSAGE);
    }
}
