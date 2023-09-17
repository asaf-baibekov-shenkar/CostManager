package com.asaf.costmanager.view_models;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.models.Cost;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * ViewModel class for managing the costs report.
 */
public class ReportsViewModel {
	
	private final IDataAccessObject<Cost> costsDAO;
	
	private final BehaviorSubject<List<Cost>> costsReportBehaviorSubject;
	
	/**
	 * Constructor for the ReportsViewModel.
	 *
	 * @param costsDAO the Data Access Object for the costs.
	 */
	public ReportsViewModel(IDataAccessObject<Cost> costsDAO) {
		this.costsDAO = costsDAO;
		this.costsReportBehaviorSubject = BehaviorSubject.create();
		this.updateCostsReport(null, null, null);
	}
	
	/**
	 * Returns an observable for the costs report updates.
	 *
	 * @return an observable for the costs report updates.
	 */
	public Observable<List<Cost>> getCostsReportsObservable() {
		return this.costsReportBehaviorSubject.hide();
	}
	
	/**
	 * Updates the costs report and emits the updated report to the observers.
	 *
	 * @param year the year of the costs to include in the report, or null to include costs from any year.
	 * @param month the month of the costs to include in the report, or null to include costs from any month.
	 * @param day the day of the costs to include in the report, or null to include costs from any day.
	 */
	public void updateCostsReport(@Nullable Integer year, @Nullable Integer month, @Nullable Integer day) {
		List<Cost> costsStream = this.costsDAO.readAll()
			.stream()
			.filter((cost) -> {
				Date date = cost.getDate();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				boolean isYearMatch = (year == null) || (calendar.get(Calendar.YEAR) == year);
				boolean isMonthMatch = (month == null) || (calendar.get(Calendar.MONTH) + 1 == month);
				boolean isDayMatch = (day == null) || (calendar.get(Calendar.DAY_OF_MONTH) == day);
				return isYearMatch && isMonthMatch && isDayMatch;
			})
            .sorted(Comparator.comparing(Cost::getDate))
			.toList();
		this.costsReportBehaviorSubject.onNext(costsStream);
	}
}
