package com.example.boot_upload.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FileUploadController {
	
	// 파일 저장 경로
	 private final Path fileStorageLocation;
	
	// application.propertiies에 file.upload-dir 키에 설정된 값을
	// 불러와서 파일 저장 경로를 설정하는 생성자
	public FileUploadController(
			// ${...}에 넣은 키에 연동된 값을 알아서 주입해줌
			@Value("${file.upload-dir}") String uploadDir) {
		 System.out.println("uploadDir : " + uploadDir);
		fileStorageLocation = Paths.get(uploadDir)
				.toAbsolutePath().normalize();
		System.out.println("path : " + fileStorageLocation);
		
		try {
			Files.createDirectories(fileStorageLocation);
		} catch (IOException e) {
			throw new RuntimeException("파일을 저장할 디렉토리 생성 불가능");
		}
	}
	
	@PostMapping("/upload")
	public String handleFileUpload(
			@RequestParam MultipartFile file,
			RedirectAttributes redirectAttributes) {
		if (file.isEmpty()) {
			System.out.println("빈 파일");
			redirectAttributes.addFlashAttribute(
					"message", "파일을 선택해주세요.");
		} else {			
			// 파일 이름
			System.out.println(file.getOriginalFilename());
			// 파일 크기
			System.out.println(file.getSize());
			// 저장 처리
			String filename = storeFile(file);
			redirectAttributes.addFlashAttribute(
					"message", "파일 업로드 성공: " + filename);
		}
		return "redirect:/";
	}
	
	private String storeFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(
				file.getOriginalFilename());
		// 저장을 위해 사용할 Path에 맞게...
		Path targetLocation = fileStorageLocation.resolve(fileName);
		
		try {
			Files.copy(file.getInputStream(), targetLocation,
					StandardCopyOption.REPLACE_EXISTING);
			return fileName;
		} catch (IOException e) {
			throw new RuntimeException("파일 저장 실패: " + fileName, e);
		}
		
	}
	
	@GetMapping
	public String fileUploadForm(
			Model model) throws IOException {
		System.out.println("fileUploadForm");
		List<String> files =
				Files.list(fileStorageLocation) // uploads에 저장된 파일 목록을 Path 목록
				.map(path -> path.getFileName().toString()) // 문자열로 전환
				.collect(Collectors.toList());
		model.addAttribute("files", files);
		return "file";
	}
}
