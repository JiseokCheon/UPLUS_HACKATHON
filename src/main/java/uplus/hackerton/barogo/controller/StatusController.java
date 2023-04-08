package uplus.hackerton.barogo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uplus.hackerton.barogo.controller.form.BidRequestForm;
import uplus.hackerton.barogo.controller.form.PerformDeliverView;
import uplus.hackerton.barogo.domain.Deliver;
import uplus.hackerton.barogo.domain.DeliveryRequest;
import uplus.hackerton.barogo.domain.User;
import uplus.hackerton.barogo.domain.code.TransportType;
import uplus.hackerton.barogo.domain.code.TransportTypeFactory;
import uplus.hackerton.barogo.service.DeliverService;
import uplus.hackerton.barogo.service.DeliveryRequestService;
import uplus.hackerton.barogo.service.UserService;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class StatusController {

	private final DeliverService deliverService;

	private final DeliveryRequestService deliveryRequestService;

	private final UserService userService;

	@ModelAttribute("transportTypes")
	public List<TransportType> transportTypes() {
		return TransportTypeFactory.getInstance();
	}

	@GetMapping("/request-deliver")
	public String requestDeliverStatus(Model model, @AuthenticationPrincipal OAuth2User loginUser) {
		String email = loginUser.getAttribute("email");
		User user = userService.getUserByEmail(email);

		List<Deliver> delivers = deliverService.getDeliveriesByRequestUserId(user.getId());
		model.addAttribute("deliverList", delivers);

		return "myPage/requestDeliveryStatus";
	}

	@GetMapping("/perform-deliver")
	public String performedDeliveryStatus(Model model, @AuthenticationPrincipal OAuth2User loginUser) {
		String email = loginUser.getAttribute("email");
		User user = userService.getUserByEmail(email);

		List<DeliveryRequest> deliveryRequests = deliveryRequestService.getDeliveriesByDeliverUserId(user.getId());
		List<PerformDeliverView> performDeliverViews = deliveryRequests.stream().map(PerformDeliverView::new).toList();

		model.addAttribute("deliverList", performDeliverViews);

		return "myPage/performDeliveryStatus";
	}

	@GetMapping("/bid-request/{id}")
	public String viewBidRequest(@PathVariable Long id, BidRequestForm form, Model model) {
		Deliver deliverById = deliverService.findDeliverById(id);
		model.addAttribute("deliverInfo", deliverById);
		model.addAttribute("bidRequest", form);

		return "delivery/bidRequest";
	}

	@PostMapping("/bid-request")
	public String processBidRequest(@Validated @ModelAttribute BidRequestForm form, Model model) {
		log.info("processBidRequest Name:{} Email:{} DeliverId:{} BidPrice:{} comment:{} DeliverPhone:{}",
			form.getLoginName(),
			form.getLoginEmail(), form.getDeliverId(), form.getBidPrice(), form.getComment(),
			form.getDeliverUserPhone());

		Long savedId = deliverService.insertDeliverRequest(form.toCommand());

		// model.addAttribute(ORDER_INFO, new OrderView(order));
		return "redirect:/";
	}

}
