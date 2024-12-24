package gr.aueb.cf.carrentalapp.core.filters;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Generic class for handling paginated responses.
 * This class wraps paginated data and metadata to facilitate
 * structured API responses for list endpoints.
 *
 * @param <T> The type of data contained in the paginated response.
 */
@Getter
@Setter
public class Paginated<T> {

    /**
     * List of data items for the current page.
     */
    List<T> data;

    /**
     * Total number of elements across all pages.
     */
    long totalElements;

    /**
     * Total number of available pages.
     */
    int totalPages;

    /**
     * Number of elements on the current page.
     */
    int numberOfElements;

    /**
     * The current page number (zero-based).
     */
    int currentPage;

    /**
     * The size of each page (number of items per page).
     */
    int pageSize;

    /**
     * Constructs a Paginated object from a Spring Data {@link Page}.
     * This constructor automatically maps page metadata to the Paginated class fields.
     *
     * @param page The {@link Page} object containing data and metadata.
     */
    public Paginated(Page<T> page) {
        this.data = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.numberOfElements = page.getNumberOfElements();
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
    }
}
