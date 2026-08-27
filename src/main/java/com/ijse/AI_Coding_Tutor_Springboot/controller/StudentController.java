package com.ijse.AI_Coding_Tutor_Springboot.controller;

import com.ijse.AI_Coding_Tutor_Springboot.constants.CommonResponse;
import com.ijse.AI_Coding_Tutor_Springboot.dto.GetStudentDetailsDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.SessionHistoryDTO;
import com.ijse.AI_Coding_Tutor_Springboot.dto.StudentDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.service.StudentService;
import com.ijse.AI_Coding_Tutor_Springboot.service.UserService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/students/signUpStudent")
    public CommonResponse signUpStudent(@RequestBody StudentDTO studentDTO){
        userService.signupUserStudent(studentDTO);
        return new CommonResponse(OPERATION_SUCCESS, SUCCESS_MESSAGE);
    }

    @GetMapping("/getStudentName/{userId}")
    public CommonResponse getStudentName(@PathVariable long userId){
        String studentName = studentService.getStudentNameById(userId);
        return new CommonResponse(OPERATION_SUCCESS, studentName, SUCCESS_MESSAGE);
    }

    @GetMapping("/getCompletedSessions/{userId}")
    public CommonResponse getCompletedSessions(@PathVariable long userId){
        long sessionCount = studentService.getCompletedSessionCount(userId);
        return new CommonResponse(OPERATION_SUCCESS, sessionCount, SUCCESS_MESSAGE);
    }

    @GetMapping("/getSessionHistory/{userId}")
    public CommonResponse getSessionHistory(@PathVariable long userId){
        List<SessionHistoryDTO> sessionHistoryDTO = studentService.getSessionHistory(userId);
        return new CommonResponse(OPERATION_SUCCESS, sessionHistoryDTO, SUCCESS_MESSAGE);
    }

    @GetMapping("/getAvgRating/{userId}")
    public CommonResponse getAvgRating(@PathVariable long userId){
        double avgRating = studentService.getAverageRating(userId);
        return new CommonResponse(OPERATION_SUCCESS, avgRating, SUCCESS_MESSAGE);
    }
}
