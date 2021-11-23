package com.example.demo.domain.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
@Valid
public class PaperSearchDTO {
	@Min(1)
	@Max(3)
	private int searchtype;

	private String content;

	@Min(1)
	@Max(3)
	private int sorttype;

	private int extraseclect;
}
