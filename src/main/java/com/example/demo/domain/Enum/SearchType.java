package com.example.demo.domain.Enum;

public enum SearchType {
	TITLE, AUTHOR, KEYWORD;

	public static SearchType of(int searchtype) {
		switch (searchtype) {
			case 1:
				return TITLE;
			case 2:
				return AUTHOR;
			case 3:
				return KEYWORD;
			default:
				return null;
		}
	}
}
