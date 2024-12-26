package gr.aueb.cf.carrentalapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing an attachment, such as an image or document, linked to a car.
 * <p>
 * This entity stores metadata about uploaded files, including their paths and content types.
 * Each attachment is uniquely associated with a single car.
 * </p>
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attachments")
public class Attachment extends AbstractEntity{

    /**
     * Unique identifier for the attachment.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Original name of the uploaded file.
     */
    private String filename;

    /**
     * The name under which the file is stored (includes a timestamp to ensure uniqueness).
     */
    private String savedName;

    /**
     * Absolute path where the file is stored on the server.
     */
    private String filePath;

    /**
     * MIME type of the uploaded file (e.g., image/jpeg, application/pdf).
     */
    private String contentType;

    /**
     * File extension (e.g., jpg, png, pdf) extracted from the original filename.
     */
    private String extension;

    /**
     * One-to-one relationship with the Car entity.
     * <p>
     * This maps the attachment to a specific car. The car's `image` field holds the relationship.
     * Cascade and orphan removal are not specified to ensure control over file deletions.
     * </p>
     */
    @OneToOne(mappedBy = "image")
    private Car car;
}