package com.example.graphqldemo.doa;

import com.example.graphqldemo.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentDao extends JpaRepository<Department, Long> {
  Department findByDepartmentId(String id);
}
