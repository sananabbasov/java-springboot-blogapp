package az.compar.blog.controllers;

import az.compar.blog.payloads.ApiResponse;
import az.compar.blog.payloads.CategoryDto;
import az.compar.blog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto)
    {
        CategoryDto category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable("categoryId") Integer uid)
    {
        CategoryDto category = categoryService.updateCategory(categoryDto,uid);
        return new ResponseEntity<CategoryDto>(category, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer uid)
    {
        categoryService.deleteCategory(uid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
    }

    @GetMapping("/get/{categoryId}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable("categoryId") Integer uid)
    {
        CategoryDto category = categoryService.getCategory(uid);
        return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getCategories()
    {
        List<CategoryDto> category = categoryService.getCategories();
        return ResponseEntity.ok(category);
    }

}
