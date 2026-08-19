package com.company.sri.prod_ready_features.prod_ready_features.clients.impl;

import com.company.sri.prod_ready_features.prod_ready_features.advice.ApiResponse;
import com.company.sri.prod_ready_features.prod_ready_features.clients.EmployeeClient;
import com.company.sri.prod_ready_features.prod_ready_features.dto.EmployeeDTO;
import com.company.sri.prod_ready_features.prod_ready_features.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        try {
            ApiResponse<List<EmployeeDTO>> employeeDTOList = restClient.get()
                    .uri("/employees")
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return employeeDTOList.getData();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public EmployeeDTO getEmployeeById(Long employeeId) {
        try {
            ApiResponse<EmployeeDTO> employeeResponse = restClient.get()
                    .uri("/employees/{employeeId}", employeeId)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });

            return employeeResponse.getData();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createNewEmployee(EmployeeDTO employeeDTO) {
        try {
            ApiResponse<EmployeeDTO> employeeResponse = restClient.post()
                    .uri("/employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
//                        System.out.println("Error occurred " + Arrays.toString(res.getBody().readAllBytes()));
//                        String body = new String(
//                                res.getBody().readAllBytes(),
//                                StandardCharsets.UTF_8
//                        );
//
//                        System.out.println("Error occurred: " + body);
                        System.out.println("Error occurred: " + new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("Could not create the employee");
                    })
//                    .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
//                        throw new RuntimeException("Server error occurred");
//                    })
//                    Shift this to RestClient Config, for Global Handling
                    .body(new ParameterizedTypeReference<>() {
                    });

            return employeeResponse.getData();
        } catch(Exception e) {
            throw new RuntimeException(e);
//            not 4xx or 5xx error, let's say the server is down. I/O exception.
        }
    }

//    To get Response Entity, will have access to Status Code, Header, Body etc...
//    if you want just body then use .body()
    @Override
    public EmployeeDTO createNewEmployee1(EmployeeDTO employeeDTO) {
        try {
            ResponseEntity<ApiResponse<EmployeeDTO>> employeeResponse = restClient.post()
                    .uri("/employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                        System.out.println("Error occurred: " + new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("Could not create the employee");
                    })
                    .toEntity(new ParameterizedTypeReference<>() {
                    });

//            employeeResponse.getHeaders();

            return employeeResponse.getBody().getData();
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
