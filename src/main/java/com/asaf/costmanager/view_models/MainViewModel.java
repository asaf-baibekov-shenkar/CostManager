package com.asaf.costmanager.view_models;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.Locale;

/**
 * ViewModel class for managing the main navigation in the application.
 */
public class MainViewModel {
	public enum NavigationType {
		Reports,
		Costs,
		Categories
	}
	
	@Nullable
	private final Locale locale;
	
	private final BehaviorSubject<NavigationType> navigationTypeBehaviorSubject;
	private Observable<NavigationType> navigationTypeObservable;
	
	/**
	 * Constructor for the MainViewModel.
	 *
	 * @param locale the locale for the application, or null to use the default locale.
	 */
	public MainViewModel(@Nullable Locale locale) {
		this.locale = locale;
		this.navigationTypeBehaviorSubject = BehaviorSubject.createDefault(NavigationType.Reports);
		if (locale != null)
			Locale.setDefault(locale);
	}
	
	/**
	 * Returns the locale for the application.
	 *
	 * @return the locale for the application, or null if the default locale is used.
	 */
	public @Nullable Locale getLocale() {
		return locale;
	}
	
	/**
	 * Returns an observable for the navigation type updates.
	 *
	 * @return an observable for the navigation type updates.
	 */
	public Observable<NavigationType> getNavigationTypeObservable() {
		if (this.navigationTypeObservable == null)
			this.navigationTypeObservable = this.navigationTypeBehaviorSubject.hide();
		return this.navigationTypeObservable;
	}
	
	/**
	 * Selects the reports navigation and emits the updated navigation type to the observers.
	 */
	public void reportNavigationSelected() {
		this.navigationTypeBehaviorSubject.onNext(NavigationType.Reports);
	}
	
	/**
	 * Selects the costs navigation and emits the updated navigation type to the observers.
	 */
	public void costsNavigationSelected() {
		this.navigationTypeBehaviorSubject.onNext(NavigationType.Costs);
	}
	
	/**
	 * Selects the categories navigation and emits the updated navigation type to the observers.
	 */
	public void categoriesNavigationSelected() {
		this.navigationTypeBehaviorSubject.onNext(NavigationType.Categories);
	}
}
