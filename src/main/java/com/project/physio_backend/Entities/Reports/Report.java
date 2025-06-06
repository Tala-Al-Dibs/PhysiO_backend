package com.project.physio_backend.Entities.Reports;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.physio_backend.Entities.Image.Image;
import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Users.User;

@Data
@Entity
@Table(name = "report")
public class Report {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "report_ID", nullable = false)
  private Long reportID;

  private LocalDateTime timestamp;
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "user_ID", nullable = false)
  private User user;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "image_id", referencedColumnName = "id")
  private Image image;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "report-problem", joinColumns = @JoinColumn(name = "report_ID"), inverseJoinColumns = @JoinColumn(name = "problem_ID"))
  private List<Problem> problems = new ArrayList<>();

  public Report() {
    this.timestamp = LocalDateTime.now();
  }

  @PrePersist
  protected void onCreate() {
    this.timestamp = LocalDateTime.now();
  }

  public void addProblem(Problem problem) {
    if (!this.problems.contains(problem)) {
      this.problems.add(problem);
    }
  }
}
