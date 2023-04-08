package uplus.hackerton.barogo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uplus.hackerton.barogo.controller.form.DeliverySearchForm;
import uplus.hackerton.barogo.domain.Deliver;
import uplus.hackerton.barogo.repository.DeliverDto;
import uplus.hackerton.barogo.service.DeliverService;
import uplus.hackerton.barogo.service.DeliveryRequestService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {

    private final DeliverService deliverService;
    private final DeliveryRequestService deliveryRequestService;

    @GetMapping
    public String viewHome(@ModelAttribute DeliverySearchForm form , @PageableDefault(size = 7) Pageable pageable, Model model) {
        Page<DeliverDto> deliverList = deliverService.findDeliverAll(pageable, form);
        List<Deliver> top5DeliveryCompleteList = deliverService.findTop5ByDeliveryStatusCompleteOrderById();


        int startPage = Math.max(deliverList.getPageable().getPageNumber() - 2, 1);
        int endPage = Math.min(deliverList.getPageable().getPageNumber() + 2, deliverList.getTotalPages());


        model.addAttribute("top5DeliveryCompleteList", top5DeliveryCompleteList);
        model.addAttribute("searchForm", form);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("deliverList", deliverList);
        return "index";
    }


    @GetMapping("delivery/{id}")
    public String viewDeliveryDetail(@PathVariable Long id, Model model) {
        Deliver deliverById = deliverService.findDeliverById(id);
        int countDeliveryRequest = deliveryRequestService.getCountDeliveryRequest(deliverById);
        model.addAttribute("deliverInfo", deliverById);
        model.addAttribute("deliverRequestCnt", countDeliveryRequest);
        return "delivery_detail";
    }

    @GetMapping("positionTest")
    public String positionTest() {
        return "map/positionTest";
    }

    @GetMapping("searchPosition")
    public String searchPosition() {
        return "map/searchPosition";
    }

    @GetMapping("addressSearch")
    public String addressSearch() {
        return "map/addressSearch";
    }
}
