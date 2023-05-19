package com.manage.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Position")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String note;

    @ManyToMany
    @JoinTable(name = "position_hsl", joinColumns = @JoinColumn(name = "position_id"), inverseJoinColumns = @JoinColumn(name = "hsl_id"))
    private Set<HSL> hsl = new HashSet<>();

    @OneToMany(mappedBy = "position",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Wage> wages = new HashSet<>();

}
