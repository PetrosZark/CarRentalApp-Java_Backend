package gr.aueb.cf.carrentalapp.service;

import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.carrentalapp.model.Attachment;
import gr.aueb.cf.carrentalapp.model.Car;
import gr.aueb.cf.carrentalapp.model.User;
import gr.aueb.cf.carrentalapp.repository.AttachmentRepository;
import gr.aueb.cf.carrentalapp.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private static final String UPLOAD_DIR = "uploads/";
    private final AttachmentRepository attachmentRepository;
    private final CarRepository carRepository;


    /**
     * Handles the file upload and metadata saving.
     *
     * @param file the uploaded file (image, document, etc.)
     * @return Attachment metadata saved in the database
     */
    public Attachment saveAttachment(User loggedInUser, Long carId, MultipartFile file)
            throws AppObjectInvalidArgumentException, AppObjectNotFoundException, AppObjectAlreadyExistsException {

        Car car = carRepository.findByIdAndUser(carId, loggedInUser)
                .orElseThrow(() -> new AppObjectNotFoundException("Car","Car not found"));

        if (file.isEmpty()) {
            throw new AppObjectInvalidArgumentException("File", "File is empty. Please select a valid file.");
        }

        if (attachmentRepository.findByCarId(carId).isPresent()) {
            throw new AppObjectAlreadyExistsException("Image", "Image already exists");
        }

        try {
            // Ensure upload directory exists
            Files.createDirectories(Paths.get(UPLOAD_DIR));

            // Save file with a unique name (timestamp + original name), handle null filename case
            String originalFilename = file.getOriginalFilename();
            String finalFilename = (originalFilename != null) ? originalFilename : "unknown_file";
            String savedName = System.currentTimeMillis() + "_" + finalFilename;
            Path filePath = Paths.get(UPLOAD_DIR + savedName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Create and save Attachment metadata
            Attachment attachment = new Attachment();
            attachment.setFilename(file.getOriginalFilename());
            attachment.setSavedName(savedName);
            attachment.setFilePath(filePath.toString());
            attachment.setContentType(file.getContentType());
            attachment.setExtension(getFileExtension(file.getOriginalFilename()));

            // Link image to car
            car.setImage(attachment);
            attachmentRepository.save(attachment);
            carRepository.save(car);

            return attachment;
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file: " + e.getMessage(), e);
        }
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

