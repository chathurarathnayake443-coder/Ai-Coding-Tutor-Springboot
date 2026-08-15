package com.ijse.AI_Coding_Tutor_Springboot.controller;

import com.ijse.AI_Coding_Tutor_Springboot.constants.CommonResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.GetStudentDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.StudentDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.service.StudentService;
import com.ijse.AI_Coding_Tutor_Springboot.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseCode.OPERATION_SUCCESS;
import static com.ijse.AI_Coding_Tutor_Springboot.constants.ResponseMessage.SUCCESS_MESSAGE;

@RestController
public class StudentController {

    private StudentService studentService;
    private UserService userService;

    public StudentController(StudentService studentService, UserService userService) {
        this.studentService = studentService;
        this.userService = userService;
    }

    @GetMapping("/students")
    public CommonResponse getAllStudents(){
        List<GetStudentDetailsDTO> studentList = studentService.getAllStudents();
        for(int i = 0; i < studentList.size(); i++){
            System.out.println(studentList.get(i).getStudentName());
            System.out.println("Hello");
        }
        return new CommonResponse(OPERATION_SUCCESS, studentList, SUCCESS_MESSAGE);
    }

    @PostMapping("students/signUpStudent")
    public CommonResponse signUpStudent(@RequestBody StudentDTO studentDTO){
        userService.signupUserStudent(studentDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }
}
