package uplus.hackerton.barogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uplus.hackerton.barogo.domain.Deliver;
import uplus.hackerton.barogo.domain.DeliveryRequest;

import java.util.List;

public interface DeliveryRequestRepository extends JpaRepository<DeliveryRequest, Long> {

	List<DeliveryRequest> findByDeliverOrderByBidPriceDesc(Deliver deliver);

	@Query("select dr from DeliveryRequest dr join fetch dr.deliver" +
		" where dr.deliverUser.id = :deliverUserId")
	List<DeliveryRequest> findByDeliverUserId(Long deliverUserId);

	int countByDeliver(Deliver deliver);

	List<DeliveryRequest> findDeliveryRequestByDeliverAndIdNot(Deliver deliver, Long id);

	// @Query("select count(*) from DeliveryRequest d where d.deliver = :deliver")
	// int findbyDeliver(Deliver deliver);
}
