package com.project.physio_backend.Entities.Users;

import com.project.physio_backend.Entities.Prize.Prize;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Progress.Progress;
import com.project.physio_backend.Entities.Reports.Report;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_ID", nullable = false)
  private Long userID;

  @NotBlank(message = "Username must not be blank")
  @Column(nullable = false, unique = true)
  private String username;

  @NotBlank(message = "Password must not be blank")
  @Column(nullable = false)
  private String password;

  @JsonIgnore
  @OneToOne(cascade = CascadeType.ALL)
  private Profile profile;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Report> reports;

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Progress> progresses = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private List<Prize> prize = new ArrayList<>();

  @JsonIgnore
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user-problem", joinColumns = @JoinColumn(name = "user_ID"), inverseJoinColumns = @JoinColumn(name = "problem_ID"))
  private List<Problem> problems;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public User(String username) {
    this.username = username;
  }

  public User() {
  }

  public void addProgress(Progress progress) {
    if (progresses == null) {
      progresses = new ArrayList<>();
    }
    this.progresses.add(progress);
  }
}
