package com.ijse.AI_Coding_Tutor_Springboot.service.impl;

import com.ijse.AI_Coding_Tutor_Springboot.dto.PaymentDTO;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Payment;
import com.ijse.AI_Coding_Tutor_Springboot.entity.Student;
import com.ijse.AI_Coding_Tutor_Springboot.entity.SubscriptionPlan;
import com.ijse.AI_Coding_Tutor_Springboot.entity.UserSubscription;
import com.ijse.AI_Coding_Tutor_Springboot.enumerations.PaymentStatus;
import com.ijse.AI_Coding_Tutor_Springboot.exceptions.CustomException;
import com.ijse.AI_Coding_Tutor_Springboot.repository.*;
import com.ijse.AI_Coding_Tutor_Springboot.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final UserSubscriptionRepository userSubscriptionRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, StudentRepository studentRepository, SubscriptionPlanRepository subscriptionPlanRepository, UserSubscriptionRepository userSubscriptionRepository) {
        this.paymentRepository = paymentRepository;
        this.studentRepository = studentRepository;
        this.subscriptionPlanRepository = subscriptionPlanRepository;
        this.userSubscriptionRepository = userSubscriptionRepository;
    }

    public void processPayment(PaymentDTO paymentDTO){
        try{
            Optional<Student> optionalStudent = studentRepository.getStudentByUserId(paymentDTO.getUserId());
            if(!optionalStudent.isPresent()){
                throw new CustomException(404,"Sorry Student Not Found");
            }
            Student student = optionalStudent.get();

            Optional<SubscriptionPlan> optionalPlan = subscriptionPlanRepository.getSubscriptionPlanByCategory(paymentDTO.getPlanCategory());
            if(!optionalPlan.isPresent()){
                throw new CustomException(404,"Plan Not Found");
            }
            SubscriptionPlan subscriptionPlan = optionalPlan.get();

            Payment payment = new Payment();
            payment.setPaymentAmount(paymentDTO.getPaymentAmount());
            payment.setPaymentDate(LocalDateTime.now());

            if(subscriptionPlan.getPrice() > payment.getPaymentAmount()){
                throw new CustomException(400,"Insufficient funds");
            }

            Optional<UserSubscription> optionalUserSubscription = userSubscriptionRepository.findUserSubscriptionById(student.getUserSubscription().getSubscriptionId());
            if(!optionalUserSubscription.isPresent()){
                throw new CustomException(404,"UserSubscription Not Found");
            }
            UserSubscription userSubscription = optionalUserSubscription.get();
            userSubscription.setStartDate(LocalDateTime.now());
            userSubscription.setEndDate(LocalDateTime.now().plusDays(30));
            userSubscription.setSubscriptionPlan(subscriptionPlan);
            userSubscriptionRepository.save(userSubscription);

            payment.setPaymentStatus(PaymentStatus.COMPLETED);
            payment.setStudent(student);
            payment.setSubscriptionPlan(subscriptionPlan);
            paymentRepository.save(payment);
        }
        catch(Exception e){
            throw e;
        }
    }
}
