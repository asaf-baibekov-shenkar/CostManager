package com.asaf.costmanager.view_models;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.models.Category;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.List;

/**
 * ViewModel class for managing the categories.
 */
public class CategoriesViewModel {
	
	/**
	 * Enumeration of possible database events.
	 */
	public enum DatabaseEvent {
		RECORD_SAVED,
		RECORD_DELETED
	}
	
	private final IDataAccessObject<Category> categoryDAO;
	
	private final PublishSubject<DatabaseEvent> updateCategoriesSubject;
	
	/**
	 * Constructor for the CategoriesViewModel.
	 *
	 * @param categoryDAO the Data Access Object for the categories.
	 */
	public CategoriesViewModel(IDataAccessObject<Category> categoryDAO) {
		this.categoryDAO = categoryDAO;
		this.updateCategoriesSubject = PublishSubject.create();
	}
	
	/**
	 * Saves a new category.
	 *
	 * @param text the name of the category.
	 */
	public void saveCategory(String text) {
		if (text == null || text.trim().isEmpty()) return;
		this.categoryDAO.create(new Category(text));
		this.updateCategoriesSubject.onNext(DatabaseEvent.RECORD_SAVED);
	}
	
	/**
	 * Fetches all categories.
	 *
	 * @return a list of all categories.
	 */
	public List<Category> getAllCategories() {
		return this.categoryDAO.readAll();
	}
	
	/**
	 * Deletes a category by its id.
	 *
	 * @param id the id of the category to delete.
	 */
	public void deleteCategory(int id) {
		this.categoryDAO.delete(id);
		this.updateCategoriesSubject.onNext(DatabaseEvent.RECORD_DELETED);
	}
	
	/**
	 * Returns an observable for the category updates.
	 *
	 * @return an observable for the category updates.
	 */
	public Observable<DatabaseEvent> updateCategoryObservable() {
		return this.updateCategoriesSubject.hide();
	}
}
