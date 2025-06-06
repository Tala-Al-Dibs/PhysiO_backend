package com.project.physio_backend.Entities.Physiotherapists;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.physio_backend.Entities.Image.Image;
import com.project.physio_backend.Entities.Users.Location;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "physiotherapist")
@Data
@NoArgsConstructor
public class Physiotherapist {

    @Column(name = "physiotherapist_ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long physiotherapistID;

    private String clinicName;

    private long phonenumber;

    private double price;

    private String address;

    private String addressLink;

    @Enumerated(EnumType.STRING)
    private Location location;

    @JsonIgnore
    @OneToMany(mappedBy = "physiotherapist", cascade = CascadeType.ALL)
    private List<WorkingHours> workingHours;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    public void addWorkDay(WorkingHours workingDays) {
        if (workingHours == null) {
            workingHours = new ArrayList<>();
        }
        this.workingHours.add(workingDays);
    }

    public Physiotherapist(String clinicName, long phonenumber, double price, String address, String addressLink,
            Location location) {
        this.clinicName = clinicName;
        this.phonenumber = phonenumber;
        this.price = price;
        this.address = address;
        this.addressLink = addressLink;
        this.location = location;
        workingHours = new ArrayList<>();
    }
}
