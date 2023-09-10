package com.asaf.costmanager.view_models;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

public class MainViewModel {
	public enum NavigationType {
		Reports,
		Costs,
		Categories
	}
	
	private final BehaviorSubject<NavigationType> navigationTypeBehaviorSubject;
	private Observable<NavigationType> navigationTypeObservable;
	
	public MainViewModel() {
		this.navigationTypeBehaviorSubject = BehaviorSubject.createDefault(NavigationType.Reports);
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
