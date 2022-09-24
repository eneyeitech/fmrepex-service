package com.example.fmrepexservice.workordermanagement.persistence;

import com.example.fmrepexservice.requestmanagement.business.Request;
import com.example.fmrepexservice.workordermanagement.exception.TableException;

import java.util.List;

public class MYSQLRequestDAO extends DAO<Request> {
    @Override
    public boolean add(Request request) {
        return false;
    }

    @Override
    public boolean remove(String id) {
        return false;
    }

    @Override
    public Request get(String id) {
        return null;
    }

    @Override
    public List<Request> getAll() {
        return null;
    }

    @Override
    public List<Request> getAll(String tenantEmail) {
        return null;
    }

    @Override
    public boolean update(Request request) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public void createTable() throws TableException {

    }

    @Override
    public void dropTable() throws TableException {

    }
}
