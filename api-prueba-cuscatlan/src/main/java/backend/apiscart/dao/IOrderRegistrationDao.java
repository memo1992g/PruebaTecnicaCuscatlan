package backend.apiscart.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.apiscart.model.OrderRegistration;

@Repository
public interface IOrderRegistrationDao extends JpaRepository<OrderRegistration, Long>{
	@Query("SELECT DISTINCT o FROM OrderRegistration o ORDER BY o.id")
    List<OrderRegistration> findAllWithOrderDetails();
	
	@Query("SELECT o FROM OrderRegistration o JOIN FETCH o.orderDetails WHERE o.id = :orderId")
    Optional<OrderRegistration> findByIdWithOrderDetails(@Param("orderId") Long orderId);
	
    Optional<OrderRegistration> findById(Long id);
}
