package uplus.hackerton.barogo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uplus.hackerton.barogo.domain.Order;
import uplus.hackerton.barogo.domain.OrderStatus;
import uplus.hackerton.barogo.repository.OrderRepository;
import uplus.hackerton.barogo.service.command.OrderCommand;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	public Order insertOrderToDB(OrderCommand command) {
		return orderRepository.save(command.toEntity());
	}

	public Order insertOrder(OrderCommand command) {
		command.setStatus(OrderStatus.REQUEST);
		Order savedOrder = this.insertOrderToDB(command);
		//		this.insertOrderToCache();
		return savedOrder;
	}

	public List<Order> getAllOrder() {
		return this.getAllOrderFromCache();
	}

	private List<Order> getAllOrderFromCache() {
		return orderRepository.findAll();
	}

	//	private void insertOrderToCache() {
	//		List<Order> all = orderRepository.findAll();
	//		OrderCache testData = new OrderCache(all);
	//		orderRedisRepository.save(testData);
	//	}
}
