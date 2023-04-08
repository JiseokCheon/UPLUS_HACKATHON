package uplus.hackerton.barogo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uplus.hackerton.barogo.controller.form.DeliverySearchForm;
import uplus.hackerton.barogo.domain.Deliver;
import uplus.hackerton.barogo.domain.DeliveryRequest;
import uplus.hackerton.barogo.domain.DeliveryStatus;
import uplus.hackerton.barogo.domain.User;
import uplus.hackerton.barogo.repository.DeliverDto;
import uplus.hackerton.barogo.repository.DeliverRepository;
import uplus.hackerton.barogo.repository.DeliveryRequestRepository;
import uplus.hackerton.barogo.repository.UserRepository;
import uplus.hackerton.barogo.service.command.BidRequestCommand;
import uplus.hackerton.barogo.service.command.DeliverCommand;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliverService {

    private final DeliverRepository deliverRepository;

    private final DeliveryRequestRepository deliveryRequestRepository;

    private final UserRepository userRepository;

    public Deliver findDeliverById(Long id) {
        return deliverRepository.findById(id).get();
    }

    @Transactional
    public void completeDeliver(Long id) {
        Deliver deliver = findDeliverById(id);
        deliver.completeDelivery();
    }

    public Page<DeliverDto> findDeliverAll(Pageable pageable, DeliverySearchForm form) {
        log.info("getStart {}, getEnd() {}", form.getStart(), form.getEnd());
        return deliverRepository.findDeliver(pageable, form.getStart() == null ? "%" : "%" + form.getStart() + "%",
                form.getEnd() == null ? "%" : "%" + form.getEnd() + "%");
    }

    public List<Deliver> findTop5ByDeliveryStatusCompleteOrderById(){
        return deliverRepository.findTop5ByDeliveryStatusOrderById(DeliveryStatus.COMPLETE);
    }

    public List<Deliver> getDeliveriesByRequestUserId(Long requestUserId) {
        return deliverRepository.findByRequestUserId(requestUserId);
    }

    public List<Deliver> getDeliveriesByDeliveryUserId(Long deliveryUserId) {
        return deliverRepository.findByDeliverUserId(deliveryUserId);
    }

    @Transactional
    public Long insertDeliverRequest(BidRequestCommand command) {

        Optional<User> optionalUser = userRepository.findByEmail(command.getLoginEmail());
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("요청하려는 유저가 등록된 유저가 아닙니다");
        }
        User user = optionalUser.get();
        command.setDeliverUser(user);

        Deliver selectedDeliver = deliverRepository.findById(command.getDeliverId()).get();
        selectedDeliver.changeToBid();
        log.info("selectedDeliver {} {}", command.getDeliverId(), selectedDeliver.getStart());
        command.setDeliver(selectedDeliver);

        DeliveryRequest savedRequest = deliveryRequestRepository.save(command.toEntity());
        return savedRequest.getId();
    }

    @Transactional
    public Deliver insertOrder(DeliverCommand command) {
        log.info("before select {}", command.getLoginEmail());

        Optional<User> optionalUser = userRepository.findByEmail(command.getLoginEmail());
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("요청하려는 유저가 등록된 유저가 아닙니다");
        }
        User user = optionalUser.get();
        log.info("selected user {} {}", user.getEmail(), user.getId());
        command.setRequestUser(user);
        return deliverRepository.save(command.toEntity());

    }

}
