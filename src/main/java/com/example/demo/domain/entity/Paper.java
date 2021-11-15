package com.example.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Paper {
	@Id
	private String id;
	private String title;
	private String author;
	private int publishedYear;
	private String pdfUrl;
	private String abstraction;
	private String type;
}