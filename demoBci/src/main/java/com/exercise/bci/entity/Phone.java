package com.exercise.bci.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "phones")
@Data
@NoArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long number;
    private Integer cityCode;
    private String countryCode;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User userPhone;

    public Phone(Long number, Integer cityCode, String countryCode) {
        this.setNumber(number);
        this.setCityCode(cityCode);
        this.setCountryCode(countryCode);
    }
}
