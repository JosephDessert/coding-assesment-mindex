package com.mindex.challenge.service;

import java.util.List;

import com.mindex.challenge.data.Compensation;

public interface CompensationService {
    List<Compensation> readAll();
    Compensation create(Compensation compensation);
    List<Compensation> readAllByEmployeeId(String id);
}
