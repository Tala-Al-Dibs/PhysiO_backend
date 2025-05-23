package com.project.physio_backend.Entities.Physiotherapists;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "working_hours")
@Data
public class WorkingHours {

    @Id
    @Column(name = "workingHours_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workingHoursID;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    private String startTime;

    private String endTime;

    @ManyToOne
    @JoinColumn(name = "physiotherapist_ID", nullable = false)
    private Physiotherapist physiotherapist;

    public WorkingHours(DayOfWeek dayOfWeek, String startTime , String endTime){
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public WorkingHours(){
        
    }
}
