package net.javaguides.springboot.springsecurity.web;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import antlr.collections.List;
import net.javaguides.springboot.springsecurity.model.MyFile;
import net.javaguides.springboot.springsecurity.model.User;
import net.javaguides.springboot.springsecurity.repository.UserRepository;
import net.javaguides.springboot.springsecurity.service.UserService;
import net.javaguides.springboot.springsecurity.service.UserServiceImpl;
import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import agorithim.VCDecode;
import agorithim.LSBDecode;

@Controller
public class MainController {

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String root(Model model, Principal principal) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		User user = userRepository.findByEmail(email);
		ArrayList<User> users = (ArrayList<User>) userRepository.findAll();
		users.remove(user);
		model.addAttribute("user", user);
		model.addAttribute("users", users);
		return "index";
	}

	@GetMapping("/login")
	public String login() {

		return "login";
	}

	@GetMapping("/user")
	public String userIndex() {
		return "user/index";
	}

	@GetMapping("/pay/{id}")
	public String pay(@PathVariable("id") int id, Model model) {
		User user = userRepository.getOne((long) id);
		model.addAttribute("user", user);
		model.addAttribute("myFile", new MyFile());
		return "pay";
	}

	@RequestMapping(value = "/pay/{id}", method = RequestMethod.POST)
    public String uploadFile(@PathVariable("id") int id, MyFile myFile, Model model) throws Exception {
      model.addAttribute("message", "Upload success");
      model.addAttribute("description", myFile.getDescription());
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
  	  String email = authentication.getName();
      MultipartFile multipartFile = myFile.getMultipartFile();
      Path filepath = Paths.get(".\\pictures\\"+email, multipartFile.getOriginalFilename());
//      String fileName = "user" +multipartFile.getOriginalFilename();
      try (OutputStream os = Files.newOutputStream(filepath)){
//        File file = new File(".\\pictures\\"+email+"\\"+ fileName);
    	  os.write(multipartFile.getBytes());
      } catch (Exception e) {
        e.printStackTrace();
        model.addAttribute("message", "Upload failed");
        return "pay/"+id;
      }
      
      VCDecode vcDecode = new VCDecode();
      String VCDecodefile = vcDecode.Decode(".\\pictures\\"+email+"\\share1.png", filepath.toString() , ".\\pictures\\"+email);
      System.out.println(VCDecodefile);
      LSBDecode lsbDecode = new LSBDecode();
      String scretMesage = lsbDecode.Decode(VCDecodefile);
      System.out.println(scretMesage);
      
	  User user = userRepository.findByEmail(email);
	  User user1 = userRepository.getOne((long) id);
	  
//	  File filetemp = new File(VCDecodefile);
//	  if(filetemp.delete()) { 
//          System.out.println("File deleted successfully"); 
//      }else{ 
//          System.out.println("Failed to delete the file"); 
//      }
//	  File filetemp1 = new File(".\\pictures\\"+email+"\\"+ fileName);
//	  filetemp1.delete();
      if(scretMesage.equals(user.getScretKey())) {
    	  user.setTotalMoney(user.getTotalMoney()-myFile.getMoney());
    	  user1.setTotalMoney(user1.getTotalMoney()+ myFile.getMoney());
    	  userRepository.save(user);
    	  userRepository.save(user1);
    	  return "result";
      } else {
    	  return "redirect:/pay/"+id;
      }
    }

//	public File getFolderUpload() {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		String email = authentication.getName();
//		File folderUpload = new File(".\\pictures\\" + email);
//		if (!folderUpload.exists()) {
//			folderUpload.mkdirs();
//		}
//		return folderUpload;
//	}

}
