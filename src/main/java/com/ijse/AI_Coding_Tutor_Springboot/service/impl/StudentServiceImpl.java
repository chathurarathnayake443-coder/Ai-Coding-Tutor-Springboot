package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.*;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.entity.User;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.UserStatus;
import com.ijse.AI_Coding_Tutor_Springboot.exceptions.CustomException;
import com.ijse.AI_Coding_Tutor_Springboot.repository.*;
import com.ijse.AI_Coding_Tutor_Springboot.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class StudentServiceImpl implements StudentService {

    private final HintRepository hintRepository;
    private final StudentRepository studentRepository;
    private final CodingSessionRepository codingSessionRepository;
    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public StudentServiceImpl(StudentRepository studentRepository, CodingSessionRepository codingSessionRepository, HintRepository hintRepository, RatingRepository ratingRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.codingSessionRepository = codingSessionRepository;
        this.hintRepository = hintRepository;
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<GetStudentDetailsDTO> getAllStudents(){
        log.info("Executing method getAllStudents()");
        try{
            return studentRepository.getStudentDetails();
        }
        catch(Exception e){
            log.error("Error in method getAllStudents()",e);
            throw e;
        }
    }

    public String getStudentNameById(long userId){
        log.info("Executing method getStudentNameById()");
        try{
            Optional<Student> studentOptional = studentRepository.getStudentByUserId(userId);
            if(!studentOptional.isPresent()){
                throw new CustomException(404,"Sorry Student not found");
            }
            Student student = studentOptional.get();
            String studentName = student.getStudentFullName();
            return studentName;
        }
        catch(Exception e){
            log.error("Error in method getStudentNameById()",e);
            throw e;
        }
    }

    public long getCompletedSessionCount(long userId){
        log.info("Executing method getCompletedSessionCount()");
        try{
            System.out.println(userId);
            Optional<Student> studentOptional = studentRepository.getStudentByUserId(userId);
            if(!studentOptional.isPresent()){
                throw new CustomException(404,"Sorry Student not found");
            }
            Student student = studentOptional.get();
            System.out.println(student.getStudentId());
            long sessionCount = codingSessionRepository.getCompletedSessionCount(student.getStudentId());
            System.out.println(sessionCount);
            return sessionCount;
        }
        catch(Exception e){
            log.error("Error in method getCompletedSessionCount()",e);
            throw e;
        }
    }


    public List<SessionHistoryDTO> getSessionHistory(long userId){
        log.info("Executing method getSessionHistory()");
        try{
            Optional<Student> studentOptional = studentRepository.getStudentByUserId(userId);
            if(!studentOptional.isPresent()){
                throw new CustomException(404,"Sorry Student not found");
            }
            Student student = studentOptional.get();

            List<SessionHistoryDTO> sessionHistoryDTOList = codingSessionRepository.getSessionHistory(student.getStudentId());
            for(SessionHistoryDTO sessionHistoryDTO : sessionHistoryDTOList){
                System.out.println(sessionHistoryDTO.getSessionId());
            }
            return sessionHistoryDTOList;
        }
        catch(Exception e){
            log.error("Error in method getSessionHistory()",e);
            throw e;
        }
    }

    public double getAverageRating(long userId){
        log.info("Executing method getAverageRating()");
        try{
            Optional<Student> studentOptional = studentRepository.getStudentByUserId(userId);
            if(!studentOptional.isPresent()){
                throw new CustomException(404,"Sorry Student not found");
            }
            Student student = studentOptional.get();
            double avgRating = ratingRepository.findAvgRatingByStudentId(student.getStudentId());
            return avgRating;
        }
        catch(Exception e){
            log.error("Error in method getAverageRating()",e);
            throw e;
        }
    }

    public StudentDTO getStudentById(long userId){
        log.info("Executing method getStudentById()");
        try{
            Optional<Student> optionalStudent = studentRepository.getStudentByUserId(userId);
            if(!optionalStudent.isPresent()){
                throw new CustomException(404,"Sorry Student not found");
            }
            Student student = optionalStudent.get();
            Optional<GetUserPasswordAndEmailDTO> optionalDTO = studentRepository.getUserPasswordAndEmail(student.getStudentId());
            if(!optionalDTO.isPresent()){
                throw new CustomException(404,"Sorry Email and Password not found");
            }
            GetUserPasswordAndEmailDTO getUserPasswordAndEmailDTO = optionalDTO.get();
            return new StudentDTO(student.getStudentId(),student.getStudentFullName(),getUserPasswordAndEmailDTO.getEmail(),student.getStudentContact(),getUserPasswordAndEmailDTO.getPassword());
        }
        catch(Exception e){
            log.error("Error in method getStudentById()",e);
            throw e;
        }
    }

    @Transactional
    public void updateStudentDetails(UpdateStudentDetailsDTO updateStudentDetailsDTO){
        log.info("Executing method updateStudentDetails()");
        try{
            Optional<Student> studentOptional = studentRepository.getStudentByUserId(updateStudentDetailsDTO.getUserId());
            if(!studentOptional.isPresent()){
                throw new CustomException(404,"Sorry Student not found");
            }

            Student student = studentOptional.get();

            if(updateStudentDetailsDTO.getNewStudentName() != null){
                student.setStudentFullName(updateStudentDetailsDTO.getNewStudentName());
            }

            if(updateStudentDetailsDTO.getNewPhoneNumber() != null){
                student.setStudentContact(updateStudentDetailsDTO.getNewPhoneNumber());
            }

            studentRepository.save(student);

            if(!updateStudentDetailsDTO.getNewPassword().isEmpty() && !updateStudentDetailsDTO.getOldPassword().isEmpty()){
                Optional<GetUserPasswordAndEmailDTO> optionalOldPassword = studentRepository.getUserPasswordAndEmail(student.getStudentId());

                if(!optionalOldPassword.isPresent()){
                    throw new CustomException(404,"Sorry Password not found");
                }

                GetUserPasswordAndEmailDTO emailPasswordDTO = optionalOldPassword.get();
                String oldPassword = emailPasswordDTO.getPassword();
                String userName = emailPasswordDTO.getEmail();
                System.out.println("old Password" + updateStudentDetailsDTO.getOldPassword());
                System.out.println("New password" + updateStudentDetailsDTO.getNewPassword());

                if (!passwordEncoder.matches(updateStudentDetailsDTO.getOldPassword(), oldPassword)) {
                    throw new CustomException(409,"Sorry, Old Password does not match");
                }

                Optional<User> optionalUser = userRepository.findByUserName(userName);
                if(!optionalUser.isPresent()){
                    throw new CustomException(404,"Sorry User not found");
                }
                User user = optionalUser.get();
                user.setPassword(encoder.encode(updateStudentDetailsDTO.getNewPassword()));
                userRepository.save(user);
            }
        }
        catch(Exception e){
            log.error("Error in method updateStudentDetails()",e);
            throw e;
        }
    }

    public List<GetStudentDetailsDTO> searchFilterStudents(String studentName){
        log.info("Executing method searchFilterStudents()");
        try{
            List<GetStudentDetailsDTO> studentList = studentRepository.searchFilterStudents(studentName);
            return studentList;
        }
        catch(Exception e){
            log.error("Error in method searchFilterStudents()",e);
            throw e;
        }
    }

    public void deleteStudent(long userId){
        log.info("Executing method deleteStudent()");
        try{
            Optional<User> optionalUser = userRepository.findById(userId);
            if(!optionalUser.isPresent()){
                throw new CustomException(404,"Sorry User not found");
            }
            User user = optionalUser.get();
            user.setUserStatus(UserStatus.INACTIVE);
            userRepository.save(user);
        }
        catch(Exception e){
            log.error("Error in method deleteStudent()",e);
            throw e;
        }
    }
}
