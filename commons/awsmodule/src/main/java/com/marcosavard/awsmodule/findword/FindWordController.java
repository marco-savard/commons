package com.marcosavard.awsmodule.findword;

import com.marcosavard.awsmodule.sendmail.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FindWordController {
    private final FindWordService findWordService;

    @Autowired
    public FindWordController(FindWordService findWordService) {
        this.findWordService = findWordService;
        System.out.println("FindWordController");
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/findword")
    public String findword() {
        return "Request should be /findword/{wordpattern} where wordpattern constains letters and dots (wildcard), eg b..m matches boom";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/findword/{wordpattern}")
    public String findwordWordPattern(@PathVariable("wordpattern") String wordpattern) {
        String[] words = findWordService.findWords(wordpattern);
        return "{" + String.join(", ", words) + "}";
    }

}
