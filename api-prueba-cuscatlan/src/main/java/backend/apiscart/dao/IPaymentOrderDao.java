package backend.apiscart.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.apiscart.model.PaymentOrder;

@Repository
public interface IPaymentOrderDao extends JpaRepository<PaymentOrder, Long>{

	@Query("SELECT p FROM PaymentOrder p WHERE p.id = :id")
	Optional<PaymentOrder> findByPayment(@Param("id") Long id);
}
