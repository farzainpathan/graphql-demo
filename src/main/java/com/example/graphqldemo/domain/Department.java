package com.example.graphqldemo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "T_DEPARTMENT")
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_DEPARTMENT")
  @SequenceGenerator(
      name = "SEQ_T_DEPARTMENT",
      sequenceName = "SEQ_T_DEPARTMENT",
      initialValue = 1,
      allocationSize = 1)
  @Column(name = "ID")
  private Long Id;

  @Column(name = "DEPARTMENT_NAME")
  private String departmentName;

  @Column(name = "DEPARTMENT_ID")
  private String departmentId;

  @Column(name = "HOD_NAME")
  private String hodName;

  @Column(name = "COLLEGE_NAME")
  private String collegeName;

  @Column(name = "PRINCIPAL_NAME")
  private String principalName;
}
