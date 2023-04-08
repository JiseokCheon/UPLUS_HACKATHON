package uplus.hackerton.barogo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uplus.hackerton.barogo.controller.form.DeliveryForm;
import uplus.hackerton.barogo.domain.Deliver;
import uplus.hackerton.barogo.domain.code.MaxWeight;
import uplus.hackerton.barogo.domain.code.TransportType;
import uplus.hackerton.barogo.domain.code.TransportTypeFactory;
import uplus.hackerton.barogo.service.DeliverService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class DeliveryController {

	public static final String ORDER_INFO = "orderInfo";
	private final DeliverService deliverService;

	@ModelAttribute("transportTypes")
	public List<TransportType> transportTypes() {
		return TransportTypeFactory.getInstance();
	}

	@ModelAttribute("maxWeights")
	public MaxWeight[] maxWeights() {
		return MaxWeight.values();
	}

	@GetMapping("request_delivery")
	public String viewRequestDelivery(DeliveryForm form, Model model) {
		model.addAttribute(ORDER_INFO, form);
		return "delivery/orderDelivery";
	}

	@GetMapping("delivery_map")
	public String viewSenderMap(DeliveryForm form, Model model) {
		model.addAttribute(ORDER_INFO, form);
		return "delivery/map";
	}

	@GetMapping("/delivery/{id}")
	public String completeDelivery(@PathVariable Long id) {
		deliverService.completeDeliver(id);
		return "redirect:/";
	}

	@PostMapping("request_delivery")
	public String processRequestDelivery(@Validated @ModelAttribute DeliveryForm form, BindingResult bindingResult,
		Model model) {
		log.info("processOrder {} {} {} {} {} {}", form.getLoginName(), form.getLoginEmail(), form.getContent(),
			form.getStart(), form.getEnd(), form.getDueDate());

		// log.info("objectName={} {}", bindingResult.getObjectName(), bindingResult.getTarget());

		//검증에 실패하면 다시 입력 폼으로
		if (bindingResult.hasErrors()) {
			log.info("errors={} ", bindingResult);
			model.addAttribute(ORDER_INFO, form);
			return "delivery/orderDelivery";
		}

		Deliver deliver = deliverService.insertOrder(form.toCommand());

		// model.addAttribute(ORDER_INFO, new OrderView(order));
		return "redirect:/";
	}

}
