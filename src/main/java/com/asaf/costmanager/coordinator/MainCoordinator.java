package com.asaf.costmanager.coordinator;

import com.asaf.costmanager.view_models.MainViewModel;
import com.asaf.costmanager.views.MainView;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainCoordinator implements Coordinator {
	
	private final CompositeDisposable compositeDisposable;
	private final ArrayList<Coordinator> coordinators;
	
	public MainCoordinator() {
		this.coordinators = new ArrayList<>();
		this.compositeDisposable = new CompositeDisposable();
	}
	
	@Override
	public List<Coordinator> getCoordinators() {
		return this.coordinators;
	}
	
	@Override
	public void start() {
//		Locale locale = Locale.forLanguageTag("he-IL");
		Locale locale = null;
		MainViewModel mainViewModel = new MainViewModel(locale);
		this.compositeDisposable.add(
			mainViewModel
				.getNavigationTypeObservable()
				.subscribe(System.out::println)
		);
		SwingUtilities.invokeLater(() -> new MainView(mainViewModel));
	}
}
