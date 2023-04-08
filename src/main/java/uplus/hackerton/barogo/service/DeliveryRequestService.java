package uplus.hackerton.barogo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uplus.hackerton.barogo.domain.Deliver;
import uplus.hackerton.barogo.domain.DeliveryRequest;
import uplus.hackerton.barogo.repository.DeliverRepository;
import uplus.hackerton.barogo.repository.DeliveryRequestRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryRequestService {

    private final DeliveryRequestRepository deliveryRequestRepository;
    private final DeliverRepository deliverRepository;

    public List<DeliveryRequest> getAllRequest(Long deliveryId) {
        Deliver deliver = deliverRepository.findById(deliveryId).get();
        return deliveryRequestRepository.findByDeliverOrderByBidPriceDesc(deliver);
    }

    public List<DeliveryRequest> getDeliveriesByDeliverUserId(Long deliverUserId) {
        return deliveryRequestRepository.findByDeliverUserId(deliverUserId);
    }

    public int getCountDeliveryRequest(Deliver deliver) {
        return deliveryRequestRepository.countByDeliver(deliver);
    }

    @Transactional
    public void updateDeliveryRequest(Long deliveryRequestId) {
        DeliveryRequest deliveryRequest = deliveryRequestRepository.findById(deliveryRequestId).get();
        Deliver deliver = deliveryRequest.getDeliver();
        deliver.insertDeliveryUser(deliveryRequest.getDeliverUser());
        deliver.completeDelivery();

        List<DeliveryRequest> deliveryRequestByDeliver = deliveryRequestRepository.findDeliveryRequestByDeliverAndIdNot(deliver, deliveryRequestId);
        for (DeliveryRequest request : deliveryRequestByDeliver) {
            request.changeToDECLINE();
        }
        deliveryRequest.changeToACCEPT();
    }
}
