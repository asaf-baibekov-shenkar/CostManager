package com.asaf.costmanager.view_models;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.models.Category;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.List;

public class CategoriesViewModel {
	public enum DatabaseEvent {
		RECORD_SAVED,
		RECORD_DELETED
	}
	
	private final IDataAccessObject<Category> categoryDAO;
	
	private final PublishSubject<DatabaseEvent> updateCategoriesSubject;
	
	public CategoriesViewModel(IDataAccessObject<Category> categoryDAO) {
		this.categoryDAO = categoryDAO;
		this.updateCategoriesSubject = PublishSubject.create();
	}
	
	public void saveCategory(String text) {
		if (text == null || text.trim().isEmpty()) return;
		this.categoryDAO.create(new Category(text));
		this.updateCategoriesSubject.onNext(DatabaseEvent.RECORD_SAVED);
	}
	
	public List<Category> getAllCategories() {
		return this.categoryDAO.readAll();
	}
	
	public void deleteCategory(int id) {
		this.categoryDAO.delete(id);
		this.updateCategoriesSubject.onNext(DatabaseEvent.RECORD_DELETED);
	}
	
	public Observable<DatabaseEvent> updateCategoryObservable() {
		return this.updateCategoriesSubject.hide();
	}
}
