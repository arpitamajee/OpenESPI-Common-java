package org.energyos.espi.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.energyos.espi.common.domain.AtomPeriod;
import org.energyos.espi.common.models.atom.DateTimeType;
import org.energyos.espi.common.models.atom.EntryType;

@SuppressWarnings("restriction")
public class ExportFilter {
	private Map<String, String> params;
	private int matchedCounter = 0, emittedCounter = 0;
	private AtomPeriod filterPeriod = null;

	public AtomPeriod getFilterPeriod() {
		return filterPeriod;
	}

	public void setFilterPeriod(AtomPeriod filterPeriod) {
		this.filterPeriod = filterPeriod;
	}

	public ExportFilter(Map<String, String> params) {
		this.params = params;
		// update the filter period
		filterPeriod = new AtomPeriod();
		if (hasParam("published-min")) {
			filterPeriod.getPublishedMin().setTimeInMillis(
					toTime("published-min"));
		}
		if (hasParam("published-max")) {
			filterPeriod.getPublishedMax().setTimeInMillis(
					toTime("published-max"));
		}
		if (hasParam("updated-min")) {
			filterPeriod.getUpdatedMin().setTimeInMillis(toTime("updated-min"));
		}
		if (hasParam("updated-max")) {
			filterPeriod.getUpdatedMax().setTimeInMillis(toTime("updated-max"));
		}
		if (hasParam("usage-min")) {
			try {
				filterPeriod
						.setUsageMin(Long.parseLong(params.get("usage-min")));
			} catch (Exception ignore) {

			}
		}
		if (hasParam("usage-max")) {
			try {
				filterPeriod
						.setUsageMax(Long.parseLong(params.get("usage-max")));
			} catch (Exception ignore) {

			}
		}
	}

	public boolean matches(EntryType entry) {

		if (hasParam("max-results")) {
			if (!(params.get("max-results").equals("All"))) {
				if (emittedCounter >= Integer
						.valueOf(params.get("max-results"))) {
					return false;
				}
			}
		}
		if (hasParam("published-max")) {
			if (!(params.get("published-max").equals("All"))) {
				if (toTime("published-max") < toTime(entry.getPublished())) {
					return false;
				}
			}
		}
		if (hasParam("published-min")) {
			if (!(params.get("published-min").equals("All"))) {
				if (toTime("published-min") > toTime(entry.getPublished())) {
					return false;
				}
			}
		}

		if (hasParam("updated-max")) {
			if (!(params.get("updated-max").equals("All"))) {
				if (toTime("updated-max") < toTime(entry.getUpdated())) {
					return false;
				}
			}
		}

		if (hasParam("updated-min")) {
			if (!(params.get("updated-min").equals("All"))) {
				if (toTime("updated-min") > toTime(entry.getUpdated())) {
					return false;
				}
			}
		}

		if (hasParam("start-index")) {
			if (++matchedCounter < Integer.valueOf(params.get("start-index"))) {
				return false;

			}
		}

		if (hasParam("depth")) {
			if (!(params.get("depth").equals("All"))) {
				if (emittedCounter > Integer.valueOf(params.get("depth"))) {
					return false;
				}
			}
		}
		emittedCounter++;
		return true;
	}

	private boolean hasParam(String paramName) {
		return params.get(paramName) != null;
	}

	private SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

	private long toTime(String key) {
		String param = params.get(key);
		// return XMLGregorianCalendarImpl.parse(param).toGregorianCalendar()
		// .getTimeInMillis();
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = null;
		try {
			date = sdf.parse(param);
		} catch (Exception ignore) {
			date = new Date();
		}
		return date.getTime();
	}

	private long toTime(DateTimeType published) {
		return published.getValue().toGregorianCalendar().getTimeInMillis();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		ExportFilter that = (ExportFilter) o;

		return params.equals(that.params);

	}

	@Override
	public int hashCode() {
		return params.hashCode();
	}
}
