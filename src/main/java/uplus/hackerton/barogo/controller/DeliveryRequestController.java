package uplus.hackerton.barogo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uplus.hackerton.barogo.domain.DeliveryRequest;
import uplus.hackerton.barogo.service.DeliveryRequestService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/DeliveryRequest")
public class DeliveryRequestController {

	private final DeliveryRequestService deliveryRequestService;

	@GetMapping("/{deliveryId}")
	public String viewBidList(@PathVariable Long deliveryId, Model model) {
		List<DeliveryRequest> allRequest = deliveryRequestService.getAllRequest(deliveryId);

		model.addAttribute("deliveryId", deliveryId);
		model.addAttribute("requestList", allRequest);
		return "deliveryRequest/requestList";
	}

	@GetMapping
	public String selectBid(Long deliveryRequestId, Model model){
		deliveryRequestService.updateDeliveryRequest(deliveryRequestId);
		return "redirect:/order/request-deliver";
	}
}
