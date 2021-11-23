package com.example.demo.domain.Enum;

public enum SortType {
	PUBLISHED, QUOTE, HOT;

	public static SortType of(int sortType) {
		switch (sortType) {
			case 1:
				return PUBLISHED;
			case 2:
				return QUOTE;
			case 3:
				return HOT;
			default:
				return null;
		}
	}
}
