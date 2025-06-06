package com.project.physio_backend.Controllers;

import com.project.physio_backend.Entities.Problems.Problem;
import com.project.physio_backend.Entities.Users.User;
import com.project.physio_backend.Repositories.ProblemRepository;
import com.project.physio_backend.Repositories.UserRepository;
import com.project.physio_backend.Services.ProblemService.ProblemService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/problems")
@CrossOrigin(origins = "*")
public class ProblemController {

  private final ProblemService problemService;

  public ProblemController(ProblemService problemService, UserRepository userRepository,
      ProblemRepository problemRepository) {
    this.problemService = problemService;

  }

  @GetMapping
  public List<Problem> getAllProblems() {
    return problemService.getAllProblems();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Problem> getProblemById(@PathVariable Long id) {
    return ResponseEntity.ok(problemService.getProblemById(id));
  }

  @PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Problem> createProblemWithImage(
      @RequestPart("problem") Problem problem,
      @RequestPart("image") MultipartFile file) {

    Problem createdProblem = problemService.createProblemWithImage(problem, file);
    return ResponseEntity.ok(createdProblem);
  }

  @PostMapping
  public ResponseEntity<Problem> createProblem(
      @RequestBody Problem problem) {
    Problem createdProblem = problemService.createProblem(problem);
    return ResponseEntity.ok(createdProblem);
  }

  @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Problem> updateProblem(
      @PathVariable Long id,
      @RequestPart("problem") Problem problem,
      @RequestPart(value = "image", required = false) MultipartFile file) {

    Problem updatedProblem = problemService.updateProblem(id, problem, file);
    return ResponseEntity.ok(updatedProblem);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProblem(@PathVariable Long id) {
    problemService.deleteProblem(id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/user/{userId}/remove-problem/{problemId}")
  public ResponseEntity<User> removeProblemFromUser(@PathVariable Long userId, @PathVariable Long problemId) {
    return ResponseEntity.ok(problemService.removeProblemFromUser(userId, problemId));
  }

  @GetMapping("/user/{id}/problems")
  public ResponseEntity<List<Problem>> getUserProblems(@PathVariable Long id) {
    return ResponseEntity.ok(problemService.getUserProblems(id));
  }

  @PostMapping("/user/{userId}/add-problem/{problemId}")
  public ResponseEntity<User> addProblemToUser(@PathVariable Long userId, @PathVariable Long problemId) {
    return ResponseEntity.ok(problemService.addProblemToUser(userId, problemId));
  }

  @GetMapping("/names")
  public ResponseEntity<List<String>> getProblemNames() {
    return ResponseEntity.ok(problemService.getProblemNames());
  }

  @GetMapping("/name/{name}")
  public ResponseEntity<Problem> getProblemByName(@PathVariable String name) {
    return ResponseEntity.ok(problemService.getProblemByName(name));
  }

}