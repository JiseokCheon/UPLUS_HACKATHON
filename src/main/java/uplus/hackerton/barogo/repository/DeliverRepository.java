package uplus.hackerton.barogo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uplus.hackerton.barogo.domain.Deliver;
import uplus.hackerton.barogo.domain.DeliveryStatus;

import java.util.List;

public interface DeliverRepository extends JpaRepository<Deliver, Long> {

    @Query(value = "select new uplus.hackerton.barogo.repository.DeliverDto(d.id, ru.name, ru.photo, d.start, d.end, " +
            "d.product, d.content, d.dueDate, d.price, d.distance, d.requestUserPhone, d.recipientUserPhone, d.deliveryStatus, d.weight)  " +
            "from Deliver d join d.requestUser ru " +
            "where (d.deliveryStatus = 'REQUEST' or d.deliveryStatus = 'BID')" +
            "and d.start like :start and d.end like :end " +
            "order by d.id desc")
    Page<DeliverDto> findDeliver(Pageable pageable, @Param("start") String start, @Param("end") String end);

    List<Deliver> findTop5ByDeliveryStatusOrderById(DeliveryStatus deliveryStatus);

    @Query("select d from Deliver d join fetch d.deliverUser " +
            "where d.deliverUser.id = :userId")
    List<Deliver> findByDeliverUserId(Long userId);

    @Query("select d from Deliver d join fetch d.requestUser " +
            "where d.requestUser.id = :userId")
    List<Deliver> findByRequestUserId(Long userId);

}
