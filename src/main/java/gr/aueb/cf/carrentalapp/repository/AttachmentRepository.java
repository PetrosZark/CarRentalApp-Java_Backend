package gr.aueb.cf.carrentalapp.repository;

import gr.aueb.cf.carrentalapp.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Attachment entities.
 * Extends JpaRepository to provide basic CRUD operations.
 */
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    Optional<Attachment> findByCarId(Long id);
}
