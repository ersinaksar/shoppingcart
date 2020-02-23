package cart;

import java.util.ArrayList;
import java.util.List;

public class Category {

    private String title;
    private Category parentCategory;

    /**
     * Constructor is private because of builder pattern
     */
    private Category() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    /**
     * use to get title and parents title
     *
     * @return List<String> consist of current category title and parent categories
     */
    public List<String> getTitleAndParentsTitles() {
        List<String> categories = new ArrayList<>();
        Category category = this;
        while (null != category) {
            categories.add(category.getTitle());
            category = category.parentCategory;
        }
        return categories;
    }

    /**
     * Builder Pattern
     */
    public static class Builder {
        private String title;
        private Category parentCategory;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder parentCategory(Category parentCategory) {
            this.parentCategory = parentCategory;
            return this;
        }

        public Category build() {
            Category category = new Category();
            category.setTitle(this.title);
            category.setParentCategory(this.parentCategory);
            return category;
        }
    }
}
