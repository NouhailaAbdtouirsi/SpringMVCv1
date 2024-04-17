package com.nouhaila.spring_mvc.web;

import com.nouhaila.spring_mvc.entities.Patient;
import com.nouhaila.spring_mvc.repositories.PatientRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepo patientRepo;
    @GetMapping( "/index")
    public String index(Model model,

                        @RequestParam(name = "page",defaultValue = "0") int page,
                        @RequestParam(name = "size",defaultValue = "4") int size,
                        @RequestParam(name = "keyword",defaultValue = "") String keyword){
        Page<Patient> pagePatients = patientRepo.findByNameContains(keyword,PageRequest.of(page,size));

        model.addAttribute("listPatient", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patients";
    }

    @GetMapping("/delete")
    public String delete(Long id,
                         @RequestParam(name = "keyword",defaultValue = "") String keyword,
                         @RequestParam(name = "page",defaultValue = "0") int page){
        patientRepo.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/displayDetails")
    public String displayDetails(Long id, Model model){
        Patient patient = patientRepo.findById(id).orElse(null);
        model.addAttribute("patient", patient);
        return "patientDetails";
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }
}
