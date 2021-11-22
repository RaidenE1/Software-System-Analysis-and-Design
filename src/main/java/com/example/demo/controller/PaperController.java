package com.example.demo.controller;

import com.example.demo.domain.common.R;
import com.example.demo.domain.entity.Paper;
import com.example.demo.service.PaperService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/paper")
public class PaperController {
	@Resource
	private PaperService paperService;

	@GetMapping("/list")
	public R list() {
		List<Paper> papers = paperService.list();
		return R.success(papers);
	}

	@GetMapping("/searchByKeyword")
	public R searchByKeyword(String keyword) {
		List<Paper> papers = paperService.searchByKeyword(keyword);
		return R.success(papers);
	}

	@GetMapping("/searchByAuthor")
	public R searchByAuthor(String author) {
		List<Paper> papers = paperService.searchByAuthor(author);
		return R.success(papers);
	}

	@GetMapping("/searchByTitle")
	public R searchByTitle(String title) {
		List<Paper> papers = paperService.searchByTitle(title);
		return R.success(papers);
	}

	@PutMapping("/add")
	public R add(@RequestBody Paper paper) {
		paperService.add(paper);
		return R.success();
	}

	@PostMapping("/update")
	public R update(@RequestBody Paper paper) {
		paperService.update(paper);
		return R.success();

	}

	@DeleteMapping("/delete")
	public R delete(@RequestParam String id) {
		paperService.delete(id);
		return R.success();
	}

	@GetMapping("/get")
	public R get(@RequestParam String id) {
		Paper paper = paperService.get(id);
		return R.success(paper);
	}
}