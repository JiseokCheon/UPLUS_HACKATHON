package uplus.hackerton.barogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import uplus.hackerton.barogo.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
