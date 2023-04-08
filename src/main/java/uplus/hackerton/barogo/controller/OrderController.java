package uplus.hackerton.barogo.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uplus.hackerton.barogo.controller.form.OrderForm;
import uplus.hackerton.barogo.controller.form.OrderView;
import uplus.hackerton.barogo.domain.Order;
import uplus.hackerton.barogo.domain.code.MaxWeight;
import uplus.hackerton.barogo.domain.code.TransportType;
import uplus.hackerton.barogo.domain.code.TransportTypeFactory;
import uplus.hackerton.barogo.service.OrderService;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

	public static final String ORDER_INFO = "orderInfo";
	private final OrderService orderService;

	@ModelAttribute("transportTypes")
	public List<TransportType> transportTypes() {
		return TransportTypeFactory.getInstance();
	}

	@ModelAttribute("maxWeights")
	public MaxWeight[] maxWeights() {
		return MaxWeight.values();
	}

	@PostMapping
	public String processOrder(@Validated @ModelAttribute OrderForm form, BindingResult bindingResult, Model model) {
		log.info("processOrder {} {} {} {} {} {} {}", form.getLoginName(), form.getSenderAddress(),
			form.getSenderPhone(), form.getReceiverAddress(),
			form.getReceiverPhone(), form.getGoods(), form.getWishPrice());

		// log.info("objectName={} {}", bindingResult.getObjectName(), bindingResult.getTarget());

		//검증에 실패하면 다시 입력 폼으로
		if (bindingResult.hasErrors()) {
			log.info("errors={} ", bindingResult);
			model.addAttribute(ORDER_INFO, form);
			return "order/orderDelivery";
		}

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Order order = orderService.insertOrder(form.toCommand());

		model.addAttribute(ORDER_INFO, new OrderView(order));
		return "redirect:/";
	}

	@GetMapping("orderList")
	public String viewOrderList(Model model) {
		List<Order> allOrder = orderService.getAllOrder();
		model.addAttribute("orderLists", allOrder.stream().map(OrderView::new).toList());
		return "order/orderList";
	}

}
