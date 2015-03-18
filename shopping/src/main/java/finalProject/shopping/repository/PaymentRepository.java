package finalProject.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import finalProject.shopping.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

}
