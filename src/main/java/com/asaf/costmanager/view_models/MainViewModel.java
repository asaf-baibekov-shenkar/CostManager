package com.asaf.costmanager.view_models;

import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.Locale;

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
	
	public MainViewModel(@Nullable Locale locale) {
		this.locale = locale;
		this.navigationTypeBehaviorSubject = BehaviorSubject.createDefault(NavigationType.Reports);
		if (locale != null)
			Locale.setDefault(locale);
	}
	
	public @Nullable Locale getLocale() {
		return locale;
	}
	
	public Observable<NavigationType> getNavigationTypeObservable() {
		if (this.navigationTypeObservable == null)
			this.navigationTypeObservable = this.navigationTypeBehaviorSubject.hide();
		return this.navigationTypeObservable;
	}
	
	public void reportNavigationSelected() {
		this.navigationTypeBehaviorSubject.onNext(NavigationType.Reports);
	}
	
	public void costsNavigationSelected() {
		this.navigationTypeBehaviorSubject.onNext(NavigationType.Costs);
	}
	
	public void categoriesNavigationSelected() {
		this.navigationTypeBehaviorSubject.onNext(NavigationType.Categories);
	}
}
