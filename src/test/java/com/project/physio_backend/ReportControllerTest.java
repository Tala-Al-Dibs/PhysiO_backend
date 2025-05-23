package com.project.physio_backend;

import static io.restassured.RestAssured.given;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.project.physio_backend.Controllers.ReportController;
import com.project.physio_backend.Entities.Reports.Report;
import com.project.physio_backend.Repositories.ProblemRepository;
import com.project.physio_backend.Repositories.ReportRepository;
import com.project.physio_backend.Repositories.UserRepository;
import com.project.physio_backend.Services.ReportService.ReportService;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

@SpringBootTest
@AutoConfigureMockMvc
public class ReportControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ReportService reportService;

  @Mock
  private UserRepository userRepository;

  @Mock
  private ProblemRepository problemRepository;

  @Mock
  private ReportRepository reportRepository;

  @InjectMocks
  private ReportController reportController;

  private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

  private static String token;

  @BeforeAll
  public static void setup() {
    Response response = given()
        .contentType(ContentType.JSON)
        .body("{\"username\":\"nour22\",\"password\":\"nour2003\"}")
        .when()
        .post("http://localhost:8080/api/auth/signin")
        .then()
        .extract()
        .response();

    token = response.jsonPath().getString("accessToken");
    assertNotNull(token);
  }

  @Test
  void testGetAllReports() throws Exception {
    Report report1 = new Report();
    Report report2 = new Report();
    List<Report> reports = Arrays.asList(report1, report2);

    when(reportService.getAllReports()).thenReturn(reports);

    mockMvc.perform(get("/api/reports")
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(reportService, times(1)).getAllReports();
  }

  @Test
  void testGetReportById() throws Exception {
    Report report = new Report();
    report.setReportID(1L);

    when(reportService.getReportById(1L)).thenReturn(report);

    mockMvc.perform(get("/api/reports/{id}", 1L)
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(reportService, times(1)).getReportById(1L);
  }

  @Test
  void testCreateReport() throws Exception {
    Report report = new Report();
    report.setReportID(1L);

    when(reportService.createReport(eq(1L), any(Report.class))).thenReturn(report);

    mockMvc.perform(post("/api/reports/{userId}", 1L)
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(report)))
        .andExpect(status().isCreated());

    verify(reportService, times(1)).createReport(eq(1L), any(Report.class));
  }

  @Test
  void testCreateReportWithImage() throws Exception {
    Report report = new Report();
    report.setReportID(1L);

    MockMultipartFile file = new MockMultipartFile(
        "image",
        "test.jpg",
        MediaType.IMAGE_JPEG_VALUE,
        "test image content".getBytes());

    MockMultipartFile reportJson = new MockMultipartFile(
        "report",
        "",
        MediaType.APPLICATION_JSON_VALUE,
        objectMapper.writeValueAsBytes(report));

    when(reportService.createReportWithImage(eq(1L), any(Report.class), any(MultipartFile.class)))
        .thenReturn(report);

    mockMvc.perform(MockMvcRequestBuilders.multipart("/api/reports/{userId}/image", 1L)
        .file(file)
        .file(reportJson)
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
        .andExpect(status().isCreated());

    verify(reportService, times(1)).createReportWithImage(eq(1L), any(Report.class), any(MultipartFile.class));
  }

  @Test
  void testAddProblemsToReport() throws Exception {
    Report report = new Report();
    report.setReportID(1L);

    List<String> problemNames = Arrays.asList("Problem1", "Problem2");

    when(reportService.addProblemsToReport(eq(1L), eq(problemNames))).thenReturn(report);

    mockMvc.perform(post("/api/reports/{reportId}/add-problems", 1L)
        .header("Authorization", "Bearer " + token)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(problemNames)))
        .andExpect(status().isOk());

    verify(reportService, times(1)).addProblemsToReport(eq(1L), eq(problemNames));
  }
}