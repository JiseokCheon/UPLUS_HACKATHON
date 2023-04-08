package uplus.hackerton.barogo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uplus.hackerton.barogo.controller.form.GradeForm;
import uplus.hackerton.barogo.service.GradeService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/grade")
public class GradeController {

    private final GradeService gradeService;

    @GetMapping("/{deliverId}/{userId}")
    public String sendDeliverId( GradeForm form, Model model){
        model.addAttribute("gradeForm", form);
//        model.addAttribute("userId", userId);
        return "grade/insertGradeForm";
    }

    @PostMapping("/test")
    public String test(GradeForm gradeForm){
//        System.out.println(gradeForm.getDeliverId());
//        System.out.println(gradeForm.getScore());
//        System.out.println(gradeForm.getContent());

        gradeService.saveGrade(gradeForm);

        return "redirect:/";
    }
}
