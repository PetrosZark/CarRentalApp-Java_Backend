package gr.aueb.cf.carrentalapp.service;

import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.carrentalapp.model.Attachment;
import gr.aueb.cf.carrentalapp.model.Car;
import gr.aueb.cf.carrentalapp.repository.AttachmentRepository;
import gr.aueb.cf.carrentalapp.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private static final String UPLOAD_DIR = System.getProperty("user.home") + "/car_rental_uploads/";
    private final AttachmentRepository attachmentRepository;
    private final CarRepository carRepository;


    /**
     * Handles file upload, storage, and attachment metadata saving.
     * <p>
     * This method uploads a file (car image), saves it to the filesystem,
     * and creates an entry in the database linking the uploaded file to a car.
     * </p>
     *
     * @param carId the ID of the car to which the file will be attached
     * @param file  the uploaded file (image or other types)
     * @return Attachment metadata saved in the database, representing the uploaded file
     * @throws AppObjectInvalidArgumentException if the file is empty or invalid
     * @throws AppObjectNotFoundException if the car with the specified ID is not found
     * @throws AppObjectAlreadyExistsException if an attachment already exists for the car
     */
    @Transactional
    public Attachment saveAttachment(Long carId, MultipartFile file)
            throws AppObjectInvalidArgumentException, AppObjectNotFoundException, AppObjectAlreadyExistsException {

        // Retrieve the car by its ID or throw an exception if not found
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new AppObjectNotFoundException("Car", "Car not found"));

        // Validate the uploaded file - reject if the file is empty
        if (file.isEmpty()) {
            throw new AppObjectInvalidArgumentException("File", "Invalid file or request data");
        }

        // Check if an attachment already exists for the car to prevent duplicate uploads
        if (attachmentRepository.findByCarId(carId).isPresent()) {
            throw new AppObjectAlreadyExistsException("Image", "Conflict - Image already exists");
        }

        try {
            // Ensure the upload directory exists; create it if necessary
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Generate a unique filename (timestamp + original filename)
            String originalFilename = file.getOriginalFilename();
            String finalFilename = (originalFilename != null) ? originalFilename : "unknown_file";
            String savedName = System.currentTimeMillis() + "_" + finalFilename;
            Path filePath = Paths.get(UPLOAD_DIR, savedName);

            // Save the uploaded file to the filesystem
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Create an Attachment object to store file metadata
            Attachment attachment = new Attachment();
            attachment.setFilename(file.getOriginalFilename());
            attachment.setSavedName(savedName);
            attachment.setFilePath(filePath.toString());
            attachment.setContentType(file.getContentType());
            attachment.setExtension(getFileExtension(file.getOriginalFilename()));

            // Link the uploaded image to the car and save to the database
            car.setImage(attachment);

            return attachmentRepository.save(attachment);
        } catch (IOException e) {
            // Handle file I/O errors by throwing a RuntimeException
            throw new RuntimeException("Failed to save file: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes an image attachment by car ID.
     *
     * @param carId the ID of the car whose image is to be deleted
     * @throws AppObjectNotFoundException if the car or attachment is not found
     * @throws IOException if file deletion fails
     */
    @Transactional
    public void deleteAttachment(Long carId) throws AppObjectNotFoundException, IOException {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new AppObjectNotFoundException("Car", "Car not found"));

        Attachment attachment = car.getImage();

        if (attachment == null) {
            throw new AppObjectNotFoundException("Attachment", "No image found for this car");
        }

        // Delete the file from the filesystem
        Path filePath = Paths.get(attachment.getFilePath());
        if (Files.exists(filePath)) {
            Files.delete(filePath);
        }

        // Remove the attachment from the car and database
        car.setImage(null);

        // Save the car without the image
        carRepository.save(car);

        attachmentRepository.delete(attachment);  // Remove the attachment from the database
    }

    /**
     * Helper method to extract the file extension.
     *
     * @param filename the name of the file
     * @return the file extension (e.g., jpg, png, pdf)
     */
    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf('.') + 1);
    }
}

