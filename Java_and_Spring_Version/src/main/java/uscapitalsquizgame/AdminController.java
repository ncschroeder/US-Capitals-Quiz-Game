package uscapitalsquizgame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;
import uscapitalsquizgame.statetypes.*;

@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private StatesRepository statesRepository;
    
    @Autowired
    private RandomnessService randomnessService;
    
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }
    
    @GetMapping("/admin")
    public String admin() {
        return "admin.html";
    }
    
    @PutMapping("/admin/statesData")
    public @ResponseBody void updateStatesData(
        @RequestParam MultipartFile file,
        @RequestParam boolean updateRandomnessService
    ) {
        statesRepository.deleteAll();
        statesRepository.saveAll(adminService.getStates(file));
        if (updateRandomnessService) {
            randomnessService.updateStates();
        }
    }
    
    @GetMapping("/admin/statesData")
    public @ResponseBody Map<String, List<NonEntityState>> getStatesData() {
        List<NonEntityState> statesInDatabase =
            statesRepository
            .findAll()
            .map(NonEntityState::new)
            .toList();
        
        List<NonEntityState> statesInRs = randomnessService.getStates();
        
        var map = new HashMap<String, List<NonEntityState>>(2);
        map.put("statesInDatabase", statesInDatabase);
        if (!statesInDatabase.equals(statesInRs)) {
            map.put("statesInRandomnessService", statesInRs);
        }
        return map;
    }
    
    @GetMapping("/admin/statesDataTableRowsHtml")
    public @ResponseBody List<String> getStatesDataTableRowsHtml() {
        return adminService.getStatesDataTableRowsHtml();
    }
}
