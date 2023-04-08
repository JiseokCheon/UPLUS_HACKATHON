package uplus.hackerton.barogo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uplus.hackerton.barogo.controller.form.GradeForm;
import uplus.hackerton.barogo.domain.Deliver;
import uplus.hackerton.barogo.domain.Grade;
import uplus.hackerton.barogo.domain.User;
import uplus.hackerton.barogo.repository.DeliverRepository;
import uplus.hackerton.barogo.repository.GradeRepository;
import uplus.hackerton.barogo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final DeliverRepository deliverRepository;

    public void saveGrade(GradeForm gradeForm) {
        Deliver deliver = deliverRepository.findById(gradeForm.getDeliverId()).get();

        User user = userRepository.findById(deliver.getDeliverUser().getId()).get();

        Grade grade =  Grade.builder()
                .score(gradeForm.getScore())
                .content(gradeForm.getContent())
                .user(user)
                .deliver(deliver)
                .build();

        gradeRepository.save(grade);
    }
}
