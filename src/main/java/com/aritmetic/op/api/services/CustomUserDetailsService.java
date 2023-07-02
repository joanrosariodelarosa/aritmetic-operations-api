package com.aritmetic.op.api.services;

import com.aritmetic.op.api.dtos.OperationResponseDto;
import com.aritmetic.op.api.models.Operation;
import com.aritmetic.op.api.models.Record;
import com.aritmetic.op.api.models.User;
import com.aritmetic.op.api.repositories.OperationRepository;
import com.aritmetic.op.api.repositories.RecordRepository;
import com.aritmetic.op.api.repositories.UserRepository;
import com.aritmetic.op.api.types.OperationType;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Data
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RecordRepository recordRepository;
    private final OperationRepository operationRepository;
    private User user;
    public final UserDetailsService userDetailsService;
    private List<Record> listOfRecords;
    private double lastBalance;
    private double operationCost;
    private Operation operation;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserData(username);
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }

    public User getUserData(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        setUser(user);
        return user;
    }

    public ResponseEntity<OperationResponseDto> saveOperation(OperationType operationType,
                                                              ResponseEntity<OperationResponseDto> result) {
        List<Record> listOfRecords = this.listOfRecords.size() == 0 ? getListOfRecords() : this.listOfRecords;
        double operationCost = this.operationCost == 0 ? getOperationCost(operationType) : this.operationCost;
        double lastBalance = this.lastBalance == 0 ? getLastBalance(listOfRecords) : this.lastBalance;
        result.getBody().setCurrentBalance(lastBalance - operationCost);
        listOfRecords.add(createAndSaveNewRecord(result));
        result.getBody().setListRecords(listOfRecords);
        return result;
    }

    public List<Record> getListOfRecords() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<Record> listOfRecords = new ArrayList<>();
        if (authentication != null && authentication.isAuthenticated()) {
            User user = (User) authentication.getPrincipal();
            listOfRecords = recordRepository.findAllByUserId(user.getId());
        }
        return listOfRecords;
    }

    public double getLastBalance(List<Record> listOfRecords) {
        Record lastRecord = listOfRecords.stream()
                .max(Comparator.comparing(Record::getDate))
                .orElse(null);
        return (lastRecord != null && lastRecord.getUserBalance() > 0) ? lastRecord.getUserBalance() : 0.0;
    }

    public double getOperationCost(OperationType operationType) {
        double operationCost = 0.0;
        Optional<List<Operation>> operationList = operationRepository.findByOperationType(operationType);
        if (operationList.isPresent()) {
            this.operation = operationList.get().get(0);
            operationCost = operationList.get().get(0).getCost();
        }
        return operationCost;
    }

    private Record createAndSaveNewRecord(ResponseEntity<OperationResponseDto> result) {
        Record record = Record.builder()
                .userBalance(0.5)
                .userId(user.getId())
                .operation(operation)
                .date(LocalDateTime.now())
                .amount(result.getBody().getResult())
                .userBalance(result.getBody().getCurrentBalance())
                .operationResponse(result.getBody().toString())
                .build();
        recordRepository.save(record);
        return record;
    }

    public boolean canPerformOperation(OperationType operationType) {
        listOfRecords = getListOfRecords();
        lastBalance = getLastBalance(listOfRecords);
        operationCost = getOperationCost(operationType);
        return lastBalance >= operationCost;
    }
}
