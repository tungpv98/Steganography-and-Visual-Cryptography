package net.javaguides.springboot.springsecurity.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import agorithim.LSBEncode;
import agorithim.VCEncode;
import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.service.UserService;
import net.javaguides.springboot.springsecurity.web.dto.UserRegistrationDto;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
	private static final String EXTERNAL_FILE_PATH = ".\\pictures\\";

	@Autowired
	private UserService userService;

	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}

	@GetMapping
	public String showRegistrationForm(Model model) {
		return "registration";
	}

	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto, BindingResult result,
			HttpServletResponse response) throws Exception {

		userDto.setTotalMoney(200);
		LSBEncode lsbEncode = new LSBEncode();
		lsbEncode.EnCode(userDto.getScretKey());
		VCEncode vcEncode = new VCEncode();
		vcEncode.Encode(userDto.getEmail());
		String path = ".\\pictures\\"+userDto.getEmail()+"\\share2.png";
		userDto.setShare2(path);
		User existing = userService.findByEmail(userDto.getEmail());
		if (existing != null) {
			result.rejectValue("email", null, "There is already an account registered with that email");
		}

		if (result.hasErrors()) {
			return "registration";
		}

		userService.save(userDto);

		return "redirect:/registration/download?fileName="+userDto.getEmail();
	}
	
	@RequestMapping("/download")
	public String download(@RequestParam String fileName, Model model){
		model.addAttribute("fileName", fileName);
		return "download";	
	}

	@RequestMapping("/download1")
	public ResponseEntity<InputStreamResource> downloadFile1(
			@RequestParam String fileName) throws IOException {

//		String mediaType = "image/png";
//		System.out.println("fileName: " + fileName);
//		System.out.println("mediaType: " + "image/png");

		File file = new File(".\\pictures\\"+fileName+"\\share2.png");
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		return ResponseEntity.ok()
				// Content-Disposition
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
				// Content-Type
				.contentType(MediaType.IMAGE_PNG)
				// Contet-Length
				.contentLength(file.length()) //
				.body(resource);
	}
}
