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
		Stats stats = new Stats(totalRecords: total, processed: processed)

		expect:
		stats.progress == percent

		where:
		processed | total   || percent
		50        | 100     || 50.0d
		178036    | 1796122 || 9.912244268485102d
	}
}
