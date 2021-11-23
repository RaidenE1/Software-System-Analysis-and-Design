package com.example.demo.controller;

import com.example.demo.domain.Enum.SearchType;
import com.example.demo.domain.Enum.SortType;
import com.example.demo.domain.common.R;
import com.example.demo.domain.dto.PaperSearchDTO;
import com.example.demo.domain.entity.Paper;
import com.example.demo.service.PaperService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PaperController {
	@Resource
	private PaperService paperService;

	@GetMapping("/paper/list")
	public R list() {
		List<Paper> papers = paperService.list();
		return R.success(papers);
	}

	@GetMapping("/paper/searchByKeyword")
	public R searchByKeyword(String keyword) {
		List<Paper> papers = paperService.searchByKeyword(keyword);
		return R.success(papers);
	}

	@GetMapping("/paper/searchByAuthor")
	public R searchByAuthor(String author) {
		List<Paper> papers = paperService.searchByAuthor(author);
		return R.success(papers);
	}

	@GetMapping("/paper/searchByTitle")
	public R searchByTitle(String title) {
		List<Paper> papers = paperService.searchByTitle(title);
		return R.success(papers);
	}

	@GetMapping("/article_search_and_sort")
	public R search(@RequestBody @Validated PaperSearchDTO dto) {
		List<Paper> papers = null;
		switch (SearchType.of(dto.getSearchtype())) {
			case TITLE:
				papers = paperService.searchByTitle(dto.getContent());
				break;
			case AUTHOR:
				papers = paperService.searchByAuthor(dto.getContent());
				break;
			case KEYWORD:
				papers = paperService.searchByKeyword(dto.getContent());
				break;
			default:
				throw new RuntimeException("search type error");
		}
		papers.sort((o1, o2) -> {
			switch (SortType.of(dto.getSorttype())) {
				case PUBLISHED:
				case QUOTE:
				case HOT:
					return o2.getPublishedYear() - o1.getPublishedYear();
				default:
					throw new RuntimeException("sort type error");
			}
		});

		return R.success(papers);
	}

	@PutMapping("/paper/add")
	public R add(@RequestBody Paper paper) {
		paperService.add(paper);
		return R.success();
	}

	@PostMapping("/paper/update")
	public R update(@RequestBody Paper paper) {
		paperService.update(paper);
		return R.success();
	}

	@DeleteMapping("/paper/delete")
	public R delete(@RequestParam String id) {
		paperService.delete(id);
		return R.success();
	}

	@GetMapping("/paper/get")
	public R get(@RequestParam String id) {
		Paper paper = paperService.get(id);
		return R.success(paper);
	}
}