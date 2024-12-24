package gr.aueb.cf.carrentalapp.core.filters;

import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Abstract class representing common filter parameters for paginated and sorted queries.
 * Provides default values and utility methods to handle sorting, paging, and direction.
 */
@Getter
@Setter
public abstract class GenericFilters {

    /**
     * Default number of results per page if not specified.
     */
    private final static int DEFAULT_PAGE_SIZE = 10;

    /**
     * Default column used for sorting if not specified.
     */
    private static final String DEFAULT_SORT_COLUMN = "id";

    /**
     * Default sorting direction (ascending).
     */
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

    /**
     * Current page number for pagination (zero-based).
     */
    private int page;

    /**
     * Number of results per page.
     */
    private int pageSize;

    /**
     * Sorting direction (ASC or DESC).
     */
    private Sort.Direction sortDirection;

    /**
     * Field by which to sort results.
     */
    private String sortBy;

    /**
     * Retrieves the page size, applying a default if the specified value is invalid.
     *
     * @return The page size or the default if the value is non-positive.
     */
    public int getPageSize() {
        return pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * Retrieves the current page, ensuring it is never negative.
     *
     * @return The current page or 0 if negative.
     */
    public int getPage() {
        return Math.max(page, 0);
    }

    /**
     * Retrieves the sorting direction, applying a default if not specified.
     *
     * @return The sorting direction (ASC by default).
     */
    public Sort.Direction getSortDirection(){
        if (this.sortDirection == null) return DEFAULT_SORT_DIRECTION;
        return this.sortDirection;
    }

    /**
     * Retrieves the field by which to sort, applying a default if not specified or blank.
     *
     * @return The sorting field or the default column if unspecified.
     */
    public String getSortBy(){
        if (this.sortBy == null || StringUtils.isBlank(this.sortBy)) return DEFAULT_SORT_COLUMN;
        return this.sortBy;
    }

    /**
     * Constructs and returns a {@link Pageable} object for pagination and sorting.
     *
     * @return A pageable object with the current page, page size, and sorting details.
     */
    public Pageable getPageable(){
        return PageRequest.of(getPage(), getPageSize(), getSort());
    }

    /**
     * Constructs and returns a {@link Sort} object based on the specified direction and sorting field.
     *
     * @return A Sort object with direction and field.
     */
    public Sort getSort(){
        return Sort.by(this.getSortDirection(), this.getSortBy());
    }
}
