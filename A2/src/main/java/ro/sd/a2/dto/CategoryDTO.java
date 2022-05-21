package ro.sd.a2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import ro.sd.a2.entity.Category;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class CategoryDTO {

    private String id;

    private String name;

    private String description;

    public Category toEntity() {

        String id_category = id;

        if (ObjectUtils.isEmpty(id))
            id_category = UUID.randomUUID().toString();

        Category category = Category.builder()
                .id(id_category)
                .name(name)
                .description(description)
                .deleted(false)
                .build();

        return category;
    }

    public static CategoryDTO fromEntity(Category category) {

        CategoryDTO categoryDTO = CategoryDTO.builder()
                                            .id(category.getId())
                                            .name(category.getName())
                                            .description(category.getDescription())
                                            .build();

        return categoryDTO;
    }
}
