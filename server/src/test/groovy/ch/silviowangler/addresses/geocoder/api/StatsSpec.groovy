package ch.silviowangler.addresses.geocoder.api

import spock.lang.Specification
import spock.lang.Unroll

/**
 * @author Silvio Wangler
 */
class StatsSpec extends Specification {

	@Unroll
	void "#processed of #total results in #percent %"() {

		given:
		Stats stats = new Stats(totalRecords: total, processed: processed, processedNoGeocodes: processedNoGeocodes)

		expect:
		stats.progress == percent

		where:
		processedNoGeocodes | processed | total   || percent
		0                   | 50        | 100     || 50.0d
		25                  | 25        | 100     || 50.0d
		0                   | 178036    | 1796122 || 9.912244268485102d
	}
}
