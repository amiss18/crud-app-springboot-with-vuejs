package com.baayel.products.web.controller;


import com.baayel.products.storage.StorageFileNotFoundException;
import com.baayel.products.storage.StorageService;
import com.baayel.products.storage.StorageServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController("/")
public class HomeController {

    private final StorageServiceInterface storageService;

    @Autowired
    public HomeController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping(value = "/")
    public String index(){

        return "home";
    }

/*
    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }
*/
    @RequestMapping(value = "upload",method = RequestMethod.POST)
    @ResponseBody
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {

        storageService.store(file);


        return "You successfully uploaded " + file.getOriginalFilename() + "!";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
